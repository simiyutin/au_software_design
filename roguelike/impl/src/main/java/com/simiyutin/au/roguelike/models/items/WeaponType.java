package com.simiyutin.au.roguelike.models.items;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by boris on 11.05.17.
 */
public enum WeaponType {
    HAND('h', "hand", 1, 10),
    SWORD('>', "sword", 10, 150);

    private char glyph;
    private int  hue;
    private int harm;
    private String name;

    WeaponType(char glyph, String name, int harm, int hue) {
        this.glyph = glyph;
        this.name = name;
        this.harm = harm;
        this.hue = hue;
    }

    public String getName() {
        return name;
    }

    public char getGlyph() {
        return glyph;
    }

    public int getHarm() {
        return harm;
    }

    public int getHue() {
        return hue;
    }

    public Weapon getItem() {
        return new Weapon(this, 1);
    }

    public static WeaponType getRandom() {
        List<WeaponType> types = Arrays.asList(SWORD);
        Integer ind = new Random().nextInt(types.size());
        return types.get(ind);
    }
}
