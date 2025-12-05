package com.buscaminas;

import com.buscaminas.vista.VentanaPrincipal;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configurar Look and Feel (apariencia del sistema operativo)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Usar el Look and Feel por defecto si falla
        }
        
        // Crear la ventana en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal();
        });
    }
}