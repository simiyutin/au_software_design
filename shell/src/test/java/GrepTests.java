import exceptions.CommandExecutionException;
import org.junit.Test;
import shell.*;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class GrepTests {

    @Test
    public void smokeTest() {

        String input = "echo 'hello world' | grep hello";
        String expected = colorize("hello") + " world";
        testTemplate(input, expected);

    }

    @Test
    public void noMatchTest() {

        String input = "echo 'hello world' | grep noMatch";
        String expected = "";
        testTemplate(input, expected);

    }

    @Test
    public void insensitivityTest() {

        String input = "echo 'hello world' | grep -i HELLO";
        String expected = colorize("hello") + " world";
        testTemplate(input, expected);

    }

    @Test
    public void wholeWordTest() {

        String input = "echo 'hello hell.' | grep -w hell";
        String expected = "hello " + colorize("hell") + ".";
        testTemplate(input, expected);

    }

    @Test
    public void linesAfterTest() throws CommandExecutionException {

        Stream input = new Stream();
        input.write("hello");
        input.write("1");
        input.write("2");
        input.write("3");
        input.write("4");
        input.write("5");
        input.write("6");
        input.write("7");
        input.write("8");
        input.write("9");

        String command = "grep -A 3 hello";

        List<Token> tokens = Tokenizer.run(command);
        List<Command> commands = Parser.run(tokens, new Environment());
        Stream output = commands.get(0).run(input);

        Stream expected = new Stream();
        expected.write(colorize("hello"));
        expected.write("1");
        expected.write("2");
        expected.write("3");

        assertEquals(expected.toString(), output.toString());

    }

    @Test
    public void overflowLinesTest() throws CommandExecutionException {

        Stream input = new Stream();
        input.write("hello");
        input.write("1");
        input.write("2");
        input.write("3");

        String command = "grep -A 10 hello";

        List<Token> tokens = Tokenizer.run(command);
        List<Command> commands = Parser.run(tokens, new Environment());
        Stream output = commands.get(0).run(input);

        Stream expected = new Stream();
        expected.write(colorize("hello"));
        expected.write("1");
        expected.write("2");
        expected.write("3");

        assertEquals(expected.toString(), output.toString());

    }

    @Test
    public void multipleMatchTest() {

        String input = "echo 'hello hell' | grep hell";
        String expected = String.format("%so %s", colorize("hell"), colorize("hell"));
        testTemplate(input, expected);

    }


    private Stream interpret(String input) {
        Environment env = new Environment();
        List<Token> tokens = Tokenizer.run(input);
        List<Command> commands = Parser.run(tokens, env);
        Stream output = CommandExecutor.run(commands);
        return output;
    }

    private void testTemplate(String input, String expected) {
        Stream output = interpret(input);
        String actual = output.toString();
        assertEquals(expected, actual);
    }

    private String colorize(String string) {
        final int esc = 27;
        final String csi = (char) esc + "[";
        final String redModifier = "31m";
        final String resetModifier = "0m";
        return csi + redModifier + string + csi + resetModifier;
    }
}
