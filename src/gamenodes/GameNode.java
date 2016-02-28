package gamenodes;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import constants.GameCompletion;
import models.BoardState;
import models.Pawn;

/**
 * A generic game node. There are subclasses for min, max, and terminal nodes, 
 * that will be combined into a minimax tree
 * @author Sanche
 *
 */
public class GameNode {
    
    protected BoardState rootState;
    protected Boolean isBlack;
    protected List<GameNode> childStates;
    public int nodeValue;
    protected String printPrefix = "";

    /**
     * Creates a new generic game node object
     * @param rootState - the state of the board rooted at this node
     * @param playerIsBlack - whether the AI controlling this search is working for black or white
     * @param depth - the depth to continue searching for
     * @param alpha - the current alpha value
     * @param beta - the current beta value
     */
    public GameNode(BoardState rootState, Boolean playerIsBlack, int depth, int alpha, int beta) {
        this.rootState = rootState;
        this.isBlack = playerIsBlack;
        this.nodeValue = this.findBestOption(depth, alpha, beta);
    }
    
    /**
     * Returns the value of this node to the hero player
     * @return
     */
    public int getNodeValue(){
        return this.nodeValue;
    }
    
    /**
     * function to calculate the value of this node
     * @param depth - the depth to search to
     * @param alpha - the current alpha value
     * @param beta - the current beta value
     * @return - the value of this node
     */
    protected int findBestOption(int depth, int alpha, int beta){
        return 0;
    }
    
    /**
     * @return a list of all possible states the enemy can move into from this state
     */
    protected List<BoardState> findEnemySuccessorStates(){
        return findSuccessorStatesHelper(!this.isBlack);
    }
    
    /**
     * @return a list of all possible states the hero player can move into from this state
     */
    protected List<BoardState> findSuccessorStates(){
        return findSuccessorStatesHelper(this.isBlack);
    }
    
    /**
     * A helper function to find the next states available to either player
     * @param forBlackTeam - the player to search for successor states for
     * @return - the next states available to the chosen player
     */
    private List<BoardState>findSuccessorStatesHelper(Boolean forBlackTeam){
        List<BoardState> resultsList = new LinkedList<BoardState>();
        
        if(this.rootState.gameCompletionState() != GameCompletion.Game_Ongoing){
            return resultsList;
        }
        
        Iterator<Integer> it = this.rootState.pawnPositions.keySet().iterator();
        while(it.hasNext()){
            Integer position = it.next();
            Pawn nextPawn = this.rootState.pawnPositions.get(position);

            if(nextPawn.isBlackTeam() == forBlackTeam){
                List<Integer> optionsList = this.rootState.nextOptionsForPawn(nextPawn, position.intValue());
                Iterator<Integer> optionsIt = optionsList.iterator();
                while(optionsIt.hasNext()){
                    Integer thisOption = optionsIt.next();
                    BoardState newState = new BoardState(this.rootState, nextPawn, position.intValue(), thisOption);
                    resultsList.add(newState);
                }
            }
        }
        return resultsList;
    }
    
    /**
     * prints a representation of the minimax tree
     */
    public void print() {
        print("", true);
    }

    /**
     * Helper function for printing the tree
     */
    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + this.printPrefix + this.nodeValue + "(" + this.rootState.findUtilityValue(this.isBlack) + ")");
        for (int i = 0; i < this.childStates.size() - 1; i++) {
            this.childStates.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (this.childStates.size() > 0) {
            this.childStates.get(this.childStates.size() - 1).print(prefix + (isTail ?"    " : "│   "), true);
        }
    }

   
}