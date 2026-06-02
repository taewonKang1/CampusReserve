package com.campusreserve.command.user;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.service.UserService;
import com.campusreserve.util.CsrfTokenUtil;
import com.campusreserve.util.RequestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command {
    private final UserService userService = new UserService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return CommandResult.forward("/WEB-INF/views/user/register.jsp");
        }

        CsrfTokenUtil.validate(request);
        try {
            userService.register(
                RequestUtil.required(request, "loginId"),
                RequestUtil.required(request, "password"),
                RequestUtil.required(request, "name"),
                request.getParameter("phone")
            );
            return CommandResult.redirect("/user/login.do?registered=1");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("loginId", request.getParameter("loginId"));
            request.setAttribute("name", request.getParameter("name"));
            request.setAttribute("phone", request.getParameter("phone"));
            return CommandResult.forward("/WEB-INF/views/user/register.jsp");
        }
    }
}
