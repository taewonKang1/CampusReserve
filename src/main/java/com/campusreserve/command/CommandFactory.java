package com.campusreserve.command;

import java.util.HashMap;
import java.util.Map;

import com.campusreserve.command.admin.AdminCancelApproveCommand;
import com.campusreserve.command.admin.AdminCancelRejectCommand;
import com.campusreserve.command.admin.AdminCancelRequestsCommand;
import com.campusreserve.command.admin.AdminDashboardCommand;
import com.campusreserve.command.admin.AdminLayoutCommand;
import com.campusreserve.command.admin.AdminReservationsCommand;
import com.campusreserve.command.admin.AdminSalesCommand;
import com.campusreserve.command.mypage.CancelRequestCommand;
import com.campusreserve.command.mypage.MyReservationsCommand;
import com.campusreserve.command.reservation.CompleteReservationCommand;
import com.campusreserve.command.reservation.CreateReservationCommand;
import com.campusreserve.command.reservation.ReservationFormCommand;
import com.campusreserve.command.resource.LockerListCommand;
import com.campusreserve.command.resource.StudyRoomListCommand;
import com.campusreserve.command.user.LoginCommand;
import com.campusreserve.command.user.LogoutCommand;
import com.campusreserve.command.user.RegisterCommand;

public class CommandFactory {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandFactory() {
        commands.put("/index.do", new IndexCommand());
        commands.put("/user/register.do", new RegisterCommand());
        commands.put("/user/login.do", new LoginCommand());
        commands.put("/user/logout.do", new LogoutCommand());
        commands.put("/resources/study-rooms.do", new StudyRoomListCommand());
        commands.put("/resources/lockers.do", new LockerListCommand());
        commands.put("/reservation/form.do", new ReservationFormCommand());
        commands.put("/reservation/create.do", new CreateReservationCommand());
        commands.put("/reservation/complete.do", new CompleteReservationCommand());
        commands.put("/mypage/reservations.do", new MyReservationsCommand());
        commands.put("/mypage/cancel-request.do", new CancelRequestCommand());
        commands.put("/admin/dashboard.do", new AdminDashboardCommand());
        commands.put("/admin/layout.do", new AdminLayoutCommand());
        commands.put("/admin/reservations.do", new AdminReservationsCommand());
        commands.put("/admin/cancel-requests.do", new AdminCancelRequestsCommand());
        commands.put("/admin/cancel-approve.do", new AdminCancelApproveCommand());
        commands.put("/admin/cancel-reject.do", new AdminCancelRejectCommand());
        commands.put("/admin/sales.do", new AdminSalesCommand());
    }

    public Command getCommand(String path) {
        return commands.getOrDefault(path, new NotFoundCommand());
    }
}
