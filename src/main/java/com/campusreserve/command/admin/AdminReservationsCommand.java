package com.campusreserve.command.admin;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminReservationsCommand implements Command {
    private final AdminService adminService = new AdminService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("reservations", adminService.getAllReservations());
        return CommandResult.forward("/WEB-INF/views/admin/reservations.jsp");
    }
}
