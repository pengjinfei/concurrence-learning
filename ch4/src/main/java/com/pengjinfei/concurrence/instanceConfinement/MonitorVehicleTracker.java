package com.pengjinfei.concurrence.instanceConfinement;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 虽然类MutablePoint不是线程安全的，但是MonitorVehicleTracker是线程安全的。它所包含的Map对象和可变的Point对象都未曾发布。
 * 当需要返回车辆位置时，通过MutablePoint拷贝构造函数或者deepCopy方法来复制正确的值。
 */
@ThreadSafe
public class MonitorVehicleTracker {

    private final Map<String,MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    /*
    每次调用就要复制数据，车辆数据大的情况下将极大的降低性能
    返回的是快照，没有实时性
     */
    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    private synchronized MutablePoint getLocation(String id) {
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);
    }

    private synchronized void setLocation(String id, int x,int y) {
        MutablePoint point = locations.get(id);
        if (point == null) {
            throw new IllegalArgumentException("No such id: " + id);
        }
        point.x=x;
        point.y = y;
    }

    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> original) {
        Map<String, MutablePoint> result = new HashMap<>();
        for (String id : original.keySet()) {
            result.put(id, new MutablePoint(original.get(id)));
        }
        return result;
    }
}
