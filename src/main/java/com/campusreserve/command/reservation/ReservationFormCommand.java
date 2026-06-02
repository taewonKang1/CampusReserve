package com.campusreserve.command.reservation;

import java.time.LocalDate;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.service.ResourceService;
import com.campusreserve.util.RequestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReservationFormCommand implements Command {
    private final ResourceService resourceService = new ResourceService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long resourceId = RequestUtil.requiredLong(request, "resourceId");
        LocalDate date = LocalDate.parse(RequestUtil.optional(request, "date", LocalDate.now().toString()));

        request.setAttribute("resource", resourceService.getResource(resourceId));
        request.setAttribute("slots", resourceService.getSlots(resourceId, date));
        request.setAttribute("selectedDate", date.toString());
        request.setAttribute("today", LocalDate.now().toString());
        return CommandResult.forward("/WEB-INF/views/reservation/reservation-form.jsp");
    }
}
