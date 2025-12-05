package com.buscaminas.vista;

import javax.swing.*;
import java.awt.*;

public class BotonCasilla extends JButton {
    private int fila;
    private int columna;
    private static final Color COLOR_OCULTA = new Color(189, 189, 189);
    private static final Color COLOR_REVELADA = new Color(220, 220, 220);
    private static final Color COLOR_BANDERA = new Color(255, 100, 100);
    private static final Color COLOR_MINA = new Color(255, 50, 50);
    
    // Colores para los números
    private static final Color[] COLORES_NUMEROS = {
        Color.BLUE,           // 1
        new Color(0, 128, 0), // 2 - Verde
        Color.RED,            // 3
        new Color(0, 0, 128), // 4 - Azul oscuro
        new Color(128, 0, 0), // 5 - Marrón
        Color.CYAN,           // 6
        Color.BLACK,          // 7
        Color.GRAY            // 8
    };
    
    public BotonCasilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        
        // Configuración visual
        setPreferredSize(new Dimension(40, 40));
        setFont(new Font("Arial", Font.BOLD, 16));
        setFocusPainted(false);
        setBackground(COLOR_OCULTA);
        setBorder(BorderFactory.createRaisedBevelBorder());
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public void mostrarOculta() {
        setText("");
        setBackground(COLOR_OCULTA);
        setBorder(BorderFactory.createRaisedBevelBorder());
        setEnabled(true);
    }
    
    public void mostrarBandera() {
        setText("🚩");
        setBackground(COLOR_BANDERA);
        setFont(new Font("Arial", Font.PLAIN, 20));
    }
    
    public void mostrarMina() {
        setText("💣");
        setBackground(COLOR_MINA);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setBorder(BorderFactory.createLoweredBevelBorder());
        setEnabled(false);
    }
    
    public void mostrarNumero(int numero) {
        if (numero == 0) {
            setText("");
        } else {
            setText(String.valueOf(numero));
            setForeground(COLORES_NUMEROS[numero - 1]);
        }
        setBackground(COLOR_REVELADA);
        setBorder(BorderFactory.createLoweredBevelBorder());
        setEnabled(false);
    }
    
    public void mostrarRevelada() {
        setText("");
        setBackground(COLOR_REVELADA);
        setBorder(BorderFactory.createLoweredBevelBorder());
        setEnabled(false);
    }
    
    public int getFila() {
        return fila;
    }
    
    public int getColumna() {
        return columna;
    }
}