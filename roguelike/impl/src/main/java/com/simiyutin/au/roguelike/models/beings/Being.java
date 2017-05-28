package com.simiyutin.au.roguelike.models.beings;

import com.simiyutin.au.roguelike.models.Position;
import com.simiyutin.au.roguelike.models.World;

import java.awt.*;


/**
 * Base being class. This beings can only exist at given position/
 */
public abstract class Being {
    protected final World world;
    public int x;
    public int y;
    protected char glyph;
    protected Color color;

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
