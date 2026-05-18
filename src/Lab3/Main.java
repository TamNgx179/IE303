package Lab3;

import Lab3.controller.ShopController;
import Lab3.model.Product;
import Lab3.model.ProductRepository;
import Lab3.view.ProductCatalogFrame;

import javax.swing.SwingUtilities;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Product> products = ProductRepository.getProducts();
            ProductCatalogFrame frame = new ProductCatalogFrame(products);
            ShopController controller = new ShopController(products, frame);
            controller.start();
        });
    }
}
