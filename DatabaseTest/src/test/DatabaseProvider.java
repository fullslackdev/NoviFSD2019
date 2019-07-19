package test;

public interface DatabaseProvider {
    final static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    final static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    final static String DB_DATABASE = "test";
    final static String DB_USERNAME = "root";
    final static String DB_PASSWORD = "mysql@1234";
}
