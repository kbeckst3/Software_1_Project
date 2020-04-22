package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

public class PartScreen {

    @FXML
    public RadioButton radio2;
    @FXML
    public TextField partName;
    @FXML
    public TextField partInv;
    @FXML
    public TextField partPrice;
    @FXML
    public TextField partMax;
    @FXML
    public TextField partMin;
    @FXML
    public TextField companyNameField;
    @FXML
    public TextField machineIDField;
    @FXML
    public TextField partId;
    @FXML
    public RadioButton radio1;
    @FXML
    public ToggleGroup partSource;
    @FXML
    public Label companyNameLabel;
    @FXML
    public Label companyMachineLabel;
    @FXML
    public Text addPartTitle;
    @FXML
    public Text modifyPartTitle;

    @FXML
    private Part partCreator(String name, int stock, double price, int max, int min) {

        //creating id if adding a new part or grabbing the old id.
        int id;

        if(partId.getText().isEmpty()){
            id = idGenerate();
        }else id = Integer.parseInt(partId.getText());

        if (radio2.isSelected()) {

            String company = companyNameField.getText();
            return new Outsourced(id, name, stock, price, max, min, company);

        } else if (radio1.isSelected()){

            int machine = Integer.parseInt(machineIDField.getText());
            return new InHouse(id, name, stock, price, max, min, machine);

        }

        return null;
    }

    @FXML
    private void savePart(ActionEvent event) {
        //New or Updated Part
        Part partToBeAdded;

        if(validatePartFields()) {

            //Grab fields for part creation
            String name = partName.getText();
            double price = Double.parseDouble(partPrice.getText());
            int stock = Integer.parseInt(partInv.getText());
            int max = Integer.parseInt(partMax.getText());
            int min = Integer.parseInt(partMin.getText());

            if (addPartTitle.isVisible()) {

                partToBeAdded = partCreator(name, stock, price, max, min);
                Inventory.addPart(partToBeAdded);

            } else {//Modify part here

                Inventory.updatePart(partCreator(name, stock, price, max, min));
            }

            //Close out window
            cancelPartWindow(event);
        }
    }

    @FXML
    private void cancelPartWindow(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void partSourceToggle() {
        RadioButton selectedRadioButton = (RadioButton) partSource.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();
        System.out.println(toggleGroupValue);
        if (toggleGroupValue.equals("Outsourced")) {
            companyMachineLabel.setVisible(false);
            machineIDField.setVisible(false);
            companyNameLabel.setVisible(true);
            companyNameField.setVisible(true);

        } else {
            companyNameLabel.setVisible(false);
            companyNameField.setVisible(false);
            companyMachineLabel.setVisible(true);
            machineIDField.setVisible(true);
        }

    }
    private boolean validatePartFields(){

        String name = partName.getText();
        String price = partPrice.getText();
        String stock = partInv.getText();
        String max = partMax.getText();
        String min = partMin.getText();
        Alert noValueAlert = new Alert(Alert.AlertType.ERROR);

        if(name.isEmpty()){
            noValueAlert.setContentText("Please enter a value for \bName");
            noValueAlert.showAndWait();
            partName.setStyle("-fx-border-color: red;");
        }else if(stock.isEmpty()){
            noValueAlert.setContentText("Please enter a value for \bInventory");
            noValueAlert.showAndWait();
            partInv.setStyle("-fx-border-color: red;");
        }else if(price.isEmpty()){
            noValueAlert.setContentText("Please enter a value for \bPrice");
            noValueAlert.showAndWait();
            partPrice.setStyle("-fx-border-color: red;");
        }else if(max.isEmpty()){
            noValueAlert.setContentText("Please enter a value for \bMax");
            noValueAlert.showAndWait();
            partMax.setStyle("-fx-border-color: red;");
        }else if(min.isEmpty()){
            noValueAlert.setContentText("Please enter a value for \bMin");
            noValueAlert.showAndWait();
            partMin.setStyle("-fx-border-color: red;");
        }else if(machineIDField.getText().isEmpty() && radio1.isSelected()){
            noValueAlert.setContentText("Please enter a value for \bMachine ID");
            noValueAlert.showAndWait();
            machineIDField.setStyle("-fx-border-color: red;");
        }else if(companyNameField.getText().isEmpty() && radio2.isSelected()){
            noValueAlert.setContentText("Please enter a value for \bCompany Name");
            noValueAlert.showAndWait();
            machineIDField.setStyle("-fx-border-color: red;");
        }else if(Integer.parseInt(max) < Integer.parseInt(min)) {
            Alert incorrectValues = new Alert(Alert.AlertType.ERROR, "Minimum is greater than Maximum please fix!");
            incorrectValues.showAndWait();
        }else{
            return true;
        }

        return false;
    }

    private int idGenerate() {
        return Inventory.getAllParts().size() + 1;
    }
}
