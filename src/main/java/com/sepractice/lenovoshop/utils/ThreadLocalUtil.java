package com.sepractice.lenovoshop.utils;

public class ThreadLocalUtil {
    private static final ThreadLocal threadLocal = new ThreadLocal();

    public static void set(Object value){
        threadLocal.set(value);
    }

    public static <T> T get(){
        return (T) threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }
}
