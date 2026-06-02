package com.campusreserve.command.reservation;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.dto.ReservationDTO;
import com.campusreserve.service.ReservationService;
import com.campusreserve.util.CsrfTokenUtil;
import com.campusreserve.util.RequestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CreateReservationCommand implements Command {
    private final ReservationService reservationService = new ReservationService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return CommandResult.redirect("/resources/study-rooms.do");
        }

        CsrfTokenUtil.validate(request);
        long userId = (Long) request.getSession().getAttribute("userId");
        long slotId = RequestUtil.requiredLong(request, "slotId");
        String paymentMethod = RequestUtil.optional(request, "paymentMethod", "MOCK_CARD");
        ReservationDTO reservation = reservationService.createReservation(userId, slotId, paymentMethod);
        return CommandResult.redirect("/reservation/complete.do?id=" + reservation.getReservationId());
    }
}
