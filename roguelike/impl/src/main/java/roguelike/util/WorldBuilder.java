package roguelike.util;

import roguelike.models.Tile;
import roguelike.models.World;
import roguelike.models.beings.Being;
import roguelike.models.items.Item;
import roguelike.models.items.MedAid;
import roguelike.models.items.ThrownItem;
import roguelike.models.items.Weapon;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by boris on 08.05.17.
 */
public class WorldBuilder {

    private int width;
    private int height;
    private Tile[][] tiles;
    private List<Constructor<? extends Being>> mobs;
    private List<Item> loot;
    private int minLevel;
    private static final RandomColorGenerator colorGen = new RandomColorGenerator();;

    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        this.mobs = new ArrayList<>();
        this.loot = new ArrayList<>();
        this.minLevel = 1;
    }

    public World build() {
        World world = new World(tiles);
        world.setMinLevel(minLevel);
        Color color = colorGen.getColor();
        Tile.FLOOR.setColor(color);
        Tile.WALL.setColor(color);

        for (Constructor<? extends Being> c : mobs) {
            try {
                world.getMobs().add(c.newInstance(world));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        for (Item w : loot) {
            world.getLoot().add(new ThrownItem(w, world)); //todo circular dependency
        }

        world.setMinLevel(minLevel);

        return world;
    }

    public WorldBuilder ofMinLevel(int level) {
        this.minLevel = level;
        return this;
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
                // todo log
                ex.printStackTrace();
            }
        }
        return this;
    }

    public WorldBuilder addWeapons(int quantity) {
        Random randGen = new Random();
        for (int i = 0; i < quantity; i++) {
            Weapon weapon = Weapon.getRandomOfLevel(minLevel + randGen.nextInt(2));
            loot.add(weapon);
        }
        return this;
    }

    public WorldBuilder addMedAids(int quantity) {
        for (int i = 0; i < quantity; i++) {
            MedAid medAid = new MedAid();
            loot.add(medAid);
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
