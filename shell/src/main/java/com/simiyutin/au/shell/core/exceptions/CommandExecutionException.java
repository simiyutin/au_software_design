package com.simiyutin.au.shell.core.exceptions;

/**
 * This is used when errors while commands execution encountered
 */
public class CommandExecutionException extends ShellException {
    public CommandExecutionException(String what) {
        super(what);
    }
}
