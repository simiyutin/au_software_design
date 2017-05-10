package roguelike;

import asciiPanel.AsciiPanel;
import roguelike.screens.Screen;
import roguelike.screens.StartScreen;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;


public class Scheduler extends JFrame implements KeyListener {
    private AsciiPanel terminal;
    private Screen screen;
    private RecurringTask task;

    public Scheduler() {
        super();

        this.terminal = new AsciiPanel();
        add(terminal);
        pack();

        this.screen = new StartScreen();

        addKeyListener(this);


        task = new RecurringTask(this::repaint, 10);
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
