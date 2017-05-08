package roguelike.models;

import java.awt.*;

/**
 * Created by boris on 08.05.17.
 */
public class World {
    private Tile[][] tiles;
    private int width;
    private int height;

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.height = tiles.length;
        this.width = tiles[0].length;
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
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
}
