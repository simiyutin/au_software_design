package com.simiyutin.au.shell.core;

/**
 * Takes care of substitution variable values in requested places
 */
public class Preprocessor {

    /**
     * Substitutes variables in unprocessed string
     * @param unprocessed unprocessed input String
     * @param env Environment object
     * @return String with substituted variables from environment
     */
    public static String run(String unprocessed, Environment env) {

        char[] chars = unprocessed.toCharArray();

        StringBuilder varName = new StringBuilder();
        StringBuilder processed = new StringBuilder();
        boolean varReading = false;
        boolean isInsideDoubleQuotes = false;
        boolean isInsideSingleQuotes = false;

        for (int i = 0; i < chars.length; i++) {

            if (!isInsideSingleQuotes && chars[i] == '$') {
                varReading = true;
                continue;
            }

            if (varReading) {
                varName.append(chars[i]);
                if (isEndOfVarName(chars, i)) {
                    processed.append(env.get(varName.toString()));
                    varName.delete(0, chars.length);
                    varReading = false;
                }
                continue;
            }

            if (chars[i] == '"' && !isInsideSingleQuotes) {
                isInsideDoubleQuotes = !isInsideDoubleQuotes;
            }

            if (chars[i] == '\'' && !isInsideDoubleQuotes) {
                isInsideSingleQuotes = !isInsideSingleQuotes;
            }

            processed.append(chars[i]);
        }

        return processed.toString();
    }

    private static boolean isEndOfVarName(char[] chars, int index) {

        if (index == chars.length - 1) {
            return true;
        }

        char c = chars[index + 1];
        return c == ' ' || c == '"' || c == '\'' || c == '$';
    }

}
