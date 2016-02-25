import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(int numPawns) {
        super(numPawns);
    }
    
    public void runTurn(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Which pawn do you want to move?: ");
        int n = reader.nextInt();
    }

}
