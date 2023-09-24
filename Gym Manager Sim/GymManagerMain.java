package com.example.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class runs the GUI and launches the user interface.
 * @author Srinidhi Ayalasomayajula and Palak Mehta
 */

public class GymManagerMain extends Application {
    /**
     *Opens up the main stage and presents interface to user.
     * @param stage window to present.
     * @throws IOException input output exception.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GymManagerMain.class.getResource("GymManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 650);
        stage.setTitle("Gym!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the GUI.
     * @param args not used
     */
    public static void main(String[] args) {
        launch();
    }
}