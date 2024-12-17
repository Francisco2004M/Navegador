package edu.uagrm.fich.navegador.utils;

import java.io.Serial;
import java.io.Serializable;

public class Pila implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final int MAX = 100;
    private final String[] vector;
    private int cima;

    public Pila() {
        vector = new String[MAX + 1];
        cima = -1;
    }

    public boolean vacia() {
        return (cima == -1);
    }

    public boolean llena() {
        return (cima == MAX);
    }

    public void push(String x) {
        if (llena()) {
            throw new ArrayStoreException("Error: Pila llena");
        }
        cima++;
        vector[cima] = x;
    }

    public String pop() {
        if (vacia()) {
            throw new NegativeArraySizeException("Error: Pila vacía");
        }
        String x = vector[cima];
        cima--;
        return x;
    }

    public void limpiar() {
        cima = -1;
    }

    @Override
    public String toString() {
        StringBuilder cad = new StringBuilder("Pila\n[");
        for (int i = cima; i >= 0; i--) {
            cad.append(vector[i]).append(",\n");
        }
        cad.append("]");
        return cad.toString();
    }
}
