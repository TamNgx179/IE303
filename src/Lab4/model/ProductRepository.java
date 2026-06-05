package Lab4.model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ProductRepository {
    private static final String MONGO_URI = "mongodb://127.0.0.1:27017";
    private static final String DATABASE_NAME = "lab4_shop";
    private static final String COLLECTION_NAME = "products";
    private static final String DISCOUNT_NOTE = "This product is excluded from all promotional discounts and offers.";

    private ProductRepository() {
    }

    public static List<Product> getProducts() {
        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> productsCollection = database.getCollection(COLLECTION_NAME);


            List<Product> products = new ArrayList<>();
            for (Document document : productsCollection.find().sort(Sorts.ascending("id"))) {
                products.add(toProduct(document));
            }
            return products;
        }
    }

    private static Document productDocument(int id, String name, String description, String brand, double price, String imagePath) {
        return new Document("id", id)
                .append("name", name)
                .append("description", description)
                .append("brand", brand)
                .append("price", price)
                .append("imagePath", imagePath);
    }

    private static Product toProduct(Document document) {
        return new Product(
                getInteger(document, "id"),
                getString(document, "name"),
                getString(document, "description"),
                getString(document, "brand"),
                getDouble(document, "price"),
                getString(document, "imagePath")
        );
    }

    private static int getInteger(Document document, String key) {
        Object value = document.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 0;
    }

    private static double getDouble(Document document, String key) {
        Object value = document.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return 0;
    }

    private static String getString(Document document, String key) {
        String value = document.getString(key);
        return value == null ? "" : value;
    }
}
