package com.simiyutin.au.roguelike.models.beings;

import com.simiyutin.au.roguelike.models.Tile;
import com.simiyutin.au.roguelike.models.World;

import java.awt.*;


/**
 * Static being. Poisons tiles around self and does nothing else.
 */
public class Mushroom extends Being {

    public Mushroom(World world) {
        super(world);

        this.glyph = 'T';
        this.color = Color.GREEN;
        poisonTiles();

    }

    private void poisonTiles() {
        world.setTilesAround(x, y, 3, Tile.POISONED_FLOOR);
    }
}
