package com.simiyutin.au.roguelike.models;


import com.simiyutin.au.roguelike.util.DelayedTask;
import com.simiyutin.au.roguelike.models.beings.Being;
import com.simiyutin.au.roguelike.models.beings.Player;
import com.simiyutin.au.roguelike.models.items.ThrownItem;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by boris on 08.05.17.
 */
public class World {

    private WorldData d;

    public List<ThrownItem> getLoot() {
        return d.loot;
    }

    public int getMinLevel() {
        return d.minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.d.minLevel = minLevel;
    }

    public String getMessage() {
        return d.message;
    }

    public void setMessage(String message) {
        this.d.message = message;
        new DelayedTask(() -> {
            if (this.d.message.equals(message)) {
                this.d.message = "";
            }
        }, 1000);
    }

    public World(Tile[][] tiles) {
        this.d = new WorldData();
        this.d.tiles = tiles;
        this.d.width = tiles.length;
        this.d.height = tiles[0].length;

        this.d.mobs = new ArrayList<>();
        this.d.loot = new ArrayList<>();
        this.d.minLevel = 1;
        this.d.player = new Player(this);
    }



    public Tile getTile(int x, int y) {
        if (x < 0 || x > getWidth() - 1 || y < 0 || y > getHeight() - 1) {
            return Tile.BOUNDS;
        }
        return d.tiles[x][y];
    }

    public void setTile(int x, int y, Tile tile) {
        d.tiles[x][y] = tile;
    }

    public void printTiles() {
        for (int i = 0; i < d.width; i++) {
            for (int j = 0; j < d.height; j++) {
                System.out.print(d.tiles[j][i].getGlyph() + " ");
            }
            System.out.println();
        }
    }

    public char getGlyph(int x, int y) {
        return getTile(x, y).getGlyph();
    }

    public Color getColor(int x, int y) {
        return getTile(x, y).getColor();
    }

    public int getWidth() {
        return d.width;
    }

    public int getHeight() {
        return d.height;
    }

    public Player getPlayer() {
        return d.player;
    }

    public List<Being> getMobs() {
        return d.mobs;
    }

    public Being getMob(int x, int y) {
        for (Being b : getMobs()) {
            if (b.x == x && b.y == y) {
                return b;
            }
        }
        return null;
    }

    public ThrownItem getItem(int x, int y) {
        for (ThrownItem b : getLoot()) {
            if (b.x == x && b.y == y) {
                return b;
            }
        }
        return null;
    }

    public Position getEmptyPosition() {
        Random randomGen = new Random();

        int x;
        int y;

        do {

            x = randomGen.nextInt(getWidth());
            y = randomGen.nextInt(getHeight());

        } while (!isEmptyFloor(x, y));

        return new Position(x, y);
    }

    public void setPlayer(Player player) {
        d.player = player;
    }

    public void moveDataFrom(World other) {
        d = other.d;
    }



    private boolean isEmptyFloor(int x, int y) {
        return getTile(x, y) == Tile.FLOOR
                && getMobs().stream().noneMatch(b -> b.x == x && b.y == y)
                && getLoot().stream().noneMatch(b -> b.x == x && b.y == y);
    }

    public void setTilesAround(int x, int y, int radius, Tile tile) {
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int wx = x + i;
                int wy = y + j;
                if (i * i + j * j <= radius * radius && getTile(wx, wy) == Tile.FLOOR) {
                    setTile(wx, wy, tile);
                }
            }
        }
    }

    private static class WorldData {
        private Tile[][] tiles;
        private int width;
        private int height;
        private Player player;
        private List<Being> mobs;
        private String message = "";
        private List<ThrownItem> loot;
        private int minLevel;
    }
}
