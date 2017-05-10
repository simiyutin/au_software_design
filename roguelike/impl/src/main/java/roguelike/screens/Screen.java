package roguelike.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

/**
 * Created by boris on 08.05.17.
 */
public interface Screen {
    void display(AsciiPanel terminal);
    Screen respondToUserInput(KeyEvent key);
    default void print(AsciiPanel terminal, String str) {
        final int onHeight = 18;
        terminal.writeCenter(str, onHeight);
    }
}
