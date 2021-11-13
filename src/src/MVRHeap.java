import java.util.HashSet;
import java.util.Set;

public class MVRHeap {

    public static Set<Cell>[] mvrGroups;

    public MVRHeap(){

        this.mvrGroups = new HashSet[10];

        for(int i = 0; i <= 9; i++){
            this.mvrGroups[i] = new HashSet<Cell>();
        }
    }

    public Cell getMVRCell(){

        for(int i = 1; i <= 9; i++){
            if(!mvrGroups[i].isEmpty()){

                Cell nextCell = (Cell) mvrGroups[i].iterator().next();
                mvrGroups[i].remove(nextCell);
                return nextCell;
            }
        }
        return null;
    }

    public void switchGroup(Cell cell){

        mvrGroups[cell.whichMVRGroup].remove(cell);
        mvrGroups[cell.valuesRemaining].add(cell);

        cell.whichMVRGroup = cell.valuesRemaining;
    }

    public void removeFromGroup(Cell cell){
        mvrGroups[cell.whichMVRGroup].remove(cell);
    }

    public boolean cellHasNoOptions(){
        return !mvrGroups[0].isEmpty();
    }

    public void clear(){
        for(int i = 0; i <= 9; i++){
            mvrGroups[i].clear();
        }
    }
}
