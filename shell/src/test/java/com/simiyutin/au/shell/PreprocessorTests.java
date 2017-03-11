package com.simiyutin.au.shell;

import org.junit.Test;
import com.simiyutin.au.shell.core.Environment;
import com.simiyutin.au.shell.core.Preprocessor;

import static org.junit.Assert.assertEquals;

public class PreprocessorTests {

    @Test
    public void smokeTest() {

        Environment env = getEnv();

        assertEquals("cat world | echo putin azazaza",
                Preprocessor.run("cat $hello | echo $vladimir azazaza", env));
    }

    @Test
    public void emptySubstitutionTest() {

        Environment env = getEnv();

        assertEquals("", Preprocessor.run("$not_in_env", env));
    }

    @Test
    public void lastSub() {
        Environment env = getEnv();

        assertEquals("world", Preprocessor.run("$hello", env));
    }

    @Test
    public void varNameBreakers() {
        Environment env = getEnv();

        assertEquals("world'hidden' \"world\" worldputin putin",
                Preprocessor.run("$hello'hidden' \"$hello\" $hello$vladimir $vladimir", env));
    }

    @Test
    public void hidesInSingleQuotes() {
        Environment env = getEnv();

        assertEquals("'$hello' world", Preprocessor.run("'$hello' $hello", env));
    }

    @Test
    public void handlesIncorrectInput() {
        Environment env = getEnv();

        assertEquals("'$hello", Preprocessor.run("'$hello", env));
    }

    @Test
    public void nestedQuotes() {
        Environment env = getEnv();

        assertEquals("\"'hello'\" world", Preprocessor.run("\"'$test'\" world", env));
    }

    private Environment getEnv() {
        Environment env = new Environment();
        env.put("hello", "world");
        env.put("vladimir", "putin");
        env.put("test", "hello");

        return env;
    }

}
