package com.simiyutin.au.roguelike.models.beings;

import com.simiyutin.au.roguelike.models.World;

import java.util.Random;


/**
 * Beings that can move and interact with environment
 */
public abstract class ActiveBeing extends Being {

    protected int xDirection;
    protected int yDirection;
    protected int health;
    protected int level;
    protected boolean immobilized;
    protected boolean alive;
    private SideEffect effect;

    public ActiveBeing(World world) {
        super(world);
        this.health = 100;
        this.effect = SideEffect.IDENTITY;
        this.level = world.getMinLevel();
        this.immobilized = false;
        this.alive = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setImmobilized(boolean immobilized) {
        this.immobilized = immobilized;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Try to move being by given delta
     */
    public void move(int dx, int dy) {
        if (immobilized) {
            return;
        }
        xDirection = Math.max(0, Math.min(x + dx, world.getWidth() - 1));
        yDirection = Math.max(0, Math.min(y + dy, world.getHeight() - 1));
        if (canMove(xDirection, yDirection)) {
            x = xDirection;
            y = yDirection;
        }

        interactWithEnvironment();
    }

    public SideEffect getEffect() {
        return effect;
    }

    protected void setEffect(SideEffect effect) {
        this.effect = effect;
    }

    /**
     * Check if map at given position is walkable and free from other mobs
     */
    public boolean canMove(int xTo, int yTo) {
        return world.getTile(xTo, yTo).isWalkable() && world.getMob(xTo, yTo) == null;
    }

    public abstract void interactWithEnvironment();

    protected void moveRandom() {
        move(new Random().nextInt(3) - 1, new Random().nextInt(3) - 1);
    }
}
