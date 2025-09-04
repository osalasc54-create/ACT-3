import java.util.*;

public class Main {

    public static void main(String[] args) {
        // 0 = celda vacía. Puedes cambiar el tablero inicial si quieres.
        int[][] puzzle = {
            {5,3,0, 0,7,0, 0,0,0},
            {6,0,0, 1,9,5, 0,0,0},
            {0,9,8, 0,0,0, 0,6,0},

            {8,0,0, 0,6,0, 0,0,3},
            {4,0,0, 8,0,3, 0,0,1},
            {7,0,0, 0,2,0, 0,0,6},

            {0,6,0, 0,0,0, 2,8,0},
            {0,0,0, 4,1,9, 0,0,5},
            {0,0,0, 0,8,0, 0,7,9}
        };

        SudokuGame game = new SudokuGame(puzzle);
        game.loop();
    }
}

/** Juego interactivo de Sudoku por consola. */
class SudokuGame {
    private int[][] board;           // tablero actual
    private final boolean[][] given; // celdas fijas del puzzle inicial
    private final Deque<Move> history = new ArrayDeque<>();

    SudokuGame(int[][] initial) {
        board = deepCopy(initial);
        given = new boolean[9][9];
        for (int r=0;r<9;r++)
            for (int c=0;c<9;c++)
                given[r][c] = (initial[r][c] != 0);
    }

