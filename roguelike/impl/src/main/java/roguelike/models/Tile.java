package roguelike.models;

import asciiPanel.AsciiPanel;

import java.awt.*;

/**
 * Created by boris on 08.05.17.
 */
public enum Tile {
    FLOOR('.', AsciiPanel.yellow, 0, true),
    POISONED_FLOOR('.', Color.GREEN, -5, true),
    ENFLAMED_FLOOR('.', Color.RED, -5, true),
    WALL('#', AsciiPanel.yellow, 0, false),
    Z_TELEPORT('Z', AsciiPanel.brightMagenta, 0, true),
    BOUNDS('x', AsciiPanel.brightBlack, 0, false);

    private char glyph;
    private Color color;
    private int deltaHealth;
    private boolean isWalkable;

    Tile(char glyph, Color color, int deltaHealth, boolean isWalkable) {
        this.glyph = glyph;
        this.color = color;
        this.deltaHealth = deltaHealth;
        this.isWalkable = isWalkable;
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public int getDeltaHealth() {
        return deltaHealth;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public boolean isDiggable() {
        return this == WALL;
    }
}
