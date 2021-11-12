import javax.management.RuntimeMBeanException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Scanner;

public class Main {

    public static boolean running = true;
    public static Scanner scanner;
    public static SudokuSolver solver;

    public static final String indentation = "      ";

    public static void main(String[] args) {

        scanner = new Scanner(System.in);
        solver = new SudokuSolver();

        printConsoleHeader();

        while(running){
            System.out.print("$: ");
            String command = scanner.nextLine();

            processCommand(command);
        }
    }

    public static void printConsoleHeader(){
        System.out.println("--- SUDOKU SOLVER ---");
    }

    public static void processCommand(String command){
        switch (command){
            case "help":
                printHelp();
                break;
            case "load":
                load();
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
            case "quit":
                running = false;
                break;
            default:
                System.out.println(indentation + "invalid command. Type 'help' for additional information.");
        }
    }

    public static void printHelp(){
        System.out.println(indentation + "help: prints this message.");
        System.out.println(indentation + "load: loads a sudoku board from a given string in to the solver.");
        System.out.println(indentation + "print: prints the currently loaded sudoku board.");
        System.out.println(indentation + "show: shows the solution to the previously solved sudoku board.");
        System.out.println(indentation + "solve: solves the currently loaded sudoku board. Uses the chosen settings.");
        System.out.println(indentation + "quit: quits the console.");
    }

    public static void load(){
        System.out.print(indentation + "sudoku string: ");
        String sudokuString = scanner.nextLine().trim();

        try{
            solver.loadSudokuFromString(sudokuString);
        }catch(RuntimeException e){
            System.out.println(indentation + e.getMessage());
        }
    }

    public static void print(){
        try{
            solver.print();
        }catch(RuntimeException e){
            System.out.println(indentation + e.getMessage());
        }
    }

    public static void solve(){
        try{
            solver.solve();
        }catch(RuntimeException e){
            System.out.println(indentation + e.getMessage());
        }
    }

    public static void show(){
        try{
            solver.show();
        }catch(RuntimeException e){
            System.out.println(indentation + e.getMessage());
        }
    }
}