    void loop() {
        printWelcome();
        printBoard();

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("\n> ");
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                String cmd = parts[0].toLowerCase();

                try {
                    switch (cmd) {
                        case "colocar": // colocar r c n
                        case "c": {
                            ensureArgs(parts, 4, "Uso: colocar r c n (1-9)");
                            int r = parseIndex(parts[1]);
                            int c = parseIndex(parts[2]);
                            int n = Integer.parseInt(parts[3]);
                            place(r,c,n);
                            break;
                        }
                        case "borrar": // borrar r c
                        case "b": {
                            ensureArgs(parts, 3, "Uso: borrar r c");
                            int r = parseIndex(parts[1]);
                            int c = parseIndex(parts[2]);
                            clear(r,c);
                            break;
                        }
                        case "pista":   // intenta una jugada lógica (única posibilidad)
                        case "hint": {
                            if (!hint()) {
                                System.out.println("No encontré una pista lógica (única posibilidad).");
                            }
                            break;
                        }
                        case "candidatos":
                        case "cand": {   // candidatos r c
                            ensureArgs(parts, 3, "Uso: candidatos r c");
                            int r = parseIndex(parts[1]);
                            int c = parseIndex(parts[2]);
                            if (board[r][c]!=0) {
                                System.out.println("La celda ya está llena.");
                            } else {
                                Set<Integer> cand = candidates(r,c);
                                System.out.println("Candidatos para ("+(r+1)+","+(c+1)+"): "+cand);
                            }
                            break;
                        }
                        case "validar":
                        case "check": {
                            if (isValidBoard()) System.out.println("Hasta ahora no hay conflictos visibles.");
                            else System.out.println("Hay conflictos (duplicados en fila/columna/cuadro).");
                            break;
                        }
                        case "deshacer":
                        case "undo": {
                            undo();
                            break;
                        }
                        case "resolver":
                        case "solve": {
                            if (solveWithBacktracking()) {
                                System.out.println("✔ Sudoku resuelto.");
                                printBoard();
                            } else {
                                System.out.println("No se pudo resolver (puzzle inválido).");
                            }
                            break;
                        }
                        case "reiniciar":
                        case "reset": {
                            reset();
                            System.out.println("Tablero reiniciado.");
                            printBoard();
                            break;
                        }
                        case "mostrar":
                        case "ver":
                        case "tablero": {
                            printBoard();
                            break;
                        }
                        case "ayuda":
                        case "help": {
                            printHelp();
                            break;
                        }
                        case "salir":
                        case "exit":
                        case "q": {
                            System.out.println("¡Hasta luego!");
                            return;
                        }
                        default:
                            System.out.println("Comando no reconocido. Escribe 'ayuda' para ver opciones.");
                    }
                    if (isComplete()) {
                        System.out.println("\n🎉 ¡Felicidades! Completaste el Sudoku.");
                        printBoard();
                        return;
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("⚠ " + ex.getMessage());
                }
            }
        }
    }

    // --------- Helpers que faltaban ----------
    private static void ensureArgs(String[] parts, int expected, String usage) {
        if (parts.length < expected) {
            throw new IllegalArgumentException(usage);
        }
    }

    /** Convierte "1..9" a índice 0..8, valida rango. */
    private int parseIndex(String s) {
        int v;
        try {
            v = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Índice inválido: " + s + ". Debe ser un número 1..9.");
        }
        if (v < 1 || v > 9) throw new IllegalArgumentException("El índice debe ser 1..9.");
        return v - 1;
    }

    // --------- Comandos ----------
    private void place(int r, int c, int n) {
        if (n<1 || n>9) throw new IllegalArgumentException("El número debe ser 1..9.");
        if (given[r][c]) throw new IllegalArgumentException("Esa celda es fija (del puzzle inicial).");
        if (board[r][c] == n) {
            System.out.println("La celda ya tiene ese valor.");
            return;
        }
        if (!isSafe(board, r, c, n)) {
            throw new IllegalArgumentException("Movimiento inválido: "+n+" entra en conflicto en fila/columna/cuadro.");
        }
        history.push(new Move(r,c,board[r][c]));
        board[r][c] = n;
        printBoard();
    }

    private void clear(int r, int c) {
        if (given[r][c]) throw new IllegalArgumentException("Esa celda es fija (del puzzle inicial).");
        if (board[r][c]==0) {
            System.out.println("La celda ya está vacía.");
            return;
        }
        history.push(new Move(r,c,board[r][c]));
        board[r][c]=0;
        printBoard();
    }

    private boolean hint() {
        // Estrategia lógica simple: "única posibilidad" en una celda
        for (int r=0;r<9;r++) {
            for (int c=0;c<9;c++) {
                if (board[r][c]==0) {
                    Set<Integer> cand = candidates(r,c);
                    if (cand.size()==1) {
                        int n = cand.iterator().next();
                        System.out.println("Pista: en ("+(r+1)+","+(c+1)+") solo puede ir "+n+".");
                        place(r,c,n);
                        return true;
                    }
                }
            }
        }
        // Fila: única posición para un número
        for (int r=0;r<9;r++) {
            Map<Integer,List<Integer>> pos = new HashMap<>();
            for (int n=1;n<=9;n++) pos.put(n,new ArrayList<>());
            for (int c=0;c<9;c++) if (board[r][c]==0) {
                for (int n: candidates(r,c)) pos.get(n).add(c);
            }
            for (int n=1;n<=9;n++) if (pos.get(n).size()==1) {
                int c = pos.get(n).get(0);
                System.out.println("Pista: en la fila "+(r+1)+", el número "+n+" solo puede ir en la columna "+(c+1)+".");
                place(r,c,n);
                return true;
            }
        }
        // Columna: única posición
        for (int c=0;c<9;c++) {
            Map<Integer,List<Integer>> pos = new HashMap<>();
            for (int n=1;n<=9;n++) pos.put(n,new ArrayList<>());
            for (int r=0;r<9;r++) if (board[r][c]==0) {
                for (int n: candidates(r,c)) pos.get(n).add(r);
            }
            for (int n=1;n<=9;n++) if (pos.get(n).size()==1) {
                int r = pos.get(n).get(0);
                System.out.println("Pista: en la columna "+(c+1)+", el número "+n+" solo puede ir en la fila "+(r+1)+".");
                place(r,c,n);
                return true;
            }
        }
        // Caja 3x3: única posición
        for (int br=0;br<9;br+=3) for (int bc=0;bc<9;bc+=3) {
            Map<Integer,List<int[]>> pos = new HashMap<>();
            for (int n=1;n<=9;n++) pos.put(n,new ArrayList<>());
            for (int r=br;r<br+3;r++) for (int c=bc;c<bc+3;c++) if (board[r][c]==0) {
                for (int n: candidates(r,c)) pos.get(n).add(new int[]{r,c});
            }
            for (int n=1;n<=9;n++) if (pos.get(n).size()==1) {
                int[] rc = pos.get(n).get(0);
                System.out.println("Pista: en el cuadro ("+((br/3)+1)+","+((bc/3)+1)+"), el "+n+" solo puede ir en ("+(rc[0]+1)+","+(rc[1]+1)+").");
                place(rc[0],rc[1],n);
                return true;
            }
        }
        return false;
    }

    private void undo() {
        if (history.isEmpty()) {
            System.out.println("Nada que deshacer.");
            return;
        }
        Move m = history.pop();
        board[m.r][m.c] = m.prev;
        System.out.println("Deshecho el último movimiento en ("+(m.r+1)+","+(m.c+1)+").");
        printBoard();
    }

    private void reset() {
        history.clear();
        for (int r=0;r<9;r++) for (int c=0;c<9;c++) if (!given[r][c]) board[r][c]=0;
    }

    // --------- Utilidades de Sudoku ----------
    private boolean isComplete() {
        for (int r=0;r<9;r++)
            for (int c=0;c<9;c++)
                if (board[r][c]==0) return false;
        return isValidBoard();
    }

    private boolean isValidBoard() {
        // filas
        for (int r=0;r<9;r++) if (!noDuplicates(getRow(r))) return false;
        // columnas
        for (int c=0;c<9;c++) if (!noDuplicates(getCol(c))) return false;
        // cuadros 3x3
        for (int br=0;br<9;br+=3)
            for (int bc=0;bc<9;bc+=3)
                if (!noDuplicates(getBox(br,bc))) return false;
        return true;
    }

    private int[] getRow(int r) {
        int[] a = new int[9];
        System.arraycopy(board[r], 0, a, 0, 9);
        return a;
    }

    private int[] getCol(int c) {
        int[] a = new int[9];
        for (int r=0;r<9;r++) a[r]=board[r][c];
        return a;
    }

    private int[] getBox(int br, int bc) {
        int[] a = new int[9]; int i=0;
        for (int r=br;r<br+3;r++) for (int c=bc;c<bc+3;c++) a[i++]=board[r][c];
        return a;
    }

    private boolean noDuplicates(int[] arr) {
        boolean[] seen = new boolean[10];
        for (int v: arr) {
            if (v==0) continue;
            if (seen[v]) return false;
            seen[v] = true;
        }
        return true;
    }

    private Set<Integer> candidates(int r, int c) {
        Set<Integer> cand = new LinkedHashSet<>();
        if (board[r][c]!=0) return cand;
        for (int n=1;n<=9;n++) if (isSafe(board,r,c,n)) cand.add(n);
        return cand;
    }

    private static boolean isSafe(int[][] b, int row, int col, int num) {
        // fila y columna
        for (int i=0;i<9;i++) if (b[row][i]==num || b[i][col]==num) return false;
        // subcuadro
        int br = (row/3)*3, bc = (col/3)*3;
        for (int r=0;r<3;r++) for (int c=0;c<3;c++)
            if (b[br+r][bc+c]==num) return false;
        return true;
    }

    private boolean solveWithBacktracking() {
        int[] empty = findEmpty(board);
        if (empty == null) return true;
        int r = empty[0], c = empty[1];

        for (int n=1;n<=9;n++) {
            if (isSafe(board,r,c,n)) {
                board[r][c]=n;
                if (solveWithBacktracking()) return true;
                board[r][c]=0;
            }
        }
        return false;
    }

    private static int[] findEmpty(int[][] b) {
        for (int r=0;r<9;r++)
            for (int c=0;c<9;c++)
                if (b[r][c]==0) return new int[]{r,c};
        return null;
    }

    private static int[][] deepCopy(int[][] a) {
        int[][] out = new int[a.length][];
        for (int i=0;i<a.length;i++) out[i]=Arrays.copyOf(a[i], a[i].length);
        return out;
    }

    // --------- UI ----------
    void printBoard() {
        System.out.println();
        System.out.println("    1 2 3   4 5 6   7 8 9");
        System.out.println("  +-------+-------+-------+");
        for (int r=0;r<9;r++) {
            System.out.print((r+1) + " | ");
            for (int c=0;c<9;c++) {
                int v = board[r][c];
                System.out.print(v==0 ? ". " : v+" ");
                if ((c+1)%3==0) System.out.print("| ");
            }
            System.out.println();
            if ((r+1)%3==0) System.out.println("  +-------+-------+-------+");
        }
        System.out.println("Comandos: ayuda");
    }

    void printWelcome() {
        System.out.println("========================================");
        System.out.println("           Sudoku — Juega por pasos     ");
        System.out.println("========================================");
        printHelp();
    }

    void printHelp() {
        System.out.println("\nComandos disponibles:");
        System.out.println("  mostrar | tablero | ver       -> Muestra el tablero");
        System.out.println("  colocar r c n   (o: c r c n)  -> Coloca número n en fila r, col c (1..9)");
        System.out.println("  borrar r c      (o: b r c)    -> Vacía la celda si no es fija");
        System.out.println("  candidatos r c  (o: cand r c) -> Muestra candidatos para una celda");
        System.out.println("  pista           (o: hint)     -> Rellena una celda con única posibilidad");
        System.out.println("  validar         (o: check)    -> Revisa si hay conflictos");
        System.out.println("  deshacer        (o: undo)     -> Revierte el último cambio");
        System.out.println("  resolver        (o: solve)    -> Resuelve el Sudoku (backtracking)");
        System.out.println("  reiniciar       (o: reset)    -> Vuelve al estado inicial");
        System.out.println("  salir | exit | q              -> Termina el juego");
        System.out.println("\nEjemplos:");
        System.out.println("  c 1 3 4       -> Pone un 4 en fila 1, columna 3");
        System.out.println("  cand 2 2      -> Muestra candidatos de (2,2)");
        System.out.println("  pista         -> Aplica una pista lógica si existe\n");
    }

    private static class Move {
        final int r, c, prev;
        Move(int r, int c, int prev) { this.r=r; this.c=c; this.prev=prev; }
    }
}
