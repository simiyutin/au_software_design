package roguelike;

import asciiPanel.AsciiPanel;
import roguelike.screens.Screen;
import roguelike.screens.StartScreen;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


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
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void run() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                screen = screen.updateState();
                repaint();
            });
        } catch (InterruptedException e) {
            e.printStackTrace(); // todo log
        } catch (InvocationTargetException e) {
            e.printStackTrace(); // todo log
        }
    }

    public void schedule() {
        new RecurringTask(this::run, 1000 / 60);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.display(terminal);
        super.repaint();
    }
}
