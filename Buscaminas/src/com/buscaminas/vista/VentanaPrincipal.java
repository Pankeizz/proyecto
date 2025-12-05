package com.buscaminas.vista;

import com.buscaminas.controlador.ControladorJuego;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private ControladorJuego controlador;
    private PanelTablero panelTablero;
    private JLabel lblEstadisticas;
    private JLabel lblEstado;
    private int filas;
    private int columnas;
    private int minas;
    
    public VentanaPrincipal() {
        // Configuración por defecto
        this.filas = 10;
        this.columnas = 10;
        this.minas = 15;
        
        inicializarVentana();
        crearMenuBar();
        crearPanelSuperior();
        inicializarJuego();
    }
    
    private void inicializarVentana() {
        setTitle("🎮 Buscaminas - Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        
        // Icono de la ventana (opcional)
        try {
            // Si tienes un icono, descomenta esta línea
            // setIconImage(new ImageIcon("icono.png").getImage());
        } catch (Exception e) {
            // Ignorar si no hay icono
        }
    }
    
    private void crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Juego
        JMenu menuJuego = new JMenu("Juego");
        
        JMenuItem itemNuevoJuego = new JMenuItem("Nuevo Juego");
        itemNuevoJuego.setAccelerator(KeyStroke.getKeyStroke("F2"));
        itemNuevoJuego.addActionListener(e -> nuevoJuego());
        
        JMenuItem itemDificultad = new JMenuItem("Cambiar Dificultad...");
        itemDificultad.addActionListener(e -> cambiarDificultad());
        
        JMenuItem itemPersonalizado = new JMenuItem("Personalizado...");
        itemPersonalizado.addActionListener(e -> configuracionPersonalizada());
        
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        
        menuJuego.add(itemNuevoJuego);
        menuJuego.addSeparator();
        menuJuego.add(itemDificultad);
        menuJuego.add(itemPersonalizado);
        menuJuego.addSeparator();
        menuJuego.add(itemSalir);
        
        // Menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        
        JMenuItem itemInstrucciones = new JMenuItem("Cómo Jugar");
        itemInstrucciones.addActionListener(e -> mostrarInstrucciones());
        
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        itemAcercaDe.addActionListener(e -> mostrarAcercaDe());
        
        menuAyuda.add(itemInstrucciones);
        menuAyuda.add(itemAcercaDe);
        
        menuBar.add(menuJuego);
        menuBar.add(menuAyuda);
        
        setJMenuBar(menuBar);
    }
    
    private void crearPanelSuperior() {
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(2, 1, 5, 5));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        panelSuperior.setBackground(new Color(240, 240, 240));
        
        lblEstadisticas = new JLabel("Casillas reveladas: 0 | Total: 0", SwingConstants.CENTER);
        lblEstadisticas.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblEstado = new JLabel("Estado: En progreso 🎮", SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
        lblEstado.setForeground(new Color(0, 128, 0));
        
        panelSuperior.add(lblEstadisticas);
        panelSuperior.add(lblEstado);
        
        add(panelSuperior, BorderLayout.NORTH);
    }
    
    private void inicializarJuego() {
        controlador = new ControladorJuego(filas, columnas, minas);
        
        if (panelTablero != null) {
            remove(panelTablero);
        }
        
        panelTablero = new PanelTablero(controlador, this);
        add(panelTablero, BorderLayout.CENTER);
        
        actualizarEstadisticas();
        
        pack();
        setLocationRelativeTo(null); // Centrar en la pantalla
        setVisible(true);
    }
    
    public void nuevoJuego() {
        controlador.reiniciarJuego(filas, columnas, minas);
        panelTablero.reiniciarTablero();
        actualizarEstadisticas();
    }
    
    private void cambiarDificultad() {
        String[] opciones = {"Fácil (8x8, 10 minas)", "Medio (10x10, 15 minas)", "Difícil (15x15, 40 minas)"};
        
        int seleccion = JOptionPane.showOptionDialog(
            this,
            "Selecciona la dificultad:",
            "Cambiar Dificultad",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[1]
        );
        
        switch (seleccion) {
            case 0: // Fácil
                filas = 8;
                columnas = 8;
                minas = 10;
                break;
            case 1: // Medio
                filas = 10;
                columnas = 10;
                minas = 15;
                break;
            case 2: // Difícil
                filas = 15;
                columnas = 15;
                minas = 40;
                break;
            default:
                return;
        }
        
        inicializarJuego();
    }
    
    private void configuracionPersonalizada() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        JTextField txtFilas = new JTextField(String.valueOf(filas));
        JTextField txtColumnas = new JTextField(String.valueOf(columnas));
        JTextField txtMinas = new JTextField(String.valueOf(minas));
        
        panel.add(new JLabel("Filas (5-20):"));
        panel.add(txtFilas);
        panel.add(new JLabel("Columnas (5-20):"));
        panel.add(txtColumnas);
        panel.add(new JLabel("Minas:"));
        panel.add(txtMinas);
        
        int resultado = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Configuración Personalizada",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int nuevasFilas = Integer.parseInt(txtFilas.getText());
                int nuevasColumnas = Integer.parseInt(txtColumnas.getText());
                int nuevasMinas = Integer.parseInt(txtMinas.getText());
                
                if (nuevasFilas < 5 || nuevasFilas > 20 || nuevasColumnas < 5 || nuevasColumnas > 20) {
                    JOptionPane.showMessageDialog(this, "Las filas y columnas deben estar entre 5 y 20");
                    return;
                }
                
                int totalCasillas = nuevasFilas * nuevasColumnas;
                if (nuevasMinas < 1 || nuevasMinas >= totalCasillas) {
                    JOptionPane.showMessageDialog(this, "El número de minas debe ser entre 1 y " + (totalCasillas - 1));
                    return;
                }
                
                filas = nuevasFilas;
                columnas = nuevasColumnas;
                minas = nuevasMinas;
                
                inicializarJuego();
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor ingresa números válidos");
            }
        }
    }
   private void mostrarInstrucciones() {
    String instrucciones =
        "🎮 CÓMO JUGAR BUSCAMINAS\n\n" +
        "OBJETIVO:\n" +
        "Revelar todas las casillas sin minas.\n\n" +
        "CONTROLES:\n" +
        "• Clic Izquierdo: Revelar casilla\n" +
        "• Clic Derecho: Marcar/Desmarcar bandera 🚩\n\n" +
        "SÍMBOLOS:\n" +
        "• 💣 = Mina (¡evítalas!)\n" +
        "• 🚩 = Bandera (marca posibles minas)\n" +
        "• Números (1-8) = Cantidad de minas vecinas\n" +
        "• Casilla vacía = No hay minas cerca\n\n" +
        "CONSEJOS:\n" +
        "• Empieza por las esquinas\n" +
        "• Usa las banderas para recordar dónde hay minas\n" +
        "• Los números te ayudan a deducir dónde están las minas\n\n" +
        "¡Buena suerte! 🍀\n";

    JOptionPane.showMessageDialog(
        this,
        instrucciones,
        "Instrucciones",
        JOptionPane.INFORMATION_MESSAGE
    );
}

        
       private void mostrarAcercaDe() {
    String acercaDe =
        "🎮 BUSCAMINAS EN JAVA\n\n" +
        "Versión: 1.0\n" +
        "Desarrollador: Tu Nombre\n\n" +
        "Proyecto educativo de Programación\n" +
        "Orientada a Objetos (POO)\n\n" +
        "Conceptos implementados:\n" +
        "✓ Clases y Objetos\n" +
        "✓ Encapsulamiento\n" +
        "✓ Herencia\n" +
        "✓ Polimorfismo\n" +
        "✓ Composición\n" +
        "✓ Patrón MVC\n\n" +
        "© 2024 - Todos los derechos reservados\n";

    JOptionPane.showMessageDialog(
        this,
        acercaDe,
        "Acerca de",
        JOptionPane.INFORMATION_MESSAGE
    );
}

        
       
    
    public void actualizarEstadisticas() {
        int reveladas = controlador.getCasillasReveladas();
        int total = (filas * columnas) - minas;
        
        lblEstadisticas.setText(String.format(
            "Casillas reveladas: %d / %d | Minas: %d",
            reveladas, total, minas
        ));
        
        if (controlador.isJuegoTerminado()) {
            if (controlador.isJuegoGanado()) {
                lblEstado.setText("Estado: ¡GANASTE! 🎉");
                lblEstado.setForeground(new Color(0, 128, 0));
            } else {
                lblEstado.setText("Estado: Perdiste 💣");
                lblEstado.setForeground(Color.RED);
            }
        } else {
            lblEstado.setText("Estado: En progreso 🎮");
            lblEstado.setForeground(new Color(0, 128, 0));
        }
    }
}