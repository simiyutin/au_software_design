package com.simiyutin.au.roguelike.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

/**
 * Created by boris on 09.05.17.
 */
public class DeadScreen implements Screen {

    @Override
    public void display(AsciiPanel terminal) {
        print(terminal, "You are dead. Retry? [enter]");

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                return new PlayScreen();
            default:
                return this;
        }
    }

    @Override
    public Screen updateState() {
        return this;
    }
}
