package com.campusreserve.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/login.do");
            return;
        }

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 접근할 수 있습니다.");
            return;
        }

        chain.doFilter(request, response);
    }
}
