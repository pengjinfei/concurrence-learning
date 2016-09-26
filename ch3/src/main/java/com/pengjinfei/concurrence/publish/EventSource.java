package com.pengjinfei.concurrence.publish;

import java.util.EventListener;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:
 */
public interface EventSource {

    void registerListener(EventListener listener);
}
