import java.util.*;

public class SudokuSolver {

    private final Cell[][] board;
    private final Cell[][] solution;
    private final MVRHeap mvrHeap;

    private final BoardUtils boardUtils;
    private final SolverUtils solverUtils;
    private final SolverState solverState;

    public SudokuSolver(){

        this.board = new Cell[9][9];
        this.solution = new Cell[9][9];
        this.mvrHeap = new MVRHeap();

        this.boardUtils = new BoardUtils();
        this.solverUtils = new SolverUtils();
        this.solverState = new SolverState();
    }

    public void loadSudokuFromString(String sudokuString){

        if(sudokuString == null || sudokuString.length() != 81 || !solverUtils.isNumeric(sudokuString)){
            throw new RuntimeException("ERROR: string violates the required format!");
        }

        for(int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                int cellVal = Integer.parseInt(sudokuString.charAt(y * 9 + x) + "");
                this.board[y][x] = new Cell(y,x,cellVal, mvrHeap);
            }
        }

        solverState.setLoaded();
    }

    public void print(){

        solverState.checkLoaded();

        boardUtils.print(board);
    }

    public void solve(){

        solverState.checkLoaded();
        solverState.resetStats();
        mvrHeap.clear();

        copyBoard(solution, board);

        solverState.startTimer();

        solverUtils.setDomainAndNeighboursAtEachCell(solution);

        solverState.setSolverExecuted();
        solverState.setSolvable(solve(solution));

        solverState.endTimer();
    }

    public boolean solve(Cell[][] board){

        Cell nextCell = getNextCell(board);

        if(nextCell == null){
            return true;
        }

        // assume that the cell already has the updated domain

        List<Assignment> assignments = getNextAssignments(board, nextCell);

        for(Assignment assignment : assignments){

            nextCell.assign(assignment.value);
            solverState.incVarAssignments();

            solverUtils.updateNeighbours(board, nextCell, "value added");

            if(!mvrHeap.cellHasNoOptions() && solve(board)){
                return true;
            }
            solverUtils.updateNeighbours(board, nextCell, "value removed");
        }
        // backtrack
        nextCell.value = 0;
        solverState.incBacktracks();
        return false;
    }

    public List<Assignment> getNextAssignments(Cell[][] board, Cell cell){

        List<Assignment> nextAssignments = new LinkedList<Assignment>();

        for(int value = 1; value <= 9; value++){

            // blocked by at least one other position
            if(cell.domain[value] > 0)
                continue;

            if(solverState.isLcvEnabled()){
                nextAssignments.add(new Assignment(value, solverUtils.getNumCollisionsWithNeighbours(board, cell, value)));
            }else {
                nextAssignments.add(new Assignment(value, 0));
            }
        }

        if(solverState.isLcvEnabled()){
            solverUtils.sortAssignments(nextAssignments);
        }

        return nextAssignments;
    }

    public Cell getNextCell(Cell[][] board){
        // null => no cell found
        Cell nextCell = null;

        if(solverState.isMvrEnabled()){

            nextCell = mvrHeap.getMVRCell();

        }else {

            outer: for(int y = 0; y < 9; y++){
                for(int x = 0; x < 9; x++){
                    if(board[y][x].value == 0){
                        nextCell = board[y][x];
                        break outer;
                    }
                }
            }
        }
        return nextCell;
    }

    public void show(){

        solverState.checkLoaded();
        solverState.checkSolved();

        boardUtils.print(solution);
    }

    public void out(){
        solverState.checkLoaded();
        solverState.checkSolved();

        System.out.println(Main.indentation + "output: " + solverUtils.getSudokuString(solution));
    }

    public SolverState getState(){
        return solverState;
    }

    public void copyBoard(Cell[][] dest, Cell[][] src){
        for(int y = 0; y < 9; y ++){
            for(int x = 0; x < 9; x++){
                dest[y][x] = src[y][x].getDeepCopy();
            }
        }
    }
}
