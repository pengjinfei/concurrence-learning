package com.pengjinfei.concurrence.reentrantlock;

import java.util.concurrent.TimeUnit;

/**
 * Created by Pengjinfei on 2016/10/21.
 * Description:
 */
public interface Transferable {

    boolean transferMoney(Account fromAcct, Account toAcct, DollarAmout amout, long timeout, TimeUnit unit) throws InsufficientFundsException, InterruptedException;
}
