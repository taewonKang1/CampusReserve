package com.campusreserve.command.admin;

import java.time.LocalDate;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.service.AdminService;
import com.campusreserve.util.RequestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminLayoutCommand implements Command {
    private final AdminService adminService = new AdminService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LocalDate date = LocalDate.parse(RequestUtil.optional(request, "date", LocalDate.now().toString()));
        request.setAttribute("selectedDate", date.toString());
        request.setAttribute("today", LocalDate.now().toString());
        request.setAttribute("statuses", adminService.getLayoutStatus(date));
        return CommandResult.forward("/WEB-INF/views/admin/resource-layout.jsp");
    }
}
