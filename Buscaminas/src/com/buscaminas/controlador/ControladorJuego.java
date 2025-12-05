package com.buscaminas.controlador;

import com.buscaminas.modelo.*;

public class ControladorJuego {
    private Tablero tablero;
    private boolean juegoTerminado;
    private boolean juegoGanado;
    private int casillasReveladas;
    private int totalCasillasSinMinas;
    
    /**
     * Constructor del controlador
     * @param filas Número de filas del tablero
     * @param columnas Número de columnas del tablero
     * @param numMinas Número de minas a colocar
     */
    public ControladorJuego(int filas, int columnas, int numMinas) {
        this.tablero = new Tablero(filas, columnas, numMinas);
        this.juegoTerminado = false;
        this.juegoGanado = false;
        this.casillasReveladas = 0;
        this.totalCasillasSinMinas = (filas * columnas) - numMinas;
    }
    
    /**
     * Revela una casilla en la posición especificada
     * @param fila Fila de la casilla
     * @param columna Columna de la casilla
     * @return Mensaje con el resultado de la acción
     */
    public String revelarCasilla(int fila, int columna) {
        if (juegoTerminado) {
            return "El juego ya terminó. Inicia un nuevo juego.";
        }
        
        Casilla casilla = tablero.obtenerCasilla(fila, columna);
        
        if (casilla == null) {
            return "Posición inválida";
        }
        
        if (!casilla.isEstaOculta()) {
            return "Esta casilla ya fue revelada";
        }
        
        if (casilla.isTieneBandera()) {
            return "No puedes revelar una casilla con bandera. Quita la bandera primero.";
        }
        
        String resultado = tablero.revelarCasilla(fila, columna);
        
        // Si es una mina, el juego termina
        if (resultado.equals("GAME OVER")) {
            juegoTerminado = true;
            juegoGanado = false;
            revelarTodasLasMinas();
            return "¡BOOM! Has perdido. Pisaste una mina en (" + fila + ", " + columna + ")";
        }
        
        // Si es un número o casilla vacía
        casillasReveladas++;
        
        // Revelar casillas vecinas si es un 0
        if (resultado.equals("revelar_vecinos")) {
            revelarVecinos(fila, columna);
        }
        
        // Verificar si ganó
        if (casillasReveladas == totalCasillasSinMinas) {
            juegoTerminado = true;
            juegoGanado = true;
            return "¡FELICIDADES! Has ganado el juego.";
        }
        
        return resultado;
    }
    
    /**
     * Revela recursivamente las casillas vecinas cuando se encuentra un 0
     * @param fila Fila de la casilla
     * @param columna Columna de la casilla
     */
    private void revelarVecinos(int fila, int columna) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Saltar la casilla actual
                
                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;
                
                Casilla vecino = tablero.obtenerCasilla(nuevaFila, nuevaColumna);
                
                if (vecino != null && vecino.isEstaOculta() && !vecino.isTieneBandera()) {
                    if (!(vecino instanceof CasillaMina)) {
                        String resultado = tablero.revelarCasilla(nuevaFila, nuevaColumna);
                        casillasReveladas++;
                        
                        // Si el vecino también es 0, revelar sus vecinos recursivamente
                        if (resultado.equals("revelar_vecinos")) {
                            revelarVecinos(nuevaFila, nuevaColumna);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Marca o desmarca una casilla con una bandera
     * @param fila Fila de la casilla
     * @param columna Columna de la casilla
     * @return Mensaje con el resultado de la acción
     */
    public String marcarCasilla(int fila, int columna) {
        if (juegoTerminado) {
            return "El juego ya terminó";
        }
        
        Casilla casilla = tablero.obtenerCasilla(fila, columna);
        
        if (casilla == null) {
            return "Posición inválida";
        }
        
        if (!casilla.isEstaOculta()) {
            return "No puedes marcar una casilla revelada";
        }
        
        tablero.marcarCasilla(fila, columna);
        
        if (casilla.isTieneBandera()) {
            return "Bandera colocada en (" + fila + ", " + columna + ")";
        } else {
            return "Bandera removida de (" + fila + ", " + columna + ")";
        }
    }
    
    /**
     * Revela todas las minas cuando el juego termina
     */
    private void revelarTodasLasMinas() {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Casilla casilla = tablero.obtenerCasilla(i, j);
                if (casilla instanceof CasillaMina) {
                    casilla.revelar();
                }
            }
        }
    }
    
    /**
     * Reinicia el juego con un nuevo tablero
     * @param filas Número de filas
     * @param columnas Número de columnas
     * @param numMinas Número de minas
     */
    public void reiniciarJuego(int filas, int columnas, int numMinas) {
        this.tablero = new Tablero(filas, columnas, numMinas);
        this.juegoTerminado = false;
        this.juegoGanado = false;
        this.casillasReveladas = 0;
        this.totalCasillasSinMinas = (filas * columnas) - numMinas;
    }
    
    /**
     * Muestra el estado actual del tablero en consola
     */
    public void mostrarTablero() {
        System.out.println("\n=== TABLERO ===");
        System.out.print("   ");
        for (int j = 0; j < tablero.getColumnas(); j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();
        
        for (int i = 0; i < tablero.getFilas(); i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Casilla casilla = tablero.obtenerCasilla(i, j);
                
                if (casilla.isTieneBandera()) {
                    System.out.print(" F "); // Bandera
                } else if (casilla.isEstaOculta()) {
                    System.out.print(" . "); // Oculta
                } else if (casilla instanceof CasillaMina) {
                    System.out.print(" * "); // Mina
                } else {
                    CasillaNumero numero = (CasillaNumero) casilla;
                    int minas = numero.getMinasVecinas();
                    if (minas == 0) {
                        System.out.print("   "); // Vacía
                    } else {
                        System.out.printf(" %d ", minas); // Número
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Muestra estadísticas del juego
     */
    public void mostrarEstadisticas() {
        System.out.println("Casillas reveladas: " + casillasReveladas + "/" + totalCasillasSinMinas);
        System.out.println("Estado del juego: " + obtenerEstadoJuego());
    }
    
    /**
     * Obtiene el estado actual del juego
     * @return Estado del juego como String
     */
    public String obtenerEstadoJuego() {
        if (!juegoTerminado) {
            return "En progreso";
        } else if (juegoGanado) {
            return "¡GANADO!";
        } else {
            return "Perdido";
        }
    }
    
    // Getters
    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
    
    public boolean isJuegoGanado() {
        return juegoGanado;
    }
    
    public Tablero getTablero() {
        return tablero;
    }
    
    public int getCasillasReveladas() {
        return casillasReveladas;
    }
}