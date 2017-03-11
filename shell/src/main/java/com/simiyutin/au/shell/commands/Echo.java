package com.simiyutin.au.shell.commands;

import com.simiyutin.au.shell.core.exceptions.CommandExecutionException;
import com.simiyutin.au.shell.core.Command;
import com.simiyutin.au.shell.core.Environment;
import com.simiyutin.au.shell.core.Stream;

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
    public Stream run(Stream ignored) throws CommandExecutionException {

        String toPrint = args.stream().collect(Collectors.joining());

        Stream stream = new Stream();
        stream.write(toPrint);
        return stream;
    }

}
