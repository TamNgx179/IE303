package Lab4.view;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

final class ImageAssets {
    private ImageAssets() {
    }

    static BufferedImage load(String resourcePath) {
        URL resource = ImageAssets.class.getResource(resourcePath);
        if (resource != null) {
            try {
                return ImageIO.read(resource);
            } catch (IOException ignored) {
            }
        }

        Path fallback = Paths.get("src", resourcePath.substring(1));
        if (Files.exists(fallback)) {
            try {
                return ImageIO.read(fallback.toFile());
            } catch (IOException ignored) {
            }
        }

        return createPlaceholder(resourcePath);
    }

    static void drawProduct(Graphics2D g2, BufferedImage image, int x, int y, int width, int height) {
        Object oldInterpolation = g2.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int sourceX = 0;
        int sourceY = (int) (image.getHeight() * 0.22);
        int sourceWidth = image.getWidth();
        int sourceHeight = (int) (image.getHeight() * 0.56);

        double sourceRatio = sourceWidth / (double) sourceHeight;
        double targetRatio = width / (double) height;
        int drawWidth = width;
        int drawHeight = height;

        if (sourceRatio > targetRatio) {
            drawHeight = (int) Math.round(width / sourceRatio);
        } else {
            drawWidth = (int) Math.round(height * sourceRatio);
        }

        int drawX = x + (width - drawWidth) / 2;
        int drawY = y + (height - drawHeight) / 2;

        g2.drawImage(
                image,
                drawX,
                drawY,
                drawX + drawWidth,
                drawY + drawHeight,
                sourceX,
                sourceY,
                sourceX + sourceWidth,
                sourceY + sourceHeight,
                null
        );

        if (oldInterpolation != null) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, oldInterpolation);
        }
    }

    private static BufferedImage createPlaceholder(String label) {
        BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(new Color(242, 242, 242));
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2.setColor(new Color(80, 80, 80));
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        FontMetrics metrics = g2.getFontMetrics();
        String text = "Missing image";
        g2.drawString(text, (image.getWidth() - metrics.stringWidth(text)) / 2, 290);
        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        metrics = g2.getFontMetrics();
        g2.drawString(label, (image.getWidth() - metrics.stringWidth(label)) / 2, 330);
        g2.dispose();
        return image;
    }
}
