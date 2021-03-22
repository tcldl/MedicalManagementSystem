package com.example.asus.medicinegod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDao {
    private static final String DBDRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://10.0.2.2/medical?useSSL=FALSE";
    private static final String DBUSER = "root";
    private static final String DBPASSWORD = "123456";
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs;
    private String sql;

    DBDao(String sql) {
        this.sql = sql;
    }

    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(DBDRIVER);
            conn = (Connection) DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);//获取连接
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
