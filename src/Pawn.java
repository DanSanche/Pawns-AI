

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
    
    public Boolean isOwnedBy(Player other){
        return (owner.isEqual(other));
    }
    
    public Boolean isBlackTeam(){
        return this.owner.isBlackTeam();
    }
    
    public Boolean isOnTeam(Pawn other){
        return owner.isEqual(other.owner);
    }

}
