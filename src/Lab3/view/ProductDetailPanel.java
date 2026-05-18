package Lab3.view;

import Lab3.model.Product;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

class ProductDetailPanel extends JPanel {
    private static final Color TEXT_DARK = new Color(75, 75, 78);
    private static final Color TEXT_MUTED = new Color(150, 150, 152);
    private static final Color SEPARATOR = new Color(180, 188, 198);

    private final Map<String, BufferedImage> imageCache;
    private final Timer animationTimer;

    private Product currentProduct;
    private Product nextProduct;
    private float progress;

    ProductDetailPanel() {
        imageCache = new HashMap<>();
        progress = 1f;

        setOpaque(true);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(305, 510));
        setMinimumSize(new Dimension(305, 510));

        animationTimer = new Timer(16, event -> updateAnimation());
    }

    void showProduct(Product product) {
        if (currentProduct == null) {
            currentProduct = product;
            progress = 1f;
            repaint();
            return;
        }

        if (currentProduct.getId() == product.getId()) {
            return;
        }

        if (animationTimer.isRunning()) {
            animationTimer.stop();
            currentProduct = nextProduct != null ? nextProduct : currentProduct;
        }

        nextProduct = product;
        progress = 0f;
        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (currentProduct == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (nextProduct == null) {
            drawProduct(g2, currentProduct, 1f, 0);
        } else {
            float eased = easeOut(progress);
            drawProduct(g2, currentProduct, 1f - eased, Math.round(-22 * eased));
            drawProduct(g2, nextProduct, eased, Math.round(22 * (1f - eased)));
        }

        g2.dispose();
    }

    private void updateAnimation() {
        progress += 0.075f;
        if (progress >= 1f) {
            progress = 1f;
            currentProduct = nextProduct;
            nextProduct = null;
            animationTimer.stop();
        }
        repaint();
    }

    private void drawProduct(Graphics2D g2, Product product, float alpha, int offsetX) {
        if (alpha <= 0f) {
            return;
        }

        Graphics2D layer = (Graphics2D) g2.create();
        layer.setComposite(AlphaComposite.SrcOver.derive(alpha));
        layer.translate(offsetX, 0);

        BufferedImage image = getImage(product);
        ImageAssets.drawProduct(layer, image, 16, 22, 270, 160);

        int separatorY = 166;
        layer.setColor(SEPARATOR);
        layer.drawLine(16, separatorY, getWidth() - 10, separatorY);

        layer.setColor(TEXT_DARK);
        layer.setFont(new Font("Arial", Font.BOLD, 21));
        drawTextWithEllipsis(layer, product.getName(), 16, separatorY + 36, getWidth() - 32);

        layer.setFont(new Font("Arial", Font.BOLD, 21));
        layer.drawString(product.getFormattedPrice(), 16, separatorY + 68);

        layer.setFont(new Font("Arial", Font.PLAIN, 12));
        layer.drawString(product.getBrand(), 16, separatorY + 92);

        layer.setColor(TEXT_MUTED);
        layer.setFont(new Font("Arial", Font.BOLD, 14));
        drawWrappedText(layer, product.getDescription(), 16, separatorY + 116, getWidth() - 38, 18, 3);

        layer.dispose();
    }

    private BufferedImage getImage(Product product) {
        BufferedImage cached = imageCache.get(product.getImagePath());
        if (cached != null) {
            return cached;
        }

        BufferedImage image = ImageAssets.load(product.getImagePath());
        imageCache.put(product.getImagePath(), image);
        return image;
    }

    private float easeOut(float value) {
        float remaining = 1f - value;
        return 1f - remaining * remaining * remaining;
    }

    private void drawTextWithEllipsis(Graphics2D g2, String text, int x, int baseline, int maxWidth) {
        FontMetrics metrics = g2.getFontMetrics();
        if (metrics.stringWidth(text) <= maxWidth) {
            g2.drawString(text, x, baseline);
            return;
        }

        String ellipsis = "...";
        int targetWidth = Math.max(0, maxWidth - metrics.stringWidth(ellipsis));
        String clipped = text;
        while (!clipped.isEmpty() && metrics.stringWidth(clipped) > targetWidth) {
            clipped = clipped.substring(0, clipped.length() - 1);
        }
        g2.drawString(clipped + ellipsis, x, baseline);
    }

    private void drawWrappedText(Graphics2D g2, String text, int x, int y, int maxWidth, int lineHeight, int maxLines) {
        FontMetrics metrics = g2.getFontMetrics();
        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();
        int lineCount = 0;

        for (String word : words) {
            String candidate = line.length() == 0 ? word : line + " " + word;
            if (metrics.stringWidth(candidate) <= maxWidth) {
                line = new StringBuilder(candidate);
                continue;
            }

            if (line.length() > 0) {
                g2.drawString(line.toString(), x, y + lineCount * lineHeight);
                lineCount++;
            }

            if (lineCount == maxLines) {
                return;
            }

            line = new StringBuilder(word);
        }

        if (line.length() > 0 && lineCount < maxLines) {
            g2.drawString(line.toString(), x, y + lineCount * lineHeight);
        }
    }
}
