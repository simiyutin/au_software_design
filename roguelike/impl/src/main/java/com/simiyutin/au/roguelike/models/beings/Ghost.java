package com.simiyutin.au.roguelike.models.beings;

import com.simiyutin.au.roguelike.util.RecurringTask;
import com.simiyutin.au.roguelike.models.World;

import java.awt.*;

import static com.simiyutin.au.roguelike.models.beings.SideEffect.IDENTITY;
import static com.simiyutin.au.roguelike.models.beings.SideEffect.INVERSED;


public class Ghost extends ActiveBeing implements ArtificialIntelligence {

    private static boolean moveTo = true;

    public static final int SMELL_RANGE = 15;
    public static final int TRIGGER_RANGE = 3;
    public static boolean isSelfActing = true;

    public Ghost(World world) {
        super(world);

        this.glyph = 'G';
        this.color = Color.cyan;

        if (isSelfActing) {
            new RecurringTask(this::move, 200);
        }
    }

    @Override
    public void interactWithEnvironment() {
        if (distToPlayer(x, y) < TRIGGER_RANGE) {
            SideEffect effect = world.getPlayer().getEffect();
            if (effect == INVERSED) {
                world.getMobs().remove(this);
                world.getPlayer().setEffect(IDENTITY);
                world.setMessage("Controls back to normal");
                alive = false;
            } else {
                world.getPlayer().setEffect(INVERSED);
                world.setMessage("Controls inversed!");
            }
            moveTo = !moveTo;
        }
    }

    @Override
    public void move() {

        if (!alive) return;

        if (distToPlayer(x, y) < SMELL_RANGE) {
            if (moveTo) {
                moveToPlayer();
            } else {
                moveFromPlayer();
            }
        } else {
            moveRandom();
        }

    }

    private void moveToPlayer() {
        int dx = 0;
        int dy = 0;
        double minDist = Double.POSITIVE_INFINITY;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (distToPlayer(x + i, y + j) < minDist && canMove(x + i, y + j)) {
                    minDist = distToPlayer(x + i, y + j);
                    dx = i;
                    dy = j;
                }
            }
        }
        move(dx, dy);
    }

    private void moveFromPlayer() {
        int dx = 0;
        int dy = 0;
        double maxDist = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (distToPlayer(x + i, y + j) > maxDist && canMove(x + i, y + j)) {
                    maxDist = distToPlayer(x + i, y + j);
                    dx = i;
                    dy = j;
                }
            }
        }
        move(dx, dy);
    }


    private double distToPlayer(int fromX, int fromY) {
        return Math.hypot(fromX - world.getPlayer().x, fromY - world.getPlayer().y);
    }
}
