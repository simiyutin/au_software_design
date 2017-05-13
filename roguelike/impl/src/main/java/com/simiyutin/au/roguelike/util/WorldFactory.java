package com.simiyutin.au.roguelike.util;

import com.simiyutin.au.roguelike.models.World;
import com.simiyutin.au.roguelike.util.WorldBuilder;
import com.simiyutin.au.roguelike.models.beings.Dragon;
import com.simiyutin.au.roguelike.models.beings.Ghost;
import com.simiyutin.au.roguelike.models.beings.Mushroom;

/**
 * Created by boris on 13.05.17.
 */
public class WorldFactory {
    public static World getOfMinLevel(int level) {
        World result = new WorldBuilder(100, 100)
                .withCaves()
                .ofMinLevel(level)
                .addMobs(Mushroom.class, 10)
                .addMobs(Ghost.class, 10)
                .addMobs(Dragon.class, 5)
                .addWeapons(5)
                .addMedAids(10)
                .build();

        return result;
    }

    public static World getTestConfig(int level) {
        World result = new WorldBuilder(100, 100)
                .withCaves()
                .ofMinLevel(level)
                .addMobs(Mushroom.class, 10)
                .addMobs(Ghost.class, 0)
                .addMobs(Dragon.class, 1)
                .addWeapons(5)
                .addMedAids(10)
                .build();

        return result;
    }
}
