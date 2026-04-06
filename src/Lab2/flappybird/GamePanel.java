package Lab2.flappybird;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final int GAME_WIDTH = 360;
    private static final int GAME_HEIGHT = 640;

    private static final int PIPE_WIDTH = 52;
    private static final int PIPE_GAP = 150;
    private static final int PIPE_SPEED = 3;

    private static final int BIRD_START_X = 80;
    private static final int BIRD_START_Y = 280;

    // mốc nơi ống dưới chạm tới, chính là vùng cỏ xanh sọc
    private static final int GROUND_TOP = 592;

    private final Bird bird;
    private final ArrayList<Pipe> pipeList;
    private final Random random;
    private final Timer loopTimer;

    private final Image background;
    private final Image topPipeImage;
    private final Image bottomPipeImage;

    private boolean started;
    private boolean ended;
    private int score;
    private long lastSpawnTime;

    public GamePanel() {
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setFocusable(true);

        background = new ImageIcon(getClass().getResource("/Lab2/resources/flappybirdbg.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("/Lab2/resources/toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("/Lab2/resources/bottompipe.png")).getImage();

        bird = new Bird(BIRD_START_X, BIRD_START_Y);
        pipeList = new ArrayList<>();
        random = new Random();

        started = false;
        ended = false;
        score = 0;
        lastSpawnTime = System.currentTimeMillis();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    triggerAction();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                triggerAction();
            }
        });

        loopTimer = new Timer(16, this);
        loopTimer.start();
    }

    private void triggerAction() {
        requestFocusInWindow();

        if (ended) {
            restartGame();
            return;
        }

        if (!started) {
            started = true;
        }

        bird.flyUp();
    }

    private void restartGame() {
        bird.resetPosition(BIRD_START_X, BIRD_START_Y);
        pipeList.clear();
        started = false;
        ended = false;
        score = 0;
        lastSpawnTime = System.currentTimeMillis();
        repaint();
    }

    private void updateWorld() {
        bird.fall();
        generatePipePair();
        movePipes();
        detectScore();
        detectCrash();
    }

    private void generatePipePair() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastSpawnTime < 1500) {
            return;
        }

        int topPipeHeight = 120 + random.nextInt(180);
        int bottomPipeY = topPipeHeight + PIPE_GAP;
        int bottomPipeHeight = GROUND_TOP - bottomPipeY;

        pipeList.add(new Pipe(GAME_WIDTH, 0, PIPE_WIDTH, topPipeHeight, topPipeImage));
        pipeList.add(new Pipe(GAME_WIDTH, bottomPipeY, PIPE_WIDTH, bottomPipeHeight, bottomPipeImage));

        lastSpawnTime = currentTime;
    }

    private void movePipes() {
        Iterator<Pipe> iterator = pipeList.iterator();

        while (iterator.hasNext()) {
            Pipe pipe = iterator.next();
            pipe.moveLeft(PIPE_SPEED);

            if (pipe.isOutOfScreen()) {
                iterator.remove();
            }
        }
    }

    private void detectScore() {
        for (Pipe pipe : pipeList) {
            if (pipe.isTopPipe() && !pipe.isCounted() && pipe.getRightEdge() < bird.getX()) {
                pipe.setCounted(true);
                score++;
            }
        }
    }

    private void detectCrash() {
        if (bird.getY() < 0 || bird.getBottom() >= GROUND_TOP) {
            ended = true;
            return;
        }

        for (Pipe pipe : pipeList) {
            if (bird.getHitBox().intersects(pipe.getHitBox())) {
                ended = true;
                return;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (started && !ended) {
            updateWorld();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);

        for (Pipe pipe : pipeList) {
            pipe.render(g);
        }

        bird.render(g);
        drawScore(g);

        if (!started && !ended) {
            drawCenteredText(g, "Press SPACE or Click to Start", 300, 22);
        }

        if (ended) {
            drawCenteredText(g, "Game Over", 285, 42);
            drawCenteredText(g, "Score: " + score, 340, 24);
            drawCenteredText(g, "Press SPACE or Click to Restart", 395, 18);
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        String text = String.valueOf(score);

        FontMetrics metrics = g.getFontMetrics();
        int x = (GAME_WIDTH - metrics.stringWidth(text)) / 2;

        g.drawString(text, x, 80);
    }

    private void drawCenteredText(Graphics g, String text, int y, int size) {
        g.setFont(new Font("Arial", Font.BOLD, size));
        FontMetrics metrics = g.getFontMetrics();

        int x = (GAME_WIDTH - metrics.stringWidth(text)) / 2;

        g.setColor(Color.BLACK);
        g.drawString(text, x + 2, y + 2);

        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }
}