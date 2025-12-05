package com.buscaminas.vista;

import com.buscaminas.controlador.ControladorJuego;
import com.buscaminas.modelo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelTablero extends JPanel {
    private ControladorJuego controlador;
    private BotonCasilla[][] botones;
    private VentanaPrincipal ventanaPrincipal;
    private int filas;
    private int columnas;
    
    public PanelTablero(ControladorJuego controlador, VentanaPrincipal ventanaPrincipal) {
        this.controlador = controlador;
        this.ventanaPrincipal = ventanaPrincipal;
        this.filas = controlador.getTablero().getFilas();
        this.columnas = controlador.getTablero().getColumnas();
        
        inicializarPanel();
        crearBotones();
    }
    
    private void inicializarPanel() {
        setLayout(new GridLayout(filas, columnas, 2, 2));
        setBackground(new Color(100, 100, 100));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    
    private void crearBotones() {
        botones = new BotonCasilla[filas][columnas];
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                BotonCasilla boton = new BotonCasilla(i, j);
                botones[i][j] = boton;
                
                // Agregar listeners
                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (controlador.isJuegoTerminado()) {
                            return;
                        }
                        
                        int fila = boton.getFila();
                        int col = boton.getColumna();
                        
                        // Clic izquierdo: revelar
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            revelarCasilla(fila, col);
                        }
                        // Clic derecho: marcar bandera
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            marcarCasilla(fila, col);
                        }
                    }
                });
                
                add(boton);
            }
        }
    }
    
    private void revelarCasilla(int fila, int columna) {
        String resultado = controlador.revelarCasilla(fila, columna);
        
        actualizarTablero();
        ventanaPrincipal.actualizarEstadisticas();
        
        if (controlador.isJuegoTerminado()) {
            if (controlador.isJuegoGanado()) {
                JOptionPane.showMessageDialog(
                    this,
                    "¡FELICIDADES! ¡Has ganado el juego! 🎉",
                    "¡Victoria!",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "¡BOOM! Has pisado una mina 💣\n" + resultado,
                    "Game Over",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            
            int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Quieres jugar de nuevo?",
                "Nuevo Juego",
                JOptionPane.YES_NO_OPTION
            );
            
            if (respuesta == JOptionPane.YES_OPTION) {
                ventanaPrincipal.nuevoJuego();
            }
        }
    }
    
    private void marcarCasilla(int fila, int columna) {
        String resultado = controlador.marcarCasilla(fila, columna);
        actualizarBoton(fila, columna);
    }
    
    public void actualizarTablero() {
        Tablero tablero = controlador.getTablero();
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                actualizarBoton(i, j);
            }
        }
    }
    
    private void actualizarBoton(int fila, int columna) {
        Casilla casilla = controlador.getTablero().obtenerCasilla(fila, columna);
        BotonCasilla boton = botones[fila][columna];
        
        if (casilla.isTieneBandera() && casilla.isEstaOculta()) {
            boton.mostrarBandera();
        } else if (casilla.isEstaOculta()) {
            boton.mostrarOculta();
        } else if (casilla instanceof CasillaMina) {
            boton.mostrarMina();
        } else if (casilla instanceof CasillaNumero) {
            CasillaNumero casillaNum = (CasillaNumero) casilla;
            boton.mostrarNumero(casillaNum.getMinasVecinas());
        } else {
            boton.mostrarRevelada();
        }
    }
    
    public void reiniciarTablero() {
        removeAll();
        crearBotones();
        revalidate();
        repaint();
    }
}