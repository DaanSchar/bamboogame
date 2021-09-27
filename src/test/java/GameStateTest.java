import com.maastricht.university.logic.GameState;
import com.maastricht.university.logic.exceptions.IllegalMoveException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameStateTest {

    private GameState state = new GameState(3,2);

    @Test
    public void placeColor1InMiddle() throws Exception {
        int q = 3;
        int r = 3;
        state.move(q, r, 1);
        Assertions.assertEquals(1, state.getBoard().getTileMap().get(q,r).getPlayerColor());
    }

    @Test
    public void placeColor1NextToMiddle() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            state.move(3, 2, 1);
            state.move(3, 3, 1);
            System.out.println(state.getBoard().getTileMap());
        });
        Assertions.assertTrue(exception.getMessage().contains("Move is Illegal"));
    }

}
