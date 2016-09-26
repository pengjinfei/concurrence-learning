package com.pengjinfei.concurrence.instanceConfinement;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 将数据封装在对象内部，可以将数据的访问限制在对象的方法法，从而更容易确保线程在访问数据是总能持有正确的锁。
 * java监视器模式：把对象的所有可变状态都封装起来，并由对象自己的内置锁来保护
 */
@ThreadSafe
public class PersonSet {

    private final Set<Person> mySet = new HashSet<>();

    public synchronized void addPerson(Person person) {
        mySet.add(person);
    }

    public synchronized boolean containsPerson(Person person) {
        return mySet.contains(person);
    }
}
