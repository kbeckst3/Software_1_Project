package controllers;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

public class ProductScreen {


    public TextField productId;
    public TextField productName;
    public TextField productInv;
    public TextField productPrice;
    public TextField productMax;
    public TextField productMin;
    public Button partSearchButton;
    public TextField partSearchField;
    public TableView<Part> partTable;
    public TableColumn<Part, Number> partTableId;
    public TableColumn<Part, String> partTableName;
    public TableColumn<Part, Number> partTableInv;
    public TableColumn<Part, Number> partTablePrice;
    public TableView<Part> associatedPartTable;
    public TableColumn<Part, Number> associatePartId;
    public TableColumn<Part, String> associatedPartName;
    public TableColumn<Part, Number> associatedPartInv;
    public TableColumn<Part, Number> associatedPartPrice;
    public Text addProductTitle;
    public Button deleteAssociatedPart;

    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public Text modifyProductTitle;

    public void initialize() {
        //Populate parts table with current parts
        partTableId.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getId()));
        partTableName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        partTableInv.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getStock()));
        partTablePrice.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(cellData.getValue().getPrice()));

        partTable.setItems(Inventory.getAllParts());

        associatePartId.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getId()));
        associatedPartName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        associatedPartInv.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getStock()));
        associatedPartPrice.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(cellData.getValue().getPrice()));

        associatedPartTable.setItems(associatedParts);

    }

    @FXML
    private Product productCreator(String name, int stock, double price, int max, int min) {
        //creating id if adding a new part or grabbing the old id.
        int id;

        if (productId.getText().isEmpty()) {
            id = idGenerate();
        } else id = Integer.parseInt(productId.getText());

        return new Product(id, name, stock, price, max, min, associatedParts);
    }

    @FXML
    private void saveProduct(ActionEvent event) {

        //New or Updated Part
        Product productToBeAdded;

        if (validateProductFields()) {
            String name = productName.getText();
            double price = Double.parseDouble(productPrice.getText());
            int stock = Integer.parseInt(productInv.getText());
            int max = Integer.parseInt(productMax.getText());
            int min = Integer.parseInt(productMin.getText());

            if (addProductTitle.isVisible()) {

                productToBeAdded = productCreator(name, stock, price, max, min);
                Inventory.addProduct(productToBeAdded);

            } else {//Modify part here

                Inventory.updateProduct(productCreator(name, stock, price, max, min));
            }

            //Close out window
            cancelProductWindow(event);
        }
    }

    private static void searchTable(TextField field,
                                    ObservableList<Part> list,
                                    TableView<Part> sortTable) {
        //Declaring Variables
        ObservableList<Part> searchedList = FXCollections.observableArrayList();

        //Removing button from end of id

        //Get Correct Text Field and perform search
        String[] parts = field.getText().toUpperCase().split(" ");
        for (Part entry : list) {
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
        searchTable(partSearchField, Inventory.getAllParts(), partTable);

    }

    @FXML
    public void addAssociatedPart() {
        Part part = partTable.getSelectionModel().getSelectedItem();
        for(Part p : associatedParts){
            if(p.getId() == part.getId()){
                Alert partAlreadyExists = new Alert(Alert.AlertType.ERROR, p.getName() + " is already an associated part! Please select a different part.");
                partAlreadyExists.showAndWait();
                return;
            }
        }
        associatedParts.add(part);
    }

    public void deleteAssociatePart() {
        Part part = associatedPartTable.getSelectionModel().getSelectedItem();
        for (int i = 0; i < associatedParts.size(); i++)
            if (part.getId() == associatedParts.get(i).getId()) {
                associatedParts.remove(i);
                break;
            }
    }

    @FXML
    private void cancelProductWindow(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    private boolean validateProductFields() {

        String name = productName.getText();
        String price = productPrice.getText();
        String stock = productInv.getText();
        String max = productMax.getText();
        String min = productMin.getText();
        //Parts sum  for validating that product price is greater than sum of total parts
        double sumOfParts = 0;
        for(Part p : associatedParts){
            sumOfParts += p.getPrice();
        }
        Alert noValueAlert = new Alert(Alert.AlertType.ERROR);

        if (name.isEmpty()) {
            noValueAlert.setContentText("Please enter a value for \bName");
            noValueAlert.showAndWait();
            productName.setStyle("-fx-border-color: red;");
        } else if (stock.isEmpty()) {
            noValueAlert.setContentText("Please enter a value for \bInventory");
            noValueAlert.showAndWait();
            productInv.setStyle("-fx-border-color: red;");
        } else if (price.isEmpty()) {
            noValueAlert.setContentText("Please enter a value for \bPrice");
            noValueAlert.showAndWait();
            productPrice.setStyle("-fx-border-color: red;");
        } else if (max.isEmpty()) {
            noValueAlert.setContentText("Please enter a value for \bMax");
            noValueAlert.showAndWait();
            productMax.setStyle("-fx-border-color: red;");
        } else if (min.isEmpty()) {
            noValueAlert.setContentText("Please enter a value for \bMin");
            noValueAlert.showAndWait();
            productMin.setStyle("-fx-border-color: red;");
        } else if (Integer.parseInt(max) < Integer.parseInt(min)) {
            Alert incorrectValues = new Alert(Alert.AlertType.ERROR, "Minimum is greater than Maximum please fix!");
            incorrectValues.showAndWait();
        } else if ((Double.parseDouble(price)) < 0) {
            Alert incorrectValues = new Alert(Alert.AlertType.ERROR, "Price must be greater than 0!");
            incorrectValues.showAndWait();
        } else if (Integer.parseInt(max) < Integer.parseInt(stock) && Integer.parseInt(min) < Integer.parseInt(stock)) {
            Alert incorrectValues = new Alert(Alert.AlertType.ERROR, "Inventory must be less than the max and greater than the min!");
            incorrectValues.showAndWait();
        } else if(Double.parseDouble(price) < sumOfParts){
            Alert incorrectValues = new Alert(Alert.AlertType.ERROR, "Product price must be greater than sum of associated part prices. \n current sum of associated parts: "
            + sumOfParts);
            incorrectValues.showAndWait();
        }else if(sumOfParts <= 0){
            Alert incorrectValues = new Alert(Alert.AlertType.ERROR, "Product must have at least one associated part please add part!");
            incorrectValues.showAndWait();
        }else {
            return true;
        }

        return false;
    }

    private int idGenerate() {
        return Inventory.getAllProducts().size() + 1;
    }
}
