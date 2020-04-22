package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static void updatePart(Part part) {
        for (int i = 0; i < allParts.size(); i++)
            if (allParts.get(i).getId() == part.getId()) {
                allParts.set(i, part);
                break;
            }
    }

    public static void updateProduct(Product product) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == product.getId()) {
                allProducts.set(i, product);
            }
        }

    }

    public static void deletePart(Part part) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == part.getId()) {
                allParts.remove(i);
                break;
            }
        }
    }

    public static void deleteProduct(Product product) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == product.getId()) {
                System.out.println("Part is deleting");
                allProducts.remove(i);
                break;
            }
        }
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
