package com.simiyutin.au.roguelike.models.items;

import com.simiyutin.au.roguelike.models.Position;
import com.simiyutin.au.roguelike.models.World;

/**
 * Created by boris on 11.05.17.
 */
public class ThrownItem {
    public int x;
    public int y;
    private Item item;

    public ThrownItem(Item item, World world) {
        this.item = item;

        Position position = world.getEmptyPosition();
        this.x = position.x;
        this.y = position.y;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
