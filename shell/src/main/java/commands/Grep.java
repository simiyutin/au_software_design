package commands;

import exceptions.CommandExecutionException;
import org.apache.commons.cli.*;
import shell.Command;
import shell.Environment;
import shell.Stream;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep extends Command {

    public static final String NAME = "grep";

    private boolean isInsensitive = false;
    private boolean isFullWord = false;
    private int linesAfterMatch = 0;

    public Grep(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream stream) throws CommandExecutionException {

        Options options = new Options();
        options.addOption("i", false, "case insensitivity");
        options.addOption("w", false, "full word search");
        options.addOption("A", true, "number of lines to append after match");
        CommandLine cmd = parseArgs(options);

        isInsensitive = cmd.hasOption('i');
        isFullWord = cmd.hasOption('w');
        try {
            String a = cmd.getOptionValue('A');
            linesAfterMatch = a == null ? 0 : Integer.valueOf(a);
            return grep(stream, cmd.getArgs());
        } catch (NumberFormatException e) {
            throw new CommandExecutionException("grep: incorrect '-A' value, required number");
        }
    }

    private Stream grep(Stream stream, String[] args) throws CommandExecutionException {

        if (args.length == 0) {
            throw new CommandExecutionException("grep: pattern must be provided");
        } else if (args.length > 1) {
            String pattern = args[0];
            String fileName = args[1];
            Stream fileStream = new Cat(Collections.singletonList(fileName), new Environment()).run(new Stream());
            return grepFromStream(pattern, fileStream);
        } else {
            String pattern = args[0];
            return grepFromStream(pattern, stream);
        }
    }

    private Stream grepFromStream(String regex, Stream input) {

        Stream output = new Stream();

        if (isFullWord) {
            regex = String.format("\\b%s\\b", regex);
        }

        if (isInsensitive) {
            regex = "(?i)" + regex;
        }

        Pattern pattern = Pattern.compile("(" + regex + ")");

        while (input.hasNext()) {
            String line = input.read();
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                String replaced = m.replaceAll(colorize("$1"));
                output.write(replaced);
                appendN(input, output, linesAfterMatch);
            }
        }

        return output;
    }

    private void appendN(Stream input, Stream output, int n) {
        for (int i = 0; i < n; i++) {
            if (input.hasNext()) {
                output.write(input.read());
            } else {
                break;
            }
        }
    }

    private String colorize(String string) {
        final int esc = 27;
        final String csi = (char) esc + "[";
        final String redModifier = "31m";
        final String resetModifier = "0m";
        return csi + redModifier + string + csi + resetModifier;
    }

    private CommandLine parseArgs(Options options) throws CommandExecutionException {
        CommandLineParser parser = new PosixParser();
        try {
            String[] arr = new String[0];
            CommandLine cmd = parser.parse(options, args.toArray(arr));
            return cmd;
        } catch (ParseException e) {
            throw new CommandExecutionException("grep: wrong arguments");
        }
    }
}
