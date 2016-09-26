package com.pengjinfei.concurrence.publish;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:
 * 要安全的发布一个对象，对象的引用以及对象的状态必须同时对其他线程可见。一个正确构造的对象可以通过以下方式来安全的发布：
 * 1.在静态初始化函数中初始化一个对象引用
 * 2.将对象的引用保存到volatile类型的域或者AtomicReference对象中
 * 3.将对象的引用保存到某个正确构造对象的final域中
 * 4.将对象的引用保存到一个由锁保护的域中{
 *  @see java.util.Hashtable
 *  @see java.util.Collections.SynchronizedMap
 *  @see java.util.concurrent.ConcurrentHashMap
 *  @see java.util.Vector
 *  @see java.util.concurrent.CopyOnWriteArrayList
 *  @see java.util.concurrent.CopyOnWriteArraySet
 *  @see java.util.Collections.SynchronizedList
 *  @see java.util.concurrent.BlockingQueue
 *  @see java.util.concurrent.ConcurrentLinkedQueue
 * }
 *
 * 对象的发布需求取决于他的可变性：
 * 1.不可变对象可以通过任意机制来发布
 * 2.事实不可变对象必须通过安全方式来发布
 * 3.可变对象必须通过安全方式来发布，并且必须是线程安全的或者某个锁保护起来
 */
public class Holder {

    /*
    如果将n声明为final，那么Holder将不可变，即使没有正确发布，也不会出现AssertionError
     */
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void assertSanity() {
        /*
        如果有没有使用同步来确保Holder对象对其他线程可见，Holer未被正确发布，将发生潜在个问题：
        1.除了发布对象的线程外，其他线程可以看到Holder域是一个失效值（空或者之前的引用），更糟糕的情况是，线程
            看到Holder引用的值是最新的，但Holder状态的值确是失效的
        2.某个线程在第一次读取域时得到失效值，再次读取这个域时得到一个更新值，这也是n!=n发生的原因
         */
        if (n != n) {
            throw new AssertionError("this statement is false");
        }
    }

    /*
    不安全的发布
     */
    public static Holder unsafePublish;

    public void initialize() {
        unsafePublish = new Holder(24);
    }

    /*
    发布一个静态构造的对象，最简单和最安全的方式是使用静态的初始化器
     */
    public static Holder holder = new Holder(24);
}
