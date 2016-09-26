package com.pengjinfei.concurrence.synchronizedContainer;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:
 */
public class HiddenIterater {

    private final Set<Integer> set = new HashSet<>();

    public synchronized void add(int i) {
        set.add(i);
    }

    public synchronized void remove(Integer integer) {
        set.remove(integer);
    }

    /*
    可能抛出ConcurrentModificatonException,容器的toString方法将隐式迭代
     */
    public void addTenThings() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            add(random.nextInt());
        }
        System.out.println("DEBUG: added ten elements to "+set);
    }
}
