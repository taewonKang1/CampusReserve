package com.campusreserve.util;

import jakarta.servlet.http.HttpServletRequest;

public final class RequestUtil {
    private RequestUtil() {
    }

    public static String required(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("필수 입력값이 누락되었습니다: " + name);
        }
        return value.trim();
    }

    public static long requiredLong(HttpServletRequest request, String name) {
        return Long.parseLong(required(request, name));
    }

    public static long optionalLong(HttpServletRequest request, String name, long defaultValue) {
        String value = request.getParameter(name);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Long.parseLong(value.trim());
    }

    public static String optional(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim();
    }
}
