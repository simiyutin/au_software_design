package roguelike.models;

import roguelike.RecurringTask;

import java.awt.*;
import java.util.Random;

/**
 * Created by boris on 10.05.17.
 */
public class Ghost extends ActiveBeing implements ArtificialIntelligence {

    private RecurringTask task;

    public Ghost(World world) {
        super(world);

        this.glyph = 'G';
        this.color = Color.cyan;

        task = new RecurringTask(this::move, 100);
    }

    @Override
    protected void getEnvironmentEffects() {

    }

    @Override
    public void move() {
        move(new Random().nextInt(3) - 1, new Random().nextInt(3) - 1);
    }
}
