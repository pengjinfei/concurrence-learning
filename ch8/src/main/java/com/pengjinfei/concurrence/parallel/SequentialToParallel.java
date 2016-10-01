package com.pengjinfei.concurrence.parallel;


import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description:
 */
public class SequentialToParallel {

    class Node<T>{

        public T compute() {
            return null;
        }

        public <T> List<Node<T>> getChildren() {
            return null;
        }
    }

    public <T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> results) {
        for (Node<T> node : nodes) {
            results.add(node.compute());
            sequentialRecursive(node.getChildren(),results);
        }
    }

    public <T> void parallelRecursive(List<Node<T>> nodes, final Collection<T> results, final Executor executor) {
        for (Node<T> node : nodes) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    results.add(node.compute());
                }
            });
            parallelRecursive(node.getChildren(),results,executor);
        }
    }

    public <T> Collection<T> getParallelResults(List<Node<T>> nodes) throws InterruptedException{
        ExecutorService executor= Executors.newCachedThreadPool();
        Queue<T> resultQueue = new ConcurrentLinkedQueue<T>();
        parallelRecursive(nodes,resultQueue,executor);
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return resultQueue;
    }
}
