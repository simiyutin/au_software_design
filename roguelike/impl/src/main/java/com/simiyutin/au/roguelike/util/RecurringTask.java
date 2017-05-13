package com.simiyutin.au.roguelike.util;

import java.util.TimerTask;


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
