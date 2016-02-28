package gamenodes;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import constants.GameCompletion;
import models.BoardState;

/**
 * Max nodes represent the best move that the hero player can make
 * They will find all possible next moves, and find the state that represents the best move available
 * @author Sanche
 *
 */
public class MaxNode extends GameNode {
    
    private BoardState bestState;

    /**
     * Creates a new max node object
     * @param rootState - the state of the board rooted at this node
     * @param playerIsBlack - whether the AI controlling this search is working for black or white
     * @param depth - the depth to continue searching for
     * @param alpha - the current alpha value
     * @param beta - the current beta value
     */
    public MaxNode(BoardState rootState, Boolean playerIsBlack, int depth, int alpha, int beta) {
        super(rootState, playerIsBlack, depth, alpha, beta);
        this.printPrefix = "+";
    }
    
    /**
     * retrieves the next best state to move into from the rooted one
     * @return the next best state from this one
     */
    public BoardState finalState(){
        return bestState;
    }

    /**
     * function to calculate the value of the maximum option available to this node
     * @param depth - the depth to search to
     * @param alpha - the current alpha value
     * @param beta - the current beta value
     * @return - the value of this node
     */
    protected int findBestOption(int depth,  int alpha, int beta){
        this.childStates = new LinkedList<GameNode>();
        int result;
        //find the list of states that we can go to from here
        List<BoardState> successorOptions = this.findSuccessorStates();
        //sort states from highest to lowest using a heuristic value of the state
        //this will improve alpha beta pruning efficiency
        if(this.isBlack){
            Collections.sort(successorOptions, BoardState.Comparators.BLACK_DESCENDING);
        } else {
            Collections.sort(successorOptions, BoardState.Comparators.WHITE_DESCENDING);
        }
        
        //if there are states we can move into, find the max best state
        if(!successorOptions.isEmpty()){
            Iterator<BoardState> it = successorOptions.iterator();
            while(it.hasNext() && alpha<beta){
                BoardState nextState = it.next();
                MinNode nextNode = new MinNode(nextState, this.isBlack, depth-1, alpha, beta);
                int nextCost = nextNode.nodeValue;
                //only add the new value to the tree if it is greater than the current alpha
                if(nextCost > alpha){
                    //update alpha, the best state found, and add the new state to the minimax tree
                    alpha = nextCost;
                    bestState = nextState;
                    this.childStates.add(nextNode);
                }
            }
            result = alpha;
        } else {
            //there are no more options. Now we need to find if the game is complete,
            //or if we are just stuck but our oppinent can move
            GameNode nextNode;
            GameCompletion state = this.rootState.gameCompletionState();
            if(state == GameCompletion.Game_Ongoing){
                //we are stuck, but our opponent isn't. The game isn't over. Let them make a move
                nextNode = new MinNode(this.rootState, this.isBlack, depth-1, alpha, beta);
            } else {
                //the game is complete. Create a terminal node to calculate our costs
                nextNode = new TerminalNode(this.rootState, this.isBlack, depth-1, alpha, beta);
            }
            this.childStates.add(nextNode);
            result = nextNode.nodeValue;
        }
        return result;
    }
}
