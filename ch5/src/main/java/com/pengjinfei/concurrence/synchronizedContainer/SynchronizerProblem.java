package com.pengjinfei.concurrence.synchronizedContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:
 */
public class SynchronizerProblem {

    Vector<String> vector=new Vector<>();

    /*
    可能出现ArrayIndexOutOfBoundsException
     */
    public String getUnsafeLast() {
        int lastIndex=vector.size()-1;
        return vector.get(lastIndex);
    }

    /*
    客户端加锁,确保安全
     */
    public String getSafeLast() {
        synchronized (vector) {
            return getUnsafeLast();
        }
    }

    List<String> list = Collections.synchronizedList(new ArrayList<>());

    /*
    可能抛出ConcurrentModificatonException,想要避免就必须在迭代过程持有容器的锁
     */
    public void iterList() {
        for (String s : list) {
            //doSomething
        }
    }
}
