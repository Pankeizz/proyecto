package com.buscaminas.modelo;

public class CasillaNumero extends Casilla {
    private int minasVecinas;
    
    public CasillaNumero(int fila, int columna, int minasVecinas) {
        super(fila, columna);
        this.minasVecinas = minasVecinas;
    }
    
    @Override
    public String revelar() {
        this.estaOculta = false;
        if (this.minasVecinas == 0) {
            return "revelar_vecinos";
        }
        return "numero_" + this.minasVecinas;
    }
    
    public int getMinasVecinas() {
        return minasVecinas;
    }
    
    public void setMinasVecinas(int minasVecinas) {
        this.minasVecinas = minasVecinas;
    }
}