package models;
import players.Player;

/**
 * model of a pawn. Holds information about the team it belongs to
 * @author Sanche
 *
 */
public class Pawn {
    
    private Player owner;

    /**
     * Constructs a new pawn 0bject
     * @param owner - the player that controls this pawn
     */
    public Pawn(Player owner) {
        this.owner = owner;
    }
    
    /**
     * Creates a string prepresentation of the pawn
     * returns b for black, or w for white
     */
    public String toString(){
        if(owner.isBlackTeam()){
            return "b";
        } else {
            return "w";
        }
    }
    
    /**
     * returns whether the pawn is owned by a player
     * @param other - the player to test
     * @return - whether the pawn is owned by this player 
     */
    public Boolean isOwnedBy(Player other){
        return (owner.isEqual(other));
    }
    
    /**
     * specifies whether the pawn is owned by the black team or the white team
     * @return - true if owned by black, false if white
     */
    public Boolean isBlackTeam(){
        return this.owner.isBlackTeam();
    }
    
    /**
     * specifies whether two pawns are on the same team
     * @param other - the pawn to compare to
     * @return - whether the pawns are on the same team
     */
    public Boolean isOnTeam(Pawn other){
        return owner.isEqual(other.owner);
    }

}
