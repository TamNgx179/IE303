package Lab3.model;

import java.util.Arrays;
import java.util.List;

public final class ProductRepository {
    private static final String DISCOUNT_NOTE = "This product is excluded from all promotional discounts and offers.";

    private ProductRepository() {
    }

    public static List<Product> getProducts() {
        return Arrays.asList(
                new Product(1, "4DFWD PULSE SHOES", DISCOUNT_NOTE, "Adidas", 160.00, "/Lab3/view/assets/img1.png"),
                new Product(2, "FORUM MID SHOES", DISCOUNT_NOTE, "Adidas", 100.00, "/Lab3/view/assets/img2.png"),
                new Product(3, "SUPERNOVA SHOES", "NMD City Stock 2", "Adidas", 150.00, "/Lab3/view/assets/img3.png"),
                new Product(4, "Adidas", "NMD City Stock 2", "Adidas", 160.00, "/Lab3/view/assets/img4.png"),
                new Product(5, "Adidas", "NMD City Stock 2", "Adidas", 120.00, "/Lab3/view/assets/img5.png"),
                new Product(6, "4DFWD PULSE SHOES", DISCOUNT_NOTE, "Adidas", 160.00, "/Lab3/view/assets/img6.png"),
                new Product(7, "4DFWD PULSE SHOES", DISCOUNT_NOTE, "Adidas", 160.00, "/Lab3/view/assets/img1.png"),
                new Product(8, "FORUM MID SHOES", DISCOUNT_NOTE, "Adidas", 100.00, "/Lab3/view/assets/img2.png")
        );
    }
}
