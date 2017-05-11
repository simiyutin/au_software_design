package roguelike.models.actions;

import roguelike.RecurringTask;
import roguelike.models.World;
import roguelike.models.beings.ActiveBeing;

/**
 * Created by boris on 11.05.17.
 */
public interface Battle {

    void doBattle(World world, ActiveBeing enemy);
}
