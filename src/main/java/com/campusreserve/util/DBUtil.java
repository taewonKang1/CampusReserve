package com.campusreserve.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class DBUtil {
    private static final String JNDI_NAME = "java:comp/env/jdbc/CampusReserveDB";
    private static DataSource dataSource;

    private DBUtil() {
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DBUtil.class) {
                if (dataSource == null) {
                    dataSource = lookupDataSource();
                }
            }
        }
        return dataSource;
    }

    private static DataSource lookupDataSource() {
        try {
            Context context = new InitialContext();
            return (DataSource) context.lookup(JNDI_NAME);
        } catch (NamingException e) {
            throw new IllegalStateException("JNDI DataSource를 찾을 수 없습니다: " + JNDI_NAME, e);
        }
    }
}
