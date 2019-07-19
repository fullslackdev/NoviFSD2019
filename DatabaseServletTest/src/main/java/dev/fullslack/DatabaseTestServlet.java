package dev.fullslack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

@WebServlet(
        name = "DatabaseServletTest",
        urlPatterns = "/DatabaseTestServlet" // URL of the servlet
)
public class DatabaseTestServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseTestServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        //writer = startHTMLOutput(writer, "Database Test");

        //DBSingleton dbSingleton = DBSingleton.getInstance();
        DatabaseConnector dbCon = new DatabaseConnector();

        //String query = "SELECT Naam AS name, Woonplaats AS city, Salaris AS salary FROM test";
        String query = "SELECT * FROM test";

        //List<LinkedHashMap<String, Object>> list = dbSingleton.getList(query);
        //List<LinkedHashMap<String, Object>> list = dbCon.getList(query);

        /*writer.append("<div><p>\r\n");
        writer.append(list.toString()+"\r\n");
        writer.append(list.size()+"\r\n");
        writer.append("</p></div>\r\n");

        writer.append("<div><p>\r\n");
        for (LinkedHashMap<String, Object> test : list) {
            writer.append(test.toString());
            writer.append("<br/>\r\n");
        }
        writer.append("</p></div>\r\n");*/

        //writer.append("<div><p>\r\n");
        JSONArray jsonArray = dbCon.getJSON(query);
        writer.append(jsonArray.toString()+"\r\n");
        //writer.append(list.size()+"\r\n");
        //writer.append("</p></div>\r\n");

        //closeHTMLOutput(writer);

        /*try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Enumeration e = session.getAttributeNames();
                writer.println(session.getId());
                while (e.hasMoreElements()) {
                    String name = (String) e.nextElement();
                    writer.println(name + ": " + session.getAttribute(name) + "<br/>");
                }
            } else {
                writer.append("ERROR: Session is not set!");
            }
        } catch (Exception ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }*/
    }

    private PrintWriter startHTMLOutput(PrintWriter writer) {
        return startHTMLOutput(writer, "Default page title");
    }

    private PrintWriter startHTMLOutput(PrintWriter writer, String pageTitle) {
        writer.append("<!DOCTYPE html>\r\n")
                .append("<html lang=\"en\">\r\n")
                .append("<head><meta charset=\"UTF-8\"><title>")
                .append(pageTitle)
                .append("</title></head>\r\n")
                .append("<body>\r\n");
        return writer;
    }

    private void closeHTMLOutput(PrintWriter writer) {
        writer.append("</body>\r\n")
                .append("</html>");
    }
}
