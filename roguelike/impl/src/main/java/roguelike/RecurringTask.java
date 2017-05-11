package roguelike;

import java.util.TimerTask;

/**
 * Created by boris on 10.05.17.
 */
public class RecurringTask {

    public RecurringTask(Runnable task, long period) {
        java.util.Timer timer = new java.util.Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, 0, period);
    }
}
