package com.pengjinfei.concurrence.cache;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pengjinfei on 16/9/26.
 * Description:
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return new BigInteger(arg);
    }
}
