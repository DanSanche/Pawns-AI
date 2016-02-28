public class RobotPlayer extends Player {
    
    protected int depth;
    private Boolean printTree;
    
    public RobotPlayer(Boolean isBlack, int depth, Boolean printTree) {
        super(isBlack);
        this.depth = depth;
        this.printTree = printTree;
    }

    public BoardState runTurn(BoardState currentState){
        MaxNode rootNode = new MaxNode(currentState, this.isBlackTeam(), this.depth, UtilityConstants.LOSS_VALUE-1, UtilityConstants.WIN_VALUE);
        if(this.printTree){
            rootNode.print();
        }
        int finalValue = rootNode.nodeValue;
        System.out.println("Decison Value: " + finalValue);
        BoardState finalState = rootNode.finalState();
        finalState.renderState();
        return finalState;
    }

}
