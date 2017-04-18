package com.simiyutin.au.shell;


import com.simiyutin.au.shell.core.*;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LsTests {
    @Test
    public void Lstest1() {
        File resourcesDirectory = new File("shell/src/test/resources");
        String input = "ls " + resourcesDirectory.toString();
        String output = interpret(input).toString();
        String expected = "cdls_test";
        assertEquals(expected, output);
    }

    @Test
    public void Lstest2() {
        File resourcesDirectory = new File("shell/src/test/resources/cdls_test");
        String input = "ls " + resourcesDirectory.toString();
        String output = interpret(input).toString();
        String expected = "inner\nfilename2\nfilename1";
        assertEquals(expected, output);
    }

    @Test
    public void Lstest3() {
        File resourcesDirectory = new File("shell/src/test/resources/cdls_test/inner");
        String input = "ls " + resourcesDirectory.toString();
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
