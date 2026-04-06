package Lab2.flappybird;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Pipe {
    private int x;
    private final int y;
    private final int width;
    private final int height;
    private final Image texture;

    private boolean counted;

    public Pipe(int x, int y, int width, int height, Image texture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.counted = false;
    }

    public void moveLeft(int speed) {
        x -= speed;
    }

    public void render(Graphics g) {
        g.drawImage(texture, x, y, width, height, null);
    }

    public Rectangle getHitBox() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isOutOfScreen() {
        return x + width < 0;
    }

    public boolean isTopPipe() {
        return y == 0;
    }

    public int getRightEdge() {
        return x + width;
    }

    public int getX() {
        return x;
    }

    public boolean isCounted() {
        return counted;
    }

    public void setCounted(boolean counted) {
        this.counted = counted;
    }
}