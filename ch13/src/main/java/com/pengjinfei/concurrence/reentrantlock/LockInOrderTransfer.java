package com.pengjinfei.concurrence.reentrantlock;

import java.util.concurrent.TimeUnit;

/**
 * Created by Pengjinfei on 2016/10/21.
 * Description: 通过固定锁获得的顺序来避免死锁
 */
public class LockInOrderTransfer implements Transferable {

    private static final Object tieLock = new Object();

    @Override
    public boolean transferMoney(Account fromAcct, Account toAcct, DollarAmout amout, long timeout, TimeUnit unit) throws InsufficientFundsException, InterruptedException {
        class Helper{
            public void transfer() throws InsufficientFundsException {
                if (fromAcct.getBalance().compareTo(amout) < 0) {
                    throw new InsufficientFundsException();
                } else {
                    fromAcct.debit(amout);
                    toAcct.credit(amout);
                }
            }
        }

        int fromHash = System.identityHashCode(fromAcct);
        int toHash = System.identityHashCode(toAcct);
        if (fromHash < toHash) {
            synchronized (fromAcct) {
                synchronized (toAcct) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (toAcct) {
                synchronized (fromAcct) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAcct) {
                    synchronized (toAcct) {
                        new Helper().transfer();
                    }
                }
            }
        }
        return true;
    }
}
