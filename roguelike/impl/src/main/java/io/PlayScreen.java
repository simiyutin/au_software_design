package io;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

/**
 * Created by boris on 08.05.17.
 */
public class PlayScreen implements Screen {
    public void display(AsciiPanel terminal) {
        terminal.writeCenter("game started", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return this;
    }
}
