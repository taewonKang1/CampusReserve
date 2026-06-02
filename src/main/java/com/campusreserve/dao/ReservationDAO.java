package com.campusreserve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.campusreserve.dto.ReservationDTO;
import com.campusreserve.util.DBUtil;

public class ReservationDAO {
    private static final String SELECT_JOINED = """
        SELECT r.reservation_id, r.user_id, r.resource_id, r.start_at, r.end_at,
               r.total_price, r.status, r.created_at,
               u.name AS user_name, u.login_id,
               res.name AS resource_name, res.resource_type,
               p.method AS payment_method, p.status AS payment_status
        FROM reservations r
        JOIN users u ON u.user_id = r.user_id
        JOIN resources res ON res.resource_id = r.resource_id
        LEFT JOIN payments p ON p.reservation_id = r.reservation_id
        """;

    public long insert(
        Connection conn,
        long userId,
        long resourceId,
        LocalDateTime startAt,
        LocalDateTime endAt,
        int totalPrice,
        String status
    ) throws SQLException {
        String sql = """
            INSERT INTO reservations (user_id, resource_id, start_at, end_at, total_price, status)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, resourceId);
            pstmt.setTimestamp(3, Timestamp.valueOf(startAt));
            pstmt.setTimestamp(4, Timestamp.valueOf(endAt));
            pstmt.setInt(5, totalPrice);
            pstmt.setString(6, status);
            pstmt.executeUpdate();
            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
                throw new SQLException("예약 ID 생성에 실패했습니다.");
            }
        }
    }

    public ReservationDTO findById(long reservationId) throws SQLException {
        String sql = SELECT_JOINED + " WHERE r.reservation_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, reservationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;
            }
        }
    }

    public ReservationDTO findByIdForUpdate(Connection conn, long reservationId) throws SQLException {
        String sql = """
            SELECT reservation_id, user_id, resource_id, start_at, end_at, total_price, status, created_at
            FROM reservations
            WHERE reservation_id = ?
            FOR UPDATE
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, reservationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapCore(rs);
                }
                return null;
            }
        }
    }

    public List<ReservationDTO> findByUserId(long userId) throws SQLException {
        String sql = SELECT_JOINED + " WHERE r.user_id = ? ORDER BY r.start_at DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return mapList(rs);
            }
        }
    }

    public List<ReservationDTO> findAll() throws SQLException {
        String sql = SELECT_JOINED + " ORDER BY r.created_at DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            return mapList(rs);
        }
    }

    public List<ReservationDTO> findByStatus(String status) throws SQLException {
        String sql = SELECT_JOINED + " WHERE r.status = ? ORDER BY r.created_at ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                return mapList(rs);
            }
        }
    }

    public boolean requestCancel(long reservationId, long userId) throws SQLException {
        String sql = """
            UPDATE reservations
            SET status = 'CANCEL_REQUESTED'
            WHERE reservation_id = ?
              AND user_id = ?
              AND status = 'RESERVED'
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, reservationId);
            pstmt.setLong(2, userId);
            return pstmt.executeUpdate() == 1;
        }
    }

    public void updateStatus(Connection conn, long reservationId, String status) throws SQLException {
        String sql = "UPDATE reservations SET status = ? WHERE reservation_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setLong(2, reservationId);
            pstmt.executeUpdate();
        }
    }

    public int countAll() throws SQLException {
        return countByWhere(null, null);
    }

    public int countByStatus(String status) throws SQLException {
        return countByWhere("status = ?", status);
    }

    private int countByWhere(String where, String value) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservations" + (where == null ? "" : " WHERE " + where);
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (value != null) {
                pstmt.setString(1, value);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private List<ReservationDTO> mapList(ResultSet rs) throws SQLException {
        List<ReservationDTO> reservations = new ArrayList<>();
        while (rs.next()) {
            reservations.add(map(rs));
        }
        return reservations;
    }

    private ReservationDTO map(ResultSet rs) throws SQLException {
        ReservationDTO reservation = mapCore(rs);
        reservation.setUserName(rs.getString("user_name"));
        reservation.setLoginId(rs.getString("login_id"));
        reservation.setResourceName(rs.getString("resource_name"));
        reservation.setResourceType(rs.getString("resource_type"));
        reservation.setPaymentMethod(rs.getString("payment_method"));
        reservation.setPaymentStatus(rs.getString("payment_status"));
        return reservation;
    }

    private ReservationDTO mapCore(ResultSet rs) throws SQLException {
        ReservationDTO reservation = new ReservationDTO();
        reservation.setReservationId(rs.getLong("reservation_id"));
        reservation.setUserId(rs.getLong("user_id"));
        reservation.setResourceId(rs.getLong("resource_id"));
        Timestamp startAt = rs.getTimestamp("start_at");
        Timestamp endAt = rs.getTimestamp("end_at");
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (startAt != null) {
            reservation.setStartAt(startAt.toLocalDateTime());
        }
        if (endAt != null) {
            reservation.setEndAt(endAt.toLocalDateTime());
        }
        if (createdAt != null) {
            reservation.setCreatedAt(createdAt.toLocalDateTime());
        }
        reservation.setTotalPrice(rs.getInt("total_price"));
        reservation.setStatus(rs.getString("status"));
        return reservation;
    }
}
