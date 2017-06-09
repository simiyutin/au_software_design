package com.simiyutin.au.roguelike.models.items;

import java.awt.*;


/**
 * Health pack.
 */
public class MedAid implements Item {

    private int value = 50;

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public String getName() {
        return String.format("Med aid of value %d", value);
    }

    @Override
    public char getGlyph() {
        return '+';
    }

    /**
     * @return how much health player will recover after picking this medAid up.
     */
    public int getValue() {
        return value;
    }
}
