package ab.lp.sudoku;


public class Sudoku {

    private final long id;
    private final int[] content;
    private final String status;

    public  Sudoku(long id, String puzzle) {
        this.id = id;
        this.content = tryParse(puzzle);
        if (isValid()) {
            status = "solved";
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
        return false;
    }
}
