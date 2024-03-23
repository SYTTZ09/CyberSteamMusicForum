package com.syt.util.thread;

public class TokenThreadLocalUtil {
    private final static ThreadLocal<String> TOKEN_THREAD_LOCAL = new ThreadLocal<>();

    public static void setToken(String token) {
        TOKEN_THREAD_LOCAL.set(token);
    }

    public static String getToken() {
        return TOKEN_THREAD_LOCAL.get();
    }

    public static void clear() {
        TOKEN_THREAD_LOCAL.remove();
    }
}
