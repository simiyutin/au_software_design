package com.simiyutin.au.shell.core;

import com.simiyutin.au.shell.commands.*;

import java.util.List;


/**
 * Factory which is used to construct Command objects relying on command token
 */
public class CommandFactory {


    /**
     * Factory method to generate commands from tokens.
     * @param commandToken token, which contains command name as a String
     * @param args arguments of a command
     * @param env Environment object
     * @return generated runnable Command object
     */
    public static Command produce(Token commandToken, List<String> args, Environment env) {
        Command command;
        switch (commandToken.getType()) {
            case EQ:
                command = new Eq(args, env);
                return command;
            case WORD:
                command = getCommand(commandToken.toString(), args, env);
                return command;
            default:
                return null;
        }
    }

    private static Command getCommand(String commandName, List<String> args, Environment env) {

        Command command = null;
        switch (commandName) {
            case Echo.NAME:
                command = new Echo(args, env);
                break;
            case Cat.NAME:
                command = new Cat(args, env);
                break;
            case Wc.NAME:
                command = new Wc(args, env);
                break;
            case Pwd.NAME:
                command = new Pwd(args, env);
                break;
            case Exit.NAME:
                command = new Exit(args, env);
                break;
            case Ls.NAME:
                command = new Ls(args, env);
                break;
            case Cd.NAME:
                command = new Cd(args, env);
                break;
            default:
                command = new OutSource(commandName, args, env);
                break;

        }
        return command;
    }
}
