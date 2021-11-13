public class SolverState {

    private boolean sudokuLoaded;
    private boolean solverExecuted;
    private boolean solvable;

    /* preferences */
    private boolean mvrEnabled;

    /* stats */
    private int numberOfBacktracks;
    private int numberOfVarAssigments;
    private long executionTime;

    /* helper variables */
    private long startTime, endTime;

    public SolverState(){

    }

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

    public void incBacktracks(){
        numberOfBacktracks++;
    }

    public void incVarAssignments(){
        numberOfVarAssigments++;
    }

    public boolean isMvrEnabled(){
        return mvrEnabled;
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
        numberOfVarAssigments = 0;
        executionTime = 0;
    }

    /* console methods */
    public void printStats(){

        System.out.println(Main.indentation + "# backtracks: " + numberOfBacktracks);
        System.out.println(Main.indentation + "# var assigments: " + numberOfVarAssigments);
        System.out.println(Main.indentation + "# execution time(ms): " + executionTime);
    }

    public void printPreferences(){
        System.out.println(Main.indentation + "MVR: " + isMvrEnabled());
    }
}
