package Lab4.view;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

class CatalogScrollBarUI extends BasicScrollBarUI {
    private static final Color THUMB = new Color(196, 196, 196);
    private static final Color TRACK = Color.WHITE;

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createInvisibleButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createInvisibleButton();
    }

    @Override
    protected void paintTrack(Graphics graphics, JComponent component, Rectangle trackBounds) {
        graphics.setColor(TRACK);
        graphics.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics graphics, JComponent component, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(THUMB);

        int thumbWidth = 4;
        int x = thumbBounds.x + (thumbBounds.width - thumbWidth) / 2;
        g2.fillRoundRect(x, thumbBounds.y, thumbWidth, thumbBounds.height, thumbWidth, thumbWidth);
        g2.dispose();
    }

    private JButton createInvisibleButton() {
        JButton button = new JButton();
        Dimension size = new Dimension(0, 0);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        return button;
    }
}
