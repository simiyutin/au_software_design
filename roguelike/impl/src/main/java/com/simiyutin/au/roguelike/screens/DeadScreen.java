package com.simiyutin.au.roguelike.screens;

import asciiPanel.AsciiPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyEvent;


/**
 * "You are dead. Retry?" screen
 */
public class DeadScreen implements Screen {
    private static final Logger LOGGER = LogManager.getLogger(DeadScreen.class);

    public DeadScreen() {
        LOGGER.trace("player died");
    }


    @Override
    public void display(AsciiPanel terminal) {
        print(terminal, "You are dead. Retry? [enter]");
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                LOGGER.trace("player pressed retry button");
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
