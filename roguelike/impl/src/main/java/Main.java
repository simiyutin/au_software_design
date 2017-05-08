/**
 * Created by boris on 08.05.17.
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        final int framerate = 60;
        Scheduler scheduler = new Scheduler(framerate);
        scheduler.schedule(game);
    }
}
