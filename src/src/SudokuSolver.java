import java.util.*;

public class SudokuSolver {

    private Cell[][] board;
    private Cell[][] solution;
    private BoardUtils boardUtils;
    private SolverUtils solverUtils;

    private boolean sudokuLoaded;
    private boolean solverExecuted;
    private boolean unsolvable;

    int numberOfBacktracks;

    private boolean MVRenabled;

    private PriorityQueue<Cell> mvrOrdering;

    private boolean DEBUG = false;

    public SudokuSolver(){
        this.board = new Cell[9][9];
        this.solution = new Cell[9][9];

        this.boardUtils = new BoardUtils();
        this.solverUtils = new SolverUtils();

        this.mvrOrdering = new PriorityQueue<>(new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {

                int remainingValuesO1 = 0;
                int remainingValuesO2 = 0;

                for(int i = 0; i < 9; i++){
                    if(o1.domain[i] == 0){
                        remainingValuesO1++;
                    }
                    if(o2.domain[i] == 0){
                        remainingValuesO2++;
                    }
                }

                return Integer.compare(remainingValuesO1,remainingValuesO2);
            }
        });
    }

    private void reset(){
        this.sudokuLoaded = false;
        this.solverExecuted = false;
        this.unsolvable = false;

        this.numberOfBacktracks = 0;
    }

    public void loadSudokuFromString(String sudokuString){

        if(sudokuString == null || sudokuString.length() != 81 || !solverUtils.isNumeric(sudokuString)){
            throw new RuntimeException("ERROR: string violates the required format!");
        }

        reset();

        for(int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                int cellVal = Integer.parseInt(sudokuString.charAt(y * 9 + x) + "");
                this.board[y][x] = new Cell(y,x,cellVal);
            }
        }
        this.sudokuLoaded = true;
    }

    public void print(){

        checkLoaded();

        boardUtils.print(board);
    }

    public void solve(){

        checkLoaded();

        boardUtils.copyBoard(solution, board);

        solverUtils.loadDomainIntoEachCell(solution);

        if(MVRenabled){
            solverUtils.loadCellsIntoOrdering(mvrOrdering, solution);
        }

        solverExecuted = true;
        unsolvable = solve(solution) ? false : true;
    }

    public boolean solve(Cell[][] board){

        if(DEBUG){
            boardUtils.print(board);
            System.out.println();
        }

        int[] nextPos = findNextPosition(board);

        if(nextPos[0] == -1 && nextPos[1] == -1){
            return true;
        }

        // assume that the cell already has the updated domain
        Cell currentCell = board[nextPos[0]][nextPos[1]];

        for(int i = 1; i <= 9; i++){

            // blocked by at least one other position
            if(currentCell.domain[i] > 0)
                continue;

            currentCell.value = i;

            updateNeighbours(board, currentCell, "value added");

            if(solve(board)){
                return true;
            }
            updateNeighbours(board, currentCell, "value removed");
        }
        // backtrack
        currentCell.value = 0;
        this.numberOfBacktracks++;
        return false;
    }
    public void updateNeighbours(Cell[][] board, Cell cell, String action){
        int y = cell.y;
        int x = cell.x;
        // collect all neighbours
        Set<Cell> neighbours = new HashSet<Cell>();

        // look across row
        for(int i = 1; i < 9; i++){
            neighbours.add(board[y][(x + i) % 9]);
        }

        // look across column
        for(int i = 1; i < 9; i++){
            neighbours.add(board[(y + i) % 9][x]);
        }

        // look across 3x3 square
        int xSquare = (x / 3) * 3;
        int ySquare = (y / 3) * 3;

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                Cell neighbour = board[ySquare + i][xSquare + j];

                if(neighbour != cell){
                    neighbours.add(neighbour);
                }
            }
        }
        for(Cell neighbour : neighbours){
            if(action.equals("value added") && neighbour.domain != null && neighbour.value == 0){
                neighbour.domain[cell.value]++;
            }else if(action.equals("value removed") && neighbour.domain != null && neighbour.value == 0){
                neighbour.domain[cell.value]--;
            }
        }
    }
    public int[] findNextPosition(Cell[][] board){
        // {-1,-1} => no cell found
        int[] nextPos = {-1,-1};
        if(MVRenabled){
            Cell nextCell = mvrOrdering.remove();
            nextPos[0] = nextCell.y;
            nextPos[1] = nextCell.x;

        }else {

            outer: for(int y = 0; y < 9; y++){
                for(int x = 0; x < 9; x++){
                    if(board[y][x].value == 0){
                        nextPos[0] = y;
                        nextPos[1] = x;
                        break outer;
                    }
                }
            }
        }
        return nextPos;
    }

    public void show(){

        checkLoaded();
        checkSolved();

        boardUtils.print(solution);
    }

    public void stats(){
        checkSolverExecuted();
        System.out.println(Main.indentation + "# backtracks: " + numberOfBacktracks);
    }

    public void printPreferences(){
        System.out.println(Main.indentation + "MVR: " + MVRenabled);
    }

    public void out(){
        checkLoaded();
        checkSolved();

        System.out.println(Main.indentation + "output: " + solverUtils.getSudokuString(solution));
    }

    public void setMVR(boolean value){
        MVRenabled = value;
    }

    private void checkLoaded(){
        if(!sudokuLoaded){
            throw new RuntimeException("ERROR: no sudoku board is currently loaded into the solver.");
        }
    }

    private void checkSolverExecuted(){
        if(!solverExecuted){
            throw new RuntimeException("ERROR: solver has not been executed yet. Type 'solve' first.");
        }
    }

    private void checkSolved(){

        checkSolverExecuted();

        if(unsolvable){
            throw new RuntimeException("ERROR: unsolvable board detected.");
        }
    }
}
