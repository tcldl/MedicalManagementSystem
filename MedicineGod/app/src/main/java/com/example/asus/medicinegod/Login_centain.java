package com.example.asus.medicinegod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login_centain {

    PreparedStatement stmt=null;

    public boolean managerlogin(String tel, String password) {
        Connection conn = null;
        conn =(Connection) DBDao.getConn();
        String sql = "select * from admin where admintel = '"+tel+"' and adminpassword = '"+password+"'";
        try {
            stmt= conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return true;
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean userlogin(String tel, String password) {
        Connection conn = null;
        conn =(Connection) DBDao.getConn();
        String sql = "select * from user where usertel = '"+tel+"' and userpassword = '"+password+"'";
        try {
            stmt= conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return true;
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
