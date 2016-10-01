package com.pengjinfei.concurrence.puzzleSolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description:
 */
public class SequentialPuzzleSolver<P,M> {
    private final Puzzle<P,M> puzzle;
    private final Set<P> seen = new HashSet<P>();

    public SequentialPuzzleSolver(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
    }

    public List<M> solve() {
        P position = puzzle.initialPosition();
        return search(new Node<P, M>(position, null, null));
    }

    private List<M> search(Node<P, M> node) {
        if (!seen.contains(node.pos)) {
            seen.add(node.pos);
            if (puzzle.isGoal(node.pos)) {
                return node.asMoveList();
            }
            for (M move : puzzle.legalMoves(node.pos)) {
                P pos = puzzle.Move(node.pos, move);
                Node<P, M> child = new Node<P, M>(pos, move, node);
                List<M> result = search(child);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
