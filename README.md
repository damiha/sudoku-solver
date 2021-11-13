## [sudoku-solver](https://github.com/damiha/sudoku-solver)

A small console application written in Java that solves 9x9 sudokus using backtracking, forward-checking and heuristics.

### features

- **load**      a sudokuboard from an input string.
- **out**       prints the solved sudoku (as a string) to the console.
- **solve**     calculates a solution (if one exists). Uses the heuristics defined in the preferences.
- **stats**     outputs performance measures (like execution time, # of backtracking) regarding the last calculation.

### heuristics

- **MVR**       = Minimum Values Remaining, the sudoku cell with the least possible assigments gets tried first.
- **LCV**       = Least Constraining Value, the value that constrains adjacent cells the least gets tried first.

 	![alt text](/images/15-puzzle-pages.png)
