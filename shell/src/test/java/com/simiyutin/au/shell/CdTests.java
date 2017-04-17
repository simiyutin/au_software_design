package com.simiyutin.au.shell;


import com.simiyutin.au.shell.core.*;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CdTests {
    @Test
    public void CdTest1() {
        File resourcesDirectory = new File("shell/src/test/resources");
        String input = "cd " + resourcesDirectory.toString() + "| ls";
        String output = interpret(input).toString();
        String expected = "cdls_test";
        assertEquals(expected, output);
    }

    @Test
    public void CdTest2() {
        File resourcesDirectory = new File("shell/src/test/resources");
        String input = "cd " + resourcesDirectory.toString() + "/cdls_test" + "| ls";
        String output = interpret(input).toString();
        String expected = "inner\nfilename2\nfilename1";
        assertEquals(expected, output);
    }

    @Test
    public void CdTest3() {
        File resourcesDirectory = new File("shell/src/test/resources");
        String input = "cd " + resourcesDirectory.toString() + "/cdls_test/inner" + "| ls";
        String output = interpret(input).toString();
        String expected = "inner_file1\ninner_file4";
        assertEquals(expected, output);
    }

    private Stream interpret(String input) {
        Environment env = new Environment();
        env.put("user.dir", System.getProperty("user.dir"));
        List<Token> tokens = Tokenizer.run(input);
        List<Command> commands = Parser.run(tokens, env);
        return CommandExecutor.run(commands);
    }
}
