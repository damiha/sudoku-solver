public class BoardUtils {

    public void print(Cell[][] board){

        for(int lineIndex = 0; lineIndex < 9; lineIndex++){
            if(lineIndex % 3 == 0){
                printHLine();
            }
            printLine(board, lineIndex);
        }
        printHLine();
    }

    private void printLine(Cell[][] board, int lineIndex){
        System.out.print(Main.indentation + "| ");
        for(int i = 0; i < 9; i++){
            System.out.print(board[lineIndex][i].value + " ");

            if((i + 1) % 3 == 0){
                System.out.print("| ");
            }
        }
        System.out.println();
    }

    private void printHLine(){
        System.out.print(Main.indentation);
        for(int i = 0; i < Main.CONSOLE_WIDTH; i++){
            System.out.print("-");
        }
        System.out.println();
    }
}
