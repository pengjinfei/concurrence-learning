package com.pengjinfei.concurrence.puzzleSolver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 限制任务的数量
 */
public class PuzzleSolver<P,M> extends ConcurrentPuzzleSolver<P,M> {

    private final AtomicInteger taskCount = new AtomicInteger(0);

    public PuzzleSolver(Puzzle<P, M> puzzle, ExecutorService executor) {
        super(puzzle, executor);
    }

    @Override
    protected Runnable newTask(P p, M m, Node<P, M> node) {
        return new CountingSolverTask(p, m, node);
    }

    private class CountingSolverTask extends SolverTask{

        CountingSolverTask(P pos, M move, Node<P, M> prev) {
            super(pos, move, prev);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } finally {
                if (taskCount.decrementAndGet() == 0) {
                    solution.setValue(null);
                }
            }
        }
    }
}
