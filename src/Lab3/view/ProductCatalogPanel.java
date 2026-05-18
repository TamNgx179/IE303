package Lab3.view;

import Lab3.model.Product;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class ProductCatalogPanel extends JPanel {
    private final ProductDetailPanel detailPanel;
    private final List<ProductCard> productCards;

    ProductCatalogPanel(List<Product> products) {
        productCards = new ArrayList<>();
        detailPanel = new ProductDetailPanel();

        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.WHITE);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(true);
        content.setBackground(Color.WHITE);

        content.add(detailPanel, BorderLayout.WEST);
        content.add(createProductList(products), BorderLayout.CENTER);

        add(new HeaderPanel(), BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }

    void showProduct(Product product) {
        detailPanel.showProduct(product);
        setSelectedProduct(product);
    }

    void setProductSelectionListener(Consumer<Product> listener) {
        for (ProductCard card : productCards) {
            card.setSelectionListener(listener);
        }
    }

    private JScrollPane createProductList(List<Product> products) {
        JPanel grid = new JPanel(new GridLayout(0, 4, 10, 10));
        grid.setOpaque(true);
        grid.setBackground(Color.WHITE);
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 14, 26));

        for (Product product : products) {
            ProductCard card = new ProductCard(product);
            productCards.add(card);
            grid.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(grid);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setBackground(Color.WHITE);

        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUI(new CatalogScrollBarUI());
        verticalBar.setUnitIncrement(18);
        verticalBar.setPreferredSize(new Dimension(10, 0));
        verticalBar.setOpaque(false);

        return scrollPane;
    }

    private void setSelectedProduct(Product selectedProduct) {
        for (ProductCard card : productCards) {
            card.setSelected(card.getProduct().getId() == selectedProduct.getId());
        }
    }
}
