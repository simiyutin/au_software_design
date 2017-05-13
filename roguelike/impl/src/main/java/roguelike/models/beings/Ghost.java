package roguelike.models.beings;

import roguelike.util.RecurringTask;
import roguelike.models.World;

import java.awt.*;

import static roguelike.models.beings.SideEffect.IDENTITY;
import static roguelike.models.beings.SideEffect.INVERSED;

/**
 * Created by boris on 10.05.17.
 */
public class Ghost extends ActiveBeing implements ArtificialIntelligence {

    private static boolean moveTo = true;

    private final int SMELL_RANGE = 15;

    public Ghost(World world) {
        super(world);

        this.glyph = 'G';
        this.color = Color.cyan;

        new RecurringTask(this::move, 200);
    }

    @Override
    protected void interactWithEnvironment() {
        if (distToPlayer(x, y) < 3) {
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
