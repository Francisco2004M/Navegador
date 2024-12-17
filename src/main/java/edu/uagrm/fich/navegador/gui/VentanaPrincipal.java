package edu.uagrm.fich.navegador.gui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.uagrm.fich.navegador.utils.Historial;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import static javafx.concurrent.Worker.State.SUCCEEDED;

public class VentanaPrincipal extends Scene {

    private static final double ESPACIADO = 8;

    private final VBox panel;
    private final TextField txtBuscador = new TextField();
    private final Button btnBuscar = new Button("Buscar");
    private final Button btnAtras = new Button();
    private final Button btnAdelante = new Button();
    private final Button btnHistorial = new Button();
    private final HBox panelBuscar = new HBox(ESPACIADO, txtBuscador, btnBuscar, btnAtras, btnAdelante, btnHistorial);
    private final WebView contenedorWeb = new WebView();

    private WebEngine manejadorWeb = contenedorWeb.getEngine();
    private Historial historial = new Historial();

    public VentanaPrincipal() {
        super(new VBox(ESPACIADO));
        this.panel = (VBox) getRoot();
        configurarComponentes();
        configurarAcciones();
    }

    private void configurarComponentes() {
        panel.getChildren().addAll(panelBuscar, contenedorWeb);
        panel.setPadding(new Insets(ESPACIADO));

        FontAwesomeIconView buscarIcon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
        FontAwesomeIconView atrasIcon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT);
        FontAwesomeIconView adelanteIcon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT);
        FontAwesomeIconView historialIcon = new FontAwesomeIconView(FontAwesomeIcon.HISTORY);

        buscarIcon.setSize("16px");
        atrasIcon.setSize("16px");
        adelanteIcon.setSize("16px");
        historialIcon.setSize("16px");

        btnBuscar.setGraphic(buscarIcon);
        btnBuscar.setTooltip(new Tooltip("Buscar página indicada en la barra de búsqueda"));
        btnAtras.setGraphic(atrasIcon);
        btnAtras.setTooltip(new Tooltip("Ir a la página anterior en el Historial"));
        btnAdelante.setGraphic(adelanteIcon);
        btnAdelante.setTooltip(new Tooltip("Ir a la siguiente Página en el Historial"));
        btnHistorial.setGraphic(historialIcon);
        btnHistorial.setTooltip(new Tooltip("Mostrar Historial de Navegación"));

        String buttonStyle = "-fx-cursor: hand;";

        btnBuscar.setStyle(buttonStyle);
        btnAdelante.setStyle(buttonStyle);
        btnAtras.setStyle(buttonStyle);
        btnHistorial.setStyle(buttonStyle);

        HBox.setHgrow(txtBuscador, Priority.ALWAYS);
        VBox.setVgrow(contenedorWeb, Priority.ALWAYS);
        refrescarBotonesDeNavegacion();
    }

    private void configurarAcciones(){
        btnBuscar.setOnAction( _ -> buscar(txtBuscador.getText()));
        btnAtras.setOnAction(_ -> clickEnAtras());
        btnAdelante.setOnAction(_ -> clickEnAdelante());

        txtBuscador.setOnKeyPressed(evt -> {
            if (evt.getCode() == KeyCode.ENTER) {
                buscar(txtBuscador.getText());
            }
        });

        manejadorWeb.getLoadWorker().stateProperty().addListener((_, _, newValue) -> {
            if (SUCCEEDED.equals(newValue) && !txtBuscador.getText().equals(manejadorWeb.getLocation())) {
                actualizarHistorial(manejadorWeb.getLocation());
            }
        });
    }

    private void buscar(String consulta) {
        if (consulta.toLowerCase().contains("http") || consulta.toLowerCase().contains("ftp")) {
            manejadorWeb.load(consulta);
        } else {
            var buscarEnGoogle = "https://www.google.com/search?q=%s".formatted(consulta.replace(" ", "+"));
            manejadorWeb.load(buscarEnGoogle);
        }
    }

    public void actualizarHistorial(String pagina) {
        historial.agregarBusqueda(pagina);
        txtBuscador.setText(pagina);
        refrescarBotonesDeNavegacion();
    }

    private void refrescarBotonesDeNavegacion() {
        btnAdelante.setDisable(!historial.hayAdelante());
        btnAtras.setDisable(!historial.hayAtras());
        historial.guardarHistorial();
    }

    private void clickEnAdelante() {
        String urlAdelante = historial.adelante();
        manejadorWeb.load(urlAdelante);
        txtBuscador.setText(urlAdelante);
        refrescarBotonesDeNavegacion();
    }

    private void clickEnAtras() {
        String urlAtras = historial.atras();
        manejadorWeb.load(urlAtras);
        txtBuscador.setText(urlAtras);
        refrescarBotonesDeNavegacion();
    }
}
