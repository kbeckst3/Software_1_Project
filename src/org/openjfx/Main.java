package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public  void start(Stage primaryStage) throws Exception{
        // Load the font
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainscreen.fxml"));

        primaryStage.setScene(new Scene(root, 1200, 525));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}