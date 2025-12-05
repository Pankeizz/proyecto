package com.buscaminas.modelo;

public class CasillaMina extends Casilla {
    
    public CasillaMina(int fila, int columna) {
        super(fila, columna);
    }
    
    @Override
    public String revelar() {
        this.estaOculta = false;
        return "GAME OVER";
    }
}