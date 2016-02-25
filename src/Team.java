import java.util.LinkedList;
import java.util.List;

public class Team {

    private String name;
    private List<Pawn> pawnList;
    
    public Team(int numPawns, String name) {
       this.setName(name);
       pawnList = new LinkedList<Pawn>();
       for (int i=0; i< numPawns; i++){
           Pawn thisPawn = new Pawn();
           pawnList.add(thisPawn);
       }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
