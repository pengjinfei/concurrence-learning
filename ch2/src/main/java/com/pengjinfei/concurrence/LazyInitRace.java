package com.pengjinfei.concurrence;

import com.pengjinfei.concurrence.annotation.NoThreadSafe;

/**
 * Created by Pengjinfei on 16/9/24.
 * Description: 先检查后执行的竞态条件
 */
@NoThreadSafe
public class LazyInitRace {

    private static LazyInitRace instance;

    public static LazyInitRace getInstance() {
        if (instance == null) {
            instance = new LazyInitRace();
        }
        return instance;
    }
}
