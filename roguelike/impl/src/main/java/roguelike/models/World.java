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

        this.player = new Player(this);
        this.mobs = new ArrayList<>();
    }

    public Tile getTile(int x, int y) {
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
}
