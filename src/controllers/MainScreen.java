package controllers;


import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;


public class MainScreen {

    public Button partSearchButton;
    public GridPane container;
    public Text title;
    @FXML
    public GridPane partsProducts;
    @FXML
    public TableView<Part> partTable;
    @FXML
    public TableColumn<Part, Number> partId, partInv, partPrice;
    @FXML
    public TableColumn<Part, String> partName;
    @FXML
    public Button partAdd, partModify, partDelete;
    @FXML
    public Button productSearchButton;
    @FXML
    public TableView<Product> productTable;
    @FXML
    public TableColumn<Product, Number> productID, productInv, productPrice;
    @FXML
    public TableColumn<Product, String> productName;
    @FXML
    public Button productAdd, productModify, productDelete;
    @FXML
    public TextField productSearch, partSearch;

    //Populating the partsTable and productsTable
    public void initialize() {

        productID.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getId()));
        productName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        productInv.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getStock()));
        productPrice.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(cellData.getValue().getPrice()));
        Inventory.addProduct(new Product(1, "myProduct", 5, 125, 3, 15, Inventory.getAllParts()));
        Inventory.addProduct(new Product(2, "theirProduct", 10, 150, 6, 30, Inventory.getAllParts()));
        Inventory.addProduct(new Product(3, "itsProduct", 15, 175, 9, 45, Inventory.getAllParts()));
        productTable.setItems(Inventory.getAllProducts());

        partId.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getId()));
        partName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        partInv.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getStock()));
        partPrice.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(cellData.getValue().getPrice()));
        Inventory.addPart(new InHouse(1, "myPart", 5, 5, 10, 1, 23));
        Inventory.addPart(new InHouse(2, "hisPart", 10, 10, 20, 2, 34));
        Inventory.addPart(new Outsourced(3, "herPart", 15, 15, 30, 3, "Acme"));
        Inventory.addPart(new Outsourced(4, "TheirPart", 20, 20, 40, 4, "ABC"));
        partTable.setItems(Inventory.getAllParts());
    }

    //Filters table based on search entry
    private static void searchTable(TextField field,
                                    ObservableList<?> list,
                                    TableView<?> sortTable) {
        //Declaring Variables
        ObservableList searchedList = FXCollections.observableArrayList();

        System.out.println(field.getText());

        //Get Correct Text Field and perform search
        String[] parts = field.getText().toUpperCase().split(" ");
        for (Object entry : list) {
            System.out.println(entry);
            boolean match = true;
            String entryText = entry.toString();
            for (String part : parts) {
                System.out.println(part);
                // The entry needs to contain all portions of the
                // search string *but* in any order
                if (!entryText.toUpperCase().contains(part)) {
                    match = false;
                    break;
                }
            }

            if (match) {
                searchedList.add(entry);
            }
        }
        sortTable.setItems(searchedList);

    }

    @FXML
    public void searchPartsTable() {
        searchTable(partSearch, Inventory.getAllParts(), partTable);

    }

    @FXML
    public void searchProductTable() {
        searchTable(productSearch, Inventory.getAllProducts(), productTable);

    }

    // CRUD Features for parts table;

    //Opens Parts Screen for creation and deletion of parts
    @FXML
    public void openPartsWindow(ActionEvent event) throws IOException {
        //PartScreen Popup window!
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/partScreen.fxml"));
        Parent root = loader.load();

        PartScreen partsScreen = loader.getController();

        //Getting Correct Button Click
        Button selectedButton = (Button) event.getSource();
        String clickedButton = selectedButton.getText();

        Alert alert = new Alert(Alert.AlertType.ERROR, "No Part Selected! Please select  a part. ");
        //Part modification
        if (clickedButton.equals("Modify") && !partTable.getSelectionModel().isEmpty()) {
            //Grab selected part
            Part part = partTable.getSelectionModel().getSelectedItem();
            System.out.println(part.getName());

            //Change title of parts table
            partsScreen.addPartTitle.setVisible(false);
            partsScreen.modifyPartTitle.setVisible(true);

            //Pre fill parts screen with selected part
            if (part instanceof Outsourced) {
                partsScreen.companyMachineLabel.setVisible(false);
                partsScreen.machineIDField.setVisible(false);
                partsScreen.companyNameLabel.setVisible(true);
                partsScreen.companyNameField.setVisible(true);
                partsScreen.radio2.setSelected(true);
                partsScreen.companyNameField.setText(((Outsourced) part).getCompanyName());

            } else {
                partsScreen.radio1.setSelected(true);
                partsScreen.machineIDField.setText(String.valueOf(((InHouse) part).getMachineId()));
            }
            partsScreen.partId.setText(Integer.toString(part.getId()));
            partsScreen.partName.setText(part.getName());
            partsScreen.partInv.setText(Integer.toString(part.getStock()));
            partsScreen.partPrice.setText(Double.toString(part.getPrice()));
            partsScreen.partMax.setText(Integer.toString(part.getMax()));
            partsScreen.partMin.setText(Integer.toString(part.getMin()));


        } else {//else just open up and add part

            partsScreen.addPartTitle.setVisible(true);
            partsScreen.modifyPartTitle.setVisible(false);
        }
        if (partTable.getSelectionModel().isEmpty() && !clickedButton.equals("Add")) {
            alert.showAndWait();

        } else {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
    }

    @FXML
    public void deletePart() {
        if (partTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No selection made for deletion!");
            alert.showAndWait();
        } else {
            Part part = partTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure that you want to delete part: " + part.getName());

            if(alert.showAndWait().get() == ButtonType.OK) {
                Inventory.deletePart(part);
            }
        }

    }

    //CRUD Features for product table
    @FXML
    public void openProductsWindow(ActionEvent event) throws IOException {
        //PartScreen Popup window!
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/productScreen.fxml"));
        Parent root = loader.load();

        ProductScreen productScreen = loader.getController();

        //Getting Correct Button Click
        Button selectedButton = (Button) event.getSource();
        String clickedButton = selectedButton.getText();

        //Alert used if user does not select product to modify
        Alert alert = new Alert(Alert.AlertType.ERROR, "No Product Selected! Please select a product. ");
        if (clickedButton.equals("Modify") && !productTable.getSelectionModel().isEmpty()) {
            Product product = productTable.getSelectionModel().getSelectedItem();
            productScreen.addProductTitle.setVisible(false);
            productScreen.modifyProductTitle.setVisible(true);
            System.out.println(product.getName());

            //Creating list of associated parts to populate associated parts table
            ObservableList<Part> associatedParts = FXCollections.observableArrayList();
            associatedParts.addAll(product.getAssociatedParts());

            productScreen.associatedParts.addAll(associatedParts);
            productScreen.productId.setText(String.valueOf(product.getId()));
            productScreen.productName.setText(product.getName());
            productScreen.productInv.setText(String.valueOf(product.getStock()));
            productScreen.productPrice.setText(String.valueOf(product.getPrice()));
            productScreen.productMax.setText(String.valueOf(product.getMax()));
            productScreen.productMin.setText(String.valueOf(product.getMin()));

        }
        if(productTable.getSelectionModel().isEmpty() && !clickedButton.equals("Add")){
            alert.showAndWait();
        }else {

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }

    }

    public void deleteProduct() {
        if (productTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No selection made for deletion!");
            alert.showAndWait();
        } else {
            Product product = productTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure that you want to delete part: " + product.getName());

            if(alert.showAndWait().get() == ButtonType.OK) {
                Inventory.deleteProduct(product);
            }
        }
    }


}
