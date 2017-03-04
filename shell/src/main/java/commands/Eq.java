package commands;

import shell.Command;
import shell.Environment;
import shell.Stream;

import java.util.List;

public class Eq extends Command {

    public Eq(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream ignored) {
        String var = args.get(0);
        String val = args.get(1);
        env.put(var, val);
        return new Stream();
    }
}
