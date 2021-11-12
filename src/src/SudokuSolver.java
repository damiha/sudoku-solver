import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SudokuSolver {

    private Cell[][] board;
    private Cell[][] solution;

    private boolean sudokuLoaded;
    private boolean solverExecuted;
    private boolean unsolvable;

    int numberOfBacktracks;

    private final int CONSOLE_WIDTH = 25;

    public SudokuSolver(){
        this.board = new Cell[9][9];
        this.solution = new Cell[9][9];
    }

    private void reset(){
        this.sudokuLoaded = false;
        this.solverExecuted = false;
        this.unsolvable = false;

        this.numberOfBacktracks = 0;
    }

    public void loadSudokuFromString(String sudokuString){

        if(sudokuString == null || sudokuString.length() != 81 || !isNumeric(sudokuString)){
            throw new RuntimeException("ERROR: string violates the required format!");
        }

        reset();

        for(int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                int cellNum = Integer.parseInt(sudokuString.charAt(col * 9 + row) + "");
                this.board[col][row] = new Cell(cellNum, cellNum > 0);
            }
        }
        this.sudokuLoaded = true;
    }

    public void print(){
        print(board);
    }

    public void print(Cell[][] board){

        checkLoaded();

        for(int lineIndex = 0; lineIndex < 9; lineIndex++){
            if(lineIndex % 3 == 0){
                printHLine();
            }
            printLine(board, lineIndex);
        }
        printHLine();
    }

    private void printLine(Cell[][] board, int lineIndex){
        System.out.print(Main.indentation + "| ");
        for(int i = 0; i < 9; i++){
            System.out.print(board[lineIndex][i] + " ");

            if((i + 1) % 3 == 0){
                System.out.print("| ");
            }
        }
        System.out.println();
    }

    private void printHLine(){
        System.out.print(Main.indentation);
        for(int i = 0; i < CONSOLE_WIDTH; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    private boolean isNumeric(String sudokuString){

        for(char c : sudokuString.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    private void checkLoaded(){
        if(!sudokuLoaded){
            throw new RuntimeException("ERROR: no sudoku board is currently loaded into the solver.");
        }
    }

    public void solve(){

        checkLoaded();

        copyBoard(solution, board);

        solverExecuted = true;
        unsolvable = solve(solution) ? false : true;
    }

    private void copyBoard(Cell[][] dest, Cell[][] src){
        for(int y = 0; y < 9; y ++){
            for(int x = 0; x < 9; x++){

                if(dest[y][x] == null){
                    dest[y][x] = new Cell(0, false);
                }

                dest[y][x].value = src[y][x].value;
                dest[y][x].fixed = src[y][x].fixed;
            }
        }
    }

    public boolean solve(Cell[][] board){

        // find next empty cell
        int nextX = 0;
        int nextY = 0;

        boolean foundEmpty = false;

        outer: for(int y = 0; y < 9; y++){
            for(int x = 0; x < 9; x++){
                if(board[y][x].value == 0){
                    nextX = x;
                    nextY = y;
                    foundEmpty = true;
                    break outer;
                }
            }
        }

        if(!foundEmpty){
            return true;
        }

        Set<Integer> domain = getDomainAt(board, nextY, nextX);

        for(Integer domainValue : domain){
            board[nextY][nextX].value = domainValue;

            if(solve(board)){
                return true;
            }
        }
        // backtrack
        board[nextY][nextX].value = 0;
        this.numberOfBacktracks++;
        return false;
    }

    public void show(){

        checkLoaded();

        if(!solverExecuted){
            throw new RuntimeException("ERROR: solver has not been executed yet. Type 'solve' first.");
        }
        else if(unsolvable){
            throw new RuntimeException("ERROR: unsolvable board detected.");
        }

        print(solution);
    }

    private Set<Integer> getDomainAt(Cell[][] board, int y, int x){
        Set<Integer> domain = new HashSet<Integer>(List.of(1,2,3,4,5,6,7,8,9));

        // look across row
        for(int i = 1; i < 9; i++){
            domain.remove(board[y][(x + i) % 9].value);
        }

        // look across column
        for(int i = 1; i < 9; i++){
            domain.remove(board[(y + i) % 9][x].value);
        }

        // look across 3x3 square
        int xSquare = (x / 3) * 3;
        int ySquare = (y / 3) * 3;

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int removeNum = board[ySquare + i][xSquare + j].value;
                domain.remove(removeNum);
            }
        }

        return domain;
    }

    public void stats(){
        if(!solverExecuted){
            throw new RuntimeException("ERROR: solver has not been executed yet. Type 'solve' first.");
        }
        System.out.println(Main.indentation + "# backtracks: " + numberOfBacktracks);
    }
}
