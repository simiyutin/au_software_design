package roguelike.models.items;

import java.awt.*;

/**
 * Created by boris on 11.05.17.
 */
public class Weapon {
    private WeaponType type;
    private final int MAX_LEVEL = 10;
    private int level; // 1 - 10

    public Weapon(WeaponType type, int level) {
        this.type = type;
        this.level = level;
    }

    public Color getColor() {
        int brightness = 150 + level / MAX_LEVEL * 100;
        return Color.getHSBColor(type.getHue(), 200, brightness);
    }

    public char getGlyph() {
        return type.getGlyph();
    }

    public int getLevel() {
        return level;
    }

    public static Weapon getRandomOfLevel(int level) {
        return new Weapon(WeaponType.getRandom(), level);
    }
}
