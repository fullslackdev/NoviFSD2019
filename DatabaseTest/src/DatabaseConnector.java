import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnector {

    private Properties getConnectionData() {
        Properties props = new Properties();

        String fileName = "src/main/resources/db.properties";

        try (FileInputStream in = new FileInputStream(fileName)) {
            props.load(in);
        } catch (IOException ex) {
            Logger lgr = Logger.getLogger(DatabaseConnector.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return props;
    }

    protected List<LinkedHashMap<String, Object>> getList(String query) {
        Properties props = getConnectionData();

        String url = props.getProperty("db.url");
        String dbName = props.getProperty("db.name");
        String user = props.getProperty("db.user");
        String passwd = props.getProperty("db.passwd");

        try (Connection con = DriverManager.getConnection(url+dbName,user,passwd);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            /*while (rs.next()) {
                System.out.println(rs.getInt(1)+" " + rs.getString(2) + " " + rs.getString(3));
            }*/
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
            //List<HashMap<Type, HashMap<String, Object>>> list = new ArrayList<HashMap<Type, HashMap<String, Object>>>();

            while(rs.next()) {
                LinkedHashMap<String, Object> row = new LinkedHashMap<String, Object>(columns);
                //HashMap<Type, HashMap<String, Object>> row = new HashMap<Type, HashMap<String, Object>>(columns);
                for (int i = 1; i <= columns; i++) {
                    //row.put(md.getColumnName(i),rs.getObject(i));
                    row.put(md.getColumnLabel(i),rs.getObject(i));
                }
                list.add(row);
            }

            return list;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DatabaseConnector.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    /*
    private ResultSet getResult(String query) {
        Properties props = getConnectionData();

        String url = props.getProperty("db.url");
        String dbName = props.getProperty("db.name");
        String user = props.getProperty("db.user");
        String passwd = props.getProperty("db.passwd");

        try (Connection con = DriverManager.getConnection(url+dbName,user,passwd);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            return rs;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DatabaseConnector.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    protected List<HashMap<String, Object>> convertResultToList(String query) {
        try {
            ResultSet rs = getResult(query);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

            while(rs.next()) {
                HashMap<String, Object> row = new HashMap<String, Object>(columns);
                for (int i = 1; i < columns; i++) {
                    row.put(md.getColumnName(i),rs.getObject(i));
                }
                list.add(row);
            }

            return list;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DatabaseConnector.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }*/

    /*
    public Connection connectDB() {
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_CONNECTION_URL+DB_DATABASE,DB_USERNAME,DB_PASSWORD);
            return con;
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return null;
    }

    public ResultSet getResult(String query, Connection con) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            stmt.close();
            return rs;
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return null;
    }*/
}
