package com.simiyutin.au.roguelike;

import asciiPanel.AsciiPanel;
import com.simiyutin.au.roguelike.screens.Screen;
import com.simiyutin.au.roguelike.screens.StartScreen;
import com.simiyutin.au.roguelike.util.RecurringTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;


/**
 * Routes user input to current Screen object, iterates Screen periodically and repaints it.
 */
public class Scheduler extends JFrame implements KeyListener {
    private static final Logger LOGGER = LogManager.getLogger(Scheduler.class);
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

    private void iterate() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                screen = screen.updateState();
                repaint();
            });
        } catch (InterruptedException | InvocationTargetException e) {
            LOGGER.error(e);
        }
    }

    /**
     * Begin iterating state.
     */
    public void schedule() {
        new RecurringTask(this::iterate, 1000 / 60);
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
