package com.hdims.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseUtil 类是一个用于管理数据库连接的工具类。
 */
public class DatabaseUtil {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HDIMS";
    private static final String USER = "sa";
    private static final String PASSWORD = "123wspsg";

    /**
     * 获取数据库连接。
     *
     * @return 数据库连接对象
     * @throws SQLException 当获取连接失败时抛出异常
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
