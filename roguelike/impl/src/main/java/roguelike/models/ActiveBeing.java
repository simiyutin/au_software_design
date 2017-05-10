package roguelike.models;

/**
 * Created by boris on 10.05.17.
 */
public abstract class ActiveBeing extends Being {

    private int xDirection;
    private int yDirection;

    protected int health;

    public ActiveBeing(World world) {
        super(world);
        this.health = 100;
    }

    public int getHealth() {
        return health;
    }

    public void move(int dx, int dy) {
        xDirection = Math.max(0, Math.min(x + dx, world.getWidth() - 1));
        yDirection  = Math.max(0, Math.min(y + dy, world.getHeight() - 1));
        if (world.getTile(xDirection, yDirection).isWalkable()) {
            x = xDirection;
            y = yDirection;
        }

        interactWithEnvironment();
    }

    public void act() {
        if (world.getTile(xDirection, yDirection).isDiggable()) {
            world.setTile(xDirection, yDirection, Tile.FLOOR);
        }
    }

    protected abstract void interactWithEnvironment();
}
