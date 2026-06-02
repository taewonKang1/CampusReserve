package com.campusreserve.command.mypage;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.service.ReservationService;
import com.campusreserve.util.CsrfTokenUtil;
import com.campusreserve.util.RequestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CancelRequestCommand implements Command {
    private final ReservationService reservationService = new ReservationService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return CommandResult.redirect("/mypage/reservations.do");
        }

        CsrfTokenUtil.validate(request);
        long userId = (Long) request.getSession().getAttribute("userId");
        long reservationId = RequestUtil.requiredLong(request, "reservationId");
        reservationService.requestCancel(reservationId, userId);
        return CommandResult.redirect("/mypage/reservations.do");
    }
}
