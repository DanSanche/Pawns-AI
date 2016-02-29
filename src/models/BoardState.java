package models;
import java.util.List;

import constants.GameCompletion;
import constants.GameConstants;
import players.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Represents a state of the game board
 * Can be return utility values for either team
 * @author Sanche
 *
 */
public class BoardState {
    
    public Hashtable<Integer, Pawn> pawnPositions;
    
    private GameCompletion cahcedState = null;
    private Integer chachedBlackUtilityValue = null;
    private Integer chachedWhiteUtilityValue = null;

    /**
     * constructs an initial game state
     * @param whiteTeam - the white player object
     * @param blackTeam - the black player object
     */
    public BoardState(Player whiteTeam, Player blackTeam) {
        //create board spaces
        this.pawnPositions = new Hashtable<Integer, Pawn>();
        
        //set up pawns in initial positions
        List<Pawn> firstList = whiteTeam.getPawnList();
        for(int i=0; i<GameConstants.BOARD_SIZE; i++){
            Pawn thisPawn = firstList.get(i);
            pawnPositions.put(new Integer(i), thisPawn);
        }
        
        List<Pawn> secondList = blackTeam.getPawnList();
        for(int i=0; i<GameConstants.BOARD_SIZE; i++){
            Pawn thisPawn = secondList.get(i);
            int pawnPos = (GameConstants.BOARD_SIZE * (GameConstants.BOARD_SIZE-1)) + i;
            pawnPositions.put(new Integer(pawnPos), thisPawn);
        }
    }
    
