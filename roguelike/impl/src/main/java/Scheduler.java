import asciiPanel.AsciiPanel;
import io.Screen;
import io.StartScreen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by boris on 08.05.17.
 */
public class Scheduler extends JFrame implements KeyListener {
    private int framerate;
    private AsciiPanel terminal;
    private Screen screen;

    public Scheduler(int framerate) {
        super();
        this.framerate = framerate;
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
