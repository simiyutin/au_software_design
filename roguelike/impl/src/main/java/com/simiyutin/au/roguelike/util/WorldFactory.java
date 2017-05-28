package com.simiyutin.au.roguelike.util;

import com.simiyutin.au.roguelike.models.World;
import com.simiyutin.au.roguelike.models.beings.Dragon;
import com.simiyutin.au.roguelike.models.beings.Ghost;
import com.simiyutin.au.roguelike.models.beings.Mushroom;


/**
 * Default World configurations.
 */
public class WorldFactory {


    /**
     * Default configuration used in game.
     */
    public static World getDefaultConfigurationOfMinLevel(int level) {
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
}
