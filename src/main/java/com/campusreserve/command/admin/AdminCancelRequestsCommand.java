package com.campusreserve.command.admin;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminCancelRequestsCommand implements Command {
    private final AdminService adminService = new AdminService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("reservations", adminService.getCancelRequests());
        return CommandResult.forward("/WEB-INF/views/admin/cancel-requests.jsp");
    }
}
