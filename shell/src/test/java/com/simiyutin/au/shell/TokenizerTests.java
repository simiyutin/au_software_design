package com.simiyutin.au.shell;

import org.junit.Test;
import com.simiyutin.au.shell.core.Token;
import com.simiyutin.au.shell.core.Tokenizer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TokenizerTests {

    @Test
    public void smokeTest() {
        List<Token> expected = new ArrayList<>();
        expected.add(Token.word("hello world"));
        expected.add(Token.eof());

        List<Token> actual = Tokenizer.run("'hello world'");

        assertEquals(expected, actual);
    }

    @Test
    public void emptyTest() {
        List<Token> expected = new ArrayList<>();
        expected.add(Token.eof());

        List<Token> actual = Tokenizer.run("");

        assertEquals(expected, actual);
    }

    @Test
    public void assignmentTest() {
        List<Token> expected = new ArrayList<>();
        expected.add(Token.word("hello"));
        expected.add(Token.eq());
        expected.add(Token.word("world"));
        expected.add(Token.eof());

        List<Token> actual = Tokenizer.run("hello=world");

        assertEquals(expected, actual);
    }

    @Test
    public void mixedTest() {
        List<Token> expected = new ArrayList<>();
        expected.add(Token.word("cat"));
        expected.add(Token.word("input.txt"));
        expected.add(Token.pipe());
        expected.add(Token.word("cat"));
        expected.add(Token.pipe());
        expected.add(Token.word("grep"));
        expected.add(Token.word("-v"));
        expected.add(Token.word("azazaazz"));
        expected.add(Token.word("long \"string\" constant"));
        expected.add(Token.eof());


        List<Token> actual = Tokenizer.run("cat input.txt | cat | grep -v azazaazz    'long \"string\" constant'   ");

        assertEquals(expected, actual);
    }
}
