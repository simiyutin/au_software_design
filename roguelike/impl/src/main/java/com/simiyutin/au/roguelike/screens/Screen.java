package com.simiyutin.au.roguelike.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;


/**
 * Representation of the game is split into screens.
 * Each of them has its purpose, can be drawn, and reacts to user input.
 */
public interface Screen {
    /**
     * print screen representation to terminal
     */
    void display(AsciiPanel terminal);

    /**
     * Change state according to user input and return self.
     */
    Screen respondToUserInput(KeyEvent key);

    /**
     * Change state according to time passed and return self.
     */
    Screen updateState();

    /**
     * Print message to terminal
     */
    default void print(AsciiPanel terminal, String str) {
        final int onHeight = 18;
        terminal.writeCenter(str, onHeight);
    }
}
