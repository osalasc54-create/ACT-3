import java.util.*;


public class Main {

  
    // 1) FIBONACCI RECURSIVO

    // f(0)=0, f(1)=1, f(n)=f(n-1)+f(n-2)
    public static long fibonacciRec(int n) {
        if (n < 0) throw new IllegalArgumentException("n no puede ser negativo");
        Map<Integer, Long> memo = new HashMap<>();
        return fibHelper(n, memo);
    }

    private static long fibHelper(int n, Map<Integer, Long> memo) {
        if (n == 0) return 0L;      // caso base
        if (n == 1) return 1L;      // caso base
        if (memo.containsKey(n)) return memo.get(n);
        long val = fibHelper(n - 1, memo) + fibHelper(n - 2, memo); // caso recursivo
        memo.put(n, val);
        return val;
    }

  
    // 2) SUBSET SUM (existencia)
   
    // Devuelve true si existe subconjunto en nums que suma target.
    public static boolean subsetSum(int[] nums, int target) {
        Map<String, Boolean> memo = new HashMap<>();
        return subsetSumHelper(nums, 0, target, memo);
    }

    private static boolean subsetSumHelper(int[] nums, int i, int target, Map<String, Boolean> memo) {
        if (target == 0) return true;                 // caso base: ya logramos la suma
        if (i == nums.length) return false;           // caso base: no hay más elementos
        String key = i + "|" + target;
        if (memo.containsKey(key)) return memo.get(key);

        // Opción 1: incluir nums[i]
        boolean incluir = subsetSumHelper(nums, i + 1, target - nums[i], memo);

        // Opción 2: excluir nums[i]
        boolean excluir = subsetSumHelper(nums, i + 1, target, memo);

        boolean res = incluir || excluir;
        memo.put(key, res);
        return res;
    }

  
    // 3) SUDOKU (BACKTRACKING)
 
    // Tablero 9x9, usa 0 para celdas vacías
    public static boolean solveSudoku(int[][] board) {
        int[] empty = findEmpty(board);
        if (empty == null) return true; // no hay vacíos: resuelto
        int row = empty[0], col = empty[1];

        for (int num = 1; num <= 9; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board)) return true;
                board[row][col] = 0; // backtrack
            }
        }
        return false; // gatilla retroceso
    }

    private static int[] findEmpty(int[][] board) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (board[r][c] == 0) return new int[]{r, c};
        return null;
    }

    private static boolean isSafe(int[][] board, int row, int col, int num) {
        // fila
        for (int c = 0; c < 9; c++)
            if (board[row][c] == num) return false;
        // columna
        for (int r = 0; r < 9; r++)
            if (board[r][col] == num) return false;
        // subcuadro 3x3
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[boxRow + r][boxCol + c] == num) return false;

        return true;
    }

    public static void printBoard(int[][] board) {
        for (int r = 0; r < 9; r++) {
            if (r % 3 == 0 && r != 0) System.out.println("------+-------+------");
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0 && c != 0) System.out.print("| ");
                System.out.print(board[r][c] == 0 ? ". " : board[r][c] + " ");
            }
            System.out.println();
        }
    }

    
    // DEMOS
 
    public static void main(String[] args) {
        // 1) Fibonacci
        int n = 10;
        System.out.println("Fibonacci(" + n + ") = " + fibonacciRec(n));

        // 2) Subset Sum
        int[] nums = {3, 34, 4, 12, 5, 2};
        int target = 9;
        System.out.println("SubsetSum target=" + target + " -> " + subsetSum(nums, target));

        // 3) Sudoku
        int[][] board = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        System.out.println("\nSudoku inicial:");
        printBoard(board);
        boolean solved = solveSudoku(board);
        System.out.println("\nResuelto: " + solved);
        printBoard(board);
    }
}