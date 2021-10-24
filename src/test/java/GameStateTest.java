import com.maastricht.university.logic.game.game.GameState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameStateTest {

    private GameState state = new GameState(3,2);

    // valid moves

    @Test
    public void validMovePlayer1a()   {
        validMovePlayer1(4, 4);
    }

    @Test
    public void validMovePlayer1b()   {
        validMovePlayer1(2, 2);
    }

    @Test
    public void validMovePlayer1c()   {
        validMovePlayer1(6, 0);
    }

    @Test
    public void validMovePlayer1d()   {
        validMovePlayer1(3, 5);
    }

    @Test
    public void validMovePlayer1e()   {
        validMovePlayer1(1, 3);
    }

    @Test
    public void validMovePlayer1f()   {
        validMovePlayer1(0, 6);
    }

    // outside hexagon moves

    @Test
    public void moveOutsideHexagonPlayer1a()   {
        moveOutsideHexagonPlayer1(0, 0);
    }

    @Test
    public void moveOutsideHexagonPlayer1b()   {
        moveOutsideHexagonPlayer1(0, 1);
    }

    @Test
    public void moveOutsideHexagonPlayer1c()   {
        moveOutsideHexagonPlayer1(0, 2);
    }

    @Test
    public void moveOutsideHexagonPlayer1d()   {
        moveOutsideHexagonPlayer1(5, 5);
    }

    @Test
    public void moveOutsideHexagonPlayer1e()   {
        moveOutsideHexagonPlayer1(6, 6);
    }

    @Test
    public void moveOutsideHexagonPlayer1f()   {
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
    public void assertTotalGroups2a() {
        assertTotalGroups2(2,2, 4, 5);
    }

    @Test
    public void assertTotalGroups2b() {
        assertTotalGroups2(4,4, 2, 2);
    }

    // total groups of 2 when merge

    @Test
    public void assertTotalGroups2WhenMergeA() {
        state.move(1, 2, 1);
        state.move(6, 0, 2);
        assertTotalGroups2WhenMerge(3,4);
    }

    @Test
    public void assertTotalGroups2WhenMergeB()  {
        state.move(4, 3, 1);
        state.move(6, 0, 2);
        assertTotalGroups2WhenMerge(3,5);
    }

    // simulate gameplay

    @Test
    public void assertPlayGamePart1a() {
        simulateGamePart1();
        Assertions.assertEquals(3,state.getTotalGroups(1));
    }

    @Test
    public void assertPlayGamePart1b() {
        simulateGamePart1();
        Assertions.assertEquals(3,state.getTotalGroups(2));
    }

    @Test
    public void assertPlayGamePart2a() {
        simulateGamePart1();
        simulateGamePart2();
        Assertions.assertEquals(3,state.getTotalGroups(1));
    }

    @Test
    public void assertPlayGamePart2b() {
        simulateGamePart1();
        simulateGamePart2();
        Assertions.assertEquals(3,state.getTotalGroups(2));
    }

    @Test
    public void assertPlayGamePart3() {
        simulateGamePart1();
        simulateGamePart2();
        state.move(5, 0,1);
        Assertions.assertEquals(0, state.getBoard().getTileMap().get(5, 0).getPlayerColor());
    }

    @Test
    public void assertLargestGroupA() {
        state.move(3,3, 1);
        state.move(2,3, 2);

        state.move(4,0, 1);
        state.move(1,2, 2);

        state.move(6,0, 1);
        state.move(0,6, 2);
        Assertions.assertEquals(1, state.getLargestGroupSize(1));
    }

    @Test
    public void assertLargestGroupB() {
        state.move(3,3, 1);
        state.move(2,3, 2);

        state.move(4,0, 1);
        state.move(1,2, 2);

        state.move(6,0, 1);
        state.move(0,6, 2);

        state.move(6,1, 1);
        Assertions.assertEquals(2, state.getLargestGroupSize(1));
    }

    // test winning condition

    @Test
    public void assertWinner1() {
        GameState newState = new GameState(1, 2);

        newState.move(2, 0, 1);
        newState.move(2, 1, 2);

        newState.move(1, 2, 1);
        newState.move(1, 0, 2);

        newState.move(0, 1, 1);
        newState.move(0, 2, 2);

        //newState.move(1, 1, 1);

        Assertions.assertEquals(2, newState.winner());
    }

    @Test
    public void assertWinner2() {
        GameState newState = new GameState(1, 2);

        newState.move(2, 0, 1);
        newState.move(2, 1, 2);

        newState.move(1, 2, 1);
        newState.move(1, 0, 2);

        newState.move(0, 1, 1);
        newState.move(0, 2, 2);

        newState.move(1, 1, 1);

        Assertions.assertNotEquals(1, newState.getBoard().getTileMap().get(1, 1).getPlayerColor());
    }

    // helper methods

    private void validMovePlayer1(int q, int r) {
        state.move(q, r, 1);
        Assertions.assertEquals(1, state.getBoard().getTileMap().get(q,r).getPlayerColor());
    }

    private void moveOutsideHexagonPlayer1(int q, int r) {
        state.move(q, r, 1);
        Assertions.assertEquals(0 ,state.getTotalGroups(1));
    }

    public void moveNextToEachOtherPlayer1(int p, int q) {
        state.move(p, q, 1);
        state.move(6,0,2);
        state.move(p, q+1, 1);
        Assertions.assertEquals(0, state.getBoard().getTileMap().get(p,q+1).getPlayerColor());
    }

    public void assertTotalGroups2(int q1, int r1, int q2, int r2) {
        state.move(q1,r1, 1);
        state.move(6,0,2);
        state.move(q2,r2, 1);
        Assertions.assertEquals(2, state.getTotalGroups(1));
    }

    public void assertTotalGroups2WhenMerge(int q, int r) {
        state.move(q,r, 1);
        state.move(q+1, r, 2);
        state.move(q-1,r, 1);
        Assertions.assertEquals(2, state.getTotalGroups(1));
    }

    public void simulateGamePart1() {
        state.move(3,3, 1);
        state.move(2,3, 2);

        state.move(4,0, 1);
        state.move(1,2, 2);

        state.move(6,0, 1);
        state.move(0,6, 2);
    }

    public void simulateGamePart2() {
        state.move(6,2,1);
        state.move(4,5,2);

        state.move(6,1,1);
        state.move(1,3,2);
    }


}
