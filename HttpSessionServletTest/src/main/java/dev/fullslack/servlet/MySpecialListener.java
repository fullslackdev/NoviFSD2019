package dev.fullslack.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySpecialListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(MySpecialListener.class.getName());

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            java.sql.Driver mySqlDriver = DriverManager.getDriver("jdbc:mariadb://localhost:3306/");
            DriverManager.deregisterDriver(mySqlDriver);
        } catch (SQLException ex) {
            //Logger lgr = Logger.getLogger(MySpecialListener.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }
    }
}
