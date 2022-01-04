import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.Tree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreeTest {

    private ITree<Integer> tree;

    @Test
    public void assertRootElement() {
        tree = new Tree<>(3, 2);
        Assertions.assertEquals(3, tree.getRoot().getElement());
    }

    @Test
    public void hasRoot() {
        tree = new Tree<>(3, 2);
        Assertions.assertTrue(tree.hasRoot());
    }

    @Test
    public void hasNoRoot() {
        tree = new Tree<>(2);
        Assertions.assertFalse(tree.hasRoot());
    }

    @Test
    public void assertChildOfRoot() {
        tree = new Tree<>(3, 2);
        tree.getRoot().addChild(7, null);

        Assertions.assertEquals(7, tree.getRoot().getChildren().get(0).getElement());
    }

    @Test
    public void hasChildren() {
        tree = new Tree<>(3, 2);
        tree.getRoot().addChild(7, null);
        Assertions.assertTrue(tree.getRoot().hasChildren());
    }

    @Test
    public void childHasNoChildren() {
        tree = new Tree<>(3, 2);
        tree.getRoot().addChild(7, null);
        Assertions.assertFalse(tree.getRoot().getChildren().get(0).hasChildren());
    }

    @Test
    public void maxChild() {
        tree = new Tree<>(3, 2);
        tree.getRoot().addChild(-40, null);
        tree.getRoot().addChild(300, null);
        tree.getRoot().addChild(20, null);
        Assertions.assertEquals(300, tree.getRoot().getMaxChild().getElement());
    }

    @Test
    public void minChild() {
        tree = new Tree<>(3, 2);
        tree.getRoot().addChild(-999, null);
        tree.getRoot().addChild(20, null);
        tree.getRoot().addChild(3000300, null);
        Assertions.assertEquals(-999, tree.getRoot().getMinChild().getElement());
    }
}
