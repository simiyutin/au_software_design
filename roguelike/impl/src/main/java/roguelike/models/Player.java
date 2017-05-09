package roguelike.models;

import java.awt.*;

/**
 * Created by boris on 09.05.17.
 */
public class Player extends Being {

    public Player(World world) {
        super(world);

        this.glyph = 'X';
        this.color = Color.BLUE;
    }
}
