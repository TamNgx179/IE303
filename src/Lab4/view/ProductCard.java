package Lab4.view;

import Lab4.model.Product;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

class ProductCard extends JPanel {
    private static final Color CARD_BACKGROUND = new Color(241, 241, 241);
    private static final Color CARD_HOVER = new Color(235, 235, 235);
    private static final Color TEXT_DARK = new Color(77, 77, 80);
    private static final Color TEXT_LIGHT = new Color(172, 172, 174);
    private static final Color SELECTED_BORDER = new Color(86, 145, 255);

    private final Product product;
    private final BufferedImage image;
    private Consumer<Product> selectionListener;
    private boolean selected;
    private boolean hover;

    ProductCard(Product product) {
        this.product = product;
        this.image = ImageAssets.load(product.getImagePath());

        setPreferredSize(new Dimension(200, 238));
        setMinimumSize(new Dimension(200, 238));
        setMaximumSize(new Dimension(200, 238));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent event) {
                hover = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent event) {
                if (selectionListener != null) {
                    selectionListener.accept(product);
                }
            }
        });
    }

    Product getProduct() {
        return product;
    }

    void setSelectionListener(Consumer<Product> selectionListener) {
        this.selectionListener = selectionListener;
    }

    void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int arc = 10;
        g2.setColor(hover ? CARD_HOVER : CARD_BACKGROUND);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        if (selected) {
            g2.setColor(SELECTED_BORDER);
            g2.setStroke(new BasicStroke(1.4f));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        }

        g2.setColor(TEXT_DARK);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        drawTextWithEllipsis(g2, product.getName(), 10, 29, getWidth() - 20);

        g2.setColor(TEXT_LIGHT);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        drawTextWithEllipsis(g2, product.getDescription(), 10, 56, getWidth() - 20);

        ImageAssets.drawProduct(g2, image, 16, 78, getWidth() - 32, 86);

        g2.setColor(TEXT_DARK);
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.drawString(product.getBrand(), 10, getHeight() - 15);

        g2.setFont(new Font("Arial", Font.BOLD, 21));
        String price = product.getFormattedPrice();
        FontMetrics metrics = g2.getFontMetrics();
        g2.drawString(price, getWidth() - metrics.stringWidth(price) - 10, getHeight() - 15);

        g2.dispose();
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
}
