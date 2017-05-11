package roguelike.models;

import java.awt.*;

/**
 * Created by boris on 09.05.17.
 */
public class Player extends ActiveBeing {

    private Weapon weapon;

    public Player(World world) {
        super(world);

        this.glyph = 'X';
        this.color = Color.BLUE;
        this.weapon = WeaponType.HAND.getItem();
    }

    @Override
    protected void interactWithEnvironment() {
        int deltaHealth = world.getTile(x, y).getDeltaHealth();
        health += deltaHealth;
    }
}
