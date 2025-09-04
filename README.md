# 🎮 Sudoku Interactivo en Java

Este proyecto implementa un **juego de Sudoku por consola**, donde el usuario puede jugar paso a paso, recibir pistas, validar movimientos y hasta resolver automáticamente el tablero.

---

## 🚀 Características
- Juega Sudoku interactivo directamente desde la terminal.
- **Comandos disponibles** para colocar números, borrar, validar, pedir candidatos, deshacer movimientos, etc.
- Sistema de **pistas lógicas** que coloca números seguros automáticamente.
- **Resolución automática** con backtracking si te atoras.
- Validación en tiempo real para evitar errores.

---

## 📋 Requisitos previos
- **Java 8 o superior** instalado en tu computadora.
  
Verifica la instalación con:
```bash
java -version
Deberías ver algo como:
java version "17.0.9" 2023-10-17 LTS

🗂️ Estructura del proyecto
├── Main.java      # Código fuente principal
└── README.md      # Documentación del proyecto

⚙️ Cómo compilar y ejecutar
1. Abrir la terminal

Abre la terminal de tu sistema o la terminal integrada de VS Code.

2. Compilar el código

Ubícate en la carpeta donde está el archivo Main.java y ejecuta:

javac Main.java


Esto generará el archivo Main.class necesario para la ejecución.

Ejecutar el programa
java Main

Si todo está correcto, verás la pantalla de bienvenida y el tablero inicial:

========================================
           Sudoku — Juega por pasos     
========================================

Comandos disponibles:
  mostrar | tablero | ver       -> Muestra el tablero
  colocar r c n   (o: c r c n)  -> Coloca número n en fila r, col c (1..9)
  borrar r c      (o: b r c)    -> Vacía la celda si no es fija
  candidatos r c  (o: cand r c) -> Muestra candidatos para una celda
  pista           (o: hint)     -> Rellena una celda con única posibilidad
  validar         (o: check)    -> Revisa si hay conflictos
  deshacer        (o: undo)     -> Revierte el último cambio
  resolver        (o: solve)    -> Resuelve el Sudoku automáticamente
  reiniciar       (o: reset)    -> Vuelve al estado inicial
  salir | exit | q              -> Termina el juego

    1 2 3   4 5 6   7 8 9
  +-------+-------+-------+
1 | 5 3 . | . 7 . | . . . | 
2 | 6 . . | 1 9 5 | . . . | 
3 | . 9 8 | . . . | . 6 . | 
  +-------+-------+-------+
4 | 8 . . | . 6 . | . . 3 | 
5 | 4 . . | 8 . 3 | . . 1 | 
6 | 7 . . | . 2 . | . . 6 | 
  +-------+-------+-------+
7 | . 6 . | . . . | 2 8 . | 
8 | . . . | 4 1 9 | . . 5 | 
9 | . . . | . 8 . | . 7 9 | 
  +-------+-------+-------+
Comandos: ayuda

🕹️ Cómo jugar
Colocar un número

Sintaxis:

c fila columna número


Ejemplo: Colocar el número 4 en la fila 1, columna 3:

c 1 3 4

Ver candidatos para una celda

Muestra qué números son posibles en una celda vacía:

cand 2 2


Salida:

Candidatos para (2,2): [2,7]

Borrar una celda

Borra el número de una celda que hayas colocado:

b 1 3

Pedir una pista

El programa coloca automáticamente un número seguro en alguna celda:

pista


Salida:

Pista: en (3,1) solo puede ir 1.

Validar el tablero

Revisa si hay conflictos como números repetidos en fila, columna o subcuadro:

validar


Salida:

Hasta ahora no hay conflictos visibles.

Deshacer el último movimiento

Revierte tu última jugada:

undo

Resolver el Sudoku automáticamente

Si te atoras, el programa lo completará usando backtracking:

resolver

Reiniciar el Sudoku

Vuelve al estado inicial:

reset

Salir del juego
salir

💻 Ejemplo de sesión
> c 1 3 4
Pusiste el número 4 en fila 1, columna 3.

> cand 2 2
Candidatos para (2,2): [2,7]

> pista
Pista: en (3,1) solo puede ir 1.

> validar
Hasta ahora no hay conflictos visibles.

> resolver
✔ Sudoku resuelto.

⚠️ Solución de problemas
1. No puedo escribir en la terminal

Asegúrate de que la terminal tiene el foco (haz clic dentro de ella).

Si usas VS Code, revisa que en el archivo .vscode/launch.json esté configurado:

"console": "integratedTerminal"


O ejecuta manualmente con:

java Main

2. Error: javac no se reconoce

Significa que Java no está instalado o la variable de entorno PATH no está configurada.

Instala Java y reinicia tu terminal.

🎯 Objetivo educativo

Este proyecto no solo te permite jugar Sudoku, sino también entender:

Backtracking para resolver problemas de búsqueda.

Validaciones lógicas para filtrar candidatos.

Estructuras de datos como Set y Stack para control de movimientos y deshacer jugadas.
