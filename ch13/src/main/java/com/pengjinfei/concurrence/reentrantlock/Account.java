package com.pengjinfei.concurrence.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Pengjinfei on 2016/10/21.
 * Description:
 */
public class Account {
    private DollarAmout balance;

    public Lock lock = new ReentrantLock();

    public DollarAmout getBalance() {
        return balance;
    }

    public void credit(DollarAmout amout) {

    }

    public void debit(DollarAmout amout) {

    }
}
