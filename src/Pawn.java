

public class Pawn {
    
    private Player owner;

    public Pawn(Player owner) {
        this.owner = owner;
    }
    
    public String toString(){
        if(owner.isBlackTeam()){
            return "b";
        } else {
            return "w";
        }
    }
    
    public Boolean isSameOwner(Player other){
        return (owner.isEqual(other));
    }

}
