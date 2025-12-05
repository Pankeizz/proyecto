package com.buscaminas.modelo;

import java.util.Random;

public class Tablero {
    private int filas;
    private int columnas;
    private Casilla[][] casillas;
    
    public Tablero(int filas, int columnas, int numMinas) {
        this.filas = filas;
        this.columnas = columnas;
        this.casillas = new Casilla[filas][columnas];
        inicializarTablero(numMinas);
    }
    
    private void inicializarTablero(int numMinas) {
        // Inicializar todas como CasillaNumero
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = new CasillaNumero(i, j, 0);
            }
        }
        
        // Colocar minas aleatoriamente
        Random rand = new Random();
        int minasColocadas = 0;
        
        while (minasColocadas < numMinas) {
            int fila = rand.nextInt(filas);
            int col = rand.nextInt(columnas);
            
            if (!(casillas[fila][col] instanceof CasillaMina)) {
                casillas[fila][col] = new CasillaMina(fila, col);
                minasColocadas++;
            }
        }
        
        // Calcular números
        calcularMinasVecinas();
    }
    
    private void calcularMinasVecinas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (casillas[i][j] instanceof CasillaNumero) {
                    int minas = contarMinasVecinas(i, j);
                    ((CasillaNumero) casillas[i][j]).setMinasVecinas(minas);
                }
            }
        }
    }
    
    private int contarMinasVecinas(int fila, int col) {
        int contador = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nuevaFila = fila + i;
                int nuevaCol = col + j;
                
                if (esValida(nuevaFila, nuevaCol) && 
                    casillas[nuevaFila][nuevaCol] instanceof CasillaMina) {
                    contador++;
                }
            }
        }
        return contador;
    }
    
    private boolean esValida(int fila, int col) {
        return fila >= 0 && fila < filas && col >= 0 && col < columnas;
    }
    
    public String revelarCasilla(int fila, int columna) {
        if (!esValida(fila, columna)) {
            return "Posición inválida";
        }
        return casillas[fila][columna].revelar();
    }
    
    public void marcarCasilla(int fila, int columna) {
        if (esValida(fila, columna)) {
            casillas[fila][columna].marcar();
        }
    }
    
    public Casilla obtenerCasilla(int fila, int columna) {
        if (esValida(fila, columna)) {
            return casillas[fila][columna];
        }
        return null;
    }
    
    public int getFilas() {
        return filas;
    }
    
    public int getColumnas() {
        return columnas;
    }
}