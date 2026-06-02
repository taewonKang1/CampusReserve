package com.campusreserve.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.campusreserve.dto.LayoutStatusDTO;
import com.campusreserve.util.DBUtil;

public class AdminDAO {
    public List<LayoutStatusDTO> findLayoutStatus(LocalDate date) throws SQLException {
        String sql = """
            SELECT r.resource_id, r.name AS resource_name, r.resource_type, r.location,
                   COALESCE(SUM(CASE WHEN s.status = 'AVAILABLE' THEN 1 ELSE 0 END), 0) AS available_count,
                   COALESCE(SUM(CASE WHEN s.status = 'RESERVED' THEN 1 ELSE 0 END), 0) AS reserved_count,
                   COUNT(s.slot_id) AS total_count
            FROM resources r
            LEFT JOIN resource_slots s
              ON s.resource_id = r.resource_id
             AND CAST(s.start_at AS DATE) = ?
            WHERE r.status = 'ACTIVE'
            GROUP BY r.resource_id, r.name, r.resource_type, r.location
            ORDER BY r.resource_type DESC, r.name ASC
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(date));
            try (ResultSet rs = pstmt.executeQuery()) {
                List<LayoutStatusDTO> statuses = new ArrayList<>();
                while (rs.next()) {
                    LayoutStatusDTO status = new LayoutStatusDTO();
                    status.setResourceId(rs.getLong("resource_id"));
                    status.setResourceName(rs.getString("resource_name"));
                    status.setResourceType(rs.getString("resource_type"));
                    status.setLocation(rs.getString("location"));
                    status.setAvailableCount(rs.getInt("available_count"));
                    status.setReservedCount(rs.getInt("reserved_count"));
                    status.setTotalCount(rs.getInt("total_count"));
                    statuses.add(status);
                }
                return statuses;
            }
        }
    }
}
