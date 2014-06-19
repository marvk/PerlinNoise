import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private Game game;

    public GameFrame(Game game) {
        super("Perlin Noise");
        this.game = game;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        GamePanel panel = new GamePanel(game, game.WIDTH, game.HEIGHT);
        add(panel);

        pack();
        setVisible(true);
    }
}
