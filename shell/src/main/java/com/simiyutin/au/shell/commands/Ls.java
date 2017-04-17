package com.simiyutin.au.shell.commands;


import com.simiyutin.au.shell.core.Command;
import com.simiyutin.au.shell.core.Environment;
import com.simiyutin.au.shell.core.Stream;
import com.simiyutin.au.shell.core.exceptions.CommandExecutionException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Prints the contents of the directory specified in argument.
 * If no argument is provided prints the contents of current working directory.
 */

public class Ls extends Command{
    public static final String NAME = "ls";

    public Ls(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream stream) throws CommandExecutionException {
        Path userDir = Paths.get(env.get("user.dir"));
        Stream outStream = new Stream();

        if (!args.isEmpty()) {
            String lsDirName = args.get(0);
            lsDirName = lsDirName.replaceFirst("^~", System.getProperty("user.home"));
            userDir = userDir.resolve(lsDirName);
        }

        File lsDir = userDir.toFile();

        if (!lsDir.isDirectory()) {
            throw new CommandExecutionException("The path you specified is not a valid directory.");
        }

        for (File file : lsDir.listFiles()) {
            if (!file.getName().startsWith(".")) {
                outStream.write(file.getName());
            }
        }

        return outStream;
    }
}
