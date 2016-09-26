package com.pengjinfei.concurrence;

/**
 * Created by Pengjinfei on 16/9/26.
 * Description:
 */
public class ExceptionUtils {
    public static RuntimeException launderThrowable(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return ((RuntimeException) throwable);
        } else if (throwable instanceof Error) {
            throw (Error) throwable;
        } else {
            throw new IllegalStateException("Not unchecked", throwable);
        }
    }
}
