package com.campusreserve.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.sql.DataSource;

import com.campusreserve.util.DBUtil;
import com.campusreserve.util.PasswordUtil;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class DatabaseInitializer implements ServletContextListener {
    private static final int SLOT_DAYS = 14;
    private static final int FIRST_HOUR = 9;
    private static final int LAST_HOUR = 18;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            executeSqlScript(conn, "schema.sql");
            executeSqlScript(conn, "data.sql");
            seedUsers(conn);
            seedSlots(conn);
        } catch (SQLException | IOException e) {
            throw new IllegalStateException("DB 초기화에 실패했습니다.", e);
        }
    }

    private void executeSqlScript(Connection conn, String resourceName) throws IOException, SQLException {
        String script = readResource(resourceName);
        for (String sql : script.split(";")) {
            String statementSql = sql.trim();
            if (statementSql.isEmpty()) {
                continue;
            }
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(statementSql);
            }
        }
    }

    private void seedUsers(Connection conn) throws SQLException {
        insertUserIfMissing(conn, "admin", "admin1234!", "관리자", "010-0000-0000", "ADMIN");
        insertUserIfMissing(conn, "student", "student1234!", "홍길동", "010-1111-2222", "MEMBER");
    }

    private void insertUserIfMissing(
        Connection conn,
        String loginId,
        String password,
        String name,
        String phone,
        String role
    ) throws SQLException {
        String sql = """
            INSERT INTO users (login_id, password_hash, name, phone, role)
            SELECT ?, ?, ?, ?, ?
            WHERE NOT EXISTS (SELECT 1 FROM users WHERE login_id = ?)
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginId);
            pstmt.setString(2, PasswordUtil.hash(password));
            pstmt.setString(3, name);
            pstmt.setString(4, phone);
            pstmt.setString(5, role);
            pstmt.setString(6, loginId);
            pstmt.executeUpdate();
        }
    }

    private void seedSlots(Connection conn) throws SQLException {
        String sql = """
            INSERT INTO resource_slots (resource_id, start_at, end_at, status)
            SELECT resource_id, ?, ?, 'AVAILABLE'
            FROM resources
            WHERE status = 'ACTIVE'
              AND NOT EXISTS (
                  SELECT 1
                  FROM resource_slots
                  WHERE resource_slots.resource_id = resources.resource_id
                    AND resource_slots.start_at = ?
                    AND resource_slots.end_at = ?
              )
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            LocalDate start = LocalDate.now();
            for (int day = 0; day < SLOT_DAYS; day++) {
                LocalDate date = start.plusDays(day);
                for (int hour = FIRST_HOUR; hour < LAST_HOUR; hour++) {
                    LocalDateTime startAt = LocalDateTime.of(date, LocalTime.of(hour, 0));
                    LocalDateTime endAt = startAt.plusHours(1);
                    Timestamp startTimestamp = Timestamp.valueOf(startAt);
                    Timestamp endTimestamp = Timestamp.valueOf(endAt);
                    pstmt.setTimestamp(1, startTimestamp);
                    pstmt.setTimestamp(2, endTimestamp);
                    pstmt.setTimestamp(3, startTimestamp);
                    pstmt.setTimestamp(4, endTimestamp);
                    pstmt.addBatch();
                }
            }
            pstmt.executeBatch();
        }
    }

    private String readResource(String name) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream input = loader.getResourceAsStream(name)) {
            if (input == null) {
                throw new IOException("리소스를 찾을 수 없습니다: " + name);
            }
            StringBuilder builder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append('\n');
                }
            }
            return builder.toString();
        }
    }
}
