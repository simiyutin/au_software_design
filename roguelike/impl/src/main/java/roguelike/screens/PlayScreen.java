package roguelike.screens;

import asciiPanel.AsciiPanel;
import roguelike.models.*;
import roguelike.models.beings.*;
import roguelike.models.items.Item;
import roguelike.models.items.ThrownItem;
import roguelike.models.items.WorldFactory;


import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by boris on 08.05.17.
 */
public class PlayScreen implements Screen {

    private World world;
    private int screenWidth = 80;
    private int screenHeight = 24;

    public PlayScreen() {

        world = WorldFactory.getOfMinLevel(1);
    }

    @Override
    public void display(AsciiPanel terminal) {
        int left = scrollLeft();
        int top = scrollTop();
        displayWorld(terminal, left, top);
        displayMobs(terminal, left, top);
        displayLoot(terminal, left, top);
        displayInfo(terminal);
        displayMessage(terminal);
    }

    private int scrollLeft() {
        return Math.max(0, Math.min(world.getPlayer().x - screenWidth / 2, world.getWidth() - screenWidth));
    }

    private int scrollTop() {
        return Math.max(0, Math.min(world.getPlayer().y - screenHeight / 2, world.getHeight() - screenHeight));
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        Player player = world.getPlayer();
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
            case KeyEvent.VK_P:
                world.printTiles();
                break;
            case KeyEvent.VK_Z:
                world.getPlayer().zlevel();
                break;
        }
        return updateState();
    }

    @Override
    public Screen updateState() {
        return world.getPlayer().getHealth() > 0 ? this : new DeadScreen();
    }

    public void displayMobs(AsciiPanel terminal, int left, int top) {

        for (Being b: world.getMobs()) {
            writeSafe(terminal, b.getGlyph(), b.x - left, b.y - top, b.getColor());
        }

        Player p = world.getPlayer();
        writeSafe(terminal, p.getGlyph(), p.x - left, p.y - top, p.getColor());

    }

    private void writeSafe(AsciiPanel terminal, char c, int x, int y, Color color) {
        if (x > 0 && x < screenWidth && y > 0 && y < screenHeight) {
            terminal.write(c, x, y, color);
        }
    }

    public void displayInfo(AsciiPanel terminal) {
        terminal.write(String.format("level: %s", world.getPlayer().getLevel()), 1, 1);
        terminal.write(String.format("health: %s", world.getPlayer().getHealth()), 1, 2);
        terminal.write(String.format("weapon: %s", world.getPlayer().getWeapon().getName()), 1, 3);
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
        for (ThrownItem b: world.getLoot()) {
            Item item = b.getItem();
            writeSafe(terminal, item.getGlyph(), b.x - left, b.y - top, item.getColor());
        }
    }

    private void displayMessage(AsciiPanel terminal) {
        terminal.write(world.getMessage(), 40 - world.getMessage().length() / 2, 5);
    }
}
