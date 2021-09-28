import com.maastricht.university.logic.GameState;
import com.maastricht.university.logic.exceptions.IllegalMoveException;
import com.maastricht.university.logic.exceptions.OutsideHexagonException;
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
        Assertions.assertEquals(3,state.getBoard().getGroups(1).size());
    }

    @Test
    public void assertPlayGamePart1b() throws Exception{
        simulateGamePart1();
        Assertions.assertEquals(3,state.getBoard().getGroups(2).size());
    }

    @Test
    public void assertPlayGamePart2a() throws Exception{
        simulateGamePart1();
        simulateGamePart2();
        Assertions.assertEquals(3,state.getBoard().getGroups(1).size());
    }

    @Test
    public void assertPlayGamePart2b() throws Exception{
        simulateGamePart1();
        simulateGamePart2();
        Assertions.assertEquals(3,state.getBoard().getGroups(2).size());
    }

    @Test
    public void assertPlayGamePart3() throws Exception{
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            simulateGamePart1();
            simulateGamePart2();
            state.move(5, 0,1);
        });
        Assertions.assertTrue(exception.getMessage().contains("Move is Illegal"));
    }



    // helper methods

    private void validMovePlayer1(int q, int r) throws Exception{
        state.move(q, r, 1);
        Assertions.assertEquals(1, state.getBoard().getTileMap().get(q,r).getPlayerColor());
    }

    private void moveOutsideHexagonPlayer1(int q, int r) throws Exception{
        Exception exception = Assertions.assertThrows(OutsideHexagonException.class, () -> {
            state.move(q, r, 1);
        });
        Assertions.assertTrue(exception.getMessage().contains("Coordinates are outside tilemap, returned null"));
    }

    public void moveNextToEachOtherPlayer1(int p, int q) {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            state.move(p, q, 1);
            state.move(p, q+1, 1);
        });
        Assertions.assertTrue(exception.getMessage().contains("Move is Illegal"));
    }

    public void assertTotalGroups2(int q1, int r1, int q2, int r2) throws Exception{
        state.move(q1,r1, 1);
        state.move(q1,q2, 1);
        Assertions.assertEquals(2, state.getBoard().getGroups(1).size());
    }

    public void assertTotalGroups2WhenMerge(int q, int r) throws Exception{
        state.move(q,r, 1);
        state.move(q-1,r, 1);
        Assertions.assertEquals(2, state.getBoard().getGroups(1).size());
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

}