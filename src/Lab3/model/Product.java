package Lab3.model;

import java.util.Locale;

public class Product {
    private final int id;
    private final String name;
    private final String description;
    private final String brand;
    private final double price;
    private final String imagePath;

    public Product(int id, String name, String description, String brand, double price, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getFormattedPrice() {
        return String.format(Locale.US, "$%.2f", price);
    }
}
