package layout;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import part.PartsTable;

import java.sql.SQLException;

import static part.PartsTable.getParts;

public class MainScreen extends Parent {


    public void start(Stage stage) {

        //Title for System
        Text title = new Text("Inventory Management System");
        title.setFont(new Font("Ariel", 25));

        ///////////// PARTS MENU ///////////////////
        //Title for Parts
        Text partsTitle = new Text("Parts");
        partsTitle.setFont(Font.font("Calibre", FontWeight.EXTRA_BOLD, 20));

        TableView partsTable = null;
        try {
            partsTable = new PartsTable().getPartsTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ObservableList testList = getParts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Buttons
        Button partAdd = new Button("Add");
        Button partModify = new Button("Modify");
        Button partDelete = new Button("Delete");


        //Search Box
        Button partSearch = new Button("Search");
        TextField partSearchText = new TextField();
        partSearchText.setPromptText("Search");
        partSearchText.setFocusTraversable(false);

        HBox searchPart = new HBox(20, partSearch, partSearchText);
        searchPart.setAlignment(Pos.TOP_RIGHT);
//
//        ///////////// PRODUCT MENU ///////////////////
//
//        //Title for Products
//        Text productTitle = new Text("Products");
//        productTitle.setFont(Font.font("Calibre", FontWeight.EXTRA_BOLD, 20));
//
//        //Product Table
//
//        //Table
////        TableView productTable = new TableView();
////        productTable.setEditable(true);
////
////        TableColumn<ProductID, Integer> productID = new TableColumn<ProductID, Integer>(" Product ID");
//        productID.setMinWidth(50);
////        productID.setCellValueFactory(new PropertyValueFactory<ProductID, Integer>("productID"));
////
////        TableColumn<ProductName, String> productName = new TableColumn<ProductName, String>("Product Name");
//        productName.setMinWidth(150);
////        productName.setCellValueFactory(new PropertyValueFactory<ProductName, String>("productName"));
////
////        TableColumn<ProductInventoryLevel, Integer> productInventoryLevel = new TableColumn<ProductInventoryLevel, Integer>("Inventory Level");
//        productInventoryLevel.setMinWidth(150);
////        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<ProductInventoryLevel, Integer>("productInventoryLevel"));
////
////        TableColumn<ProductPriceCost, Double> productPriceCost = new TableColumn<ProductPriceCost, Double>("Price/Cost per unit");
//        productPriceCost.setMinWidth(150);
////        productPriceCost.setCellValueFactory(new PropertyValueFactory<ProductPriceCost, Double>("productPriceCost"));
////
////        productTable.setItems(getParts());
//          productTable.getColumns().addAll(productID, productName, productInventoryLevel, productPriceCost);
//
//        //Buttons
//        Button productAdd = new Button("Add");
//        Button productModify = new Button("Modify");
//        Button productDelete = new Button("Delete");
//
//        //Search Box
//        Button productSearch = new Button("Search");
//        TextField productSearchText = new TextField();
//        productSearchText.setPromptText("Search");
//        productSearchText.setFocusTraversable(false);
//
//        HBox searchProduct = new HBox(20, productSearch, productSearchText);
//        searchProduct.setAlignment(Pos.TOP_RIGHT);


        //Parts Menu//
        GridPane partMenu = new GridPane();

        partMenu.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" + "-fx-border-color: grey;" + "-fx-border-radius: 5%;");

        //Setting size for the pane
        partMenu.setMaxHeight(375);
        partMenu.setMaxWidth(600);

        //Setting the padding
        partMenu.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        partMenu.setVgap(10);
        partMenu.setHgap(10);

        //Setting the Grid alignment
        partMenu.setAlignment(Pos.CENTER);

        partMenu.add(partsTitle, 0 , 0);
        partMenu.add(searchPart, 4, 0, 1, 1);
        partMenu.add(partsTable, 0, 1, 5, 1);
        partMenu.add(partAdd, 1, 2);
        partMenu.add(partModify, 2, 2);
        partMenu.add(partDelete, 3, 2);

        //Products Menu//
        GridPane productMenu = new GridPane();

        productMenu.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" + "-fx-border-color: grey;" + "-fx-border-radius: 5%;");

        //Setting size for the pane
        productMenu.setMaxHeight(375);
        productMenu.setMaxWidth(600);

        //Setting the padding
        productMenu.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        productMenu.setVgap(10);
        productMenu.setHgap(10);

        //Setting the Grid alignment
        productMenu.setAlignment(Pos.CENTER);

//        productMenu.add(productTitle, 0 , 0);
//        productMenu.add(searchProduct, 4, 0, 1, 1);
//        productMenu.add(productTable, 0, 1, 5, 1);
//        productMenu.add(productAdd, 1, 2);
//        productMenu.add(productModify, 2, 2);
//        productMenu.add(productDelete, 3, 2);

         ///////////////////////////////////////////
         ///////////// Main Menu ///////////////////
         ///////////////////////////////////////////

        //Exit Button
        Button exit = new Button("Exit");
        exit.setMinSize(50, 25);
        HBox exitBox = new HBox(exit);
        exitBox.setAlignment(Pos.TOP_RIGHT);

        GridPane gridPane = new GridPane();

        //Setting size for the pane
        gridPane.setMinSize(1000, 650);

        //Setting the padding
        gridPane.setPadding(new Insets(5, 5, 5, 5));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(20);
        gridPane.setHgap(10);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(title, 0 , 0);
        gridPane.add(partMenu, 0, 3);
        gridPane.add(productMenu, 1, 3);
        gridPane.add(exitBox, 1, 4 );


        //Setting the back ground color
        gridPane.setStyle("-fx-background-color: #d3d3d3;");

        //Creating a scene object
        Scene scene = new Scene(gridPane);

        //Setting title to the Stage
        stage.setTitle("Registration Form");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
}

