package com.simiyutin.au.shell.core;

import java.util.ArrayList;

/**
 * Describes possible lexemes
 */
public class Token {

    /**
     * describes possible token types
     */
    public enum Type {
        SINGLE_QUOTE,
        DOUBLE_QUOTE,
        WHITESPACE,
        PIPE,
        WORD,
        EQ,
        EOF
    }

    private String value;
    private Type type;

    private static final String SINGLE_QUOTE = "'";
    private static final String DOUBLE_QUOTE = "\"";
    private static final String EQUALITY_OPERATOR = "=";
    private static final String WHITESPACE = " ";
    private static final String PIPE = "|";
    private static final String EOF = "\0";

    private Token(Token.Type type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Static factory method
     * @return new single quote token
     */
    public static Token singleQuote() {
        return new Token(Type.SINGLE_QUOTE, SINGLE_QUOTE);
    }

    /**
     * Static factory method
     * @return new double quote token
     */
    public static Token doubleQuote() {
        return new Token(Type.DOUBLE_QUOTE, DOUBLE_QUOTE);
    }

    /**
     * Static factory method
     * @return new '=' token
     */
    public static Token eq() {
        return new Token(Type.EQ, EQUALITY_OPERATOR);
    }

    /**
     * Static factory method
     * @return new whitespace token
     */
    public static Token whitespace() {
        return new Token(Type.WHITESPACE, WHITESPACE);
    }

    /**
     * Static factory method
     * @return new pipe token
     */
    public static Token pipe() {
        return new Token(Type.PIPE, PIPE);
    }

    /**
     * Static factory method
     * @param word string to encapsulate in newly created token
     * @return new word token
     */
    public static Token word(String word) {
        return new Token(Type.WORD, word);
    }

    /**
     * Static factory method
     * @return new end of line token
     */
    public static Token eof() {
        return new Token(Type.EOF, EOF);
    }


    /**
     * Tests if given char corresponds to any delimiter token
     * @param c character to test
     */
    public static boolean isDelimiter(char c) {
        ArrayList<String> test = new ArrayList<>();
        test.add(SINGLE_QUOTE);
        test.add(DOUBLE_QUOTE);
        test.add(WHITESPACE);
        test.add(EQUALITY_OPERATOR);
        test.add(PIPE);
        return test.contains(String.valueOf(c));
    }

    /**
     * Static factory method
     * @param c character to get corresponding token
     * @return generate corresponding token to passed char
     */
    public static Token valueOf(char c) {
        switch (String.valueOf(c)) {
            case SINGLE_QUOTE:
                return singleQuote();
            case DOUBLE_QUOTE:
                return doubleQuote();
            case WHITESPACE:
                return whitespace();
            case EQUALITY_OPERATOR:
                return eq();
            case PIPE:
                return pipe();
            default:
                return word(String.valueOf(c));
        }
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (value != null ? !value.equals(token.value) : token.value != null) return false;
        return type == token.type;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
