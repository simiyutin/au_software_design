package com.simiyutin.au.roguelike;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by boris on 08.05.17.
 */
public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.schedule();
        LOGGER.trace("application started");
    }
}
