package shell;

import java.util.List;
import java.util.Scanner;


/**
 * Main entity. Reads from stdin in an infinite loop and executes commands
 */
public class Shell {

    private Environment env;

    /**
     * constructs Shell with empty environment
     */
    Shell() {
        env = new Environment();
    }

    /**
     * runs Shell
     */
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
            input = Preprocessor.run(input, env);
            List<Token> tokens = Tokenizer.run(input);
            List<Command> commands = Parser.run(tokens, env);
            Stream stream = CommandExecutor.run(commands);
            while (stream.hasNext()) {
                System.out.println(stream.read());
            }
        }
    }
}
