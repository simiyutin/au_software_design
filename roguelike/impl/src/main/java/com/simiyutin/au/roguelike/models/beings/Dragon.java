package com.simiyutin.au.roguelike.models.beings;

import com.simiyutin.au.roguelike.util.RecurringTask;
import com.simiyutin.au.roguelike.models.Tile;
import com.simiyutin.au.roguelike.models.World;

import java.awt.*;

/**
 * Created by boris on 11.05.17.
 */
public class Dragon extends ActiveBeing implements ArtificialIntelligence {
    public Dragon(World world) {
        super(world);

        this.glyph = 'D';
        this.color = Color.RED;
        enflameTiles();

        new RecurringTask(this::move, 1000);
    }

    @Override
    public void move() {
        moveRandom();
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        enflameTiles();
    }

    @Override
    protected void interactWithEnvironment() {

    }

    private void enflameTiles() {
        world.setTilesAround(x, y, 0, Tile.ENFLAMED_FLOOR);
    }
}
