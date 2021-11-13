## [sudoku-solver](https://github.com/damiha/sudoku-solver)

A small console application written in Java that solves 9x9 sudokus using backtracking, forward-checking and heuristics.

### features

- **load**      a sudokuboard from an input string.
  ![alt text](https://github.com/damiha/sudoku-solver/blob/f9146a215c8e8cfb4c3bd865a77759e5c69bf2cc/console%20read.png)
- **out**       prints the solved sudoku (as a string) to the console.
- **solve**     calculates a solution (if one exists). Uses the heuristics defined in the preferences.
- **stats**     outputs performance measures (like execution time, # of backtracking) regarding the last calculation.

### heuristics

- **MVR**       = Minimum Values Remaining, the sudoku cell with the least possible assigments gets tried first.
- **LCV**       = Least Constraining Value, the value that constrains adjacent cells the least gets tried first.

![alt text](https://github.com/damiha/sudoku-solver/blob/5b8358e422e30b74391c4982cf3d36fd679f1c16/console%20solve%20no%20heuristics.png)
![alt text](https://github.com/damiha/sudoku-solver/blob/5b8358e422e30b74391c4982cf3d36fd679f1c16/console%20solve%20heuristics.png)
