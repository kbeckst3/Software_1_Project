package model;

import javafx.collections.ObservableList;

public class Inventory {

     ObservableList<Part> allParts;
     ObservableList<Product> allProducts;

     void addPart(Part part){

     }
     void addProduct(Product product){

     }
     private Part lookupPart(String partName){
         return null;
     }
     private Product lookupProduct(String productName){
         return null;
     }
     private ObservableList<Part> lookupPart(int partId){
        return null;
     }
     private ObservableList<Product> lookupProduct(int productId){
         return null;
     }
     void updatePart(int index, Part selectedPart){

     }
     void updateProduct(int index, Product selectedProduct){

     }
     boolean deletePart(Part part){
         return true;
     }
     boolean deleteProduct(Product product){
         return true;
     }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
