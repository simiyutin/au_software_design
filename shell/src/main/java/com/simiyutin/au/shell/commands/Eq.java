package com.simiyutin.au.shell.commands;

import com.simiyutin.au.shell.core.exceptions.CommandExecutionException;
import com.simiyutin.au.shell.core.Command;
import com.simiyutin.au.shell.core.Environment;
import com.simiyutin.au.shell.core.Stream;

import java.util.List;

/**
 * Puts new variable in context or changes variable value
 */
public class Eq extends Command {

    public Eq(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream ignored) throws CommandExecutionException {
        String var = args.get(0);
        String val = args.get(1);
        env.put(var, val);
        return new Stream();
    }
}
