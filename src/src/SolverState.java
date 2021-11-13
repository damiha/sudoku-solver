public class SolverState {

    private boolean sudokuLoaded;
    private boolean solverExecuted;
    private boolean solvable;

    /* preferences */
    private boolean mvrEnabled;
    private boolean lcvEnabled;

    /* stats */
    private int numberOfBacktracks;
    private int numberOfVarAssignments;
    private long executionTime;

    /* helper variables */
    private long startTime, endTime;

    public void setLoaded(){
        sudokuLoaded = true;
        solverExecuted = false;
        solvable = true;
    }

    public void setSolverExecuted(){
        solverExecuted = true;
    }

    public void setSolvable(boolean solvable){
        this.solvable = solvable;
    }

    public void setMVR(boolean value){
        mvrEnabled = value;
    }

    public void setLcv(boolean value){
        lcvEnabled = value;
    }

    public void incBacktracks(){
        numberOfBacktracks++;
    }

    public void incVarAssignments(){
        numberOfVarAssignments++;
    }

    public boolean isMvrEnabled(){
        return mvrEnabled;
    }

    public boolean isLcvEnabled(){
        return lcvEnabled;
    }

    public void startTimer(){
        startTime = System.currentTimeMillis();
    }
    public void endTimer(){
        endTime = System.currentTimeMillis();
        executionTime = endTime - startTime;
    }

    public void checkLoaded(){
        if(!sudokuLoaded){
            throw new RuntimeException("ERROR: no sudoku board is currently loaded into the solver.");
        }
    }

    public void checkSolverExecuted(){
        if(!solverExecuted){
            throw new RuntimeException("ERROR: solver has not been executed yet. Type 'solve' first.");
        }
    }

    public void checkSolved(){

        checkSolverExecuted();

        if(!solvable){
            throw new RuntimeException("ERROR: unsolvable board detected.");
        }
    }

    public void resetStats(){
        numberOfBacktracks = 0;
        numberOfVarAssignments = 0;
        executionTime = 0;
    }

    /* console methods */
    public void printStats(){

        System.out.println(Main.indentation + "# backtracks: " + numberOfBacktracks);
        System.out.println(Main.indentation + "# var assigments: " + numberOfVarAssignments);
        System.out.println(Main.indentation + "# execution time(ms): " + executionTime);
    }

    public void printPreferences(){
        System.out.println(Main.indentation + "MVR: " + isMvrEnabled());
        System.out.println(Main.indentation + "LCR: " + isLcvEnabled());
    }
}
