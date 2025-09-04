# Proyecto de Algoritmos Recursivos en Java

Este proyecto contiene la implementación en **Java** de tres algoritmos clásicos:

1. **Serie de Fibonacci** (recursiva con memoización).  
2. **Subset Sum Problem** (determinación de subconjunto con suma objetivo).  
3. **Resolución de Sudoku** usando **backtracking**.

---

## 🚀 Tecnologías utilizadas
- **Lenguaje:** Java 8+  
- **IDE recomendado:** VS Code, IntelliJ IDEA, NetBeans o Eclipse  
- **Terminal:** Windows PowerShell, CMD, Linux Bash, o terminal integrada del IDE

---

## 📂 Estructura del proyecto
├── Main.java # Código fuente principal
└── README.md # Documentación del proyecto


---

## 📝 Descripción de los algoritmos

### 1. Serie de Fibonacci
Calcula el n-ésimo número en la serie de Fibonacci de forma **recursiva**, utilizando **memoización** para optimizar el rendimiento.

Ejemplo:


---

## 📝 Descripción de los algoritmos

### 1. Serie de Fibonacci
Calcula el n-ésimo número en la serie de Fibonacci de forma **recursiva**, utilizando **memoización** para optimizar el rendimiento.

Ejemplo:

Fibonacci(10) = 55


---

### 2. Subset Sum Problem
Determina si existe un subconjunto dentro de un conjunto de enteros que sume un **valor objetivo**.

Ejemplo:
Conjunto: {3, 34, 4, 12, 5, 2}
Objetivo: 9
Resultado: true



---

### 3. Sudoku Solver
Resuelve un **Sudoku 9x9** utilizando **backtracking**, llenando las celdas vacías (representadas por `0`).

Ejemplo de entrada:

5 3 0 | 0 7 0 | 0 0 0
6 0 0 | 1 9 5 | 0 0 0
0 9 8 | 0 0 0 | 0 6 0
------+-------+------
8 0 0 | 0 6 0 | 0 0 3
4 0 0 | 8 0 3 | 0 0 1
7 0 0 | 0 2 0 | 0 0 6
------+-------+------
0 6 0 | 0 0 0 | 2 8 0
0 0 0 | 4 1 9 | 0 0 5
0 0 0 | 0 8 0 | 0 7 9



---

## ⚙️ Cómo ejecutar el proyecto

Sigue estos pasos para compilar y ejecutar el programa en tu computadora.

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/algoritmos-java.git
cd algoritmos-java

java -version

Deberías ver algo similar a:
java version "17.0.9" 2023-10-17 LTS

javac Main.java
Esto generará un archivo Main.class en el mismo directorio.

4. Ejecutar el programa
java Main


Ejemplo de salida
Fibonacci(10) = 55
SubsetSum target=9 -> true

Sudoku inicial:
5 3 . | . 7 . | . . . 
6 . . | 1 9 5 | . . . 
. 9 8 | . . . | . 6 . 
------+-------+------
8 . . | . 6 . | . . 3 
4 . . | 8 . 3 | . . 1 
7 . . | . 2 . | . . 6 
------+-------+------
. 6 . | . . . | 2 8 . 
. . . | 4 1 9 | . . 5 
. . . | . 8 . | . 7 9 

Resuelto: true
5 3 4 | 6 7 8 | 9 1 2 
6 7 2 | 1 9 5 | 3 4 8 
1 9 8 | 3 4 2 | 5 6 7 
------+-------+------
8 5 9 | 7 6 1 | 4 2 3 
4 2 6 | 8 5 3 | 7 9 1 
7 1 3 | 9 2 4 | 8 5 6 
------+-------+------
9 6 1 | 5 3 7 | 2 8 4 
2 8 7 | 4 1 9 | 6 3 5 
3 4 5 | 2 8 6 | 1 7 9
