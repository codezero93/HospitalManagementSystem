/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Olca
 */
public class DBConnect {

    private static Connection connection;
    private static PreparedStatement pstmt;
    private static Statement stmt;
    private final static String user = "root", password = "cantona93";
    private final static String connectionUrl = "jdbc:mysql://localhost/hrm";

    public static void openConnection() {

        try {
            connection = DriverManager.getConnection(connectionUrl, user, password);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static void closeConnection() {

        try {
            connection.close();

        } catch (SQLException exe) {
            exe.printStackTrace();
        }
    }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    public String loginCheck(String username, String password) throws SQLException {
        openConnection();

        pstmt = connection.prepareStatement("SELECT * FROM account WHERE username=? AND password =?");
        pstmt.setString(1, username);
        pstmt.setString(2, password);

        ResultSet rs = pstmt.executeQuery();
        String name;
        if (rs.next()) {
            name = rs.getString(1);
        } else {
            name = null;
        }

        pstmt.close();
        closeConnection();

        return name;

    }

    public static void closePreparedStatement() {
        try {
            pstmt.close();;
            pstmt.clearParameters();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
