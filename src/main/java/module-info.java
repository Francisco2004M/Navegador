module edu.uagrm.fich.navegador {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;
    requires javafx.web;

    exports edu.uagrm.fich.navegador;
    exports edu.uagrm.fich.navegador.gui;
    exports edu.uagrm.fich.navegador.utils;
}