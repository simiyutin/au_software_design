package roguelike.models;

import java.awt.*;

/**
 * Created by boris on 09.05.17.
 */
public class Player extends ActiveBeing {

    public Player(World world) {
        super(world);

        this.glyph = 'X';
        this.color = Color.BLUE;
    }

    @Override
    protected void interactWithEnvironment() {
        int deltaHealth = world.getTile(x, y).getDeltaHealth();
        health += deltaHealth;
    }
}
