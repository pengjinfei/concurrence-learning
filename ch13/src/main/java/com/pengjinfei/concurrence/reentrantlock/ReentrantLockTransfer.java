package com.pengjinfei.concurrence.reentrantlock;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pengjinfei on 2016/10/21.
 * Description:
 */
public class ReentrantLockTransfer implements Transferable {

    /*
    使用tryLock获得两个锁，如果不能同时获得，那么就回退并重新尝试。
    在休眠时间中包含固定部分和随机部分，从而降低发生活锁的可能性。
    如果在指定的时间内不能获得所有需要的锁，将返回一个失败状态。
     */
    @Override
    public boolean transferMoney(Account fromAcct, Account toAcct, DollarAmout amout, long timeout, TimeUnit unit) throws InsufficientFundsException, InterruptedException {
        Random random = new Random();
        long fixedDelay = getFixedDelayComponentNanos(timeout, unit);
        long randMod = getRandomDelayModulusNanos(timeout, unit);
        long stopTime = System.nanoTime() + unit.toNanos(timeout);
        while (true) {
            if (fromAcct.lock.tryLock()) {
                try {
                    if (toAcct.lock.tryLock()) {
                        try {
                            if (fromAcct.getBalance().compareTo(amout) < 0) {
                                throw new InsufficientFundsException();
                            } else {
                                fromAcct.debit(amout);
                                toAcct.credit(amout);
                                return true;
                            }
                        } finally {
                            toAcct.lock.unlock();
                        }
                    }
                } finally {
                    fromAcct.lock.unlock();
                }
            }
            if (System.nanoTime() > stopTime) {
                return false;
            }
            TimeUnit.NANOSECONDS.sleep(fixedDelay + random.nextLong() % randMod);
        }
    }

    private long getRandomDelayModulusNanos(long timeout, TimeUnit unit) {
        return 0;
    }

    private long getFixedDelayComponentNanos(long timeout, TimeUnit unit) {
        return 0;
    }
}
