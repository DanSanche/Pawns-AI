import java.util.LinkedList;
import java.util.List;

public class Player {

    private List<Pawn> pawnList;
    
    public Player(int numPawns) {
       pawnList = new LinkedList<Pawn>();
       for (int i=0; i< numPawns; i++){
           Pawn thisPawn = new Pawn();
           pawnList.add(thisPawn);
       }
    }

    public List<Pawn> getPawnList(){
        return this.pawnList;
    }

    public void runTurn(){
        
    }

}
