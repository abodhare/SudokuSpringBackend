package ab.lp.sudoku;


import java.util.List;

public class Sudoku {

    private final long id;
    private final int[] content;
    private final String status;
    private boolean multiple;

    public  Sudoku(long id, String puzzle) {
        this.id = id;
        this.content = tryParse(puzzle);
        if (isSolved(this.content)) {
            status = "solved";
        } else {
            solve();
            if (isMultiple()) {
                status = "invalid";
            } else if (!isValid(this.content)) {
                status = "invalid";
            } else {
                status = "valid";
            }
        }
    }

    public long getId() {
        return id;
    }

    public int[] getContent() {
        return content;
    }

    public int[] tryParse(String puzzle) {
        if (puzzle.length() != 81) {
            throw new IllegalArgumentException("Length of the puzzle must be 81");
        }
        int[] val = new int[81];
        for (int i = 0; i < 81; i++) {
            try {
                val[i] = Integer.parseInt(String.valueOf(puzzle.charAt(i)));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("String must contain numbers 0-9");
            }
        }
        return val;
    }

    public String getStatus() {
        return status;
    }

    public static boolean isValid(int[] content) {
        // Rows
        for (int i = 0; i < 9; i++) {
            int[] set = new int[10];
            for (int j = i * 9; j < (i + 1) * 9; j++) {
                set[content[j]]++;
            }
            for (int j = 1; j < 10; j++) {
                if (set[j] > 1) return false;
            }
        }
        // Columns
        for (int i = 0; i < 9; i++) {
            int[] set = new int[10];
            for (int j = i; j < 81; j += 9) {
                set[content[j]]++;
            }
            for (int j = 1; j < 10; j++) {
                if (set[j] > 1) return false;
            }
        }
        // Blocks
        for (int i = 0; i < 9; i++) {
            int[] set = new int[10];
            int a = 3 * (i % 3), b = 3 * Math.floorDiv(i, 3);
            int start = a + 9 * b;
            for (int j = 0; j < 3; j++) {
                int l = start + 9 * j;
                for (int k = l; k < l + 3; k++) {
                    set[content[k]]++;
                }
            }
            for (int j = 1; j < 10; j++) {
                if (set[j] > 1) return false;
            }
        }
        return true;
    }

    public int[] solve() {
        int[] puzzle = new int[81];
        int[] answer = new int[81];
        System.arraycopy(this.content, 0, puzzle, 0, this.content.length);
        int x = solve(answer, puzzle, 0, 0, 0);
        if (x > 1) setMultiple(true);
        return answer;
    }

    private int solve(int[] answer, int[] puzzle, int i, int j, int x) {
        int index = i + 9 * j;
        if (index == 81) {
            System.arraycopy(puzzle, 0, answer, 0, 81);
            return x + 1;
        }

        if (puzzle[index] != 0) {
            if (i == 8) {
                return solve(answer, puzzle, 0, j+1, x);
            }
            return solve(answer, puzzle, i+1, j, x);
        }

        for (int k = 1; k < 10; k++) {
            if (isSafe(puzzle, k, i, j)) {
                puzzle[index] = k;
                int solved;
                if (i == 8) {
                    solved = solve(answer, puzzle, 0, j+1, x);
                } else {
                    solved = solve(answer, puzzle, i+1, j, x);
                }
                if (solved > x) {
                    x = solved;
                }
                if (solved > 1) {
                    return solved;
                }
                puzzle[index] = 0;
            }
        }

        return x;
    }

    private boolean isSafe(int[] puzzle, int num, int i, int j) {
        int index = 9 * j + i;
        if (puzzle[index] != 0) return false;
        puzzle[index] = num;
        int[] set = new int[10];

        // Row
        for (int k = 9 * j; k < 9 * (j + 1); k++) {
            set[puzzle[k]]++;
        }
        for (int k = 1; k < 10; k++) {
            if (set[k] > 1) {
                puzzle[index] = 0;
                return false;
            }
        }

        // Column
        set = new int[10];
        for (int k = i; k < 81; k += 9) {
            set[puzzle[k]]++;
        }
        for (int k = 1; k < 10; k++) {
            if (set[k] > 1) {
                puzzle[index] = 0;
                return false;
            }
        }

        // Block
        set = new int[10];
        int a = i / 3, b = j / 3;
        int start = (3 * b) * 9 + (a * 3);
        for (int k = 0; k < 3; k++) {
            int l = start + 9 * k;
            for (int m = l; m < l + 3; m++) {
                set[puzzle[m]]++;
            }
        }
        for (int k = 1; k < 10; k++) {
            if (set[k] > 1) {
                puzzle[index] = 0;
                return false;
            }
        }
        return true;
    }

    public static boolean isSolved(int[] content) {
        if (isValid(content)) {
            for (int i = 0; i < content.length; i++) {
                if (content[i] == 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }
}
