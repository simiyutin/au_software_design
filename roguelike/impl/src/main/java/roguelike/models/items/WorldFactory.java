package roguelike.models.items;

import roguelike.models.World;
import roguelike.util.WorldBuilder;
import roguelike.models.beings.Dragon;
import roguelike.models.beings.Ghost;
import roguelike.models.beings.Mushroom;

/**
 * Created by boris on 13.05.17.
 */
public class WorldFactory {
    public static World getOfMinLevel(int level) {
        World result = new WorldBuilder(100, 100)
                .ofMinLevel(level)
                .makeCaves()
                .addMobs(Mushroom.class, 10)
                .addMobs(Ghost.class, 0)
                .addMobs(Dragon.class, 1)
                .addWeapons(5)
                .addMedAids(10)
                .build();

        return result;
    }
}
