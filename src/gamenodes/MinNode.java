package gamenodes;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import constants.GameCompletion;
import models.BoardState;

/**
 * Min nodes represent the move that the enemy player is likely to make.
 * They will find all possible movements that the other player can make, and returns the state
 * that is the least beneficial to the hero player
 * @author Sanche
 *
 */
public class MinNode extends GameNode {

    /**
     * Creates a new min node object
     * @param rootState - the state of the board rooted at this node
     * @param playerIsBlack - whether the AI controlling this search is working for black or white
     * @param depth - the depth to continue searching for
     * @param alpha - the current alpha value
     * @param beta - the current beta value
     */
    public MinNode(BoardState rootState, Boolean playerIsBlack, int depth, int alpha, int beta) {
        super(rootState, playerIsBlack, depth, alpha, beta);
        this.printPrefix = "-";
    }
    
    /**
     * function to calculate the value of the minimum option available to this node
     * @param depth - the depth to search to
     * @param alpha - the current alpha value
     * @param beta - the current beta value
     * @return - the value of this node
     */
    protected int findBestOption(int depth,  int alpha, int beta){
        this.childStates = new LinkedList<GameNode>();
        //find the list of states that we can go to from here
        List<BoardState> successorOptions = this.findEnemySuccessorStates();
        //sort states from lowest to highest using a heuristic value of the state
        //this will improve alpha beta pruning efficiency
        if(this.isBlack){
            Collections.sort(successorOptions, BoardState.Comparators.BLACK_ASCENDING);
        } else {
            Collections.sort(successorOptions, BoardState.Comparators.WHITE_ASCENDING);
        }
        //if there are states we can move into, find the minimum value state to move into
        if(!successorOptions.isEmpty()){
            Iterator<BoardState> it = successorOptions.iterator();
            while(it.hasNext() && alpha < beta){
                int nextCost;
                BoardState nextState = it.next();
                GameNode nextNode;
                if(depth <= 0){
                    //terminal node. use base cost
                    nextNode = new TerminalNode(nextState, this.isBlack, depth, alpha, beta);
                } else {
                    //more levels. Make another max node
                    nextNode = new MaxNode(nextState, this.isBlack, depth, alpha, beta);
                }
                nextCost = nextNode.nodeValue;
                //only add the new value to the tree if it is lower than the current beta
                if(nextCost < beta){
                    //update beta, and add the new state to the minimax tree
                    this.childStates.add(nextNode);
                    beta = nextCost;
                }
            }
            return beta;
        } else {
            //there are no more options. Now we need to find if the game is complete,
            //or if we are just stuck but our opponent can move
            GameNode nextNode;
            GameCompletion state = this.rootState.gameCompletionState();
            if(state == GameCompletion.Game_Ongoing){
                //we are stuck, but our opponent isn't. The game isn't over. Let them make a move
                if(depth <= 0){
                    //no time to keep looking. How good is this state for us?
                    nextNode = new TerminalNode(this.rootState, this.isBlack, depth-1, alpha, beta);
                } else {
                    //more levels. Let the enemy make a move
                    nextNode = new MaxNode(this.rootState, this.isBlack, depth-1, alpha, beta);
                }
            } else {
                //the game is complete. Create a terminal node to calculate our costs
                nextNode = new TerminalNode(this.rootState, this.isBlack, depth-1, alpha, beta);
            }
            this.childStates.add(nextNode);
            return nextNode.nodeValue;
        }
    }
    
}
