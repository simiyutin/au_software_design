package roguelike.models.items;

import java.awt.*;

/**
 * Created by boris on 11.05.17.
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

    public int getValue() {
        return value;
    }
}
