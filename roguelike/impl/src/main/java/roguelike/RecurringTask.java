package roguelike;

import java.util.TimerTask;

/**
 * Created by boris on 10.05.17.
 */
public class RecurringTask {
    private java.util.Timer timer;

    public RecurringTask(Runnable task, long period) {
        this.timer = new java.util.Timer(true);
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, 0, period);
    }
}
