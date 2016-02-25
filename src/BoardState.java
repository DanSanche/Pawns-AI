import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class BoardState {
    
    private Hashtable<Pawn, Integer> pawnPositions;
    
    private int boardSize;

    public BoardState(int boardSize, Player firstTeam, Player secondTeam) {
        //create board spaces
        this.pawnPositions = new Hashtable<Pawn, Integer>();

        this.boardSize = boardSize;
        
        //set up pawns in initial positions
        List<Pawn> firstList = firstTeam.getPawnList();
        for(int i=0; i<boardSize; i++){
            Pawn thisPawn = firstList.get(i);
            pawnPositions.put(thisPawn, new Integer(i));
        }
        
        List<Pawn> secondList = secondTeam.getPawnList();
        for(int i=0; i<boardSize; i++){
            Pawn thisPawn = secondList.get(i);
            int pawnPos = (boardSize * (boardSize-1)) + i;
            pawnPositions.put(thisPawn, new Integer(pawnPos));
        }
    }
    
    public BoardState(BoardState prevState, Pawn movedPawn, Integer newPosition){
        this.pawnPositions = new Hashtable<Pawn, Integer>(prevState.pawnPositions);
        this.boardSize = prevState.boardSize;
        
        this.pawnPositions.put(movedPawn, newPosition);
    }
    
    public Collection<Integer> renderPawnOptions(Player selectedPlayer){
        String boardString = "";
        Collection<Integer> validOptions = new LinkedList<Integer>();
        
        ArrayList<String> tileStrings = new ArrayList<String>(this.boardSize * this.boardSize);
        for(int i=0; i<this.boardSize*this.boardSize; i++){
            tileStrings.add(i, ".");
        }
        
        List<Pawn> playerPawns = selectedPlayer.getPawnList();
        for(int i=0; i<playerPawns.size(); i++){
            Pawn thisPawn = playerPawns.get(i);
            Integer pawnPos = this.pawnPositions.get(thisPawn);
            if(pawnPos != null){
                tileStrings.set(pawnPos, Integer.toString(i+1));
                validOptions.add(new Integer(i));
            }
        }
        
        Set<Pawn> allPawns = this.pawnPositions.keySet();
        Iterator<Pawn> it = allPawns.iterator();
        while(it.hasNext()){
            Pawn thisPawn = it.next();
            if(!thisPawn.isOwnedBy(selectedPlayer)){
                Integer pawnPos = this.pawnPositions.get(thisPawn);
                tileStrings.set(pawnPos, thisPawn.toString());   
            }
        }
        
        for(int i=0; i<tileStrings.size(); i++){
            if(i%(this.boardSize) == 0 && i!=0){
                boardString = boardString + "\n";
            }
            String tileValue = tileStrings.get(i);
            boardString = boardString + " " + tileValue + " ";
        }
        System.out.println(boardString);
        return validOptions;
    }  
    
    public List<Integer> nextOptionsForPawn(Pawn selectedPawn){
        int pos = this.pawnPositions.get(selectedPawn).intValue();
        List<Integer> validOptions = new LinkedList<Integer>();
        
        //test one space in front
        int forwardValue;
        if(selectedPawn.isBlackTeam()){
            forwardValue = pos - this.boardSize;
        } else {
            forwardValue = pos + this.boardSize;
        }
        //test if it's off the board, or if there's another pawn in the space
        if(forwardValue >= 0 && forwardValue < this.boardSize*this.boardSize &&
           !this.pawnPositions.contains(new Integer(forwardValue))){
            validOptions.add(new Integer(forwardValue));
        }
           
        //test attacking to the left (relative to observer)
        int leftAttackValue = forwardValue -1;
        //test if the attack is on the right edge, or if there is no pawn to attack
        if(this.pawnPositions.contains(new Integer(leftAttackValue)) &&
        leftAttackValue % (this.boardSize) != (this.boardSize-1)){
            validOptions.add(new Integer(leftAttackValue));
        }
        
        //test attacking to the right
       int rightAttackValue = forwardValue +1;
     //test if the attack is on the left edge, or if there is no pawn to attack
       if(this.pawnPositions.contains(new Integer(rightAttackValue)) &&
       rightAttackValue % (this.boardSize) != (0)){
           validOptions.add(new Integer(rightAttackValue));    
       }
       
        return validOptions;
    }
    
    public List<Integer> renderMoveOptions(Player selectedPlayer, Pawn selectedPawn){
        String boardString = "";
        List<Integer> validOptions = this.nextOptionsForPawn(selectedPawn);
        
        //initialize all spaces to empty
        ArrayList<String> tileStrings = new ArrayList<String>(this.boardSize * this.boardSize);
        for(int i=0; i<this.boardSize*this.boardSize; i++){
            tileStrings.add(i, ".");
        }
        
        //add pawns to empty spaces
        Set<Pawn> allPawns = this.pawnPositions.keySet();
        Iterator<Pawn> it = allPawns.iterator();
        while(it.hasNext()){
            Pawn thisPawn = it.next();
            Integer pawnPos = this.pawnPositions.get(thisPawn);
            String pawnString =  thisPawn.toString();
            if(thisPawn == selectedPawn){
                pawnString = "*";   
            }
            tileStrings.set(pawnPos, pawnString);   
        }
        
        //add moves to empty spaces
        for(int i=0; i<validOptions.size(); i++){
            Integer thisMove = validOptions.get(i);
            tileStrings.set(thisMove, Integer.toString(i+1));   
        }
        
        //render as string
        for(int i=0; i<tileStrings.size(); i++){
            if(i%(this.boardSize) == 0 && i!=0){
                boardString = boardString + "\n";
            }
            String tileValue = tileStrings.get(i);
            boardString = boardString + " " + tileValue + " ";
        }
        System.out.println(boardString);
        return validOptions;
    }
}
