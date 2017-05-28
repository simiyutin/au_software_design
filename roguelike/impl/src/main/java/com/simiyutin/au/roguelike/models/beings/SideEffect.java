package com.simiyutin.au.roguelike.models.beings;

import java.awt.event.KeyEvent;
import java.util.function.Function;


/**
 * Entity that maps user input to one of predefined profiles.
 */
public enum SideEffect {

    IDENTITY(Function.identity()),

    INVERSED(key -> {
        switch (key) {
            case KeyEvent.VK_S:
                return KeyEvent.VK_W;
            case KeyEvent.VK_W:
                return KeyEvent.VK_S;
            case KeyEvent.VK_A:
                return KeyEvent.VK_D;
            case KeyEvent.VK_D:
                return KeyEvent.VK_A;
            default:
                return key;
        }
    });

    private Function<Integer, Integer> proxy;

    SideEffect(Function<Integer, Integer> proxy) {
        this.proxy = proxy;
    }

    public Integer apply(Integer key) {
        return proxy.apply(key);
    }
}
