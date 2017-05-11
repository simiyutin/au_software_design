package roguelike.models.beings;

import roguelike.RecurringTask;
import roguelike.models.Tile;
import roguelike.models.World;
import roguelike.models.actions.Actions;

import java.awt.*;

/**
 * Created by boris on 11.05.17.
 */
public class Dragon extends ActiveBeing implements ArtificialIntelligence {
    public Dragon(World world) {
        super(world);

        this.glyph = 'D';
        this.color = Color.RED;
        enflameTiles();

        new RecurringTask(this::move, 1000);
    }

    @Override
    public void move() {
        moveRandom();
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        enflameTiles();
    }

    @Override
    protected void interactWithEnvironment() {

    }

    private void enflameTiles() {
        Actions.setTilesAround(world, x, y, 0, Tile.ENFLAMED_FLOOR);
    }
}
