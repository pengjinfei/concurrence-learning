package com.pengjinfei.concurrence.puzzleSolver;

import java.util.Set;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description:
 */
public interface Puzzle<P, M> {
    P initialPosition();

    boolean isGoal(P position);

    Set<M> legalMoves(P position);

    P Move(P position, M move);
}
