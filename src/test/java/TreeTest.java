import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.Tree;
import com.maastricht.university.logic.game.util.interfaces.IScoreSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreeTest {

    private ITree<Integer> tree;

    @Test
    public void assertRootElement() {
        tree = new Tree<>(3);
        Assertions.assertEquals(tree.getRoot().getElement(), 3);
    }

    @Test
    public void hasRoot() {
        tree = new Tree<>(3);
        Assertions.assertTrue(tree.hasRoot());
    }

    @Test
    public void hasNoRoot() {
        tree = new Tree<>(2);
        Assertions.assertFalse(tree.hasRoot());
    }

    @Test
    public void assertChildOfRoot() {
        tree = new Tree<>(3);
        tree.getRoot().addChild(7);

        Assertions.assertEquals(tree.getRoot().getChildren().get(0).getElement(), 7);
    }

    @Test
    public void hasChildren() {
        tree = new Tree<>(3);
        tree.getRoot().addChild(7);
        Assertions.assertTrue(tree.getRoot().hasChildren());
    }

    @Test
    public void childHasNoChildren() {
        tree = new Tree<>(3);
        tree.getRoot().addChild(7);
        Assertions.assertFalse(tree.getRoot().getChildren().get(0).hasChildren());
    }

    @Test
    public void maxChild() {
        tree = new Tree<>(3);
        tree.getRoot().addChild(-40);
        tree.getRoot().addChild(300);
        tree.getRoot().addChild(20);
        Assertions.assertEquals(tree.getRoot().getMaxChild().getElement(), 300);
    }

    @Test
    public void minChild() {
        tree = new Tree<>(3);
        tree.getRoot().addChild(-999);
        tree.getRoot().addChild(20);
        tree.getRoot().addChild(3000300);
        Assertions.assertEquals(tree.getRoot().getMinChild().getElement(), -999);
    }
}
