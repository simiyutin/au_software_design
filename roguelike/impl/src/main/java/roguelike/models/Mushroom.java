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
        poisonTiles();

    }

    private void poisonTiles() {
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                int wx = x + i;
                int wy = y + j;
                if (i * i + j * j <= 5 && world.getTile(wx, wy) == Tile.FLOOR) {
                    world.setTile(wx, wy, Tile.POISONED_FLOOR);
                }
            }
        }
    }
}
