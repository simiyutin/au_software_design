package com.simiyutin.au.shell;

import org.junit.Test;
import com.simiyutin.au.shell.core.*;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParserTests {

    @Test
    public void smokeTest() throws Exception {
        Environment env = getEnv();
        String input = "hello=hacked";
        List<Token> tokens = Tokenizer.run(input);
        List<Command> commands = Parser.run(tokens, env);
        commands.get(0).run(new Stream());
        assertEquals("hacked", Preprocessor.run("$hello", env));
    }

    @Test
    public void reduceTest() throws Exception {
        Environment env = getEnv();
        String input = "hello='hacked = | azazazazaa'";
        List<Token> tokens = Tokenizer.run(input);
        List<Command> commands = Parser.run(tokens, env);
        commands.get(0).run(new Stream());
        assertEquals("hacked = | azazazazaa", Preprocessor.run("$hello", env));
    }

    @Test
    public void reduceWithSubstitutionTest() throws Exception {
        Environment env = getEnv();
        String input = "hello=\"hacked $vladimir\"";
        input = Preprocessor.run(input, env);
        List<Token> tokens = Tokenizer.run(input);
        List<Command> commands = Parser.run(tokens, env);
        commands.get(0).run(new Stream());
        assertEquals("hacked putin", Preprocessor.run("$hello", env));
    }

    @Test
    public void recursiveReduceWithSubstitutionTest() throws Exception {
        Environment env = getEnv();
        String input = "hello=\"hacked $hello\"";
        input = Preprocessor.run(input, env);
        List<Token> tokens = Tokenizer.run(input);
        List<Command> commands = Parser.run(tokens, env);
        commands.get(0).run(new Stream());
        assertEquals("hacked world", Preprocessor.run("$hello", env));
    }

    @Test
    public void echoTest() {
        Environment env = getEnv();
        String input = "echo hello";
        List<Token> tokens = Tokenizer.run(input);
        List<Command> commands = Parser.run(tokens, env);
        Stream output = CommandExecutor.run(commands);
        assertEquals("hello", output.toString());
    }

    @Test
    public void echoToPipeToCatTest() {
        Environment env = getEnv();
        String input = "echo hello | cat";
        List<Token> tokens = Tokenizer.run(input);
        List<Command> commands = Parser.run(tokens, env);
        Stream output = CommandExecutor.run(commands);
        assertEquals("hello", output.toString());
    }

    @Test
    public void echoToSedAsOutSourceTest() {

        Environment env = getEnv();
        String input = "echo \"hello_world\" | sed 's/hello/goodbye/'";
        List<Token> tokens = Tokenizer.run(input);
        List<Command> commands = Parser.run(tokens, env);
        Stream output = CommandExecutor.run(commands);
        assertEquals("goodbye_world", output.toString());
    }

    private Environment getEnv() {
        Environment env = new Environment();
        env.put("hello", "world");
        env.put("vladimir", "putin");

        return env;
    }
}
