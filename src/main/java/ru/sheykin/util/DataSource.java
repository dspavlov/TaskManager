package ru.sheykin.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection pool handler
 */

public class DataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;
    private final static String userName = "root";
    private final static String password = "1488";
    private final static String connectionUrl = "jdbc:mysql://localhost:3306/test";

    static {
        config.setJdbcUrl(connectionUrl);
        config.setUsername(userName);
        config.setPassword(password);
        config.addDataSourceProperty("verifyServerCertificate", "false");
        config.addDataSourceProperty("useSSL", "false");
        config.addDataSourceProperty("useUnicode", "true");
        config.addDataSourceProperty("characterEncoding", "utf8");
        config.addDataSourceProperty("serverTimezone", "Europe/Moscow");
        config.addDataSourceProperty("useJDBCCompliantTimezoneShift", "true");
        config.addDataSourceProperty("useLegacyDatetimeCode", "false");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}