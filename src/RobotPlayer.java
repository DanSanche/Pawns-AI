public class RobotPlayer extends Player {
    
    protected int depth;
    
    public RobotPlayer(int numPawns, Boolean isBlack, int depth) {
        super(numPawns, isBlack);
        this.depth = depth;
    }

    public BoardState runTurn(BoardState currentState){
        int maxValPossible = 100;
        MaxNode rootNode = new MaxNode(currentState, this.isBlackTeam(), this.depth, Integer.MIN_VALUE, maxValPossible);
        rootNode.print();
        BoardState finalState = rootNode.finalState();
        finalState.renderState();
        return finalState;
    }

}
