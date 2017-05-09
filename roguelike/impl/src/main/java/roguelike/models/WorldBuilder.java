package roguelike.models;

import roguelike.models.Tile;
import roguelike.models.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by boris on 08.05.17.
 */
public class WorldBuilder {

    private int width;
    private int height;
    private Tile[][] tiles;
    private List<Constructor<? extends Being>> mobs;

    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        this.mobs = new ArrayList<>();
    }

    public World build() {
        World world = new World(tiles);
        for (Constructor<? extends Being> c : mobs) {
            try {
                world.getMobs().add(c.newInstance(world));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return world;
    }

    public WorldBuilder makeCaves() {
        return randomizeTiles()
                .smooth(8);
    }

    public WorldBuilder addMobs(Class<? extends Being> clazz, int quantity) {
        for (int i = 0; i < quantity; i++) {
            try {
                mobs.add(clazz.getConstructor(World.class));
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            }
        }
        return this;
    }

    private WorldBuilder randomizeTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
            }
        }
        return this;
    }

    private WorldBuilder smooth(int times) {
        Tile[][] tiles2 = new Tile[width][height];
        for (int time = 0; time < times; time++) {

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int floors = 0;
                    int rocks = 0;

                    for (int ox = -1; ox < 2; ox++) {
                        for (int oy = -1; oy < 2; oy++) {
                            if (x + ox < 0 || x + ox >= width || y + oy < 0
                                    || y + oy >= height)
                                continue;

                            if (tiles[x + ox][y + oy] == Tile.FLOOR)
                                floors++;
                            else
                                rocks++;
                        }
                    }
                    tiles2[x][y] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
                }
            }
            tiles = tiles2;
        }
        return this;
    }
}
