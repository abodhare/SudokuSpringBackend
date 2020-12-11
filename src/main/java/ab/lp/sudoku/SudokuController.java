package ab.lp.sudoku;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudokuController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/isvalid")
    public Sudoku greeting(@RequestParam(value = "puzzle", defaultValue = "") String puzzle) {
        return new Sudoku(counter.incrementAndGet(), puzzle);
    }
}
