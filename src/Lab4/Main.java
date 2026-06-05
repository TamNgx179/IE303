package Lab4;

import Lab4.controller.ShopController;
import Lab4.model.Product;
import Lab4.model.ProductRepository;
import Lab4.view.ProductCatalogFrame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products;
        try {
            products = ProductRepository.getProducts();
        } catch (RuntimeException exception) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                    null,
                    "Khong the ket noi MongoDB",
                    "Lab4 MongoDB",
                    JOptionPane.ERROR_MESSAGE
            ));
            return;
        }

        SwingUtilities.invokeLater(() -> {
            ProductCatalogFrame frame = new ProductCatalogFrame(products);
            ShopController controller = new ShopController(products, frame);
            controller.start();
        });
    }
}
