package com.simiyutin.au.roguelike.models.items;

import java.awt.*;


/**
 * Weapon itself.
 */
public class Weapon implements Item {
    private final int MAX_LEVEL = 10;
    private WeaponType type;
    private int level; // 1 - 10

    public Weapon(WeaponType type, int level) {
        this.type = type;
        this.level = level;
    }

    /**
     * Get weapon of random type of given level.
     */
    public static Weapon getRandomOfLevel(int level) {
        return new Weapon(WeaponType.getRandom(), level);
    }

    public Color getColor() {
        int brightness = 150 + level / MAX_LEVEL * 100;
        return Color.getHSBColor(type.getHue(), 200, brightness);
    }

    public String getName() { // todo handle level name correlation
        if (type == WeaponType.HAND) return type.getName();
        return type.getName() + String.format(" of level %d", level);
    }

    /**
     * @return measure of harm this weapon can damage.
     */
    public int getHarm() {
        return type.getHarm();
    }

    public char getGlyph() {
        return type.getGlyph();
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weapon weapon = (Weapon) o;

        if (MAX_LEVEL != weapon.MAX_LEVEL) return false;
        if (level != weapon.level) return false;
        return type == weapon.type;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + MAX_LEVEL;
        result = 31 * result + level;
        return result;
    }
}
