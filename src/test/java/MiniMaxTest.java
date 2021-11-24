import com.maastricht.university.logic.ai.agent.MiniMaxAgent;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.Tree;
import com.maastricht.university.logic.ai.minimax.tree.TreeNode;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MiniMaxTest {
    GameState gameState = new GameState(3,2);
    MiniMaxAgent agentPlayer1 = new MiniMaxAgent(gameState,1);
    MiniMaxAgent agentPlayer2 = new MiniMaxAgent(gameState,2);
    ITree<Integer> tree;

    @Test
    public void assertLegalMove(){
        tree = new Tree<>(3,2);

    }
}
