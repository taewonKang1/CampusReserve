package com.campusreserve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.campusreserve.dto.SalesStatDTO;
import com.campusreserve.util.DBUtil;

public class PaymentDAO {
    public void insertMockPayment(Connection conn, long reservationId, int amount, String method) throws SQLException {
        String sql = """
            INSERT INTO payments (reservation_id, amount, method, status)
            VALUES (?, ?, ?, 'PAID')
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, reservationId);
            pstmt.setInt(2, amount);
            pstmt.setString(3, method);
            pstmt.executeUpdate();
        }
    }

    public void updateStatusByReservation(Connection conn, long reservationId, String status) throws SQLException {
        String sql = "UPDATE payments SET status = ? WHERE reservation_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setLong(2, reservationId);
            pstmt.executeUpdate();
        }
    }

    public int sumPaidAmount() throws SQLException {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM payments WHERE status = 'PAID'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public List<SalesStatDTO> findDailySales() throws SQLException {
        String sql = """
            SELECT CAST(paid_at AS DATE) AS label, COUNT(*) AS reservation_count, COALESCE(SUM(amount), 0) AS amount
            FROM payments
            WHERE status = 'PAID'
            GROUP BY CAST(paid_at AS DATE)
            ORDER BY label DESC
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            return mapSales(rs);
        }
    }

    public List<SalesStatDTO> findResourceSales() throws SQLException {
        String sql = """
            SELECT res.name AS label, COUNT(*) AS reservation_count, COALESCE(SUM(p.amount), 0) AS amount
            FROM payments p
            JOIN reservations r ON r.reservation_id = p.reservation_id
            JOIN resources res ON res.resource_id = r.resource_id
            WHERE p.status = 'PAID'
            GROUP BY res.name
            ORDER BY amount DESC, label ASC
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            return mapSales(rs);
        }
    }

    private List<SalesStatDTO> mapSales(ResultSet rs) throws SQLException {
        List<SalesStatDTO> stats = new ArrayList<>();
        while (rs.next()) {
            SalesStatDTO stat = new SalesStatDTO();
            stat.setLabel(rs.getString("label"));
            stat.setReservationCount(rs.getInt("reservation_count"));
            stat.setAmount(rs.getInt("amount"));
            stats.add(stat);
        }
        return stats;
    }
}
