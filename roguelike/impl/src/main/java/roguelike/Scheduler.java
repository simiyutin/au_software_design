package roguelike;

import asciiPanel.AsciiPanel;
import roguelike.screens.Screen;
import roguelike.screens.StartScreen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Scheduler extends JFrame implements KeyListener {
    private AsciiPanel terminal;
    private Screen screen;

    public Scheduler() {
        super();

        this.terminal = new AsciiPanel();
        add(terminal);
        pack();

        this.screen = new StartScreen();

        addKeyListener(this);
        repaint();
    }

    public void schedule(Game game) {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.display(terminal);
        super.repaint();
    }
}
