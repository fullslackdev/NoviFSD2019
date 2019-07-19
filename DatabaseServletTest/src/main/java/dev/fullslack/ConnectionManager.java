package dev.fullslack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionManager.class.getName());
    private static Connection con = null;

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }
        try {
            con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test","root","mysql@1234");
        } catch (SQLException ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }
    }

    public static Connection getConnection() {
        return con;
    }
}
