package com.simiyutin.au.shell.commands;


import com.simiyutin.au.shell.core.Command;
import com.simiyutin.au.shell.core.Environment;
import com.simiyutin.au.shell.core.Stream;
import com.simiyutin.au.shell.core.exceptions.CommandExecutionException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Changes current directory to specified in argument. If no argument is provided changes current directory to HOME.
 */

public class Cd extends Command {
    public static final String NAME = "cd";

    public Cd(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream stream) throws CommandExecutionException {
        String changeDirTo;
        Path userDir = Paths.get(env.get("user.dir"));

        if (args.isEmpty()) {
            changeDirTo = System.getProperty("user.home");
        } else {
            changeDirTo = args.get(0);
            changeDirTo = changeDirTo.replaceFirst("^~", System.getProperty("user.home"));
        }

        Path targetDir = userDir.resolve(changeDirTo);
        if (targetDir.toFile().isDirectory()) {
            env.put("user.dir", targetDir.normalize().toAbsolutePath().toString());
        } else {
            throw new CommandExecutionException("The path you specified is not a valid directory.");
        }

        return stream;
    }
}
