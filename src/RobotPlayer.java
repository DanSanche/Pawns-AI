public class RobotPlayer extends Player {
    
    protected int depth;
    
    public RobotPlayer(int numPawns, Boolean isBlack, int depth) {
        super(numPawns, isBlack);
        this.depth = depth;
    }

    public BoardState runTurn(BoardState currentState){
        MaxNode rootNode = new MaxNode(currentState, this.isBlackTeam(), this.depth, UtilityConstants.LOSS_VALUE-1, UtilityConstants.WIN_VALUE);
       // rootNode.print();
        int finalValue = rootNode.nodeValue;
        System.out.println("Decison Value: " + finalValue);
        BoardState finalState = rootNode.finalState();
        finalState.renderState();
        return finalState;
    }

}
