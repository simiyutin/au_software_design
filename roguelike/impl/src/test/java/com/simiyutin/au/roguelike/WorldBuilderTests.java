package com.simiyutin.au.roguelike;

import com.simiyutin.au.roguelike.models.World;
import com.simiyutin.au.roguelike.models.beings.*;
import com.simiyutin.au.roguelike.models.items.Item;
import com.simiyutin.au.roguelike.models.items.MedAid;
import com.simiyutin.au.roguelike.models.items.ThrownItem;
import com.simiyutin.au.roguelike.models.items.Weapon;
import com.simiyutin.au.roguelike.util.WorldBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import sun.java2d.pipe.hw.AccelDeviceEventListener;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class WorldBuilderTests {

    @Test
    public void testSimple() {
        World world = new WorldBuilder(100, 100).build();
        assertThat(world.getHeight(), is(100));
        assertThat(world.getWidth(), is(100));
        assertTrue(world.getItems().isEmpty());
        assertTrue(world.getMobs().isEmpty());
        assertTrue(world.getPlayer().isAlive());
        assertThat(world.getPlayer().getWeapon(), CoreMatchers.notNullValue());
    }



    @Test
    public void testMobs() {
        World world = new WorldBuilder(100, 100)
                .withCaves()
                .addMobs(Mushroom.class, 10)
                .addMobs(Ghost.class, 10)
                .addMobs(Dragon.class, 10)
                .build();

        assertThat(world.getMobs().stream().filter(m -> m instanceof Mushroom).count(), is(10L));
        assertThat(world.getMobs().stream().filter(m -> m instanceof Ghost).count(), is(10L));
        assertThat(world.getMobs().stream().filter(m -> m instanceof Dragon).count(), is(10L));
    }

    @Test
    public void testMinLevel() {
        World world = new WorldBuilder(100, 100)
                .withCaves()
                .ofMinLevel(10)
                .addMobs(Mushroom.class, 10)
                .addMobs(Ghost.class, 10)
                .addMobs(Dragon.class, 10)
                .addWeapons(10)
                .build();

        assertThat(world.getMinLevel(), is(10));

        for (Being mob : world.getMobs()) {
            if (mob instanceof ActiveBeing) {
                assertTrue(((ActiveBeing) mob).getLevel() >= world.getMinLevel());
            }
        }

        for (ThrownItem item : world.getItems()) {
            if (item.getItem() instanceof Weapon) {
                Weapon w = ((Weapon) item.getItem());
                assertTrue(w.getLevel() >= world.getMinLevel());
            }
        }
    }


    @Test
    public void testWeapons() {
        World world = new WorldBuilder(100, 100)
                .withCaves()
                .addWeapons(10)
                .build();

        assertThat(world.getItems().stream().map(ThrownItem::getItem).filter(i -> i instanceof Weapon).count(), is(10L));

        for (Item item : world.getItems().stream().map(ThrownItem::getItem).filter(i -> i instanceof Weapon).collect(Collectors.toList())) {
            Weapon weapon = (Weapon) item;
            assertTrue(weapon.getLevel() >= world.getMinLevel());
        }
    }

    @Test
    public void testMedAids() {
        World world = new WorldBuilder(100, 100)
                .withCaves()
                .addWeapons(10)
                .addMedAids(10)
                .build();

        assertThat(world.getItems().stream().map(ThrownItem::getItem).filter(i -> i instanceof MedAid).count(), is(10L));
    }
}
