package roguelike;

import roguelike.models.Tile;
import roguelike.models.World;

/**
 * Created by boris on 08.05.17.
 */
public class WorldFactory {
    public static World getWorld() {
        Tile[][] tiles = new Tile[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                tiles[i][j] = Tile.FLOOR;
            }
        }

        for (int i = 0; i < 100; i++) {
            tiles[i][i] = Tile.WALL;
        }

        return new World(tiles);
    }
}
