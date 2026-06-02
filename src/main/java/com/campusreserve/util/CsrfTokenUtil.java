package com.campusreserve.util;

import java.security.SecureRandom;
import java.util.Base64;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class CsrfTokenUtil {
    private static final String SESSION_KEY = "csrfToken";
    private static final SecureRandom RANDOM = new SecureRandom();

    private CsrfTokenUtil() {
    }

    public static String getOrCreateToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object existing = session.getAttribute(SESSION_KEY);
        if (existing instanceof String token && !token.isBlank()) {
            return token;
        }

        String token = newToken();
        session.setAttribute(SESSION_KEY, token);
        return token;
    }

    public static void rotate(HttpSession session) {
        session.setAttribute(SESSION_KEY, newToken());
    }

    public static void validate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String expected = session == null ? null : (String) session.getAttribute(SESSION_KEY);
        String actual = request.getParameter("_csrf");
        if (expected == null || actual == null || !expected.equals(actual)) {
            throw new IllegalArgumentException("요청 보안 토큰이 유효하지 않습니다. 화면을 새로고침한 뒤 다시 시도하세요.");
        }
    }

    private static String newToken() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
