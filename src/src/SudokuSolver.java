public class SudokuSolver {

    private Cell[][] board;
    private Cell[][] solution;

    private boolean sudokuLoaded;
    private boolean solved;

    private final int CONSOLE_WIDTH = 25;

    public SudokuSolver(){
        this.board = new Cell[9][9];
        this.solution = new Cell[9][9];

        this.sudokuLoaded = false;
        this.solved = false;
    }

    public void loadSudokuFromString(String sudokuString){

        if(sudokuString == null || sudokuString.length() != 81 || !isNumeric(sudokuString)){
            throw new RuntimeException("ERROR: string violates the required format!");
        }

        for(int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                int cellNum = Integer.parseInt(sudokuString.charAt(col * 9 + row) + "");
                this.board[col][row] = new Cell(cellNum);
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

        // TODO: implement algorithm

        solved = true;
    }

    public void show(){

        checkLoaded();

        if(!solved){
            throw new RuntimeException("ERROR: board has not been solved yet. Type 'solve' first.");
        }

        print(solution);
    }
}
