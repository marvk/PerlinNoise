import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Game game;
    private final int WIDTH;
    private final int HEIGHT;

    GamePanel(Game game) {
        this.game = game;

        //Default edge length 500p
        this.HEIGHT = 500;
        this.WIDTH = 500;
    }

    GamePanel(Game game, int width, int height) {
        this.game = game;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.lightGray);
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        game.draw(g2);
    }
}
