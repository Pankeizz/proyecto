# 🎮 Buscaminas en Java

Un juego clásico de Buscaminas implementado en Java utilizando Programación Orientada a Objetos (POO).

## 📋 Descripción

Este proyecto es una implementación completa del juego Buscaminas que demuestra los conceptos fundamentales de POO:
- **Clases y Objetos**
- **Encapsulamiento**
- **Herencia**
- **Polimorfismo**
- **Composición**

## 🎯 Características

- Tablero configurable (filas, columnas y número de minas)
- Sistema de banderas para marcar posibles minas
- Revelación automática de casillas vecinas cuando se encuentra un cero
- Detección automática de victoria/derrota
- Interfaz de consola interactiva
- Arquitectura MVC (Modelo-Vista-Controlador)

## 🏗️ Estructura del Proyecto
```
Buscaminas/
│
├── src/
│   └── com/
│       └── buscaminas/
│           ├── modelo/
│           │   ├── Casilla.java          # Clase padre abstracta
│           │   ├── CasillaMina.java      # Casilla con mina
│           │   ├── CasillaNumero.java    # Casilla con número
│           │   └── Tablero.java          # Gestión del tablero
│           │
│           ├── controlador/
│           │   └── ControladorJuego.java # Lógica del juego
│           │
│           └── Main.java                 # Punto de entrada
│
└── README.md
```

## 🔧 Requisitos

- **Java JDK 8** o superior
- Un IDE como IntelliJ IDEA, Eclipse o VS Code (opcional)
- Terminal/Consola para ejecutar el juego

## 🚀 Instalación y Ejecución

### Opción 1: Compilar desde la Terminal
```bash
# 1. Clonar o descargar el proyecto
cd Buscaminas

# 2. Compilar todos los archivos
javac -d bin src/com/buscaminas/*.java src/com/buscaminas/modelo/*.java src/com/buscaminas/controlador/*.java

# 3. Ejecutar el juego
java -cp bin com.buscaminas.Main
```

### Opción 2: Usar un IDE

1. Abre tu IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Importa el proyecto como "Proyecto Java"
3. Ejecuta la clase `Main.java`

## 🎮 Cómo Jugar

1. Al iniciar, verás un tablero con casillas ocultas (`.`)
2. Selecciona una opción:
   - **1. Revelar casilla**: Descubre qué hay en una casilla
   - **2. Marcar bandera**: Marca/desmarca una posible mina con `F`
3. Ingresa la fila y columna de la casilla
4. El juego termina cuando:
   - ✅ Revelas todas las casillas sin minas (¡GANASTE!)
   - ❌ Revelas una mina (¡PERDISTE!)

### Símbolos del Tablero

| Símbolo | Significado |
|---------|-------------|
| `.` | Casilla oculta |
| `F` | Bandera (posible mina) |
| `*` | Mina (solo visible al perder) |
| `0-8` | Número de minas vecinas |
| ` ` (espacio) | Casilla vacía sin minas vecinas |

### Ejemplo de Juego
```
=== TABLERO ===
    0  1  2  3  4  5  6  7 
 0  .  .  .  .  .  .  .  . 
 1  .  .  .  .  .  .  .  . 
 2  .  .  .  1  2  .  .  . 
 3  .  .  1  2  *  .  .  . 
 4  .  .  .  .  .  .  .  . 
 5  .  F  .  .  .  .  .  . 
 6  .  .  .  .  .  .  .  . 
 7  .  .  .  .  .  .  .  . 

Casillas reveladas: 5/54
Estado del juego: En progreso

Opciones:
1. Revelar casilla
2. Marcar/Desmarcar bandera
3. Salir
```

## 📚 Conceptos de POO Implementados

### 1. Clases y Objetos
- **Clase `Casilla`**: Plantilla que define propiedades comunes
- **Objetos**: 100 instancias de casillas en un tablero 10x10

### 2. Encapsulamiento
```java
public class Casilla {
    private boolean estaOculta;  // Atributo privado
    
    public void revelar() {      // Método público controlado
        this.estaOculta = false;
    }
}
```

### 3. Herencia
```
        Casilla (Padre)
           |
    -------+-------
    |             |
CasillaMina   CasillaNumero
```

### 4. Polimorfismo
```java
Casilla casilla = new CasillaMina();
casilla.revelar();  // Ejecuta la versión de CasillaMina
```

### 5. Composición
```java
public class Tablero {
    private Casilla[][] casillas;  // El tablero "tiene" casillas
}
```

## 🧪 Personalizaciones

Puedes modificar el tamaño del tablero y el número de minas en `Main.java`:
```java
// Crear un tablero 10x10 con 20 minas
ControladorJuego juego = new ControladorJuego(10, 10, 20);

// Crear un tablero 15x15 con 30 minas
ControladorJuego juego = new ControladorJuego(15, 15, 30);
```

## 📖 Diagrama de Clases
```
┌──────────────────────────────────┐
│         Tablero                  │
│  (Composición)                   │
│  - casillas: Casilla[][]         │
│  + revelarCasilla()              │
│  + marcarCasilla()               │
└────────────┬─────────────────────┘
             │ contiene
             ▼
      ┌─────────────┐
      │   Casilla   │ (Padre)
      ├─────────────┤
      │ # estaOculta│
      │ # tieneBandera│
      │ + revelar()  │
      │ + marcar()   │
      └──────┬───────┘
             │ extends
        ─────┴─────
        ↓         ↓
┌──────────────┐ ┌─────────────────┐
│ CasillaMina  │ │ CasillaNumero   │
│  (Hija)      │ │  (Hija)         │
├──────────────┤ ├─────────────────┤
│+ revelar()   │ │- minasVecinas   │
│  → Game Over │ │+ revelar()      │
└──────────────┘ └─────────────────┘
```

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Si deseas mejorar el proyecto:

1. Haz un fork del repositorio
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Haz commit de tus cambios (`git commit -m 'Agrega nueva funcionalidad'`)
4. Sube los cambios (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📝 Ideas para Mejorar

- [ ] Agregar interfaz gráfica (Swing o JavaFX)
- [ ] Implementar diferentes niveles de dificultad
- [ ] Añadir un sistema de puntuación y tiempo
- [ ] Guardar mejores puntajes
- [ ] Agregar sonidos y animaciones
- [ ] Modo multijugador
- [ ] Implementar "primer clic seguro" (nunca mina en el primer clic)

## 👨‍💻 Autor

**Tu Nombre**
- GitHub: [@tu-usuario](https://github.com/tu-usuario)
- Email: tu-email@example.com

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - mira el archivo [LICENSE](LICENSE) para más detalles.

## 🙏 Agradecimientos

- Inspirado en el clásico juego Buscaminas de Microsoft
- Proyecto educativo para aprender POO en Java
- Gracias a la comunidad de Java por su documentación

---

⭐ Si este proyecto te fue útil, considera darle una estrella en GitHub