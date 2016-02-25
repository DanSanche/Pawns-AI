import java.util.LinkedList;
import java.util.List;

public class Team {

    private List<Pawn> pawnList;
    
    public Team(int numPawns) {
       pawnList = new LinkedList<Pawn>();
       for (int i=0; i< numPawns; i++){
           Pawn thisPawn = new Pawn();
           pawnList.add(thisPawn);
       }
    }



}
