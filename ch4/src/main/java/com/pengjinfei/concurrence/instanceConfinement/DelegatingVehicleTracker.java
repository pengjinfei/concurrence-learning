package com.pengjinfei.concurrence.instanceConfinement;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 委托给线程安全类ConcurrentHashMap
 */
@ThreadSafe
public class DelegatingVehicleTracker {

    private final ConcurrentHashMap<String,Point> locations;
    private final Map<String,Point> unmodifiableMap;

    public DelegatingVehicleTracker(ConcurrentHashMap<String, Point> points) {
        this.locations = new ConcurrentHashMap<>(points);
        this.unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    /*
    返回不可修改但却是实时的位置
     */
    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    /*
    返回locations的静态拷贝而非实时拷贝
     */
    public Map<String, Point> getStatelessLocations() {
        return Collections.unmodifiableMap(new HashMap<>(locations));
    }

    /*
    Point类是不可变的，因此是线程安全的，返回location不需要复制
     */
    public Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null) {
            throw new IllegalArgumentException("Invalid vehicle name: " + id);
        }
    }
}
