package io;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

/**
 * Created by boris on 08.05.17.
 */
public class StartScreen implements Screen {
    public void display(AsciiPanel terminal) {
        final int onHeight = 22;
        terminal.writeCenter("Press [enter] to start", onHeight);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}
