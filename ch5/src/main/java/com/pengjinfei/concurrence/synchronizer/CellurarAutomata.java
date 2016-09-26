package com.pengjinfei.concurrence.synchronizer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Pengjinfei on 16/9/26.
 * Description: CyclicBarrier的用法：
 * 一组线程等待大家都完成，然后继续执行
 */
public class CellurarAutomata {

    private final CyclicBarrier barrier;
    private final Board mainBoard;
    private final Worker[] workers;

    public CellurarAutomata(Board board) {
        this.mainBoard = board;
        int count=Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count,
                /*
                线程通过栅栏之后执行的方法
                 */
                new Runnable() {
                    @Override
                    public void run() {
                        mainBoard.commitNewVaules();
                    }
                });
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
        }
    }

    public void start() {
        for (int i = 0; i < workers.length; i++) {
            new Thread(workers[i]).start();
        }
        mainBoard.waitForConvergence();
    }

    private class Worker implements Runnable{

        private final Board board;


        public Worker(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            while (!board.hasConverged()) {
                for(int x=0;x<board.getMaxX();x++) {
                    for(int y=0;y<board.getMaxY();y++) {
                        board.setNewValue(x, y, computeVaule(x, y));
                    }
                }
                try {
                    /*
                    等待大家都到达栅栏一起通过,将有parties个线程到达栅栏
                    awaitIndex表示到达的次序，parties-1表示第一个到达，0表示最后一个到达
                    可以通过这个到达次序选举产生一个领导线程，并在下一次迭代中由该领导执行一些特殊操作
                     */
                    int parties = barrier.getParties();
                    int awaitIndex = barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    return;
                }
            }
        }

        private int computeVaule(int x, int y) {
            return 0;
        }
    }

    private class Board{

        public boolean hasConverged() {
            return false;
        }

        public int getMaxX() {
            return 0;
        }

        public int getMaxY() {
            return 0;
        }

        public void setNewValue(int x, int y, int i) {

        }

        public void commitNewVaules() {

        }

        public Board getSubBoard(int count, int i) {
            return null;
        }

        public void waitForConvergence() {

        }
    }
}
