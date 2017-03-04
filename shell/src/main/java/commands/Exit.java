package commands;

import shell.Command;
import shell.Environment;
import shell.Stream;

import java.util.List;

public class Exit extends Command {

    public static final String NAME = "exit";

    public Exit(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream stream) {
        System.exit(0);
        return null;
    }
}
