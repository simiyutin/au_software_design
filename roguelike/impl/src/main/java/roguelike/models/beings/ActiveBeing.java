package roguelike.models.beings;

import roguelike.models.Tile;
import roguelike.models.World;

import java.util.Random;

/**
 * Created by boris on 10.05.17.
 */
public abstract class ActiveBeing extends Being {

    protected int xDirection;
    protected int yDirection;

    protected int health;


    private PlayerEffects effect;

    public ActiveBeing(World world) {
        super(world);
        this.health = 100;
        this.effect = PlayerEffects.IDENTITY;
    }

    public int getHealth() {
        return health;
    }

    public void move(int dx, int dy) {
        xDirection = Math.max(0, Math.min(x + dx, world.getWidth() - 1));
        yDirection  = Math.max(0, Math.min(y + dy, world.getHeight() - 1));
        if (canMove(xDirection, yDirection)) {
            x = xDirection;
            y = yDirection;
        }

        interactWithEnvironment();
    }

    protected boolean canMove(int xTo, int yTo) {
        return world.getTile(xTo, yTo).isWalkable() && world.getMob(xTo, yTo) == null;
    }

    public void act() {
        if (world.getTile(xDirection, yDirection).isDiggable()) {
            world.setTile(xDirection, yDirection, Tile.FLOOR);
        }
    }

    public void setEffect(PlayerEffects effect) {
        this.effect = effect;
    }

    public PlayerEffects getEffect() {
        return effect;
    }


    protected void moveRandom() {
        move(new Random().nextInt(3) - 1, new Random().nextInt(3) - 1);
    }

    protected abstract void interactWithEnvironment();
}
