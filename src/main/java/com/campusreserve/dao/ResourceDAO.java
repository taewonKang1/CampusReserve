package com.campusreserve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.campusreserve.dto.ResourceDTO;
import com.campusreserve.util.DBUtil;

public class ResourceDAO {
    public List<ResourceDTO> findActiveByType(String resourceType) throws SQLException {
        String sql = """
            SELECT resource_id, resource_type, name, location, capacity, price_per_slot, status
            FROM resources
            WHERE resource_type = ? AND status = 'ACTIVE'
            ORDER BY name
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, resourceType);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<ResourceDTO> resources = new ArrayList<>();
                while (rs.next()) {
                    resources.add(map(rs));
                }
                return resources;
            }
        }
    }

    public ResourceDTO findById(long resourceId) throws SQLException {
        String sql = """
            SELECT resource_id, resource_type, name, location, capacity, price_per_slot, status
            FROM resources
            WHERE resource_id = ?
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, resourceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;
            }
        }
    }

    public ResourceDTO findById(Connection conn, long resourceId) throws SQLException {
        String sql = """
            SELECT resource_id, resource_type, name, location, capacity, price_per_slot, status
            FROM resources
            WHERE resource_id = ?
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, resourceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;
            }
        }
    }

    public int countActive() throws SQLException {
        String sql = "SELECT COUNT(*) FROM resources WHERE status = 'ACTIVE'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    private ResourceDTO map(ResultSet rs) throws SQLException {
        ResourceDTO resource = new ResourceDTO();
        resource.setResourceId(rs.getLong("resource_id"));
        resource.setResourceType(rs.getString("resource_type"));
        resource.setName(rs.getString("name"));
        resource.setLocation(rs.getString("location"));
        resource.setCapacity(rs.getInt("capacity"));
        resource.setPricePerSlot(rs.getInt("price_per_slot"));
        resource.setStatus(rs.getString("status"));
        return resource;
    }
}
