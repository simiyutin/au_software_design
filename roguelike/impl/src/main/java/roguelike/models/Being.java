package roguelike.models;

import asciiPanel.AsciiPanel;

import java.awt.*;
import java.util.Random;

/**
 * Created by boris on 09.05.17.
 */
public abstract class Being {
    public int x;
    public int y;
    private int xDirection;
    private int yDirection;

    protected char glyph;
    protected Color color;
    protected World world;
    protected int health;

    public Being(World world) {
        this.world = world;
        this.health = 100;

        Random randomGen = new Random(42);

        do {

            x = randomGen.nextInt(world.getWidth());
            y = randomGen.nextInt(world.getHeight());

        } while (world.getTile(x, y) != Tile.FLOOR || world.getMobs().stream().anyMatch(b -> b.x == x && b.y == y));
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public int getHealth() {
        return health;
    }

    public void move(int dx, int dy) {
        xDirection = Math.max(0, Math.min(x + dx, world.getWidth() - 1));
        yDirection  = Math.max(0, Math.min(y + dy, world.getHeight() - 1));
        if (world.getTile(xDirection, yDirection).isWalkable()) {
            x = xDirection;
            y = yDirection;
        }

        respondToMove();
    }

    public void dig() {
        if (world.getTile(xDirection, yDirection).isDiggable()) {
            world.setTile(xDirection, yDirection, Tile.FLOOR);
        }
    }

    protected void respondToMove() {}
}
