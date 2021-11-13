import java.util.Scanner;

public class Main {

    public static boolean running = true;
    public static Scanner scanner;
    public static SudokuSolver solver;

    public static final String indentation = "      ";
    public static final int NEW_LINES_AT_CLEAR = 200;
    public static final int CONSOLE_WIDTH = 25;

    public static void main(String[] args) {

        scanner = new Scanner(System.in);
        solver = new SudokuSolver();

        printConsoleHeader();

        while (running) {
            System.out.print("$: ");
            String command = scanner.nextLine().trim();

            processCommand(command);
        }
    }

    public static void printConsoleHeader() {
        System.out.println("--- SUDOKU SOLVER ---");
    }

    public static void processCommand(String command) {
        switch (command) {
            case "out":
                out();
                break;
            case "clear":
                clear();
                break;
            case "help":
                printHelp();
                break;
            case "load":
                load();
                break;
            case "prefs":
                printPreferences();
                break;
            case "print":
                print();
                break;
            case "solve":
                solve();
                break;
            case "show":
                show();
                break;
            case "stats":
                stats();
                break;
            case "quit":
                running = false;
                break;
            case "+MVR":
                solver.getState().setMVR(true);
                break;
            case "-MVR":
                solver.getState().setMVR(false);
                break;
            case "+LCV":
                solver.getState().setLcv(true);
                break;
            case "-LCV":
                solver.getState().setLcv(false);
                break;
            default:
                System.out.println(indentation + "invalid command. Type 'help' for additional information.");
        }
    }

    public static void printHelp() {
        System.out.println(indentation + "clear: clears the console.");
        System.out.println(indentation + "help: prints this message.");
        System.out.println(indentation + "load: loads a sudoku board from a given string in to the solver.");
        System.out.println(indentation + "out: outputs the solved sudoku as a string.");
        System.out.println(indentation + "prefs: prints the current preferences. Use +- <pref> to change the preferences.");
        System.out.println(indentation + "print: prints the currently loaded sudoku board.");
        System.out.println(indentation + "show: shows the solution to the previously solved sudoku board.");
        System.out.println(indentation + "solve: solves the currently loaded sudoku board. Uses the preferences.");
        System.out.println(indentation + "stats: shows statistics concerning the last calculation.");
        System.out.println(indentation + "quit: quits the console.");
    }

    public static void load() {
        System.out.print(indentation + "sudoku string: ");
        String sudokuString = scanner.nextLine().trim();

        try {
            solver.loadSudokuFromString(sudokuString);
        } catch (RuntimeException e) {
            System.out.println(indentation + e.getMessage());
        }
    }

    public static void print() {
        try {
            solver.print();
        } catch (RuntimeException e) {
            System.out.println(indentation + e.getMessage());
        }
    }

    public static void printPreferences(){
        solver.getState().printPreferences();
    }

    public static void solve() {
        try {
            solver.solve();
        } catch (RuntimeException e) {
            System.out.println(indentation + e.getMessage());
        }
    }

    public static void show() {
        try {
            solver.show();
        } catch (RuntimeException e) {
            System.out.println(indentation + e.getMessage());
        }
    }

    public static void clear() {
        String newlines = "";
        for(int i = 0; i < NEW_LINES_AT_CLEAR;  i++) {
            newlines += "\n";
        }
        System.out.print(newlines);
        printConsoleHeader();
    }

    public static void stats(){
        try{
            solver.getState().printStats();
        }catch(RuntimeException e){
            System.out.println(indentation + e.getMessage());
        }
    }

    public static void out(){
        try{
            solver.out();
        }catch(RuntimeException e){
            System.out.println(indentation + e.getMessage());
        }
    }
}
