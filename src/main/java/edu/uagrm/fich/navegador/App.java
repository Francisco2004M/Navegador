package edu.uagrm.fich.navegador;

import edu.uagrm.fich.navegador.gui.VentanaPrincipal;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;


public class App extends Application {

    public static final Image ICON = new Image(Objects.requireNonNull(App.class.getResourceAsStream("/images/icon.png")));

    @Override
    public void start(Stage stage) {
        stage.setTitle("Navegador Web - UAGRM-FICH");
        stage.setScene(new VentanaPrincipal(stage));
        stage.getIcons().add(ICON);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main() {
        launch();
    }
}