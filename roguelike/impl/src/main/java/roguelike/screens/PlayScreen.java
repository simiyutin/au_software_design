package roguelike.screens;

import asciiPanel.AsciiPanel;
import roguelike.models.Player;
import roguelike.models.WorldBuilder;
import roguelike.models.World;


import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by boris on 08.05.17.
 */
public class PlayScreen implements Screen {

    private World world;
    private Player player;
    private int screenWidth = 80;
    private int screenHeight = 23;

    public PlayScreen() {

        world = new WorldBuilder(100, 100).makeCaves().build();
        player = world.getPlayer();
    }

    public void display(AsciiPanel terminal) {
        int left = scrollLeft();
        int top = scrollTop();
        displayWorld(terminal, left, top);
        displayPlayer(terminal, left, top);
    }

    private int scrollLeft() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.getWidth() - screenWidth));
    }

    private int scrollTop() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.getHeight() - screenHeight));
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_S:
                player.move(0, 1);
                break;
            case KeyEvent.VK_W:
                player.move(0, -1);
                break;
            case KeyEvent.VK_A:
                player.move(-1, 0);
                break;
            case KeyEvent.VK_D:
                player.move(1, 0);
                break;
            case KeyEvent.VK_F:
                player.dig();
                break;
        }
        return this;
    }

    public void displayPlayer(AsciiPanel terminal, int left, int top) {
        Player p = world.getPlayer();
        terminal.write(p.getGlyph(), p.x - left, p.y - top, p.getColor());
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
