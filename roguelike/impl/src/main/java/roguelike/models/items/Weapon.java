package roguelike.models.items;

import java.awt.*;

/**
 * Created by boris on 11.05.17.
 */
public class Weapon implements Item {
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

    public String getName() { // todo handle level name correlation
        if (type == WeaponType.HAND) return type.getName();
        return type.getName() + String.format(" of level %d", level);
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
