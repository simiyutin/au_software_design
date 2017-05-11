package roguelike.models.beings;

import roguelike.models.Tile;
import roguelike.models.World;
import roguelike.models.actions.Actions;

import java.awt.*;

/**
 * Created by boris on 09.05.17.
 */
public class Mushroom extends Being {

    public Mushroom(World world) {
        super(world);

        this.glyph = 'T';
        this.color = Color.GREEN;
        poisonTiles();

    }

    private void poisonTiles() {
        Actions.setTilesAround(world, x, y, 3, Tile.POISONED_FLOOR);
    }
}
