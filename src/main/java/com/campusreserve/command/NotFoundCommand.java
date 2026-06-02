package com.campusreserve.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NotFoundCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        request.setAttribute("errorMessage", "요청한 페이지를 찾을 수 없습니다.");
        return CommandResult.forward("/WEB-INF/views/common/error.jsp");
    }
}
