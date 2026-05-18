package Lab3.controller;

import Lab3.model.Product;
import Lab3.view.ProductCatalogFrame;

import java.util.List;

public class ShopController {
    private final List<Product> products;
    private final ProductCatalogFrame view;

    public ShopController(List<Product> products, ProductCatalogFrame view) {
        this.products = products;
        this.view = view;
    }

    public void start() {
        view.setProductSelectionListener(view::showProduct);

        if (!products.isEmpty()) {
            view.showProduct(products.get(0));
        }

        view.setVisible(true);
    }
}
