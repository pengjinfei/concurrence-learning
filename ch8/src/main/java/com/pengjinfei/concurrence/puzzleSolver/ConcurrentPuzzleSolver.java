package com.pengjinfei.concurrence.puzzleSolver;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description:
 */
public class ConcurrentPuzzleSolver<P, M> {
    private final Puzzle<P, M> puzzle;
    private final ExecutorService executor;
    private final ConcurrentHashMap<P, Boolean> seen;
    protected final Valuelatch<Node<P, M>> solution = new Valuelatch<>();

    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle, ExecutorService executor) {
        this.puzzle = puzzle;
        this.executor = executor;
        seen = new ConcurrentHashMap<P, Boolean>();
    }

    public List<M> solve() throws InterruptedException {
        try {
            P p = puzzle.initialPosition();
            executor.execute(newTask(p, null, null));
            Node<P, M> solutionValue = solution.getValue();
            return (solutionValue == null) ? null : solutionValue.asMoveList();
        } finally {
            executor.shutdown();
        }
    }

    protected Runnable newTask(P p, M m, Node<P, M> node) {
        return new SolverTask(p, m, node);
    }

    public class SolverTask extends Node<P, M> implements Runnable {

        SolverTask(P pos, M move, Node<P, M> prev) {
            super(pos, move, prev);
        }

        @Override
        public void run() {
            /*
            如果已经找到解答或者已经遍历了这个位置
             */
            if (solution.isSet() || seen.putIfAbsent(pos, true) != null) {
                return;
            }
            if (puzzle.isGoal(pos)) {
                solution.setValue(this);
            } else {
                for (M m : puzzle.legalMoves(pos)) {
                    executor.execute(newTask(puzzle.Move(pos, m), m, this));
                }
            }
        }
    }
}
