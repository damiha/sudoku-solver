public class Cell {

    public int value;
    public boolean fixed;

    public Cell(int value, boolean fixed){
        this.fixed = fixed;
        this.value = value;
    }

    public String toString(){
        return this.value + "";
    }
}
