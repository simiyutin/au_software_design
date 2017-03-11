package com.simiyutin.au.shell.commands;

import com.simiyutin.au.shell.core.exceptions.CommandExecutionException;
import com.simiyutin.au.shell.core.Command;
import com.simiyutin.au.shell.core.Environment;
import com.simiyutin.au.shell.core.Stream;

import java.util.List;

/**
 * Exits shell
 */
public class Exit extends Command {

    public static final String NAME = "exit";

    public Exit(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream stream) throws CommandExecutionException {
        System.exit(0);
        return null;
    }
}
