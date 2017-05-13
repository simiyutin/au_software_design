package com.simiyutin.au.roguelike.screens;

import asciiPanel.AsciiPanel;
import com.simiyutin.au.roguelike.Main;
import com.simiyutin.au.roguelike.models.*;
import com.simiyutin.au.roguelike.models.beings.*;
import com.simiyutin.au.roguelike.models.items.Item;
import com.simiyutin.au.roguelike.models.items.ThrownItem;
import com.simiyutin.au.roguelike.util.WorldFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.awt.*;
import java.awt.event.KeyEvent;


public class PlayScreen implements Screen {

    private final World world;
    private final int screenWidth = 80;
    private final int screenHeight = 24;
    private static final Logger LOGGER = LogManager.getLogger(PlayScreen.class);


    public PlayScreen() {
        world = WorldFactory.getOfMinLevel(1);
        LOGGER.trace("Game screen created");
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
                world.getPlayer().levelUp();
                break;
        }
        return updateState();
    }

    @Override
    public Screen updateState() {
        return world.getPlayer().getHealth() > 0 ? this : new DeadScreen();
    }

    private void displayMobs(AsciiPanel terminal, int left, int top) {

        for (Being b: world.getMobs()) {
            writeSafe(terminal, b.getGlyph(), b.x - left, b.y - top, b.getColor());
        }

        Player p = world.getPlayer();
        writeSafe(terminal, p.getGlyph(), p.x - left, p.y - top, p.getColor());

    }

    private void displayWorld(AsciiPanel terminal, int left, int top) {

        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.getGlyph(wx, wy), x, y, world.getColor(wx, wy));
            }
        }
    }

    private void displayLoot(AsciiPanel terminal, int left, int top) {
        for (ThrownItem b: world.getItems()) {
            Item item = b.getItem();
            writeSafe(terminal, item.getGlyph(), b.x - left, b.y - top, item.getColor());
        }
    }

    private void displayMessage(AsciiPanel terminal) {
        terminal.write(world.getMessage(), 40 - world.getMessage().length() / 2, 5);
    }

    private void displayInfo(AsciiPanel terminal) {
        terminal.write(String.format("level: %s", world.getPlayer().getLevel()), 1, 1);
        terminal.write(String.format("health: %s", world.getPlayer().getHealth()), 1, 2);
        terminal.write(String.format("weapon: %s", world.getPlayer().getWeapon().getName()), 1, 3);
    }

    private int scrollTop() {
        return Math.max(0, Math.min(world.getPlayer().y - screenHeight / 2, world.getHeight() - screenHeight));
    }

    private int scrollLeft() {
        return Math.max(0, Math.min(world.getPlayer().x - screenWidth / 2, world.getWidth() - screenWidth));
    }

    private void writeSafe(AsciiPanel terminal, char c, int x, int y, Color color) {
        if (x > 0 && x < screenWidth && y > 0 && y < screenHeight) {
            terminal.write(c, x, y, color);
        }
    }
}
