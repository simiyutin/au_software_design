package com.simiyutin.au.roguelike.models.beings;

import com.simiyutin.au.roguelike.models.Tile;
import com.simiyutin.au.roguelike.models.World;
import com.simiyutin.au.roguelike.util.RecurringTask;

import java.awt.*;


/**
 * Walking mob with AI. It moves randomly, inflames floor below. When getting close, player can attack dragon,
 * and if wins, gets weapon.
 */
public class Dragon extends ActiveBeing implements ArtificialIntelligence {

    public static boolean isSelfActing = true;

    public Dragon(World world) {
        super(world);

        this.glyph = 'D';
        this.color = Color.RED;
        inflameTiles();


        if (isSelfActing) {
            new RecurringTask(this::move, 1000);
        }
    }

    @Override
    public void move() {
        moveRandom();
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        inflameTiles();
    }

    @Override
    public void interactWithEnvironment() {

    }

    private void inflameTiles() {
        world.setTilesAround(x, y, 0, Tile.INFLAMED_FLOOR);
    }
}
