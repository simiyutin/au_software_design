package com.simiyutin.au.shell.commands;

import com.simiyutin.au.shell.core.exceptions.CommandExecutionException;
import com.simiyutin.au.shell.core.Command;
import com.simiyutin.au.shell.core.Environment;
import com.simiyutin.au.shell.core.Stream;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Passes input arguments and stream to external shell and reads result from its stdout
 */
public class OutSource extends Command {

    private String commandName;

    public OutSource(String commandName, List<String> args, Environment env) {
        super(args, env);
        this.commandName = commandName;
    }

    @Override
    public Stream run(Stream stream) throws CommandExecutionException {
        String command = commandName + " " + args.stream().collect(Collectors.joining(" "));
        Stream output = new Stream();
        try {
            Process process = Runtime.getRuntime().exec(command);
            write(process, stream);
            output = read(process);
        } catch (IOException | InterruptedException e) {
            throw new CommandExecutionException("System error: " + e.toString());
        }
        return output;
    }

    private void write(Process process, Stream stream) throws IOException {
        Writer handle = new PrintWriter(process.getOutputStream());
        final int lineFeed = 10;
        while (stream.hasNext()) {
            handle.write(stream.read());
            handle.write(lineFeed);
        }
        handle.close();
    }

    private Stream read(Process process) throws IOException, InterruptedException {
        Stream output = new Stream();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            output.write(line);
        }
        process.waitFor();
        return output;
    }
}
