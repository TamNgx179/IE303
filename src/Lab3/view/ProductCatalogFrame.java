package Lab3.view;

import Lab3.model.Product;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.util.List;
import java.util.function.Consumer;

public class ProductCatalogFrame extends JFrame {
    private final ProductCatalogPanel catalogPanel;

    public ProductCatalogFrame(List<Product> products) {
        super("Lab 3 - Adidas Product Catalog");

        catalogPanel = new ProductCatalogPanel(products);
        setContentPane(catalogPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 560));
        setPreferredSize(new Dimension(1180, 600));
        pack();
        setLocationRelativeTo(null);
    }

    public void showProduct(Product product) {
        catalogPanel.showProduct(product);
    }

    public void setProductSelectionListener(Consumer<Product> listener) {
        catalogPanel.setProductSelectionListener(listener);
    }
}
