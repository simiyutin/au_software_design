package roguelike.models.beings;

import asciiPanel.AsciiPanel;
import roguelike.models.Position;
import roguelike.models.World;

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
    protected final World world;

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
