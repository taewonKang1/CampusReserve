package com.campusreserve.controller;

import java.io.IOException;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandFactory;
import com.campusreserve.command.CommandResult;
import com.campusreserve.util.CsrfTokenUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
    private CommandFactory commandFactory;

    @Override
    public void init() {
        commandFactory = new CommandFactory();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        request.setAttribute("currentPath", path);
        request.setAttribute("csrfToken", CsrfTokenUtil.getOrCreateToken(request));

        try {
            Command command = commandFactory.getCommand(path);
            CommandResult result = command.execute(request, response);
            if (result.isRedirect()) {
                response.sendRedirect(request.getContextPath() + result.getPath());
                return;
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(result.getPath());
            dispatcher.forward(request, response);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException || e instanceof IllegalStateException) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("errorDetail", e.getClass().getSimpleName());
            request.getRequestDispatcher("/WEB-INF/views/common/error.jsp").forward(request, response);
        }
    }
}
