public class GameState {

    private int numberOfGroupsBlue;
    private int numberOfGroupsRed;
    private boolean redsTurn;
    private Tile[][][] board;

    public GameState() {
        this.numberOfGroupsBlue = 0;
        this.numberOfGroupsRed = 0;
        this.redsTurn = true;
        this.board = new Tile[9][9][9];
        for(int i=0; i<7; i++) {
            for(int j=0; j<7; j++) {
                for(int k=0; k<7; k++) {

                }
            }
        }
    }

    public boolean[][][] getLegalMovesBlue() {
        return new boolean[9][9][9];
    }

    public boolean[][][] getLegalMovesRed() {
        return new boolean[9][9][9];
    }

    public void moveBlue() {
        if(!redsTurn) {
            //update board
            //recount number of groups for Blue
            //update legal moves
        }
    }

    public void moveRed() {
        if(redsTurn) {
            //update board
            //recount number of groups for Red
            //update legal moves
        }
    }

    public int getNumberOfGroupsBlue() {
        return numberOfGroupsBlue;
    }

    public int getNumberOfGroupsRed() {
        return numberOfGroupsRed;
    }
}
