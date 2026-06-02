package com.campusreserve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationSlotDAO {
    public void insert(Connection conn, long reservationId, long slotId) throws SQLException {
        String sql = "INSERT INTO reservation_slots (reservation_id, slot_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, reservationId);
            pstmt.setLong(2, slotId);
            pstmt.executeUpdate();
        }
    }

    public void updateSlotStatusByReservation(Connection conn, long reservationId, String status) throws SQLException {
        String sql = """
            UPDATE resource_slots
            SET status = ?
            WHERE slot_id IN (
                SELECT slot_id
                FROM reservation_slots
                WHERE reservation_id = ?
            )
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setLong(2, reservationId);
            pstmt.executeUpdate();
        }
    }
}
