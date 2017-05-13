package com.simiyutin.au.roguelike.screens;

import asciiPanel.AsciiPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyEvent;


public class StartScreen implements Screen {

    private static final Logger LOGGER = LogManager.getLogger(StartScreen.class);

    public StartScreen() {
        LOGGER.trace("start screen showed");
    }

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
