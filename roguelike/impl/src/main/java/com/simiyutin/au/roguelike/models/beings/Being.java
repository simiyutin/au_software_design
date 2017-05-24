package com.simiyutin.au.roguelike.models.beings;

import com.simiyutin.au.roguelike.models.Position;
import com.simiyutin.au.roguelike.models.World;

import java.awt.*;


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
