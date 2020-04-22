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

    public static boolean deletePart(Part part) {
        boolean partSuccessfullyDeleted = false;
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == part.getId()) {
                allParts.remove(i);
                partSuccessfullyDeleted = true;
                break;
            }
        }
        return partSuccessfullyDeleted;
    }

    public static boolean deleteProduct(Product product) {
        boolean productSuccessfullyDeleted = false;
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == product.getId()) {
                allProducts.remove(i);
                productSuccessfullyDeleted = true;
                break;
            }
        }
        return productSuccessfullyDeleted;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
