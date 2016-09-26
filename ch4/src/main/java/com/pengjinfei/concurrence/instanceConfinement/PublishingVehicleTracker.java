package com.pengjinfei.concurrence.instanceConfinement;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 调用者不可以修改或者删除车辆，但却可以通过修改返回Map中的SafePoint值来改变车辆的对象
 */
@ThreadSafe
public class PublishingVehicleTracker {
    private final Map<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

    public PublishingVehicleTracker(Map<String, SafePoint> locations) {
        this.locations = new ConcurrentHashMap<>(locations);
        this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
    }

    public Map<String, SafePoint> getLocations() {
        return unmodifiableMap;
    }

    public SafePoint getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (!locations.containsKey(id)) {
            throw new IllegalArgumentException("Invalid vehicle name: " + id);
        }
        locations.get(id).set(x, y);
    }
}
