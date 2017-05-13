package com.simiyutin.au.roguelike;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.trace("application started");
        Scheduler scheduler = new Scheduler();
        LOGGER.trace("start scheduling");
        scheduler.schedule();
    }
}
