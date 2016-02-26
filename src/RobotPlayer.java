public class RobotPlayer extends Player {
    
    protected int depth;
    
    public RobotPlayer(int numPawns, Boolean isBlack, int depth) {
        super(numPawns, isBlack);
        this.depth = depth;
    }

    public BoardState runTurn(BoardState currentState){
        MaxNode rootNode = new MaxNode(currentState, this.isBlackTeam());
        BoardState finalState = rootNode.findFinalState(this.depth);
        finalState.renderState();
        return finalState;
    }

}
