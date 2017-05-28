package com.simiyutin.au.roguelike.models;


import com.simiyutin.au.roguelike.models.beings.Being;
import com.simiyutin.au.roguelike.models.beings.Player;
import com.simiyutin.au.roguelike.models.items.ThrownItem;
import com.simiyutin.au.roguelike.util.DelayedTask;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Holds map, player, mobs and loot.
 */
public class World {

    private WorldData d;

    public World(Tile[][] tiles) {
        this.d = new WorldData();
        this.d.tiles = tiles;
        this.d.width = tiles.length;
        this.d.height = tiles[0].length;

        this.d.mobs = new ArrayList<>();
        this.d.thrownItems = new ArrayList<>();
        this.d.minLevel = 1;
        this.d.player = new Player(this);
    }

    public List<ThrownItem> getThrownItems() {
        return d.thrownItems;
    }

    /**
     * minLevel defines minimum level of items and mobs.
     */
    public int getMinLevel() {
        return d.minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.d.minLevel = minLevel;
    }

    /**
     * @return message that will be displayed on top of a screen.
     */
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

    /**
     * @return Tile at given position.
     */
    public Tile getTile(int x, int y) {
        if (x < 0 || x > getWidth() - 1 || y < 0 || y > getHeight() - 1) {
            return Tile.BOUNDS;
        }
        return d.tiles[x][y];
    }

    public void setTile(int x, int y, Tile tile) {
        d.tiles[x][y] = tile;
    }

    /**
     * Print current map co console.
     */
    public void printTiles() {
        for (int i = 0; i < d.width; i++) {
            for (int j = 0; j < d.height; j++) {
                System.out.print(d.tiles[j][i].getGlyph() + " ");
            }
            System.out.println();
        }
    }

    /**
     * @return glyph of the tile at given position.
     */
    public char getGlyph(int x, int y) {
        return getTile(x, y).getGlyph();
    }

    /**
     * @return color of the tile at given position.
     */
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

    public void setPlayer(Player player) {
        d.player = player;
    }

    public List<Being> getMobs() {
        return d.mobs;
    }

    /**
     * @return mob at given position. (except player)
     */
    public Being getMob(int x, int y) {
        for (Being b : getMobs()) {
            if (b.x == x && b.y == y) {
                return b;
            }
        }
        return null;
    }

    /**
     * @return thrown item at given position.
     */
    public ThrownItem getItem(int x, int y) {
        for (ThrownItem b : getThrownItems()) {
            if (b.x == x && b.y == y) {
                return b;
            }
        }
        return null;
    }

    /**
     * @return walkable position free of mobs.
     */
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

    /**
     * Set all tiles in given radius to given type.
     */
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

    /**
     * move data from another world to current.
     */
    public void moveDataFrom(World other) {
        d = other.d;
    }


    private boolean isEmptyFloor(int x, int y) {
        return getTile(x, y) == Tile.FLOOR
                && getMobs().stream().noneMatch(b -> b.x == x && b.y == y)
                && getThrownItems().stream().noneMatch(b -> b.x == x && b.y == y);
    }

    private static class WorldData {
        private Tile[][] tiles;
        private int width;
        private int height;
        private Player player;
        private List<Being> mobs;
        private String message = "";
        private List<ThrownItem> thrownItems;
        private int minLevel;
    }
}
