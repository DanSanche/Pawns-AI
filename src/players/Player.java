package players;
import java.util.LinkedList;
import java.util.List;

import constants.GameConstants;
import models.BoardState;
import models.Pawn;

public class Player {
    
    private Boolean isBlackTeam;

    private List<Pawn> pawnList;
    
    /**
     * Creates a new object representing a player
     * Subclassed for human players and AI players
     * @param isBlack - represents whether the player controls the black team or the white team
     */
    public Player(Boolean isBlack) {
        this.isBlackTeam = isBlack;
       pawnList = new LinkedList<Pawn>();
       for (int i=0; i< GameConstants.BOARD_SIZE; i++){
           Pawn thisPawn = new Pawn(this);
           pawnList.add(thisPawn);
       }
    }

    /**
     * @return a list of the pawns controlled by this player
     */
    public List<Pawn> getPawnList(){
        return this.pawnList;
    }

    /**
     * Runs code to decide on the next state of the game
     * Overridden in subclasses
     * @param currentState - the current state of the game
     * @return - the new state representing the player's move
     */
    public BoardState runTurn(BoardState currentState){
        return currentState;
    }

    /**
     * @param other - the player object to compare to this one
     * @return a bool indicating whether they are the same player
     */
    public Boolean isEqual(Player other) {
       return other.isBlackTeam() == this.isBlackTeam;
    }

    /**
     * @return - whether the player represents the black team or the white team
     */
    public Boolean isBlackTeam(){
        return this.isBlackTeam;
    }
    
    /**
     * @return a string representation of the player
     */
    public String toString(){
        if(this.isBlackTeam){
            return "Black Team";
        } else {
            return "White Team";
        }
    }
}
