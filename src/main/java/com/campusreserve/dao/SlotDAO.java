package com.campusreserve.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.campusreserve.dto.SlotDTO;
import com.campusreserve.util.DBUtil;

public class SlotDAO {
    public List<SlotDTO> findByResourceAndDate(long resourceId, LocalDate date) throws SQLException {
        String sql = """
            SELECT slot_id, resource_id, start_at, end_at, status
            FROM resource_slots
            WHERE resource_id = ? AND CAST(start_at AS DATE) = ?
            ORDER BY start_at
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, resourceId);
            pstmt.setDate(2, Date.valueOf(date));
            try (ResultSet rs = pstmt.executeQuery()) {
                List<SlotDTO> slots = new ArrayList<>();
                while (rs.next()) {
                    slots.add(map(rs));
                }
                return slots;
            }
        }
    }

    public SlotDTO findByIdForUpdate(Connection conn, long slotId) throws SQLException {
        String sql = """
            SELECT slot_id, resource_id, start_at, end_at, status
            FROM resource_slots
            WHERE slot_id = ?
            FOR UPDATE
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, slotId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;
            }
        }
    }

    public void updateStatus(Connection conn, long slotId, String status) throws SQLException {
        String sql = "UPDATE resource_slots SET status = ? WHERE slot_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setLong(2, slotId);
            pstmt.executeUpdate();
        }
    }

    private SlotDTO map(ResultSet rs) throws SQLException {
        SlotDTO slot = new SlotDTO();
        slot.setSlotId(rs.getLong("slot_id"));
        slot.setResourceId(rs.getLong("resource_id"));
        Timestamp startAt = rs.getTimestamp("start_at");
        Timestamp endAt = rs.getTimestamp("end_at");
        if (startAt != null) {
            slot.setStartAt(startAt.toLocalDateTime());
        }
        if (endAt != null) {
            slot.setEndAt(endAt.toLocalDateTime());
        }
        slot.setStatus(rs.getString("status"));
        return slot;
    }
}
