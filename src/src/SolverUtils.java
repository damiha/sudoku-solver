import java.util.*;

public class SolverUtils {

    private Comparator<Assignment> assignmentComparator;

    public SolverUtils(){
        assignmentComparator = new Comparator<Assignment>() {
            @Override
            public int compare(Assignment o1, Assignment o2) {
                return Integer.compare(o1.numberOfCollisions, o2.numberOfCollisions);
            }
        };
    }


    public boolean isNumeric(String sudokuString){

        for(char c : sudokuString.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public void setDomainAt(Cell[][] board, Cell cell){

        int y = cell.y;
        int x = cell.x;

        // look across row
        for(int i = 1; i < 9; i++){
            cell.addBlocked(board[y][(x + i) % 9].value);
        }

        // look across column
        for(int i = 1; i < 9; i++){
            cell.addBlocked(board[(y + i) % 9][x].value);
        }

        // look across 3x3 square
        int xSquare = (x / 3) * 3;
        int ySquare = (y / 3) * 3;

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int removeNum = board[ySquare + i][xSquare + j].value;
                cell.addBlocked(removeNum);
            }
        }
    }

    public void setDomainAtEachCell(Cell[][] board){
        for(int y = 0; y < 9; y ++){
            for(int x = 0; x < 9; x++){

                if(board[y][x].value == 0){
                    setDomainAt(board, board[y][x]);
                    board[y][x].updateMVRGroup();
                }
            }
        }
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
                neighbour.addBlocked(cell.value);
            }else if(action.equals("value removed") && neighbour.domain != null && neighbour.value == 0){
                neighbour.removeBlocked(cell.value);
            }
            neighbour.updateMVRGroup();
        }
    }

    public int getNumCollisionsWithNeighbours(Cell[][] board, Cell cell, int value){
        int y = cell.y;
        int x = cell.x;

        int numCollisions = 0;

        // look across row
        for(int i = 1; i < 9; i++){
            numCollisions += collidesWith(board[y][(x + i) % 9], value);
        }

        // look across column
        for(int i = 1; i < 9; i++){
            numCollisions += collidesWith(board[(y + i) % 9][x], value);
        }

        // look across 3x3 square
        int xSquare = (x / 3) * 3;
        int ySquare = (y / 3) * 3;

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){

                Cell neighbour = board[ySquare + i][xSquare + j];

                if(neighbour != cell){
                    numCollisions += collidesWith(neighbour, value);
                }
            }
        }
        return numCollisions;
    }

    private int collidesWith(Cell cell, int value){
        return (cell.isEmpty() && cell.domain[value] == 0) ? 1 : 0;
    }

    public void sortAssignments(List<Assignment> assignments){
        assignments.sort(assignmentComparator);
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
