package com.simiyutin.au.roguelike.models.beings;

import com.google.common.collect.Iterators;
import com.simiyutin.au.roguelike.models.Action;
import com.simiyutin.au.roguelike.models.Position;
import com.simiyutin.au.roguelike.models.Tile;
import com.simiyutin.au.roguelike.models.World;
import com.simiyutin.au.roguelike.models.items.*;
import com.simiyutin.au.roguelike.util.DelayedTask;
import com.simiyutin.au.roguelike.util.RecurringTask;
import com.simiyutin.au.roguelike.util.WorldFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Player himself. Can move around, pick up weapons and health packs, dig through walls and attack dragons.
 */
public class Player extends ActiveBeing {

    private static final Logger LOGGER = LogManager.getLogger(Player.class);
    private Weapon weapon;
    private Iterator<Weapon> weaponIterator;
    private Action action;

    public Player(World world) {
        super(world);

        this.glyph = 'X';
        this.color = Color.BLUE;
        this.weapon = WeaponType.HAND.getItem();
        this.weaponIterator = Iterators.cycle(this.weapon);
        this.action = new RegularAction();
    }


    /**
     * Each level of player is followed by world of incremented level.
     */
    public void levelUp() {
        level++;
        World newWorld = WorldFactory.getDefaultConfigurationOfMinLevel(world.getMinLevel() + 1);
        Position pos = newWorld.getEmptyPosition();
        x = pos.x;
        y = pos.y;
        newWorld.setPlayer(this);
        world.moveDataFrom(newWorld);
        LOGGER.trace("player level upped");
    }


    /**
     * Do something on pressing [enter] key. "something" is taken from context.
     */
    public void act() {
        action.act();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void changeWeapon() {
        weapon = weaponIterator.next();
    }

    private void addWeapon(Weapon newWeapon) {
        List<Weapon> weapons = linearizeWeapons();
        if (!weapons.contains(newWeapon)) {
            weapons.add(newWeapon);
        }
        weaponIterator = Iterators.cycle(weapons);
        changeWeapon();
    }

    private List<Weapon> linearizeWeapons() {
        List<Weapon> list = new ArrayList<>();


        list.add(weapon);
        Weapon next = weaponIterator.next();
        while (next != weapon) {
            list.add(next);
            next = weaponIterator.next();
        }

        return list;
    }

    @Override
    public void interactWithEnvironment() {
        int deltaHealth = world.getTile(x, y).getDeltaHealth();
        health += deltaHealth * level;

        ThrownItem thrownItem = world.getItem(x, y);
        if (thrownItem != null) {
            Item item = thrownItem.getItem();
            if (item instanceof Weapon) {
                LOGGER.trace(String.format("picked up weapon: %s", item.getName()));
                addWeapon((Weapon) item);
                world.getThrownItems().removeIf(w ->
                        w.getItem() instanceof Weapon && ((Weapon) w.getItem()).getLevel() <= weapon.getLevel());
            } else if (item instanceof MedAid) {
                LOGGER.trace("picked up med aid");
                health += ((MedAid) item).getValue();
                health = Math.min(health, 100);
            }
            world.setMessage(String.format("picked %s", item.getName()));
            world.getThrownItems().remove(thrownItem);
        }

        Being being = getMobNearMe();
        if (being != null) {
            if (being instanceof Dragon) { // todo inheritance
                world.setMessage("press [enter] to start battle with dragon");
            }
        }

        if (world.getTile(x, y) == Tile.Z_TELEPORT) {
            levelUp();
        }
    }

    private Being getMobNearMe() {
        for (Being b : world.getMobs()) {
            if (distTo(b.x, b.y) == 1) {
                return b;
            }
        }

        return null;
    }

    private void startBattleWithDragon(Dragon dragon) {
        setImmobilized(true);
        dragon.setImmobilized(true);
        action = new AttackingAction(dragon);
        LOGGER.trace("started battle with dragon");
        new RecurringTask(() -> {
            if (dragon.isAlive()) {
                int harm = dragon.getLevel();
                setHealth(health - harm);
            }
        }, 500);
    }

    private double distTo(int toX, int toY) {
        return Math.hypot(toX - x, toY - y);
    }

    /**
     * Describes how to act in normal mode.
     */
    class RegularAction implements Action {

        @Override
        public void act() {
            if (world.getTile(xDirection, yDirection).isDiggable()) {
                world.setTile(xDirection, yDirection, Tile.FLOOR);
            }

            Being being = getMobNearMe();
            if (being != null) {
                if (being instanceof Dragon) { // todo inheritance
                    startBattleWithDragon((Dragon) being);
                }
            }
        }
    }

    /**
     * Describes how to act in attacking mode.
     */
    class AttackingAction implements Action {
        private ActiveBeing enemy;

        public AttackingAction(ActiveBeing enemy) {
            this.enemy = enemy;
        }

        @Override
        public void act() {
            int harm = level * weapon.getLevel() + weapon.getHarm();
            int health = enemy.getHealth();
            enemy.setHealth(health - harm);
            world.setMessage(String.format("enemy health: %d", enemy.getHealth()));
            if (enemy.getHealth() < 0) {
                world.setMessage("You won!");
                LOGGER.trace("player won battle with dragon");
                Weapon newWeapon = Weapon.getRandomOfLevel(enemy.getLevel() + 2);
                addWeapon(newWeapon);
                new DelayedTask(() -> world.setMessage(String.format("Obtained %s", newWeapon.getName())), 1000);
                world.getMobs().remove(enemy);
                enemy.setAlive(false);
                setImmobilized(false);
                action = new RegularAction();
                if (world.getMobs().stream().noneMatch(m -> m instanceof Dragon)) {
                    world.setMessage("Killed last dragon. Find Z tile to proceed to next level");
                    Position position = world.getEmptyPosition();
                    world.setTile(position.x, position.y, Tile.Z_TELEPORT);
                }
            }
        }
    }
}
