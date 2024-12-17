package edu.uagrm.fich.navegador.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

import static java.lang.System.out;

public final class Historial {

    private Pila historialAtras;
    private Pila historialAdelante;
    private String actualURL;
    private final ObservableList<String> historialCompleto = FXCollections.observableArrayList();

    private static final String FILE_NAME = "historial.txt";

    public Historial() {
        this.actualURL = null;
        this.historialAdelante = new Pila();
        this.historialAtras = new Pila();
        cargarHistorial();
    }

    public void agregarBusqueda(String url) {
        if (actualURL != null) {
            historialAtras.push(actualURL);
        }
        actualURL = url;
        historialCompleto.add(url);
        historialAdelante.limpiar();
    }

    public ObservableList<String> obtenerHistorialCompleto() {
        return historialCompleto;
    }

    public boolean hayAtras() {
        return !historialAtras.vacia();
    }

    public boolean hayAdelante() {
        return !historialAdelante.vacia();
    }

    public String atras() {
        if (!historialAtras.vacia()) {
            historialAdelante.push(actualURL);
            actualURL = historialAtras.pop();
            return actualURL;
        }
        return null;
    }

    public String adelante() {
        if (!historialAdelante.vacia()) {
            historialAtras.push(actualURL);
            actualURL = historialAdelante.pop();
            return actualURL;
        }
        return null;
    }

    public void limpiarHistorial() {
        historialAtras = new Pila();
        historialAdelante = new Pila();
        historialCompleto.clear();
        actualURL = null;
        guardarHistorial();
    }

    public void guardarHistorial() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String url : historialCompleto) {
                writer.write(url);
                writer.newLine();
            }
        } catch (IOException e) {
            out.printf("Error: %s%n", e.getMessage());
        }
    }

    public void cargarHistorial() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String url;
            while ((url = reader.readLine()) != null) {
                historialCompleto.add(url);
                if (actualURL == null) {
                    actualURL = url;
                } else {
                    historialAtras.push(url);
                }
            }
        } catch (IOException e) {
            out.printf("Error: %s%n", e.getMessage());
            guardarHistorial();
        }
    }
}
