package gamenodes;
import java.util.LinkedList;

import constants.GameCompletion;
import constants.UtilityConstants;
import models.BoardState;

/**
 * Terminal nodes will be created when we reach an end state of the game, or when wehave reached the depth limit
 * Will either return a utility constant representing the value of the game end state (win/los/stalemate), 
 * or a utility value representing the estimated value of this state (if the game is in an ongoing state)
 * 
 * @author Sanche
 *
 */
public class TerminalNode extends GameNode {

    /**
     * Creates a new terminal node
     * @param rootState - the state of the board rooted at this node
     * @param playerIsBlack - whether the AI controlling this search is working for black or white
     * @param depth - the depth to continue searching for
     * @param alpha - the current alpha value
     * @param beta - the current beta value
     */
    public TerminalNode(BoardState rootState, Boolean playerIsBlack, int depth, int alpha, int beta) {
        super(rootState, playerIsBlack, depth, alpha, beta);
        this.childStates = new LinkedList<GameNode>();
    }
    
    protected int findBestOption(int depth, int alpha, int beta){
        int stateValue = 0;
        //if the game is over, we can assign a max or a min value depending on the result
        GameCompletion state = this.rootState.gameCompletionState();
        if(state == GameCompletion.Black_More_Pawns || state == GameCompletion.Black_Reached_End){
            if(this.isBlack){
                stateValue = UtilityConstants.WIN_VALUE;
            } else {
                stateValue = UtilityConstants.LOSS_VALUE;
            }
        } else if(state == GameCompletion.White_More_Pawns || state == GameCompletion.White_Reached_End){
            if(!this.isBlack){
                stateValue = UtilityConstants.WIN_VALUE;
            } else {
                stateValue = UtilityConstants.LOSS_VALUE;
            }
        } else if (state == GameCompletion.Stalemate){
            stateValue = UtilityConstants.STALEMATE_VALUE;
        } else {
            //if no winner was decided, return the utility value of the state
            stateValue = this.rootState.findUtilityValue(this.isBlack);
        }
        return stateValue;
    }
   

}
