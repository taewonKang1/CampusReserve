package com.campusreserve.command.reservation;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.dto.ReservationDTO;
import com.campusreserve.service.ReservationService;
import com.campusreserve.util.RequestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CompleteReservationCommand implements Command {
    private final ReservationService reservationService = new ReservationService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long reservationId = RequestUtil.requiredLong(request, "id");
        ReservationDTO reservation = reservationService.getReservation(reservationId);
        long userId = (Long) request.getSession().getAttribute("userId");
        String role = (String) request.getSession().getAttribute("role");
        if (!"ADMIN".equals(role) && reservation.getUserId() != userId) {
            throw new IllegalArgumentException("본인 예약만 조회할 수 있습니다.");
        }
        request.setAttribute("reservation", reservation);
        return CommandResult.forward("/WEB-INF/views/reservation/reservation-complete.jsp");
    }
}
