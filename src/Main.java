public class Main {
    public static void main(String[] args) {
        double tickTime, lastTick;

        Game game = new Game();
        GameFrame frame = new GameFrame(game);

        //Game loop
        for (;;) {
            tickTime = System.nanoTime();

            frame.repaint();

            lastTick = tickTime;

            while (tickTime - lastTick < game.NANOSECONDS_PER_UPDATE) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ignored) {}

                tickTime = System.nanoTime();
            }
        }
    }
}
