/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Shriman
 */
public class credentials {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://localhost/c0652964";
            String userName = "root";
            String pwd = "";
            con = DriverManager.getConnection(jdbc, userName, pwd);
            String query = "SELECT * FROM products";

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Class not found!" +ex.getMessage());
        }
        return con;
    }
}