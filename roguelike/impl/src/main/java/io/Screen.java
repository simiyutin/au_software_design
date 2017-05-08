package io;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

/**
 * Created by boris on 08.05.17.
 */
public interface Screen {
    void display(AsciiPanel terminal);
    Screen respondToUserInput(KeyEvent key);
}
