package shell;

import java.util.List;

/**
 * Abstract class which impersonates Command entity.
 * Command can be run with Stream as an argument and can modify environment of a shell.
 */
public abstract class Command {

    protected List<String> args;
    protected Environment env;


    /**
     * @param args arguments of a command as a List
     * @param env environment of current running shell
     */
    public Command(List<String> args, Environment env) {
        this.args = args;
        this.env = env;
    }

    /**
     * @param stream
     * @return result data as a Stream object
     */
    public abstract Stream run(Stream stream);
}
