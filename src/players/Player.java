package players;
import java.util.LinkedList;
import java.util.List;

import constants.UtilityConstants;
import models.BoardState;
import models.Pawn;

public class Player {
    
    private Boolean isBlackTeam;

    private List<Pawn> pawnList;
    
    public Player(Boolean isBlack) {
        this.isBlackTeam = isBlack;
       pawnList = new LinkedList<Pawn>();
       for (int i=0; i< UtilityConstants.BOARD_SIZE; i++){
           Pawn thisPawn = new Pawn(this);
           pawnList.add(thisPawn);
       }
    }

    public List<Pawn> getPawnList(){
        return this.pawnList;
    }

    public BoardState runTurn(BoardState currentState){
        return currentState;
    }

    public Boolean isEqual(Player other) {
       return other.isBlackTeam() == this.isBlackTeam;
    }

    public Boolean isBlackTeam(){
        return this.isBlackTeam;
    }
    
    public String toString(){
        if(this.isBlackTeam){
            return "Black Team";
        } else {
            return "White Team";
        }
    }
}
