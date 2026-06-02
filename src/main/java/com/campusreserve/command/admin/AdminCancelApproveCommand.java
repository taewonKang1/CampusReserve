package com.campusreserve.command.admin;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.service.AdminService;
import com.campusreserve.util.CsrfTokenUtil;
import com.campusreserve.util.RequestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminCancelApproveCommand implements Command {
    private final AdminService adminService = new AdminService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return CommandResult.redirect("/admin/cancel-requests.do");
        }

        CsrfTokenUtil.validate(request);
        adminService.approveCancel(RequestUtil.requiredLong(request, "reservationId"));
        return CommandResult.redirect("/admin/cancel-requests.do");
    }
}
