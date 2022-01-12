package com.maastricht.university.logic.ai.hybrid.search;

import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.ai.minimax.tree.Tree;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ComputeLeafNodes {

    ITree<GameState> tree;
    protected List<ITreeNode> leafNodes = new LinkedList<>();

    /**
     * Creates a full tree with specified root till depth is reached
     * @param state state of the root
     * @param depth depth of the desired tree
     */
    public ComputeLeafNodes(IGameState state, int depth) {
        tree = new Tree<GameState>((GameState) state, 2);

        ArrayList<ITreeNode<GameState>> frontiers = new ArrayList<>();
        frontiers.add(tree.getRoot());

        while(frontiers.size() != 0) {
            ITreeNode<GameState> parent = frontiers.get(0);

            if(parent.getDepth() < depth && !parent.getElement().isGameOver()) {
                addChildren(parent);
                List<ITreeNode<GameState>> children = parent.getChildren();
                for (int j = 0; j < children.size(); j++)
                    frontiers.add(children.get(j));
            }
            //add the children to the leafNodes list
            else {
                leafNodes.add(parent);
//                List<ITreeNode<GameState>> kids = parent.getChildren();
//                for(ITreeNode node: kids)
//                    leafNodes.add(node);
            }
            frontiers.remove(0);
        }
    }

    /**
     * @return the SearchTree
     */
    public ITree<GameState> getTree() {return tree;}

    /**
     * Get all the leaf nodes
     * only possible if already searched the tree!
     * @return leafNodes all the leaf nodes
     */
    public List<ITreeNode> getLeafNodes() {
        return this.leafNodes;
    }



    /**
     * add all possible children to a nodes
     * @param parent the node the children are added too
     */
    private void addChildren(ITreeNode<GameState> parent) {
        GameState parentState = parent.getElement();
        List<Move> moves = parentState.getLegalMoves(parentState.getPlayerTurn());

        //for every possible move, add a child to the parent where that move is played
        for(Move move: moves) {
            GameState childState = parentState.clone();
            childState.move(move.getX(), move.getY(), childState.getPlayerTurn());
            parent.addChild(childState, move);
        }
    }
}