package dev.fullslack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

public class DatabaseConnector {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseConnector.class.getName());

    private Properties getConnectionData() {
        Properties props = new Properties();

        //String fileName = "src/main/resources/db.properties";
        //String fileName = "/WEB-INF/classes/db.properties";
        String fileName = "db.properties";

        //try (FileInputStream in = new FileInputStream(fileName)) {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            props.load(in);
        } catch (IOException ex) {
            //Logger lgr = Logger.getLogger(DatabaseConnector.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }

        return props;
    }

    protected JSONArray getJSON(String query) {
        Properties props = getConnectionData();

        String url = props.getProperty("db.url");
        String dbName = props.getProperty("db.name");
        String user = props.getProperty("db.user");
        String passwd = props.getProperty("db.passwd");
        String driver = props.getProperty("db.driver");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            //Logger lgr = Logger.getLogger(DatabaseConnector.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }

        /*MariaDbPoolDataSource pool = new MariaDbPoolDataSource(url+dbName);
        try {
            pool.setUser(user);
            pool.setPassword(passwd);
            pool.setMaxPoolSize(10);
        } catch (SQLException ex) {
            //Logger lgr = Logger.getLogger(DatabaseConnector.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }*/

        try (Connection con = DriverManager.getConnection(url+dbName,user,passwd);
                /*Connection con = pool.getConnection();*/
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
        /*try {
            Connection con = DriverManager.getConnection(url+dbName,user,passwd);
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();*/
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            JSONArray jsonArray = new JSONArray();

            while(rs.next()) {
                JSONObject obj = new JSONObject();
                for (int i = 1; i <= columns; i++) {
                    obj.put(md.getColumnLabel(i).toLowerCase(), rs.getObject(i));
                }
                jsonArray.put(obj);
            }

            /*rs.close();
            pst.close();
            con.close();*/

            return jsonArray;
        } catch (SQLException ex) {
            //Logger lgr = Logger.getLogger(DatabaseConnector.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }
        return null;
    }

    protected List<LinkedHashMap<String, Object>> getList(String query) {
        Properties props = getConnectionData();

        String url = props.getProperty("db.url");
        String dbName = props.getProperty("db.name");
        String user = props.getProperty("db.user");
        String passwd = props.getProperty("db.passwd");
        String driver = props.getProperty("db.driver");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            //Logger lgr = Logger.getLogger(DatabaseConnector.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }

        /*MariaDbPoolDataSource pool = new MariaDbPoolDataSource(url+dbName);
        try {
            pool.setUser(user);
            pool.setPassword(passwd);
            pool.setMaxPoolSize(10);
        } catch (SQLException ex) {
            //Logger lgr = Logger.getLogger(DatabaseConnector.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }*/

        try (Connection con = DriverManager.getConnection(url+dbName,user,passwd);
                /*Connection con = pool.getConnection();*/
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
        /*try {
            Connection con = DriverManager.getConnection(url+dbName,user,passwd);
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();*/
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();

            while(rs.next()) {
                LinkedHashMap<String, Object> row = new LinkedHashMap<String, Object>(columns);
                for (int i = 1; i <= columns; i++) {
                    row.put(md.getColumnLabel(i),rs.getObject(i));
                }
                list.add(row);
            }

            /*rs.close();
            pst.close();
            con.close();*/

            return list;
        } catch (SQLException ex) {
            //Logger lgr = Logger.getLogger(DatabaseConnector.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }
        return null;
    }
}
