package com.simiyutin.au.roguelike.models.items;

import java.awt.*;


/**
 * Some object that can be useful for player.
 */
public interface Item {
    Color getColor();

    String getName();

    char getGlyph();
}
