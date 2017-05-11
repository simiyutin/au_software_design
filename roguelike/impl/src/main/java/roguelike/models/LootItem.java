package roguelike.models;

/**
 * Created by boris on 11.05.17.
 */
public class LootItem {
    public int x;
    public int y;
    private Weapon weapon;

    public LootItem(Weapon weapon, World world) {
        this.weapon = weapon;

        Position position = world.getEmptyPosition();
        this.x = position.x;
        this.y = position.y;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
