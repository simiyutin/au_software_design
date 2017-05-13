package com.simiyutin.au.roguelike;

import com.simiyutin.au.roguelike.models.Position;
import com.simiyutin.au.roguelike.models.Tile;
import com.simiyutin.au.roguelike.models.World;
import com.simiyutin.au.roguelike.models.beings.Ghost;
import com.simiyutin.au.roguelike.util.WorldBuilder;
import com.simiyutin.au.roguelike.util.WorldFactory;
import javafx.geometry.Pos;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by boris on 13.05.17.
 */
public class WorldTests {

    @Test
    public void testEmptyPosition() {
        World world = new WorldBuilder(100, 100)
                .withCaves()
                .addMedAids(50)
                .addWeapons(50)
                .addMobs(Ghost.class, 100)
                .build();

        for (int i = 0; i < 10000; i++) {
            Position position = world.getEmptyPosition();
            assertTrue(world.getMob(position.x, position.y) == null);
            assertTrue(world.getItem(position.x, position.y) == null);
            assertTrue(world.getTile(position.x, position.y) == Tile.FLOOR);
        }
    }
}
