package roguelike.screens;

import asciiPanel.AsciiPanel;
import roguelike.models.WorldBuilder;
import roguelike.models.World;


import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by boris on 08.05.17.
 */
public class PlayScreen implements Screen {

    private World world;
    private int centerX;
    private int centerY;
    private int screenWidth = 80;
    private int screenHeight = 23;

    public PlayScreen() {

        world = new WorldBuilder(100, 100).makeCaves().build();
        centerX = world.getWidth() / 2;
        centerY = world.getHeight() / 2;
    }

    public void display(AsciiPanel terminal) {
        int left = scrollLeft();
        int top = scrollTop();
        displayWorld(terminal, left, top);
        displayPlayer(terminal);
    }

    private int scrollLeft() {
        return Math.max(0, Math.min(centerX - screenWidth / 2, world.getWidth() - screenWidth));
    }

    private int scrollTop() {
        return Math.max(0, Math.min(centerY - screenHeight / 2, world.getHeight() - screenHeight));
    }

    private void scrollBy(int dx, int dy) {
        centerX = Math.max(0, Math.min(centerX + dx, world.getWidth() - 1));
        centerY = Math.max(0, Math.min(centerY + dy, world.getHeight() - 1));
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_S:
                scrollBy(0, 1);
                break;
            case KeyEvent.VK_W:
                scrollBy(0, -1);
                break;
            case KeyEvent.VK_A:
                scrollBy(-1, 0);
                break;
            case KeyEvent.VK_D:
                scrollBy(1, 0);
                break;
        }
        return this;
    }

    public void displayPlayer(AsciiPanel terminal) {
        terminal.write('X', centerX - scrollLeft(), centerY - scrollTop(), Color.BLUE);
    }

    public void displayWorld(AsciiPanel terminal, int left, int top) {

        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.getGlyph(wx, wy), x, y, world.getColor(wx, wy));
            }
        }
    }
}
