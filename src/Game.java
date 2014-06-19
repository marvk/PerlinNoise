import java.awt.*;
import java.util.Random;

public class Game {
    public final double FPS = 0.2f;
    public final int NANOSECONDS_PER_UPDATE = (int)(1000000000/FPS);
    public final int WIDTH = 800;
    public final int HEIGHT = 800;
    public final int SCALE = 1;
    public final int OCTAVES = 8;

    public final Color[] COLORS = {
            new Color(184, 194, 137),
            new Color(135, 142, 101),
            new Color(104, 99, 72),
            new Color(36, 79, 22),
            new Color(115, 158, 32),
            new Color(230, 230, 135),
            new Color(47, 118, 124),
            new Color(34, 102, 108),
            new Color(25, 57, 72)
    };

    /**
     * This function paints a pseudo-randomly generated map.
     *
     * @param g Graphics2D to draw the map on
     */
    public void draw(Graphics2D g) {
        double[][] noise = Noise.generatePerlinNoise(Noise.generateRandomNoise(WIDTH / SCALE, HEIGHT / SCALE), OCTAVES);

        for (int x = 0; x < WIDTH; x += SCALE) {
            for (int y = 0; y < HEIGHT; y += SCALE) {
                int c = (int)((noise[x][y]*8.0f));

                if (c > 8 || c < 0) c = 8;

                g.setColor(COLORS[c]);
                g.fillRect(x, y, SCALE, SCALE);
            }
        }
    }

    public Color getGreyscale(double d) {
        int c = (int)(d*255.0f);
        return new Color(c, c, c);
    }
}
