package roguelike;

import java.awt.*;
import java.util.Random;

/**
 * Created by boris on 13.05.17.
 */
public class RandomColorGenerator {
    private final Random randomGen = new Random(42);

    public Color getColor() {
        return Color.getHSBColor(getComponent(), 0.8f, 0.4f);
    }

    private float getComponent() {
        return (float) randomGen.nextDouble();
    }
}
