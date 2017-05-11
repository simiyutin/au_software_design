package roguelike.models;

import java.awt.event.KeyEvent;
import java.util.function.Function;

/**
 * Created by boris on 10.05.17.
 */
public enum PlayerEffects {

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
                return 0;
        }
    });

    private Function<Integer, Integer> proxy;

    PlayerEffects(Function<Integer, Integer> proxy) {
        this.proxy = proxy;
    }

    public Integer apply(Integer key) {
        return proxy.apply(key);
    }
}
