package ab.lp.sudoku;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SudokuController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/isvalid")
    public Sudoku greeting(@RequestParam(value = "puzzle", defaultValue = "") String puzzle) {
        return new Sudoku(counter.incrementAndGet(), puzzle);
    }

    @GetMapping("/generate")
    public Sudoku generatePuzzle(@RequestParam(value = "level", defaultValue = "1") String level) {
        int difficulty = 1;
        try {
            difficulty = Integer.parseInt(level);
        } catch (NumberFormatException e) {
            difficulty = 1;
        }
        return new Sudoku(true, difficulty);
    }
}
