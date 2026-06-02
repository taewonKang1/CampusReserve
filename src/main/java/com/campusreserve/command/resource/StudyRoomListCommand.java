package com.campusreserve.command.resource;

import java.time.LocalDate;

import com.campusreserve.command.Command;
import com.campusreserve.command.CommandResult;
import com.campusreserve.service.ResourceService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StudyRoomListCommand implements Command {
    private final ResourceService resourceService = new ResourceService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("resources", resourceService.getStudyRooms());
        request.setAttribute("title", "스터디룸 예약");
        request.setAttribute("subtitle", "인원과 위치를 확인하고 가능한 시간대를 선택하세요.");
        request.setAttribute("today", LocalDate.now().toString());
        return CommandResult.forward("/WEB-INF/views/reservation/study-room-list.jsp");
    }
}
