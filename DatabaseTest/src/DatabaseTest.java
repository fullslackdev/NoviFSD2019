import java.util.LinkedHashMap;
import java.util.List;

public class DatabaseTest {

    public static void main(String[] args) {
        DatabaseConnector dbCon = new DatabaseConnector();
        //String query = "SELECT Naam AS name, Woonplaats AS city, Salaris AS salary FROM test";
        String query = "SELECT * FROM test";
        //List<HashMap<String, Object>> list = dbCon.getList(query);
        List<LinkedHashMap<String, Object>> list = dbCon.getList(query);

        System.out.println(list);

        for (LinkedHashMap<String, Object> test : list) {
            System.out.println(test);
        }
    }

    /*
    public static void main(String[] args) {
        String query = "SELECT * FROM test";
        DatabaseConnector databaseConnector = new DatabaseConnector();
        ResultSet rs = databaseConnector.getResult(query, databaseConnector.connectDB());
        try {
            while (rs.next()) {
                System.out.println(rs.getInt(1)+" " + rs.getString(2) + " " + rs.getString(3));
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }*/
}
