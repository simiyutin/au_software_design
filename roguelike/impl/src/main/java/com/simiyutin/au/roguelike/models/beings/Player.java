package com.simiyutin.au.roguelike.models.beings;

import com.simiyutin.au.roguelike.util.DelayedTask;
import com.simiyutin.au.roguelike.util.RecurringTask;
import com.simiyutin.au.roguelike.models.Position;
import com.simiyutin.au.roguelike.models.Tile;
import com.simiyutin.au.roguelike.util.Action;
import com.simiyutin.au.roguelike.models.items.*;
import com.simiyutin.au.roguelike.models.World;
import com.simiyutin.au.roguelike.util.WorldFactory;

import java.awt.*;

/**
 * Created by boris on 09.05.17.
 */
public class Player extends ActiveBeing {

    private Weapon weapon;
    private Action action;

    public Player(World world) {
        super(world);

        this.glyph = 'X';
        this.color = Color.BLUE;
        this.weapon = WeaponType.HAND.getItem();
        this.action = new RegularAction();
    }

    public void levelUp() {
        level++;
        World newWorld = WorldFactory.getOfMinLevel(world.getMinLevel() + 1);
        Position pos = newWorld.getEmptyPosition();
        x = pos.x;
        y = pos.y;
        newWorld.setPlayer(this);
        world.moveDataFrom(newWorld);
    }

    public void act() {
        action.act();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public void interactWithEnvironment() {
        int deltaHealth = world.getTile(x, y).getDeltaHealth();
        health += deltaHealth * level;

        ThrownItem thrownItem = world.getItem(x, y);
        if (thrownItem != null) {
            Item item = thrownItem.getItem();
            if (item instanceof Weapon) {
                weapon = (Weapon) item;
                world.getItems().removeIf(w ->
                        w.getItem() instanceof Weapon && ((Weapon) w.getItem()).getLevel() <= weapon.getLevel());
            } else if (item instanceof MedAid) {
                health += ((MedAid) item).getValue();
                health = Math.min(health, 100);
            }
            world.setMessage(String.format("picked %s", item.getName()));
            world.getItems().remove(thrownItem);
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
                weapon = Weapon.getRandomOfLevel(enemy.getLevel() + 2);
                new DelayedTask(() -> world.setMessage(String.format("Obtained %s", weapon.getName())), 1000);
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
