package com.campusreserve.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.campusreserve.dao.PaymentDAO;
import com.campusreserve.dao.ReservationDAO;
import com.campusreserve.dao.ReservationSlotDAO;
import com.campusreserve.dao.ResourceDAO;
import com.campusreserve.dao.SlotDAO;
import com.campusreserve.dto.ReservationDTO;
import com.campusreserve.dto.ResourceDTO;
import com.campusreserve.dto.SlotDTO;
import com.campusreserve.util.DBUtil;

public class ReservationService {
    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final ReservationSlotDAO reservationSlotDAO = new ReservationSlotDAO();
    private final SlotDAO slotDAO = new SlotDAO();
    private final ResourceDAO resourceDAO = new ResourceDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    public ReservationDTO createReservation(long userId, long slotId, String paymentMethod) throws SQLException {
        long reservationId;
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);

                SlotDTO slot = slotDAO.findByIdForUpdate(conn, slotId);
                if (slot == null) {
                    throw new IllegalArgumentException("존재하지 않는 시간대입니다.");
                }
                if (!"AVAILABLE".equals(slot.getStatus())) {
                    throw new IllegalStateException("이미 예약된 시간입니다.");
                }

                ResourceDTO resource = resourceDAO.findById(conn, slot.getResourceId());
                if (resource == null || !"ACTIVE".equals(resource.getStatus())) {
                    throw new IllegalArgumentException("사용할 수 없는 자원입니다.");
                }

                reservationId = reservationDAO.insert(
                    conn,
                    userId,
                    resource.getResourceId(),
                    slot.getStartAt(),
                    slot.getEndAt(),
                    resource.getPricePerSlot(),
                    "RESERVED"
                );
                reservationSlotDAO.insert(conn, reservationId, slot.getSlotId());
                slotDAO.updateStatus(conn, slot.getSlotId(), "RESERVED");
                paymentDAO.insertMockPayment(conn, reservationId, resource.getPricePerSlot(), normalizeMethod(paymentMethod));

                conn.commit();
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
        return reservationDAO.findById(reservationId);
    }

    public List<ReservationDTO> getMyReservations(long userId) throws SQLException {
        return reservationDAO.findByUserId(userId);
    }

    public ReservationDTO getReservation(long reservationId) throws SQLException {
        ReservationDTO reservation = reservationDAO.findById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("예약 정보를 찾을 수 없습니다.");
        }
        return reservation;
    }

    public void requestCancel(long reservationId, long userId) throws SQLException {
        boolean updated = reservationDAO.requestCancel(reservationId, userId);
        if (!updated) {
            throw new IllegalArgumentException("취소 요청할 수 없는 예약입니다.");
        }
    }

    private String normalizeMethod(String method) {
        if (method == null || method.isBlank()) {
            return "MOCK_CARD";
        }
        return switch (method) {
            case "MOCK_BANK" -> "MOCK_BANK";
            case "MOCK_POINT" -> "MOCK_POINT";
            default -> "MOCK_CARD";
        };
    }
}
