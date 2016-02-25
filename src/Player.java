import java.util.LinkedList;
import java.util.List;

public class Player {
    
    private Boolean isBlackTeam;

    private List<Pawn> pawnList;
    
    public Player(int numPawns, Boolean isBlack) {
        this.isBlackTeam = isBlack;
       pawnList = new LinkedList<Pawn>();
       for (int i=0; i< numPawns; i++){
           Pawn thisPawn = new Pawn(this);
           pawnList.add(thisPawn);
       }
    }

    public List<Pawn> getPawnList(){
        return this.pawnList;
    }

    public void runTurn(BoardState currentState){
        
    }

    public Boolean isEqual(Player other) {
       return other.isBlackTeam() == this.isBlackTeam;
    }

    public Boolean isBlackTeam(){
        return this.isBlackTeam;
    }
}
