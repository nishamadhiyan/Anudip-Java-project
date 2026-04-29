package com.sms;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_db",
                "root",
                "nisha0530"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}