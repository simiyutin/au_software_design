package commands;

import shell.Command;
import shell.Environment;
import shell.Stream;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Prints arguments to output stream
 */
public class Echo extends Command {

    public static final String NAME = "echo";

    public Echo(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream ignored) {

        String toPrint = args.stream().collect(Collectors.joining());

        Stream stream = new Stream();
        stream.write(toPrint);
        return stream;
    }

}
