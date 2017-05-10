package roguelike.models;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by boris on 08.05.17.
 */
public class World {
    private Tile[][] tiles;
    private int width;
    private int height;
    private Player player;
    private List<Being> mobs;

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;

        this.mobs = new ArrayList<>();
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
}
