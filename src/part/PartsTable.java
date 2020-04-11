package part;

import connection.Connect;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import part.Part;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PartsTable {

    public TableView getPartsTable() throws SQLException {

        // Part Table
        TableView partsTable = new TableView();
        partsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        partsTable.setEditable(true);
        partsTable.setMaxHeight(250);


        TableColumn<Part, Number> partID = new TableColumn<>("Part ID");
        partID.setMinWidth(50);
        partID.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getPartID()));

        TableColumn<Part, String> partName = new TableColumn<>("Part Name");
        partName.setMinWidth(150);
        partName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPartName()));

        TableColumn<Part, Number> partInventoryLevel = new TableColumn<>("Inventory Level");
        partInventoryLevel.setMinWidth(150);
        partInventoryLevel.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getPartInventoryLevel()));

        TableColumn<Part, String> partPriceCost = new TableColumn<>("Price/Cost per unit");
        partPriceCost.setMinWidth(150);
        partPriceCost.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPartPriceCost()));


        partsTable.getColumns().addAll(partID, partName, partInventoryLevel, partPriceCost);
        partsTable.setItems(getParts());

        return partsTable;
    }
    public static ObservableList<Part> getParts() throws SQLException {

        ObservableList<Part> parts = FXCollections.observableArrayList();
        Connect connection = new Connect();

        ResultSet rs = connection.connect("Select * From parts");

        while (rs.next()){
            try {
                parts.add(new Part(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getInt(5)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return parts;
    }


}
