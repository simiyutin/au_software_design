package roguelike.models;


import roguelike.DelayedTask;
import roguelike.models.beings.Being;
import roguelike.models.beings.Player;
import roguelike.models.items.ThrownItem;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by boris on 08.05.17.
 */
public class World {
    private Tile[][] tiles;
    private int width;
    private int height;
    private Player player;
    private List<Being> mobs;
    private String message = "";
    private List<ThrownItem> loot;

    public List<ThrownItem> getLoot() {
        return loot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        new DelayedTask(() -> {
            if (this.message.equals(message)) {
                this.message = "";
            }
        }, 1000);
    }

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;

        this.mobs = new ArrayList<>();
        this.loot = new ArrayList<>();
        this.player = new Player(this);
        this.mobs.add(this.player);

    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
            return Tile.BOUNDS;
        }
        return tiles[x][y];
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    public void printTiles() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(tiles[j][i].getGlyph() + " ");
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
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Being> getMobs() {
        return mobs;
    }

    public Being getMob(int x, int y) {
        for (Being b : mobs) {
            if (b.x == x && b.y == y) {
                return b;
            }
        }
        return null;
    }

    public ThrownItem getItem(int x, int y) {
        for (ThrownItem b : loot) {
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

    private boolean isEmptyFloor(int x, int y) {
        return getTile(x, y) == Tile.FLOOR
                && getMobs().stream().noneMatch(b -> b.x == x && b.y == y)
                && getLoot().stream().noneMatch(b -> b.x == x && b.y == y);
    }
}
