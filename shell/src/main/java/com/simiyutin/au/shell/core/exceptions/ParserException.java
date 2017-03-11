package com.simiyutin.au.shell.core.exceptions;


/**
 * This is thrown when command has incorrect syntax
 */
public class ParserException extends ShellException {
    public ParserException(String what) {
        super(what);
    }
}
