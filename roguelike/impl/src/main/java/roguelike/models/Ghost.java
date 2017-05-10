package roguelike.models;

import roguelike.RecurringTask;

import java.awt.*;
import java.util.Random;

import static roguelike.models.PlayerEffects.IDENTITY;
import static roguelike.models.PlayerEffects.INVERSED;

/**
 * Created by boris on 10.05.17.
 */
public class Ghost extends ActiveBeing implements ArtificialIntelligence {

    private static boolean moveTo = true;

    private RecurringTask task;
    private final int SMELL_RANGE = 15;
    private boolean alive = true;

    public Ghost(World world) {
        super(world);

        this.glyph = 'G';
        this.color = Color.cyan;

        task = new RecurringTask(this::move, 100);
    }

    @Override
    protected void interactWithEnvironment() {
        if (distToPlayer(x, y) == 1) {
            PlayerEffects effect = world.getPlayer().getEffect();
            if (effect == INVERSED) {
                world.getMobs().remove(this);
                world.getPlayer().setEffect(IDENTITY);
                alive = false;
            } else {
                world.getPlayer().setEffect(INVERSED);
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
                if (distToPlayer(x + i, y + j) < minDist) {
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
                if (distToPlayer(x + i, y + j) > maxDist) {
                    maxDist = distToPlayer(x + i, y + j);
                    dx = i;
                    dy = j;
                }
            }
        }
        move(dx, dy);
    }

    private void moveRandom() {
        move(new Random().nextInt(3) - 1, new Random().nextInt(3) - 1);
    }

    private double distToPlayer(int fromX, int fromY) {
        return Math.hypot(fromX - world.getPlayer().x, fromY - world.getPlayer().y);
    }
}
