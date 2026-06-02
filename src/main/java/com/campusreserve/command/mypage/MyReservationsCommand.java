package com.campusreserve.command.mypage;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.service.ReservationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyReservationsCommand implements Command {
    private final ReservationService reservationService = new ReservationService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long userId = (Long) request.getSession().getAttribute("userId");
        request.setAttribute("reservations", reservationService.getMyReservations(userId));
        return CommandResult.forward("/WEB-INF/views/mypage/my-reservations.jsp");
    }
}
