package edu.uagrm.fich.navegador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private Stage stage;
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        Label label = new Label("Hola Mundo");
        Scene scene = new Scene(new StackPane(label), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}