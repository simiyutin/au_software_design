package roguelike.screens;

import asciiPanel.AsciiPanel;
import roguelike.models.*;


import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by boris on 08.05.17.
 */
public class PlayScreen implements Screen {

    private World world;
    private Player player;
    private int screenWidth = 80;
    private int screenHeight = 24;

    public PlayScreen() {

        world = new WorldBuilder(100, 100)
                .makeCaves()
                .addMobs(Mushroom.class, 10)
                .addMobs(Ghost.class, 10)
                .addLoot(15)
                .build();

        player = world.getPlayer();
    }

    public void display(AsciiPanel terminal) {
        int left = scrollLeft();
        int top = scrollTop();
        displayWorld(terminal, left, top);
        displayMobs(terminal, left, top);
        displayLoot(terminal, left, top);
        displayHealth(terminal);
        displayMessage(terminal);
    }

    private int scrollLeft() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.getWidth() - screenWidth));
    }

    private int scrollTop() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.getHeight() - screenHeight));
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (player.getEffect().apply(key.getKeyCode())) {
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
            case KeyEvent.VK_ENTER:
                player.act();
                break;
        }
        return player.getHealth() > 0 ? this : new DeadScreen();
    }

    public void displayMobs(AsciiPanel terminal, int left, int top) {

        for (Being b: world.getMobs()) {
            writeSafe(terminal, b.getGlyph(), b.x - left, b.y - top, b.getColor());
        }

    }

    private void writeSafe(AsciiPanel terminal, char c, int x, int y, Color color) {
        if (x > 0 && x < screenWidth && y > 0 && y < screenHeight) {
            terminal.write(c, x, y, color);
        }
    }

    public void displayHealth(AsciiPanel terminal) {
        terminal.write(String.format("health: %s", player.getHealth()), 67, 1);
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

    public void displayLoot(AsciiPanel terminal, int left, int top) {
        for (LootItem b: world.getLoot()) {
            Weapon w = b.getWeapon();
            writeSafe(terminal, w.getGlyph(), b.x - left, b.y - top, w.getColor());
        }
    }

    private void displayMessage(AsciiPanel terminal) {
        terminal.write(world.getMessage(), 40 - world.getMessage().length() / 2, 1);
    }
}
