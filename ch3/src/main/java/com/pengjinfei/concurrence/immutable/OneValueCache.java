package com.pengjinfei.concurrence.immutable;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 每当需要对一组相关数据以原子方式执行某个操作时，就可以考虑创建一个不可变的类来包含这些数据。
 */
public class OneValueCache {
    /*
    final域能确保初始化过程的安全性，从而可以不受限制地访问不可变对象，并在共享这些对象时无须同步
     */
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger lastNumber, BigInteger[] lastFactors) {
        this.lastNumber = lastNumber;
        this.lastFactors = Arrays.copyOf(lastFactors,lastFactors.length);
    }

    public BigInteger[] getFactors(BigInteger bigInteger) {
        if (lastNumber == null || !lastNumber.equals(bigInteger)) {
            return null;
        } else {
            return Arrays.copyOf(lastFactors, lastFactors.length);
        }
    }
}
