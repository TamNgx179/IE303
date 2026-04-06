package Lab2.flappybird;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Bird {
    private int x;
    private int y;
    private final int width;
    private final int height;

    private double verticalSpeed;
    private final double gravityForce;

    private final Image sprite;

    public Bird(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.width = 34;
        this.height = 24;
        this.verticalSpeed = 0;
        this.gravityForce = 0.55;
        this.sprite = new ImageIcon(getClass().getResource("/Lab2/resources/flappybird.png")).getImage();
    }

    public void fall() {
        verticalSpeed += gravityForce;
        y += (int) verticalSpeed;
    }

    public void flyUp() {
        verticalSpeed = -8.0;
    }

    public void resetPosition(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.verticalSpeed = 0;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x, y, width, height, null);
    }

    public Rectangle getHitBox() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBottom() {
        return y + height;
    }
}