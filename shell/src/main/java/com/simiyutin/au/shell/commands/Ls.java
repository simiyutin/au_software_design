package com.simiyutin.au.shell.commands;


import com.simiyutin.au.shell.core.Command;
import com.simiyutin.au.shell.core.Environment;
import com.simiyutin.au.shell.core.Stream;
import com.simiyutin.au.shell.core.exceptions.CommandExecutionException;

import java.io.File;
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
        File currentDir;
        Stream outStream = new Stream();

        if (args.isEmpty()) {
            currentDir = new File(env.get("user.dir"));
        } else {
            String curDirName = args.get(0);
            curDirName = curDirName.replaceFirst("^~", System.getProperty("user.home"));
            currentDir = new File(curDirName);
        }

        if (!currentDir.isDirectory()) {
            throw new CommandExecutionException("The path you specified is not a valid directory.");
        }

        for (File file : currentDir.listFiles()) {
            if (!file.getName().startsWith(".")) {
                outStream.write(file.getName());
            }
        }

        return outStream;
    }
}
