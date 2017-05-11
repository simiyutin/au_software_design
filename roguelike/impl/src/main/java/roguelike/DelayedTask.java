package roguelike;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by boris on 11.05.17.
 */
public class DelayedTask {

    public DelayedTask(Runnable task, long timeout) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, timeout);
    }
}
