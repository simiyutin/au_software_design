package roguelike.models.items;

import java.awt.*;

/**
 * Created by boris on 11.05.17.
 */
public interface Item {
    Color getColor();
    String getName();
    char getGlyph();
}
