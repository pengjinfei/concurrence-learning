package com.pengjinfei.concurrence.puzzleSolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description:
 */
public class Node<P,M> {
    final P pos;
    final M move;
    final Node<P,M> prev;

    public Node(P pos, M move, Node<P, M> prev) {
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }

    List<M> asMoveList() {
        List<M> solution = new ArrayList<M>();
        for (Node<P,M> n=this;n.move!=null;n=n.prev) {
            solution.add(0, n.move);
        }
        return solution;
    }
}
