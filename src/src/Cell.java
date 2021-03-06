import java.util.HashSet;
import java.util.Set;

public class Cell {

    public int x;
    public int y;
    public int value;
    public int whichMVRGroup;
    public int valuesRemaining;

    public int[] domain;
    public Set<Cell> changeableNeighbours;
    public MVRHeap mvrHeap;

    public Cell(int y,int x, int value, MVRHeap mvrHeap){
        this.x = x;
        this.y = y;
        this.value = value;
        this.mvrHeap = mvrHeap;

        this.domain = new int[10]; // numbers form 1 to 9 (mapped to indizes 1-9, 0 empty) with how many squares blocked them
        this.valuesRemaining = 9;

        this.changeableNeighbours = new HashSet<>();
    }

    public void loadChangeableNeighbours(Cell[][] board){
        Cell neighbour = null;
        // look across row
        for(int i = 1; i < 9; i++){
            neighbour = board[y][(x + i) % 9];

            if(neighbour.isEmpty()){
                changeableNeighbours.add(neighbour);
            }
        }

        // look across column
        for(int i = 1; i < 9; i++){
            neighbour = board[(y + i) % 9][x];

            if(neighbour.isEmpty()){
                changeableNeighbours.add(neighbour);
            }
        }

        // look across 3x3 square
        int xSquare = (x / 3) * 3;
        int ySquare = (y / 3) * 3;

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){

                neighbour = board[ySquare + i][xSquare + j];

                if(neighbour.isEmpty() && neighbour != this){
                    changeableNeighbours.add(neighbour);
                }
            }
        }
    }
    // for easy debugging in IntelliJ
    public String toString(){
        return "x: " + this.x + ", y: " + this.y + ", " + this.value + ", " + this.valuesRemaining;
    }

    public void updateMVRGroup(){

        if(!isEmpty())
            return;

        mvrHeap.switchGroup(this );
    }

    public void addBlocked(int value){

        if(value == 0 || !isEmpty())
            return;

        if(this.domain[value] == 0){

            this.valuesRemaining--;
        }
        this.domain[value]++;
    }

    public void removeBlocked(int value){

        if(value == 0 || !isEmpty())
            return;

        this.domain[value]--;

        if(this.domain[value] == 0){

            this.valuesRemaining++;
        }
    }

    public void assign(int value){
        this.value = value;
        mvrHeap.removeFromGroup(this);
    }

    public boolean isEmpty(){
        return this.value == 0;
    }

    public Cell getDeepCopy(){
        Cell deepCopy = new Cell(this.y, this.x, this.value, this.mvrHeap);
        deepCopy.whichMVRGroup = this.whichMVRGroup;
        deepCopy.valuesRemaining = this.valuesRemaining;

        for(int i = 0; i < this.domain.length; i++){
            deepCopy.domain[i] = this.domain[i];
        }
        return deepCopy;
    }
}
