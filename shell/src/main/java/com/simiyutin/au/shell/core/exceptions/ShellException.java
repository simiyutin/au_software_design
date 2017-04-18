package com.simiyutin.au.shell.core.exceptions;


/**
 * Base class for shell exceptions
 */
public class ShellException extends Exception {
    private String what = "";
    public ShellException(String what) {
        this.what = what;
    }

    @Override
    public String toString() {
        return what;
    }
}
