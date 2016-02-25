public class RobotPlayer extends Player {
    
    protected int depth;
    
    public RobotPlayer(int numPawns, Boolean isBlack, int depth) {
        super(numPawns, isBlack);
        this.depth = depth;
    }

    public BoardState runTurn(BoardState currentState){
        MaxNode rootNode = new MaxNode(currentState, this.isBlackTeam());
        int maxScore = rootNode.findBestOption(this.depth);
        System.out.println("max score: " + maxScore);
        return rootNode.bestOption;
    }

}
