package com.fgreau.offertechtest.aspect;

/**
 * Util class to store the threadlocal timer.
 */
public class AspectUtils {
    public static final ThreadLocal<Long> startTime = new ThreadLocal<>();
}
