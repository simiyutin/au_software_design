package com.simiyutin.au.roguelike.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

/**
 * Created by boris on 08.05.17.
 */
public class StartScreen implements Screen {

    @Override
    public void display(AsciiPanel terminal) {
        print(terminal, "Press [enter] to start");
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }

    @Override
    public Screen updateState() {
        return this;
    }
}
