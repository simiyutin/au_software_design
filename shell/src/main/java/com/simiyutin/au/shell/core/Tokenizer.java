package com.simiyutin.au.shell.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Takes care of translating given char string to list of lexemes.
 */
public class Tokenizer {

    /**
     * Start tokenizer on preprocessed string
     * @param input processed string corresponding to some shell command
     * @return list of words and operators obtained from passed string
     */
    public static List<Token> run(String input) {

        List<Token> tokens = new ArrayList<>();

        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length;) {
            char c = chars[i];
            if (!Token.isDelimiter(c)) {
                String word = readWord(chars, i);
                i += word.length();
                tokens.add(Token.word(word));
                continue;
            }
            Token.Type type = Token.valueOf(c).getType();
            switch (type) {
                case SINGLE_QUOTE:
                case DOUBLE_QUOTE:
                    i++;
                    String word = readWordInsideQuotes(chars, i, c);
                    i += word.length() + 1;
                    tokens.add(Token.word(word));
                    break;
                case EQ:
                    tokens.add(Token.eq());
                    i++;
                    break;
                case WHITESPACE:
                    i++;
                    break;
                case PIPE:
                    tokens.add(Token.pipe());
                    i++;
                    break;
            }
        }

        tokens.add(Token.eof());

        return tokens;
    }

    private static String readWordInsideQuotes(char[] chars, int i, char matcher) {
        return readWordUntil(c -> c.equals(matcher), chars, i);
    }

    private static String readWord(char[] chars, int i) {
        return readWordUntil(Token::isDelimiter, chars, i);
    }

    private static String readWordUntil(Predicate<Character> breaker, char[] chars, int i) {
        StringBuilder sb = new StringBuilder();
        while (i < chars.length && !breaker.test(chars[i])) {
            sb.append(chars[i++]);
        }
        return sb.toString();
    }

}
