package players;
import constants.UtilityConstants;
import gamenodes.MaxNode;
import models.BoardState;

public class RobotPlayer extends Player {
    
    protected int depth;
    private Boolean printTree;
    
    /**
     * Creates a new robot player object
     * @param isBlack - whether the player represents the black team or the white team
     * @param depth - the depth to search for in minimax
     * @param printTree - indicates whether the minimax tree should be printed to the console for debugging
     */
    public RobotPlayer(Boolean isBlack, int depth, Boolean printTree) {
        super(isBlack);
        this.depth = depth;
        this.printTree = printTree;
    }

    /**
     * Runs a turn for the player
     * Since it's an AI player, it will run minimax to find the best possible move to make next
     * @param currentState - the current state of the game
     * @return an object representing the new game state
     */
    public BoardState runTurn(BoardState currentState){
        MaxNode rootNode = new MaxNode(currentState, this.isBlackTeam(), this.depth, UtilityConstants.LOSS_VALUE-1, UtilityConstants.WIN_VALUE);
        if(this.printTree){
            rootNode.print();
        }
        String finalValue = "" + rootNode.nodeValue;
        if(rootNode.nodeValue == UtilityConstants.WIN_VALUE){
            finalValue = "Win Guaranteed";
        } else if(rootNode.nodeValue == UtilityConstants.LOSS_VALUE){
            finalValue = "Loss Expected";
        } else if(rootNode.nodeValue == UtilityConstants.STALEMATE_VALUE){
            finalValue = "Stalemate Expected";
        }
        
        System.out.println("Decison Value: " + finalValue);
        BoardState finalState = rootNode.finalState();
        finalState.renderState();
        return finalState;
    }

}
