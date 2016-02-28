package gamenodes;
import java.util.LinkedList;

import constants.GameCompletion;
import constants.UtilityConstants;
import models.BoardState;

public class TerminalNode extends GameNode {

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
