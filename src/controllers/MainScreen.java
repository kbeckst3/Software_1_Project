package controllers;


import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.InHouse;
import model.Part;
import model.Product;

import java.lang.reflect.Type;

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
    public GridPane productsTable;
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
        productList.add(new Product(1, "myProduct", 2, 3, 3, 3));
        productList.add(new Product(12, "theirProduct", 27, 38, 83, 83));
        productList.add(new Product(34, "itProduct", 7, 7, 9, 9));
        productTable.setItems(productList);

        partId.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getId()));
        partName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        partInv.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getStock()));
        partPrice.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(cellData.getValue().getPrice()));
        partList.add(new InHouse(1, "myPart", 2, 3, 3, 3));
        partList.add(new InHouse(150, "hisPart", 4, 5, 6, 6));
        partList.add(new InHouse(164, "herPart", 23, 34, 34, 33));
        partTable.setItems(partList);
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
        for ( Object entry : list ) {
            boolean match = true;
            String entryText = entry.toString();
            for (String part: parts) {
                // The entry needs to contain all portions of the
                // search string *but* in any order
                if ( ! entryText.toUpperCase().contains(part) ) {
                    match = false;
                    break;
                }
            }

            if ( match ) {
                searchedList.add(entry);
            }
        }
        sortTable.setItems(searchedList);
        // OnAction get Search String
        // Clear Search box
        // Filter Items
        // call table Create Function insert new values

    }
    @FXML
    public void searchPartsTable(){
        searchTable(partSearch, partList, partTable);

    }
    @FXML
    public void searchProductTable(){
        searchTable(productSearch, productList, productTable);

    }

}
