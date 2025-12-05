package com.buscaminas.modelo;

public class Casilla {
    protected int fila;
    protected int columna;
    protected boolean estaOculta;
    protected boolean tieneBandera;
    
    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.estaOculta = true;
        this.tieneBandera = false;
    }
    
    public void marcar() {
        this.tieneBandera = !this.tieneBandera;
    }
    
    public String revelar() {
        this.estaOculta = false;
        return "revelada";
    }
    
    // Getters
    public boolean isEstaOculta() {
        return estaOculta;
    }
    
    public boolean isTieneBandera() {
        return tieneBandera;
    }
    
    public int getFila() {
        return fila;
    }
    
    public int getColumna() {
        return columna;
    }
}