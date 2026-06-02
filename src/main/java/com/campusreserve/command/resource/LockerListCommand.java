package com.campusreserve.command.resource;

import java.time.LocalDate;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.service.ResourceService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LockerListCommand implements Command {
    private final ResourceService resourceService = new ResourceService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("resources", resourceService.getLockers());
        request.setAttribute("title", "사물함 예약");
        request.setAttribute("subtitle", "보관 위치와 이용 금액을 확인하고 원하는 시간대를 선택하세요.");
        request.setAttribute("today", LocalDate.now().toString());
        return CommandResult.forward("/WEB-INF/views/reservation/locker-list.jsp");
    }
}
