package com.simiyutin.au.roguelike.util;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Runnable with delayed start.
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
