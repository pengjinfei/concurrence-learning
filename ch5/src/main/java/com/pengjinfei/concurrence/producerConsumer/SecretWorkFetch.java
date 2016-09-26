package com.pengjinfei.concurrence.producerConsumer;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:工作密取：
 * 1.大多数时候，他们只访问自己的双端队列，从而极大的减小了竞争
 * 2.当工作线程需要访问另一个列队时，它从队列的尾部而不是头部获取工作，进一步降低了队列上的竞争程度
 */
public class SecretWorkFetch {
    public static Work generateWork() {
        return new Work();
    }

    public static void main(String[] args) {
        LinkedBlockingDeque<Work> deque = new LinkedBlockingDeque<Work>();
        LinkedBlockingDeque<Work> other = new LinkedBlockingDeque<Work>();
        new Thread(new ConsumerAndProducer(deque, other)).start();
        new Thread(new ConsumerAndProducer(deque, other)).start();

        new Thread(new ConsumerAndProducer(other, deque)).start();
        new Thread(new ConsumerAndProducer(other, deque)).start();
    }

    private static class Work implements Runnable {
        private static Object object = new Object();
        private static int count = 0;
        public final int id;
        private long putThread;

        public Work() {
            synchronized (object) {
                id = count++;
            }
        }

        @Override
        public void run() {
            if (Thread.currentThread().getId() != putThread) {
                System.out.println("===================================================");
            }
            System.out.println(Thread.currentThread().getId() + ":" + putThread + "// finish job " + id);

        }

        public long getPutThread() {
            return putThread;
        }

        public void setPutThread(long putThread) {
            this.putThread = putThread;
        }


    }

    private static class ConsumerAndProducer implements Runnable {
        private final LinkedBlockingDeque<Work> deque;
        private final LinkedBlockingDeque<Work> otherWork;
        private Random random = new Random();

        public ConsumerAndProducer(LinkedBlockingDeque<Work> deque, LinkedBlockingDeque<Work> otherWork) {
            this.deque = deque;
            this.otherWork = otherWork;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(200);
                    if (random.nextBoolean()) {
                        int count = random.nextInt(5);
                        for (int i = 0; i < count; i++) {
                            Work w = generateWork();
                            w.setPutThread(Thread.currentThread().getId());
                            /*
                            消费者本身也是生产者
                             */
                            deque.putLast(w);
                        }
                    }
                    if (deque.isEmpty()) {
                        if (!otherWork.isEmpty()) {
                            /*
                            从其他消费者队列末尾秘密地获取工作
                             */
                            otherWork.takeLast().run();
                        }

                    } else {
                        deque.takeFirst().run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }


        }


    }

}
