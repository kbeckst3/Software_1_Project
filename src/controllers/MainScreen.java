package controllers;


import javafx.beans.property.*;
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
    public TextField productSearch, partSearch;

    private ObservableList<Part> partList = FXCollections.observableArrayList();
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    //Populating the partsTable and productsTable
    public void initialize() {

        productID.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getId()));
        productName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        productInv.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getStock()));
        productPrice.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(cellData.getValue().getPrice()));
        Inventory.addProduct(new Product(1, "myProduct", 2, 3, 3, 3, Inventory.getAllParts()));
        Inventory.addProduct(new Product(12, "theirProduct", 27, 38, 83, 83, partList));
        Inventory.addProduct(new Product(34, "itProduct", 7, 7, 9, 9, partList));
        productTable.setItems(Inventory.getAllProducts());

        partId.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getId()));
        partName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        partInv.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getStock()));
        partPrice.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(cellData.getValue().getPrice()));
        Inventory.addPart(new InHouse(1, "myPart", 2, 3, 3, 3, 23));
        Inventory.addPart(new InHouse(150, "hisPart", 4, 5, 6, 6, 34));
        Inventory.addPart(new InHouse(164, "herPart", 23, 34, 34, 33, 32));
        Inventory.addPart(new Outsourced(69, "herPart", 23, 34, 34, 33, "dog"));
        partTable.setItems(Inventory.getAllParts());
    }

    //Filters table based on search entry
    private static void searchTable(TextField field,
                                    ObservableList list,
                                    TableView sortTable) {
        //Declaring Variables
        ObservableList searchedList = FXCollections.observableArrayList();
        String searchText;
        //Getting Button ID
        //Removing button from end of id

        //Get Correct Text Field and perform search
        String[] parts = field.getText().toUpperCase().split(" ");
        for (Object entry : list) {
            boolean match = true;
            String entryText = entry.toString();
            for (String part : parts) {
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
        searchTable(partSearch, partList, partTable);

    }

    @FXML
    public void searchProductTable() {
        searchTable(productSearch, productList, productTable);

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
        Alert alert = new Alert(Alert.AlertType.ERROR, "No Part Selected! Please select  a part. ");
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
        }else if(partTable.getSelectionModel().isEmpty() && !clickedButton.equals("Add")){
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

    //Open Product Screen for creation and deletion of products

}
