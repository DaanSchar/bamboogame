import com.maastricht.university.logic.game.GameState;
import com.maastricht.university.logic.game.LogicTile;
import com.maastricht.university.logic.game.TileGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameStateTest {

    private GameState state = new GameState(3,2);


    // valid moves
    @Test
    public void validMovePlayer1a() throws Exception {
        validMovePlayer1(4, 4);
    }

    @Test
    public void validMovePlayer1b() throws Exception {
        validMovePlayer1(2, 2);
    }

    @Test
    public void validMovePlayer1c() throws Exception {
        validMovePlayer1(6, 0);
    }

    @Test
    public void validMovePlayer1d() throws Exception {
        validMovePlayer1(3, 5);
    }

    @Test
    public void validMovePlayer1e() throws Exception {
        validMovePlayer1(1, 3);
    }

    @Test
    public void validMovePlayer1f() throws Exception {
        validMovePlayer1(0, 6);
    }

    // outside hexagon moves

    @Test
    public void moveOutsideHexagonPlayer1a() throws Exception {
        moveOutsideHexagonPlayer1(0, 0);
    }

    @Test
    public void moveOutsideHexagonPlayer1b() throws Exception {
        moveOutsideHexagonPlayer1(0, 1);
    }

    @Test
    public void moveOutsideHexagonPlayer1c() throws Exception {
        moveOutsideHexagonPlayer1(0, 2);
    }

    @Test
    public void moveOutsideHexagonPlayer1d() throws Exception {
        moveOutsideHexagonPlayer1(5, 5);
    }

    @Test
    public void moveOutsideHexagonPlayer1e() throws Exception {
        moveOutsideHexagonPlayer1(6, 6);
    }

    @Test
    public void moveOutsideHexagonPlayer1f() throws Exception {
        moveOutsideHexagonPlayer1(6, 5);
    }

    // placing 2 of same color next to each other

    @Test
    public void moveNextToEachOtherPlayer1a() {
        moveNextToEachOtherPlayer1(3,3);
    }

    @Test
    public void moveNextToEachOtherPlayer1b() {
        moveNextToEachOtherPlayer1(4,4);
    }

    @Test
    public void moveNextToEachOtherPlayer1c() {
        moveNextToEachOtherPlayer1(2,3);
    }

    @Test
    public void moveNextToEachOtherPlayer1d() {
        moveNextToEachOtherPlayer1(2,5);
    }

    @Test
    public void moveNextToEachOtherPlayer1e() {
        moveNextToEachOtherPlayer1(5, 2);
    }

    @Test
    public void moveNextToEachOtherPlayer1f() {
        moveNextToEachOtherPlayer1(6, 1);
    }

    // total groups of 2

    @Test
    public void assertTotalGroups2a() throws Exception{
        assertTotalGroups2(2,2, 4, 5);
    }

    @Test
    public void assertTotalGroups2b() throws Exception{
        assertTotalGroups2(4,4, 2, 2);
    }

    // total groups of 2 when merge

    @Test
    public void assertTotalGroups2WhenMergeA() throws Exception {
        state.move(1, 2, 1);
        assertTotalGroups2WhenMerge(3,4);
    }

    @Test
    public void assertTotalGroups2WhenMergeB() throws Exception {
        state.move(4, 3, 1);
        assertTotalGroups2WhenMerge(3,5);
    }

    // simulate gameplay

    @Test
    public void assertPlayGamePart1a() throws Exception{
        simulateGamePart1();
        Assertions.assertEquals(3,state.getTotalGroups(1));
    }

    @Test
    public void assertPlayGamePart1b() throws Exception{
        simulateGamePart1();
        Assertions.assertEquals(3,state.getTotalGroups(2));
    }

    @Test
    public void assertPlayGamePart2a() throws Exception{
        simulateGamePart1();
        simulateGamePart2();
        Assertions.assertEquals(3,state.getTotalGroups(1));
    }

    @Test
    public void assertPlayGamePart2b() throws Exception{
        simulateGamePart1();
        simulateGamePart2();
        Assertions.assertEquals(3,state.getTotalGroups(2));
    }

    @Test
    public void assertPlayGamePart3() throws Exception{
        simulateGamePart1();
        simulateGamePart2();
        state.move(5, 0,1);
        Assertions.assertEquals(0, state.getBoard().getTileMap().get(5, 0).getPlayerColor());
    }

    // Test that a player can't do multiple moves in a row

    @Test
    public void assertPlayerTurnPart1a() throws Exception{
        invalidTestPlayerTurnPart1(2);
    }

    @Test
    public void assertPlayerTurnPart1b() throws Exception{
        invalidTestPlayerTurnPart1(5);
    }

    @Test
    public void assertPlayerTurnPart1c() throws Exception{
        invalidTestPlayerTurnPart1(0);
    }

    @Test
    public void assertPlayerTurnPart1d() throws Exception{
        invalidTestPlayerTurnPart1(-2);
    }

    @Test
    public void assertPlayerTurnPart2a() throws Exception{
        invalidTestPlayerTurnPart2(1);
    }

    @Test
    public void assertPlayerTurnPart2b() throws Exception{
        state.move(2,2, 1);
        invalidTestPlayerTurnPart2(2);
    }

    // Test the clone method

    @Test
    public void cloneTest1() throws Exception{
        GameState clonedState = state.clone();
        checkNotMirroredAndCopyMove(1,6, 1, clonedState);
        checkNotMirroredAndCopyMove(6,2, 2, clonedState);
        checkNotMirroredAndCopyMove(2,2, 1, clonedState);
    }

    @Test
    public void cloneTest2() throws Exception{
        GameState clonedState = state.clone();
        checkNotMirrored(1,6, 1, clonedState);
        checkNotMirrored(6,2, 2, clonedState);
        checkNotMirrored(2,2, 1, clonedState);
        checkGroupsnotMirrored(clonedState);
    }

    @Test
    public void cloneTest3() throws Exception{
        simulateGamePart1();
        simulateGamePart2();
        assertValuesCopied();
    }



    // helper methods

    private void validMovePlayer1(int q, int r) throws Exception{
        state.move(q, r, 1);
        Assertions.assertEquals(1, state.getBoard().getTileMap().get(q,r).getPlayerColor());
    }

    private void moveOutsideHexagonPlayer1(int q, int r) throws Exception{
        state.move(q, r, 1);
        Assertions.assertEquals(0 ,state.getTotalGroups(1));
    }

    public void moveNextToEachOtherPlayer1(int p, int q) {
        state.move(p, q, 1);
        state.move(6,0,2);
        state.move(p, q+1, 1);
        Assertions.assertEquals(0, state.getBoard().getTileMap().get(p,q+1).getPlayerColor());
    }

    public void assertTotalGroups2(int q1, int r1, int q2, int r2) throws Exception{
        state.move(q1,r1, 1);
        state.move(5,0,2);
        state.move(q2,r2, 1);
        Assertions.assertEquals(2, state.getTotalGroups(1));
    }

    public void assertTotalGroups2WhenMerge(int q, int r) throws Exception{
        state.move(q,r, 1);
        state.move(q+1, r, 2);
        state.move(q-1,r, 1);
        Assertions.assertEquals(2, state.getTotalGroups(1));
    }

    public void simulateGamePart1() throws Exception{
        state.move(3,3, 1);
        state.move(2,3, 2);

        state.move(4,0, 1);
        state.move(1,2, 2);

        state.move(6,0, 1);
        state.move(0,6, 2);
    }

    public void simulateGamePart2() throws Exception{
        state.move(6,2,1);
        state.move(4,5,2);

        state.move(6,1,1);
        state.move(1,3,2);
    }

    public void invalidTestPlayerTurnPart1(int playerColour) throws Exception{
        //don't do anything
        try {
            state.move(2, 6, playerColour);
        }

        //don't do anything if exception, but check if tile is coloured (it shouldn't be)
        catch(Exception e) {}

        Assertions.assertEquals(0, state.getBoard().getTileMap().get(3,3).getPlayerColor());
    }

    public void invalidTestPlayerTurnPart2(int playerColour) throws Exception{
        try {
            state.move(1, 6, playerColour);
            state.move(6, 1, playerColour);
        }

        //don't do anything if exception, but check if tile is coloured (it shouldn't be)
        catch(Exception e) {}

        Assertions.assertEquals(0, state.getBoard().getTileMap().get(6,1).getPlayerColor());
    }

    public void checkNotMirrored(int q, int r, int playerColour, GameState clonedState) {
        state.move(q,r,playerColour);
        Assertions.assertNotEquals(state.getBoard().getTileMap().get(q, r).getPlayerColor(), clonedState.getBoard().getTileMap().get(q, r).getPlayerColor());
    }

    public void checkNotMirroredAndCopyMove(int q, int r, int playerColour, GameState clonedState) {
        state.move(q,r,playerColour);
        Assertions.assertNotEquals(state.getBoard().getTileMap().get(q, r).getPlayerColor(), clonedState.getBoard().getTileMap().get(q, r).getPlayerColor());
        clonedState.move(q,r,playerColour);
    }

    public void checkGroupsnotMirrored(GameState clonedState) {
        Assertions.assertNotEquals(state.getTotalGroups(1), clonedState.getTotalGroups(1));
        Assertions.assertNotEquals(state.getTotalGroups(2), clonedState.getTotalGroups(2));
    }

    public void assertValuesCopied() {

        GameState clonedState = state.clone();

        // if all the tiles have the same value
        for(int q=0; q<state.getBoard().getBoardSize(); q++) {
            for(int r=0; r<state.getBoard().getBoardSize(); r++) {
                if(state.getBoard().getTileMap().get(q, r)!=null)
                    Assertions.assertEquals(state.getPlayerColorOfTile(q,r), clonedState.getPlayerColorOfTile(q,r));
            }
        }


        Assertions.assertEquals(state.getPlayerColorOfTile(3,3), clonedState.getPlayerColorOfTile(3,3));
        Assertions.assertEquals(state.getPlayerColorOfTile(2,3), clonedState.getPlayerColorOfTile(2,3));


        //Check info about the playerTurn and the groups
        Assertions.assertEquals(state.getPlayerTurn(), clonedState.getPlayerTurn());
        Assertions.assertEquals(state.getTotalGroups(1), clonedState.getTotalGroups(1));
        Assertions.assertEquals(state.getTotalGroups(2), clonedState.getTotalGroups(2));
        Assertions.assertEquals(state.getLargestGroupSize(1), clonedState.getLargestGroupSize(1));
        Assertions.assertEquals(state.getLargestGroupSize(2), clonedState.getLargestGroupSize(2));
    }
}
