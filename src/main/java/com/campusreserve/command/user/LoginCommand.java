package com.campusreserve.command.user;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.dto.UserDTO;
import com.campusreserve.service.UserService;
import com.campusreserve.util.CsrfTokenUtil;
import com.campusreserve.util.RequestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private final UserService userService = new UserService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return CommandResult.forward("/WEB-INF/views/user/login.jsp");
        }

        CsrfTokenUtil.validate(request);
        try {
            UserDTO user = userService.login(
                RequestUtil.required(request, "loginId"),
                RequestUtil.required(request, "password")
            );

            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("loginId", user.getLoginId());
            session.setAttribute("name", user.getName());
            session.setAttribute("role", user.getRole());
            CsrfTokenUtil.rotate(session);

            if ("ADMIN".equals(user.getRole())) {
                return CommandResult.redirect("/admin/dashboard.do");
            }
            return CommandResult.redirect("/index.do");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("loginId", request.getParameter("loginId"));
            return CommandResult.forward("/WEB-INF/views/user/login.jsp");
        }
    }
}
