package com.syt.util.thread;

public class UserIdThreadLocalUtil {
    private final static ThreadLocal<Integer> USER_ID_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUserId(Integer userId) {
        USER_ID_THREAD_LOCAL.set(userId);
    }

    public static Integer getUserId() {
        return USER_ID_THREAD_LOCAL.get();
    }

    public static void clear() {
        USER_ID_THREAD_LOCAL.remove();
    }
}
