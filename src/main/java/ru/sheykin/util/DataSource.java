package ru.sheykin.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Simple class for creating Connection Pool and getting Connections.
 */
public class DataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;
    private final static String userName = "root";
    private final static String password = "1488";
    private final static String connectionUrl = "jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&useSSL=false&useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    static {
        config.setJdbcUrl(connectionUrl);
        config.setUsername(userName);
        config.setPassword(password);
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