package edu.uagrm.fich.navegador.gui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.uagrm.fich.navegador.App;
import edu.uagrm.fich.navegador.utils.Historial;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaHistorial extends Scene {

    private static final double ESPACIADO = 8;

    private final VBox panel;
    private final ListView<String> listaHistorial = new ListView<>();
    private final Button btnBorrarHistorial = new Button("Limpiar Historial");

    private final Historial historial;

    public VentanaHistorial(Historial historial) {
        super(new VBox(ESPACIADO), 640, 480);
        this.historial = historial;
        panel = (VBox) getRoot();
        configurarComponentes();
        configurarAcciones();
    }

    private void configurarComponentes() {
        panel.setPadding(new Insets(ESPACIADO));
        panel.getChildren().addAll(listaHistorial, btnBorrarHistorial);
        panel.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(listaHistorial, Priority.ALWAYS);

        FontAwesomeIconView limpiarHistorialIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        limpiarHistorialIcon.setSize("16px");
        btnBorrarHistorial.setGraphic(limpiarHistorialIcon);
        btnBorrarHistorial.setTooltip(new Tooltip("Elimina todo el Historial"));
        listaHistorial.setItems(historial.obtenerHistorialCompleto());
    }

    private void configurarAcciones() {
        btnBorrarHistorial.setOnAction( _ -> eliminarHistorial());
    }

    private void eliminarHistorial() {
        Alert alertConfirmacion = new Alert(null, "Seguro que desea eliminar todo el historial ?", ButtonType.YES, ButtonType.CANCEL);
        alertConfirmacion.setTitle("Confirmación");
        Stage stage = (Stage) alertConfirmacion.getDialogPane().getScene().getWindow();
        stage.getIcons().add(App.ICON);
        alertConfirmacion.showAndWait();

        if (alertConfirmacion.getResult() == ButtonType.YES) {
            historial.limpiarHistorial();
        }
    }
}
