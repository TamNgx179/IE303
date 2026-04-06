package Lab2.flappybird;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Flappy Bird - Java Swing");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);

            GamePanel gamePanel = new GamePanel();
            window.setContentPane(gamePanel);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}