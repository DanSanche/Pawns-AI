import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class BoardState {
    
    Hashtable<Integer, Pawn> pawnPositions;
    
    int boardSize;
    private GameCompletion cahcedState = null;

    public BoardState(int boardSize, Player firstTeam, Player secondTeam) {
        //create board spaces
        this.pawnPositions = new Hashtable<Integer, Pawn>();

        this.boardSize = boardSize;
        
        //set up pawns in initial positions
        List<Pawn> firstList = firstTeam.getPawnList();
        for(int i=0; i<boardSize; i++){
            Pawn thisPawn = firstList.get(i);
            pawnPositions.put(new Integer(i), thisPawn);
        }
        
        List<Pawn> secondList = secondTeam.getPawnList();
        for(int i=0; i<boardSize; i++){
            Pawn thisPawn = secondList.get(i);
            int pawnPos = (boardSize * (boardSize-1)) + i;
            pawnPositions.put(new Integer(pawnPos), thisPawn);
        }
    }
    
    public BoardState(BoardState prevState, Pawn movedPawn, Integer newPosition){
        this.pawnPositions = new Hashtable<Integer, Pawn>();
        this.boardSize = prevState.boardSize;
        
        //add back in other pawns
        Iterator<Integer> it = prevState.pawnPositions.keySet().iterator();
        while(it.hasNext()){
            Integer pos = it.next();
            Pawn nextPawn = prevState.pawnPositions.get(pos);
            if(nextPawn != movedPawn){
                this.pawnPositions.put(pos, nextPawn);
            }
        }
        //add back in moved pawn
        this.pawnPositions.put(newPosition, movedPawn);
    }
    
    public Collection<Integer> renderPawnOptions(Player selectedPlayer){
        String boardString = "";
        Collection<Integer> validOptions = new LinkedList<Integer>();
        
        //initialize all spaces to empty
        ArrayList<String> tileStrings = new ArrayList<String>(this.boardSize * this.boardSize);
        for(int i=0; i<this.boardSize*this.boardSize; i++){
            tileStrings.add(i, ".");
        }
        
        //add pawns to empty spaces
        Iterator<Integer> it = this.pawnPositions.keySet().iterator();
        while(it.hasNext()){
            Integer thisPos = it.next();
            Pawn thisPawn = this.pawnPositions.get(thisPos);
            String pawnString = thisPawn.toString();
            
            //check if the pawn belongs to the selected player
            int idx = selectedPlayer.getPawnList().indexOf(thisPawn);
            if(idx > -1 ){
                pawnString = Integer.toString(idx+1);
                validOptions.add(new Integer(idx));  
            }
            tileStrings.set(thisPos, pawnString);
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
    
    //returns a list of options for moves for the pawn,
    //or throws an exception if it's not on the board
    public List<Integer> nextOptionsForPawn(Pawn selectedPawn) throws RuntimeException{
        Iterator<Integer> it = this.pawnPositions.keySet().iterator();
        int pos = -1;
        while(pos == -1 && it.hasNext()){
            Integer thisPos = it.next();
            Pawn nextPawn = this.pawnPositions.get(thisPos);
            if(nextPawn == selectedPawn){
                pos = thisPos.intValue();
            }
        }
        if(pos != -1){
            return nextOptionsForPawn(selectedPawn, pos);
        } else {
            throw new RuntimeException("Pawn not on board");
        }
    }
    
    //another version of the function if you know the pawns position. More efficient
    public List<Integer> nextOptionsForPawn(Pawn selectedPawn, int pawnPos){
        List<Integer> validOptions = new LinkedList<Integer>();
        
        //test one space in front
        int forwardValue;
        if(selectedPawn.isBlackTeam()){
            forwardValue = pawnPos - this.boardSize;
        } else {
            forwardValue = pawnPos + this.boardSize;
        }
        //test if it's off the board, or if there's another pawn in the space
        if(forwardValue >= 0 && forwardValue < this.boardSize*this.boardSize && !this.pawnPositions.containsKey(new Integer(forwardValue))){
            validOptions.add(new Integer(forwardValue));
        }
           
        //test attacking to the left (relative to observer)
        int leftAttackValue = forwardValue -1;
        //test if the attack is on the right edge, or if there is no pawn to attack
        Pawn target = this.pawnPositions.get(new Integer(leftAttackValue));
        if(target != null && !target.isOnTeam(selectedPawn) &&
        leftAttackValue % (this.boardSize) != (this.boardSize-1)){
            validOptions.add(new Integer(leftAttackValue));
        }
        
        //test attacking to the right
       int rightAttackValue = forwardValue +1;
     //test if the attack is on the left edge, or if there is no pawn to attack
       target = this.pawnPositions.get(new Integer(rightAttackValue));
       if(target != null && !target.isOnTeam(selectedPawn) && rightAttackValue % (this.boardSize) != (0)){
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
        Iterator<Integer> it = this.pawnPositions.keySet().iterator();
        while(it.hasNext()){
            Integer thisPos = it.next();
            Pawn thisPawn = this.pawnPositions.get(thisPos);
            String pawnString = thisPawn.toString();
            if(thisPawn == selectedPawn ){
                pawnString = "*"; 
            }
            tileStrings.set(thisPos, pawnString);
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
    
    public void renderState(){
        String boardString = "";
        //initialize all spaces to empty
        ArrayList<String> tileStrings = new ArrayList<String>(this.boardSize * this.boardSize);
        for(int i=0; i<this.boardSize*this.boardSize; i++){
            tileStrings.add(i, ".");
        }
        
        //add pawns to empty spaces
        Iterator<Integer> it = this.pawnPositions.keySet().iterator();
        while(it.hasNext()){
            Integer thisPos = it.next();
            Pawn thisPawn = this.pawnPositions.get(thisPos);
            tileStrings.set(thisPos, thisPawn.toString());
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
    }
    
    public Boolean playerCanMove(Player selectedPlayer){
        Iterator<Integer> it = this.pawnPositions.keySet().iterator();
        Boolean hasMovesLeft = false;
        
        while(it.hasNext()){
            Integer pos = it.next();
            Pawn thisPawn = this.pawnPositions.get(pos);
            if(!hasMovesLeft && thisPawn.isOwnedBy(selectedPlayer)){
                hasMovesLeft = (this.nextOptionsForPawn(thisPawn).size() > 0);
            }
        }
        return (gameCompletionState() == GameCompletion.Game_Ongoing && hasMovesLeft);
    }

    
    public GameCompletion gameCompletionState(){
        
        if(this.cahcedState != null){
            return this.cahcedState;
        }
        
        Iterator<Integer> it = this.pawnPositions.keySet().iterator();
        
        int blackCount = 0;
        int whiteCount = 0;
        
        Boolean whiteCanMove = false;
        Boolean blackCanMove = false;
               
        while(it.hasNext()){
            Integer pos = it.next();
            Pawn thisPawn = this.pawnPositions.get(pos);
            //increment number of living pawns for right team
            if(thisPawn.isBlackTeam()){
               blackCount++;
               if(!blackCanMove){
                   blackCanMove = (this.nextOptionsForPawn(thisPawn).size() > 0);
               }
            } else {
                whiteCount++;
                if(!whiteCanMove){
                    whiteCanMove = (this.nextOptionsForPawn(thisPawn).size() > 0);
                }
            }
            if(thisPawn.isBlackTeam() && pos.intValue() < this.boardSize){
                //black has reached the end. Black won
                return GameCompletion.Black_Reached_End;
            } else if(!thisPawn.isBlackTeam() && pos.intValue() >= (this.boardSize*(this.boardSize-1))){
                //white has reached the end. White won
                return GameCompletion.White_Reached_End;
            }
        }
        if(!blackCanMove && !whiteCanMove){
            if(blackCount > whiteCount){
                return GameCompletion.Black_More_Pawns;
            } else if(whiteCount > blackCount){
                return GameCompletion.White_More_Pawns;
            } else {
            return GameCompletion.Stalemate;
            }
        } else {
            return GameCompletion.Game_Ongoing;
        }
    }
    
    public List<Integer> pawnPositionsForPlayer(Boolean playerIsBlack){
        List<Integer> resultsList = new LinkedList<Integer>();
        Iterator<Integer> it = this.pawnPositions.keySet().iterator();
        while(it.hasNext()){
            Integer pos = it.next();
            Pawn thisPawn = this.pawnPositions.get(pos);
            if(thisPawn.isBlackTeam() == playerIsBlack){
                resultsList.add(pos);
            }
        }
        return resultsList;
    }
    
}
