package commands;

import exceptions.CommandExecutionException;
import shell.Command;
import shell.Environment;
import shell.Stream;

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
        Path currentRelativePath = Paths.get("");
        String currentDir = currentRelativePath.toAbsolutePath().toString();
        Stream stream = new Stream();
        stream.write(currentDir);
        return stream;
    }
}
