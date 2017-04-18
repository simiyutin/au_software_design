package com.simiyutin.au.shell.commands;

import com.simiyutin.au.shell.core.exceptions.CommandExecutionException;
import com.simiyutin.au.shell.core.Command;
import com.simiyutin.au.shell.core.Environment;
import com.simiyutin.au.shell.core.Stream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Prints current directory
 */
public class Pwd extends Command {

    public static final String NAME = "pwd";

    public Pwd(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream ignored) throws CommandExecutionException {
        String currentDir = env.get("user.dir");
        Stream stream = new Stream();
        stream.write(currentDir);
        return stream;
    }
}
