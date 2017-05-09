package roguelike.models;

import java.awt.*;

/**
 * Created by boris on 09.05.17.
 */
public class Mushroom extends Being {

    public Mushroom(World world) {
        super(world);

        this.glyph = 'T';
        this.color = Color.GREEN;
    }
}
