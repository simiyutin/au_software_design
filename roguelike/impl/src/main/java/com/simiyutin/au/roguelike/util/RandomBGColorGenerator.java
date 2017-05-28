package com.simiyutin.au.roguelike.util;

import java.awt.*;
import java.util.Random;


/**
 * Generates color from preset color range.
 */
public class RandomBGColorGenerator {
    private final Random randomGen = new Random(42);

    public Color getColor() {
        return Color.getHSBColor(getComponent(), 0.8f, 0.4f);
    }

    private float getComponent() {
        return (float) randomGen.nextDouble();
    }
}
