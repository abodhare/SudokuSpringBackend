package ab.lp.sudoku;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SudokuApplicationTests {

	@LocalServerPort
	private int port;

	@MockBean
	private Sudoku sudoku;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void emptyPuzzleShouldFail() throws Exception {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			new Sudoku(0, "");
		});
	}

	@Test
	public void isValidWithInvalidPuzzleShouldSayInvalid() throws Exception {
	    String puzzle = "000000000000000000000000000000000000000000000000000000000000000000000000000000000";
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/isvalid?puzzle="+puzzle,
				String.class)).contains("invalid");
	}

	@Test
	public void emptySudokuShouldFail() throws Exception {
		when(sudoku.getStatus()).thenReturn("invalid");
	}

	// Columns should contain distinct values
	@Test
	public void invalidSudokuShouldFail() throws Exception {
	    String puzzle = "123456789123456789123456789123456789123456789123456789123456789123456789123456789";
		assertThat(new Sudoku(0, puzzle)).hasFieldOrPropertyWithValue("status", "invalid");
	}

	// Rows should contain distinct values
	@Test
	public void invalidSudokuShouldFail1() throws Exception {
		String puzzle = "111111111222222222333333333444444444555555555666666666777777777888888888999999999";
		assertThat(new Sudoku(0, puzzle)).hasFieldOrPropertyWithValue("status", "invalid");
	}

	// Blocks should contain distinct values
	@Test
	public void invalidSudokuShouldFail2() throws Exception {
		String puzzle = "111222333111222333111222333444555666444555666444555666777888999777888999777888999";
		assertThat(new Sudoku(0, puzzle)).hasFieldOrPropertyWithValue("status", "invalid");
	}

	@Test
	public void validSudokuShouldPass() throws Exception {
		String puzzle = "435269781682571493197834562826195347374682915951743628519326874248957136763418259";
		assertThat(new Sudoku(0, puzzle)).hasFieldOrPropertyWithValue("status", "valid");
	}

	@Test
	public void anotherValidSudokuShouldPass() throws Exception {
		String puzzle = "123678945584239761967145328372461589691583274458792613836924157219857436745316892";
		assertThat(new Sudoku(0, puzzle)).hasFieldOrPropertyWithValue("status", "valid");
	}

	@Test
	public void incompleteValidSudokuShouldPass() throws Exception {
		String puzzle = "000260701680070090190004500820100040004602900050003028009300074040050036703018000";
		assertThat(new Sudoku(0, puzzle)).hasFieldOrPropertyWithValue("status", "valid");
	}

	@Test
	public void anotherIncompleteValidSudokuShouldPass() throws Exception {
		String puzzle = "020608000580009700000040000370000500600000004008000013000020000009800036000306090";
		assertThat(new Sudoku(0, puzzle)).hasFieldOrPropertyWithValue("status", "valid");
	}


	@Test
	public void unsolvableSudokuShouldFail() throws Exception {
		String puzzle = "000260701680070090190004500820100040000000000000000000000000000000000000000000000";
		assertThat(new Sudoku(0, puzzle)).hasFieldOrPropertyWithValue("status", "invalid");
	}

	@Test
	public void anotherUnsolvableSudokuShouldFail() throws Exception {
		String puzzle = "020608000580009700000040000370000500600000004008000013000000000000000000000000000";
		assertThat(new Sudoku(0, puzzle)).hasFieldOrPropertyWithValue("status", "invalid");
	}
}
