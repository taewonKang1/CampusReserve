package com.campusreserve.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.campusreserve.dao.AdminDAO;
import com.campusreserve.dao.PaymentDAO;
import com.campusreserve.dao.ReservationDAO;
import com.campusreserve.dao.ReservationSlotDAO;
import com.campusreserve.dao.ResourceDAO;
import com.campusreserve.dto.DashboardDTO;
import com.campusreserve.dto.LayoutStatusDTO;
import com.campusreserve.dto.ReservationDTO;
import com.campusreserve.dto.SalesStatDTO;
import com.campusreserve.util.DBUtil;

public class AdminService {
    private final AdminDAO adminDAO = new AdminDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final ReservationSlotDAO reservationSlotDAO = new ReservationSlotDAO();
    private final ResourceDAO resourceDAO = new ResourceDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    public DashboardDTO getDashboard() throws SQLException {
        DashboardDTO dashboard = new DashboardDTO();
        dashboard.setTotalReservations(reservationDAO.countAll());
        dashboard.setPendingCancelCount(reservationDAO.countByStatus("CANCEL_REQUESTED"));
        dashboard.setActiveResourceCount(resourceDAO.countActive());
        dashboard.setTotalSales(paymentDAO.sumPaidAmount());
        return dashboard;
    }

    public List<LayoutStatusDTO> getLayoutStatus(LocalDate date) throws SQLException {
        return adminDAO.findLayoutStatus(date);
    }

    public List<ReservationDTO> getAllReservations() throws SQLException {
        return reservationDAO.findAll();
    }

    public List<ReservationDTO> getCancelRequests() throws SQLException {
        return reservationDAO.findByStatus("CANCEL_REQUESTED");
    }

    public List<SalesStatDTO> getDailySales() throws SQLException {
        return paymentDAO.findDailySales();
    }

    public List<SalesStatDTO> getResourceSales() throws SQLException {
        return paymentDAO.findResourceSales();
    }

    public void approveCancel(long reservationId) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                ReservationDTO reservation = reservationDAO.findByIdForUpdate(conn, reservationId);
                if (reservation == null || !"CANCEL_REQUESTED".equals(reservation.getStatus())) {
                    throw new IllegalArgumentException("승인할 수 없는 취소 요청입니다.");
                }
                reservationDAO.updateStatus(conn, reservationId, "CANCELLED");
                reservationSlotDAO.updateSlotStatusByReservation(conn, reservationId, "AVAILABLE");
                paymentDAO.updateStatusByReservation(conn, reservationId, "REFUNDED");
                conn.commit();
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void rejectCancel(long reservationId) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                ReservationDTO reservation = reservationDAO.findByIdForUpdate(conn, reservationId);
                if (reservation == null || !"CANCEL_REQUESTED".equals(reservation.getStatus())) {
                    throw new IllegalArgumentException("거절할 수 없는 취소 요청입니다.");
                }
                reservationDAO.updateStatus(conn, reservationId, "RESERVED");
                conn.commit();
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
