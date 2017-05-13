package com.simiyutin.au.roguelike.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;


public interface Screen {
    void display(AsciiPanel terminal);
    Screen respondToUserInput(KeyEvent key);
    Screen updateState();
    default void print(AsciiPanel terminal, String str) {
        final int onHeight = 18;
        terminal.writeCenter(str, onHeight);
    }
}
