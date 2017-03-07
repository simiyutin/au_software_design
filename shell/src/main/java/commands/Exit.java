package commands;

import exceptions.CommandExecutionException;
import shell.Command;
import shell.Environment;
import shell.Stream;

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