    /**
     * Constructs a new game state by moving a pawn in an old game state
     * @param prevState - the previous state of the game
     * @param movedPawn - the pawn to move
     * @param newPosition - the new position of the pawn
     */
    public BoardState(BoardState prevState, Pawn movedPawn, Integer newPosition){
        this.pawnPositions = new Hashtable<Integer, Pawn>();
        
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
    
    /**
     * Constructs a new game state by moving a pawn in an old game state
     * Takes in the old position as a parameter for efficiency
     * @param prevState - the previous state of the game
     * @param movedPawn - the pawn to move
     * @param oldPosition - the previous position of the pawn
     * @param newPosition - the new position of the pawn
     */
    public BoardState(BoardState prevState, Pawn movedPawn, Integer oldPosition, Integer newPosition){
        this.pawnPositions = new Hashtable<Integer, Pawn>(prevState.pawnPositions);
        
        this.pawnPositions.remove(oldPosition);
        //add back in moved pawn
        this.pawnPositions.put(newPosition, movedPawn);
    }
    
    /**
     * Creates a visual representation of the pawns available to move
     * used to allow human players to select the next pawn to move
     * @param selectedPlayer - the player that gets to select a new movement
     * @return - a list representing of the indices of the pawns that are avaliable in the current state
     */
    public Collection<Integer> renderPawnOptions(Player selectedPlayer){
        String boardString = "";
        Collection<Integer> validOptions = new LinkedList<Integer>();
        
        //initialize all spaces to empty
        int numSquares = GameConstants.BOARD_SIZE * GameConstants.BOARD_SIZE;
        ArrayList<String> tileStrings = new ArrayList<String>(numSquares);
        for(int i=0; i<numSquares; i++){
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
            if(i%(GameConstants.BOARD_SIZE) == 0 && i!=0){
                boardString = boardString + "\n";
            }
            String tileValue = tileStrings.get(i);
            boardString = boardString + " " + tileValue + " ";
        }
        System.out.println(boardString);
        return validOptions;
    }  
    
    /**
     * Finds all possible movements for a given pawn
     * Used when pawn position is unknown. There is a more efficient method if position is known
     * @param selectedPawn - the pawn that is going to move
     * @return - a list of possible positions the pawn can end up in
     * @throws RuntimeException - thrown if the pawn doesn't exist in the current board state
     */
    public List<Integer> nextOptionsForPawn(Pawn selectedPawn) throws RuntimeException{
        Iterator<Integer> it = this.pawnPositions.keySet().iterator();
        //find the pawn's position
        int pos = -1;
        while(pos == -1 && it.hasNext()){
            Integer thisPos = it.next();
            Pawn nextPawn = this.pawnPositions.get(thisPos);
            if(nextPawn == selectedPawn){
                pos = thisPos.intValue();
            }
        }
        if(pos != -1){
            //call helper function to find the options for the pawn at the new known position
            return nextOptionsForPawn(selectedPawn, pos);
        } else {
            throw new RuntimeException("Pawn not on board");
        }
    }
    
    /**
     * Finds all possible movements for a given pawn
     * More efficient than previous method because the position is already known
     * @param selectedPawn - the pawn that is going to move
     * @param pawnPos - the current position of the selected pawn
     * @return - a list of possible positions the pawn can end up in
     * @throws RuntimeException - thrown if the pawn doesn't exist in the current board state
     */
    public List<Integer> nextOptionsForPawn(Pawn selectedPawn, int pawnPos){
        List<Integer> validOptions = new LinkedList<Integer>();
        
        //test one space in front
        int forwardValue;
        if(selectedPawn.isBlackTeam()){
            forwardValue = pawnPos - GameConstants.BOARD_SIZE;
        } else {
            forwardValue = pawnPos + GameConstants.BOARD_SIZE;
        }
        //test if it's off the board, or if there's another pawn in the space
        int numSquares = GameConstants.BOARD_SIZE * GameConstants.BOARD_SIZE;
        if(forwardValue >= 0 && forwardValue < numSquares && !this.pawnPositions.containsKey(new Integer(forwardValue))){
            validOptions.add(new Integer(forwardValue));
        }
           
        //test attacking to the left (relative to observer)
        int leftAttackValue = forwardValue -1;
        //test if the attack is on the right edge, or if there is no pawn to attack
        Pawn target = this.pawnPositions.get(new Integer(leftAttackValue));
        if(target != null && !target.isOnTeam(selectedPawn) &&
        leftAttackValue % (GameConstants.BOARD_SIZE) != (GameConstants.BOARD_SIZE-1)){
            validOptions.add(new Integer(leftAttackValue));
        }
        
        //test attacking to the right
       int rightAttackValue = forwardValue +1;
     //test if the attack is on the left edge, or if there is no pawn to attack
       target = this.pawnPositions.get(new Integer(rightAttackValue));
       if(target != null && !target.isOnTeam(selectedPawn) && rightAttackValue % (GameConstants.BOARD_SIZE) != (0)){
           validOptions.add(new Integer(rightAttackValue));    
       }
       
        return validOptions;
    }
    
    /**
     * Creates a visual representation of the moves available to a specific pawn
     * used to allow human players to select the next movement to make
     * @param selectedPawn - the pawn that is going to move
     * @return - a list of possible positions the pawn can end up in
     */
    public List<Integer> renderMoveOptions(Player selectedPlayer, Pawn selectedPawn){
        String boardString = "";
        List<Integer> validOptions = this.nextOptionsForPawn(selectedPawn);
        
        //initialize all spaces to empty
        int numSquares = GameConstants.BOARD_SIZE * GameConstants.BOARD_SIZE;
        ArrayList<String> tileStrings = new ArrayList<String>(numSquares);
        for(int i=0; i<numSquares; i++){
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
            if(i%(GameConstants.BOARD_SIZE) == 0 && i!=0){
                boardString = boardString + "\n";
            }
            String tileValue = tileStrings.get(i);
            boardString = boardString + " " + tileValue + " ";
        }
        System.out.println(boardString);
        return validOptions;
    }
    
    /**
     * Renders the current state of the board in the console
     */
    public void renderState(){
        String boardString = "";
        //initialize all spaces to empty
        int numSquares = GameConstants.BOARD_SIZE * GameConstants.BOARD_SIZE;
        ArrayList<String> tileStrings = new ArrayList<String>(numSquares);
        for(int i=0; i<numSquares; i++){
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
            if(i%(GameConstants.BOARD_SIZE) == 0 && i!=0){
                boardString = boardString + "\n";
            }
            String tileValue = tileStrings.get(i);
            boardString = boardString + " " + tileValue + " ";
        }
        System.out.println(boardString);
    }
    
    /**
     * returns a bool representing whether the player can make a movement
     * @param selectedPlayer - the player to test for
     * @return - true of the player has at least one possible movement, false if they are stuck
     */
    public Boolean playerCanMove(Player selectedPlayer){
        Iterator<Integer> it = this.pawnPositions.keySet().iterator();
        Boolean hasMovesLeft = false;
        //check all pawns to see if there is at least one that can move
        while(it.hasNext()){
            Integer pos = it.next();
            Pawn thisPawn = this.pawnPositions.get(pos);
            if(!hasMovesLeft && thisPawn.isOwnedBy(selectedPlayer)){
                hasMovesLeft = (this.nextOptionsForPawn(thisPawn).size() > 0);
            }
        }
        return (gameCompletionState() == GameCompletion.Game_Ongoing && hasMovesLeft);
    }

    /**
     * returns the current state of the board.
     *  Possible values include either player winning, a stalemate reached, or the game being ongoing
     * @return - an enum representing the game's state
     */
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
            if(thisPawn.isBlackTeam() && pos.intValue() < GameConstants.BOARD_SIZE){
                //black has reached the end. Black won
                return GameCompletion.Black_Reached_End;
            } else if(!thisPawn.isBlackTeam() && pos.intValue() >= (GameConstants.BOARD_SIZE*(GameConstants.BOARD_SIZE-1))){
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
    
    /**
     * The utility function attempts to create a score for a player from a certain layout of the board
     * The utility value is made up of two parts: 
     *    1) in the thousands digit,  we use number of pawns, because it is a good way to tell who is in the lead.
     *       The scale will go between 0 and 2n, where n represents the number of pawns on each team.
     *       If both sides have the same number of pawns, the score is n. 
     *       If the team this AI controls is winning, the score will be > n
     *    2) in the lower three digits, we add a number reperesenting the number of pawns that are being
     *       defended by team mates. Defended pawns have a teammate diagonally behind them, poised to attack
     *       if the pawn is captured. This will encourage the game to find strong defencive positions, all other things being equal
     * Because the number of pawns is a significant digit, the AI will prefer states defensive it has more pawns, but it will also
     * take good pawn positioning into account
     * @return the utility value of the current game state
     */
    public int findUtilityValue(Boolean isBlack){
        if(isBlack && this.chachedBlackUtilityValue != null){
            return this.chachedBlackUtilityValue.intValue();
        } else if (!isBlack && this.chachedWhiteUtilityValue  != null){
            return this.chachedWhiteUtilityValue.intValue();
        }
        
        List<Integer> positionsList = new ArrayList<Integer>();
        
        int numDefending = 0;
        int numEnemies = 0;
        int numPawns = 0;
        Iterator<Integer> it = this.pawnPositions.keySet().iterator();
        while(it.hasNext()){
            Integer pos = it.next();
            Pawn nextPawn = this.pawnPositions.get(pos);
            if(nextPawn.isBlackTeam() == isBlack){
                positionsList.add(pos);
                numPawns++;
            } else {
                numEnemies++;
            }
        }
        
        Collections.sort(positionsList);
        
        Iterator<Integer> posIt = positionsList.iterator();
        while(posIt.hasNext()){
            Integer nextPos = posIt.next();
            
            Integer backwardValue;
            if(isBlack){
                backwardValue = nextPos + GameConstants.BOARD_SIZE;
            } else {
                backwardValue = nextPos - GameConstants.BOARD_SIZE;
            }
            
            if(Collections.binarySearch(positionsList,backwardValue+1)>=0 && nextPos.intValue() % GameConstants.BOARD_SIZE != GameConstants.BOARD_SIZE-1){
                numDefending = numDefending + 1;
            } else  if(Collections.binarySearch(positionsList,backwardValue-1)>=0 && nextPos.intValue() % GameConstants.BOARD_SIZE != 0){
                numDefending = numDefending + 1;
            }
        }
        
        int pawnValue = 1000 * (GameConstants.BOARD_SIZE + numPawns - numEnemies);
        int utility = numDefending + pawnValue;
        
        if(isBlack){
            this.chachedBlackUtilityValue = new Integer(utility);
        } else {
            this.chachedWhiteUtilityValue = new Integer(utility);
        }
        
        return utility;
    }

    public static class Comparators {

        /**
         * used to compare game states to see which one is better for a particular player
         * This one will return 1 if the state is better for black than the given state, or -1 if it is worse
         */
        public static Comparator<BoardState> BLACK_ASCENDING = new Comparator<BoardState>() {
            @Override
            public int compare(BoardState o1, BoardState o2) {
                int thisVal = o1.findUtilityValue(true);
                int otherVal = o2.findUtilityValue(true);
                if(thisVal > otherVal){
                    return 1;
                } else if(thisVal == otherVal){
                    return 0;
                } else {
                    return -1;
                }
            }
        };
        
        /**
         * used to compare game states to see which one is better for a particular player
         * This one will return 1 if the state is worse for black than the given state, or -1 if it is better
         */
        public static Comparator<BoardState> BLACK_DESCENDING = new Comparator<BoardState>() {
            @Override
            public int compare(BoardState o1, BoardState o2) {
                int thisVal = o1.findUtilityValue(true);
                int otherVal = o2.findUtilityValue(true);
                if(thisVal < otherVal){
                    return 1;
                } else if(thisVal == otherVal){
                    return 0;
                } else {
                    return -1;
                }
            }
        };
        
        /**
         * used to compare game states to see which one is better for a particular player
         * This one will return 1 if the state is better for white than the given state, or -1 if it is worse
         */
        public static Comparator<BoardState> WHITE_ASCENDING = new Comparator<BoardState>() {
            @Override
            public int compare(BoardState o1, BoardState o2) {
                int thisVal = o1.findUtilityValue(false);
                int otherVal = o2.findUtilityValue(false);
                if(thisVal > otherVal){
                    return 1;
                } else if(thisVal == otherVal){
                    return 0;
                } else {
                    return -1;
                }
            }
        };
        
        /**
         * used to compare game states to see which one is better for a particular player
         * This one will return 1 if the state is worse for white than the given state, or -1 if it is better
         */
        public static Comparator<BoardState> WHITE_DESCENDING = new Comparator<BoardState>() {
            @Override
            public int compare(BoardState o1, BoardState o2) {
                int thisVal = o1.findUtilityValue(false);
                int otherVal = o2.findUtilityValue(false);
                if(thisVal < otherVal){
                    return 1;
                } else if(thisVal == otherVal){
                    return 0;
                } else {
                    return -1;
                }
            }
        };
        
    }
    
}
