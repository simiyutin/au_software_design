package com.simiyutin.au.roguelike.models.items;

import com.simiyutin.au.roguelike.models.Position;
import com.simiyutin.au.roguelike.models.World;


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
}
