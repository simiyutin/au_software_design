package roguelike.models;

import asciiPanel.AsciiPanel;

import java.awt.*;

/**
 * Created by boris on 08.05.17.
 */
public enum Tile {
    FLOOR('.', AsciiPanel.yellow, 0),
    POISONED_FLOOR('.', Color.GREEN, 5),
    WALL('#', AsciiPanel.yellow, 0),
    BOUNDS('x', AsciiPanel.brightBlack, 0);

    private char glyph;
    private Color color;
    private int harmness;

    Tile(char glyph, Color color, int harmness) {
        this.glyph = glyph;
        this.color = color;
        this.harmness = harmness;
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public int getHarmness() {
        return harmness;
    }

    public boolean isWalkable() {
        return this == FLOOR || this == POISONED_FLOOR;
    }

    public boolean isDiggable() {
        return this == WALL;
    }
}
