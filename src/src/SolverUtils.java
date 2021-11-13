import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class SolverUtils {


    public boolean isNumeric(String sudokuString){

        for(char c : sudokuString.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public int[] getDomainAt(Cell[][] board, int y, int x){
        int[] domain = new int[10];

        // look across row
        for(int i = 1; i < 9; i++){
            domain[board[y][(x + i) % 9].value]++;
        }

        // look across column
        for(int i = 1; i < 9; i++){
            domain[board[(y + i) % 9][x].value]++;
        }

        // look across 3x3 square
        int xSquare = (x / 3) * 3;
        int ySquare = (y / 3) * 3;

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int removeNum = board[ySquare + i][xSquare + j].value;
                domain[removeNum]++;
            }
        }

        return domain;
    }

    public void loadDomainIntoEachCell(Cell[][] board){
        for(int y = 0; y < 9; y ++){
            for(int x = 0; x < 9; x++){

                if(board[y][x].value == 0){
                    board[y][x].domain = getDomainAt(board, y, x);
                }
            }
        }
    }

    public void loadCellsIntoOrdering(PriorityQueue<Cell> ordering, Cell[][] board){
        for(int y = 0; y < 9; y ++){
            for(int x = 0; x < 9; x++){

                if(!board[y][x].fixed){
                    ordering.add(board[y][x]);
                }
            }
        }
    }

    public String getSudokuString(Cell[][] board){
        String sudokuString = "";

        for(int y = 0; y < 9; y ++){
            for(int x = 0; x < 9; x++){
                sudokuString += board[y][x].value;
            }
        }
        return sudokuString;
    }
}
