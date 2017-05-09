package roguelike.models;

import asciiPanel.AsciiPanel;

import java.awt.*;

/**
 * Created by boris on 08.05.17.
 */
public enum Tile {
    FLOOR('.', AsciiPanel.yellow),
    WALL('#', AsciiPanel.yellow),
    BOUNDS('x', AsciiPanel.brightBlack);

    private char glyph;
    private Color color;

    Tile(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public boolean isWalkable() {
        return this == FLOOR;
    }

    public boolean isDiggable() {
        return this == WALL;
    }
}
