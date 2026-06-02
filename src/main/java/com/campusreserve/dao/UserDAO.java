package com.campusreserve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.campusreserve.dto.UserDTO;
import com.campusreserve.util.DBUtil;

public class UserDAO {
    public UserDTO findByLoginId(String loginId) throws SQLException {
        String sql = """
            SELECT user_id, login_id, password_hash, name, phone, role, created_at
            FROM users
            WHERE login_id = ?
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;
            }
        }
    }

    public boolean existsByLoginId(String loginId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE login_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginId);
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public void insert(String loginId, String passwordHash, String name, String phone, String role) throws SQLException {
        String sql = """
            INSERT INTO users (login_id, password_hash, name, phone, role)
            VALUES (?, ?, ?, ?, ?)
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginId);
            pstmt.setString(2, passwordHash);
            pstmt.setString(3, name);
            pstmt.setString(4, phone);
            pstmt.setString(5, role);
            pstmt.executeUpdate();
        }
    }

    private UserDTO map(ResultSet rs) throws SQLException {
        UserDTO user = new UserDTO();
        user.setUserId(rs.getLong("user_id"));
        user.setLoginId(rs.getString("login_id"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setName(rs.getString("name"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getString("role"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }
        return user;
    }
}
