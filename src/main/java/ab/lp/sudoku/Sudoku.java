package ab.lp.sudoku;


public class Sudoku {

    private final long id;
    private final int[] content;
    private final String status;

    public  Sudoku(long id, String puzzle) {
        this.id = id;
        this.content = tryParse(puzzle);
        if (isSolved()) {
            status = "solved";
        } else if (isValid()) {
            status = "valid";
        } else {
            status = "invalid";
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

    private boolean isValid() {
        // Rows
        for (int i = 0; i < 9; i++) {
            int[] set = new int[10];
            for (int j = i * 9; j < (i + 1) * 9; j++) {
                set[content[j]]++;
            }
            for (int j = 0; j < 9; j++) {
                if (set[j] == 2) return false;
            }
        }
        // Columns
        for (int i = 0; i < 9; i++) {
            int[] set = new int[10];
            for (int j = i; j < 81; j += 9) {
                set[content[j]]++;
            }
            for (int j = 0; j < 9; j++) {
                if (set[j] == 2) return false;
            }
        }
        // Blocks
        for (int i = 0; i < 9; i++) {
            int[] set = new int[10];
            int start = (3 * i) + (3 * (int) (i / 3));
            for (int j = 0; j < 3; j++) {
                int l = start + 9 * j;
                for (int k = l; k < l + 3; k++) {
                    set[content[k]]++;
                }
            }
            for (int j = 0; j < 9; j++) {
                if (set[j] == 2) return false;
            }
        }
        return true;
    }

    private boolean isSafe(int num, int i, int j) {
        int index = 9 * j + i;
        if (this.content[index] != 0) return false;
        this.content[index] = num;
        int[] set = new int[10];

        // Row
        for (int k = 9 * j; k < 9 * (j + 1); k++) {
            set[k]++;
        }
        for (int k = 1; k < 10; k++) {
            if (set[k] > 1) {
                this.content[index] = 0;
                return false;
            }
        }

        // Column
        set = new int[10];
        for (int k = i; k < 81; k += 9) {
            set[k]++;
        }
        for (int k = 1; k < 10; k++) {
            if (set[k] > 1) {
                this.content[index] = 0;
                return false;
            }
        }

        // Block
        set = new int[10];
        int a = i / 3, b = j / 3;
        int start = b * 9 + a;
        for (int k = 0; k < 3; k++) {
            int l = start + 9 * k;
            for (int m = l; m < l + 3; m++) {
                set[content[m]]++;
            }
        }
        for (int k = 1; k < 10; k++) {
            if (set[k] > 1) {
                this.content[index] = 0;
                return false;
            }
        }
        return true;
    }

    private boolean isSolved() {
        if (isValid()) {
            for (int i = 0; i < content.length; i++) {
                if (content[i] == 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
