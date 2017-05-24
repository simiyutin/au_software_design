package com.simiyutin.au.roguelike;

import com.simiyutin.au.roguelike.models.Tile;
import com.simiyutin.au.roguelike.models.World;
import com.simiyutin.au.roguelike.models.beings.*;
import com.simiyutin.au.roguelike.models.items.MedAid;
import com.simiyutin.au.roguelike.models.items.ThrownItem;
import com.simiyutin.au.roguelike.models.items.Weapon;
import com.simiyutin.au.roguelike.models.items.WeaponType;
import com.simiyutin.au.roguelike.util.WorldBuilder;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by boris on 13.05.17.
 */
public class MobsTests {

    private World world;
    private Player player;

    @Test
    public void testAlive() {
        init();
        assertTrue(player.isAlive());
    }

    @Test
    public void testCanMove() {
        init();
        world.setTile(50, 51, Tile.WALL);
        assertFalse(player.canMove(50, 51));
    }

    @Test
    public void testMove() {
        init();
        int prevX = player.x;
        player.move(1, 0);
        assertThat(player.x, is(prevX + 1));
    }

    @Test
    public void testMoveToPoisonedFloor() {
        init();
        world.setTile(50, 51, Tile.POISONED_FLOOR);
        int healthBefore = player.getHealth();
        player.move(0, 1);
        assertTrue(player.getHealth() < healthBefore);
    }

    @Test
    public void testMoveOverEdge() {
        init();
        player.x = 0;
        player.y = 0;
        player.move(-1, 0);
        assertThat(player.x, is(0));
    }

    @Test
    public void testMoveImmobilized() {
        init();
        player.setImmobilized(true);
        int prevX = player.x;
        player.move(1, 0);
        assertThat(player.x, is(prevX));

    }

    @Test
    public void testAct() {
        init();
        world.setTile(50, 51, Tile.WALL);
        player.move(0, 1);
        player.act();
        assertThat(world.getTile(50, 51), is(Tile.FLOOR));
    }

    @Test
    public void testGhost() {
        init();
        Ghost.isSelfActing = false;
        Ghost ghost = new Ghost(world);
        world.getMobs().add(ghost);
        ghost.x = 50 + Ghost.TRIGGER_RANGE;
        ghost.y = 50;
        ghost.interactWithEnvironment();
        assertThat(player.getEffect(), is(SideEffect.IDENTITY));
        ghost.x--;
        ghost.interactWithEnvironment();
        assertThat(player.getEffect(), is(SideEffect.INVERSED));

        ghost.interactWithEnvironment();
        assertThat(player.getEffect(), is(SideEffect.IDENTITY));
        assertTrue(world.getMobs().isEmpty());
        assertFalse(ghost.isAlive());
    }

    @Test
    public void testDragon() throws InterruptedException {
        init();
        Dragon dragon = new Dragon(world);
        world.getMobs().add(dragon);
        dragon.x = 50;
        dragon.y = 51;

        int dragonHealthBefore = dragon.getHealth();
        int playerHealthBefore = player.getHealth();

        player.act();
        sleep(1000);
        player.act();

        assertTrue(player.getHealth() < playerHealthBefore);
        assertTrue(dragon.getHealth() < dragonHealthBefore);

    }

    @Test
    public void testMushroom() {
        init();
        Mushroom mushroom = new Mushroom(world);
        int healthBefore = player.getHealth();
        player.x = mushroom.x;
        player.y = mushroom.y + 1;
        assertThat(world.getTile(player.x, player.y), is(Tile.POISONED_FLOOR));
        player.move(1, 0);
        assertTrue(player.getHealth() < healthBefore);
    }

    @Test
    public void testPickUpWeapon() {
        init();
        ThrownItem sword = new ThrownItem(new Weapon(WeaponType.SWORD, 1), world);
        world.getItems().add(sword);
        player.x = sword.x;
        player.y = sword.y;

        player.interactWithEnvironment();
        assertThat(player.getWeapon() , is(sword.getItem()));
        assertTrue(world.getItems().isEmpty());
    }

    @Test
    public void testPickUpMed() {
        init();
        ThrownItem medAidPos = new ThrownItem(new MedAid(), world);
        MedAid medAid = ((MedAid) medAidPos.getItem());

        world.getItems().add(medAidPos);
        player.x = medAidPos.x;
        player.y = medAidPos.y;
        int prevHealth = player.getHealth();
        player.setHealth(player.getHealth() - medAid.getValue() - 10);
        player.interactWithEnvironment();

        assertThat(player.getHealth(), is(prevHealth - 10));
        assertTrue(world.getItems().isEmpty());
    }


    private void init() {
        world = new WorldBuilder(100, 100)
                .build();

        player = world.getPlayer();
        player.x = 50;
        player.y = 50;
    }
}
