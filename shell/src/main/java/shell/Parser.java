package shell;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * A DFA which accepts a list of tokens and evaluates
 * corresponding shell command or a sequence of them
 */
public class Parser {

    private Queue<Token> queue;
    private Environment env;
    private List<Command> commands = new ArrayList<>();

    private Parser(List<Token> tokens, Environment env) {
        this.queue = new ArrayDeque<>(tokens);
        this.env = env;
    }


    /**
     * Parses list of tokens into list of commands
     * @param tokens List of tokens to parse as a chain of shell commands
     * @param env Environment object
     * @return List of generated commands
     */
    public static List<Command> run(List<Token> tokens, Environment env) {

        Parser parser = new Parser(tokens, env);
        parser.start();
        return parser.commands;

    }

    private void start() {
        Token token = queue.poll();
        if (token.getType() == Token.Type.WORD) {
            parseCommand(token);
        } else {
            error();
        }

    }

    private void parseCommand(Token firstWord) {
        Token token = queue.poll();
        switch (token.getType()) {
            case EQ:
                parseEQ(firstWord);
                break;
            case WORD:
                List<String> args = new ArrayList<>();
                args.add(token.toString());
                parseArg(firstWord, args);
                break;
            case PIPE:
                createCommand(firstWord, new ArrayList<>());
                start();
                break;
            case EOF:
                createCommand(firstWord, new ArrayList<>());
                break;
            default:
                error();
                break;
        }
    }

    private void createCommand(Token commandToken, List<String> args) {
        Command command = CommandFactory.produce(commandToken, args, env);
        commands.add(command);
    }

    private void parseEQ(Token var) {
        Token val = queue.poll();
        switch (val.getType()) {
            case WORD:
                checkEQSyntax();
                List<String> args = new ArrayList<>();
                args.add(var.toString());
                args.add(val.toString());
                createCommand(Token.eq(), args);
                break;
            default:
                error();
                break;
        }
    }

    private void checkEQSyntax() {
        Token token = queue.poll();
        if (token.getType() != Token.Type.EOF) {
            error();
        }
    }

    private void parseArg(Token command, List<String> args) {
        Token token = queue.poll();
        switch (token.getType()) {
            case WORD:
                args.add(token.toString());
                parseArg(command, args);
                break;
            case PIPE:
                createCommand(command, args);
                start();
                break;
            case EOF:
                createCommand(command, args);
                break;
            default:
                error();
                break;
        }
    }

    private void error() {}

}
