package roguelike.models.beings;

import roguelike.models.items.LootItem;
import roguelike.models.items.Weapon;
import roguelike.models.items.WeaponType;
import roguelike.models.World;

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

        LootItem lootItem = world.getWeapon(x, y);
        if (lootItem != null) {
            weapon = lootItem.getWeapon();
            world.setMessage(String.format("picked %s", weapon.getName()));
            world.getLoot().remove(lootItem);
        }
    }
}
