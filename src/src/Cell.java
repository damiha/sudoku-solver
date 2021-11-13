import java.util.Arrays;
import java.util.Set;

public class Cell {

    public int value;
    public int x;
    public int y;
    public boolean fixed;

    public int[] domain;

    public Cell(int y,int x, int value){
        this.x = x;
        this.y = y;
        this.value = value;
        this.fixed = value != 0;

        this.domain = new int[10]; // numbers form 1 to 9 (mapped to indizes 1-9, 0 empty) with how many squares blocked them
    }

    public String toString(){
        return "x: " + this.x + ", y: " + this.y + ", " + this.value + ", "
                + getDomainString();
    }

    private String getDomainString(){
        String domainString = "";
        for(int i = 1; i <= 9; i++){
            domainString += domain[i];
        }
        return domainString;
    }
}
