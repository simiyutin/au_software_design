package shell;

import java.util.List;


/**
 * Entity to implement chained computations.
 * Every computation is processed one after another
 * and result of current one is passed to the next one.
 */
public class CommandExecutor {
    private CommandExecutor() {}

    /**
     * @param commands List of Command objects
     * @return Stream object which contain result of a chain of computations
     */
    public static Stream run(List<Command> commands) {
        Stream stream = new Stream();
        for (Command command : commands) {
            stream = command.run(stream);
        }
        return stream;
    }
}
