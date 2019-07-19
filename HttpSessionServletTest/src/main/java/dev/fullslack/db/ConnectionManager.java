package dev.fullslack.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseProperties.class.getName());

    public static Connection getConnection() {
        Properties props = DatabaseProperties.getConnectionData();
        Connection con = null;

        String url = props.getProperty("db.url");
        String dbName = props.getProperty("db.name");
        String user = props.getProperty("db.user");
        String passwd = props.getProperty("db.passwd");
        String driver = props.getProperty("db.driver");

        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url+dbName,user,passwd);
            } catch (SQLException ex) {
                LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
            }
        } catch (ClassNotFoundException ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }
        return con;
    }
}
