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

    protected char glyph;
    protected Color color;
    protected World world;

    public Being(World world) {
        this.world = world;

        Position position = world.getEmptyPosition();
        this.x = position.x;
        this.y = position.y;
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }


}
