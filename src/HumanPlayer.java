import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(int numPawns, Boolean isBlack) {
        super(numPawns, isBlack);
    }
    
    public void runTurn(Board gameBoard){
        Scanner reader = new Scanner(System.in);
        
        String boardState = gameBoard.pawnOptionsString(this);
        System.out.print(boardState);
        
        System.out.println("Which pawn do you want to move?: ");
        int n = reader.nextInt();
    }

}
