package com.simiyutin.au.roguelike.util;

import com.simiyutin.au.roguelike.models.Tile;
import com.simiyutin.au.roguelike.models.World;
import com.simiyutin.au.roguelike.models.beings.Being;
import com.simiyutin.au.roguelike.models.items.Item;
import com.simiyutin.au.roguelike.models.items.MedAid;
import com.simiyutin.au.roguelike.models.items.ThrownItem;
import com.simiyutin.au.roguelike.models.items.Weapon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Builds world. (!)
 */
public class WorldBuilder {

    private static final RandomBGColorGenerator colorGen = new RandomBGColorGenerator();
    private static final Logger LOGGER = LogManager.getLogger(WorldBuilder.class);
    private int width;
    private int height;
    private Tile[][] tiles;
    private List<Constructor<? extends Being>> mobs;
    private List<Item> loot;
    private int minLevel;

    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;

        this.tiles = new Tile[width][height];
        fillWithFloor();

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
                LOGGER.error(e);
            }
        }

        for (Item w : loot) {
            world.getThrownItems().add(new ThrownItem(w, world)); //todo circular dependency
        }

        world.setMinLevel(minLevel);

        return world;
    }

    /**
     * Add random caves to world.
     */
    public WorldBuilder withCaves() {
        randomizeTiles();
        smooth(8);
        return this;
    }

    /**
     * Set minimum level of mobs and items.
     */
    public WorldBuilder ofMinLevel(int level) {
        this.minLevel = level;
        return this;
    }

    /**
     * Add mobs of given class and given quantity.
     */
    public WorldBuilder addMobs(Class<? extends Being> clazz, int quantity) {
        for (int i = 0; i < quantity; i++) {
            try {
                mobs.add(clazz.getConstructor(World.class));
            } catch (NoSuchMethodException ex) {
                LOGGER.error(ex);
            }
        }
        return this;
    }

    /**
     * Add random weapons of given quantity.
     */
    public WorldBuilder addWeapons(int quantity) {
        Random randGen = new Random();
        for (int i = 0; i < quantity; i++) {
            Weapon weapon = Weapon.getRandomOfLevel(minLevel + randGen.nextInt(2));
            loot.add(weapon);
        }
        return this;
    }

    /**
     * Add medAids of given quantity.
     */
    public WorldBuilder addMedAids(int quantity) {
        for (int i = 0; i < quantity; i++) {
            MedAid medAid = new MedAid();
            loot.add(medAid);
        }

        return this;
    }

    private void randomizeTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
            }
        }
    }

    private void smooth(int times) {
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
    }

    private void fillWithFloor() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = Tile.FLOOR;
            }
        }
    }
}
