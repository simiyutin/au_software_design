package roguelike.models.actions;

import roguelike.models.Tile;
import roguelike.models.World;

/**
 * Created by boris on 11.05.17.
 */
public class Actions {
    public static void setTilesAround(World world, int x, int y, int radius, Tile tile) {
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int wx = x + i;
                int wy = y + j;
                if (i * i + j * j <= radius * radius && world.getTile(wx, wy) == Tile.FLOOR) {
                    world.setTile(wx, wy, tile);
                }
            }
        }
    }
}
