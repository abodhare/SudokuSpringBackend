# SudokuSpringBackend

REST API for Sudoku validation and generation

This project was bootstrapped with Spring Boot and uses unit testing to check correctness of the core solver. You can download this project and get started with

```sh
# For checking if changes are okay
mvn test

mvn spring-boot:run
```

For a better experience, you can use docker
```sh
docker build -t sudoku .
docker run --rm -dp 8080:8080 sudoku
```

TODO: Better Sudoku generation, hard puzzles are not hard enough.
