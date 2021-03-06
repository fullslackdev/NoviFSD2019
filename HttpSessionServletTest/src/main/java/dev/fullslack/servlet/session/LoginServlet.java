package dev.fullslack.servlet.session;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import dev.fullslack.db.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*@WebServlet(
        name = "LoginServlet",
        urlPatterns = "/LoginServlet"
)*/
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class.getName());
    //private String userID = null;
    //private String password = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        String query = "SELECT u.id, u.password, " +
                "IF(u.secret IS NULL OR u.secret = '' OR u.salt IS NULL OR u.salt = '', 0, 1) AS secretFilled, " +
                "u.active, i.firstname, i.lastname, i.email, g.id, g.name FROM user AS u " +
                "JOIN user_info AS i ON i.user_id = u.id " +
                "JOIN `group` AS g ON u.group_id = g.id " +
                "WHERE u.username = ?";
        //byte[] pwd = request.getParameter("pwd").getBytes(StandardCharsets.UTF_8);
        //byte[] pwdHash = createPasswordHash(user,pwd);
        //debugging section
        /*if (request.getSession() != null) {
            PrintWriter writer = response.getWriter();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            String formattedDate = formatter.format(LocalDateTime.now());
            System.out.println(formattedDate + "  Session ID before authentication: " + request.getSession().getId());
        }*/


        try (Connection con = ConnectionManager.getConnection()) {
            if (con != null) {
                //PreparedStatement pst = con.prepareStatement("SELECT id,group_id,active FROM user WHERE username=? AND password=?");
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, user);
                //pst.setString(2, pwd);
                //pst.setBytes(2, pwdHash);
                //pwd = new byte[0];
                //pwdHash = new byte[0];
                ResultSet rs = pst.executeQuery();
                //ResultSetMetaData md = pst.getMetaData();
                int rowCount = 0;
                if (rs != null) {
                    if (rs.last()) {
                        rowCount = rs.getRow();
                        rs.beforeFirst();
                        if (rowCount == 1) {
                            boolean userActive = false;
                            boolean twoFactorActive = false;
                            if (rs.next()) {
                                userActive = rs.getBoolean(4);
                                twoFactorActive = rs.getBoolean(3);
                            }
                            if (userActive) {
                                String pwdHash = rs.getString(2);
                                Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                                boolean pwdCorrect = argon2.verify(pwdHash, pwd);
                                if (pwdCorrect) {
                                    request.getSession().invalidate(); //invalidate current session
                                    HttpSession session = request.getSession(true); //create new session ID to prevent hijacking
                                    if (twoFactorActive) {
                                        session.setAttribute("tempid", rs.getInt(1));
                                        session.setAttribute("tempuser", user);
                                        //setting session to expire in 60 seconds
                                        session.setMaxInactiveInterval(60);
                                        response.sendRedirect("2falogin.html");
                                    } else {
                                        session.setAttribute("userid", rs.getInt(1));
                                        session.setAttribute("username", user);
                                        session.setAttribute("firstname", rs.getString(5));
                                        session.setAttribute("lastname", rs.getString(6));
                                        session.setAttribute("email", rs.getString(7));
                                        session.setAttribute("groupid", rs.getInt(8));
                                        session.setAttribute("groupname", rs.getString(9));
                                        //setting session to expire in 30 mins
                                        //session.setMaxInactiveInterval(30*60);
                                        //setting session to expire in 60 seconds
                                        session.setMaxInactiveInterval(60);
                                        Cookie userCookie = new Cookie("user", user);
                                        //userName.setMaxAge(30*60);
                                        userCookie.setMaxAge(60);
                                        response.addCookie(userCookie);
                                        response.sendRedirect("LoginSuccess.jsp");
                                    }
                                } else {
                                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                                    PrintWriter writer = response.getWriter();
                                    writer.println("<font color=red>Either password or username is not in database!</font>");
                                    rd.include(request, response);
                                }
                            } else {
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                                PrintWriter writer = response.getWriter();
                                writer.println("<font color=red>User has been disabled in database!</font>");
                                rd.include(request, response);
                            }
                        } else {
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                            PrintWriter writer = response.getWriter();
                            writer.println("<font color=red>Duplicate entry in database!</font>");
                            rd.include(request, response);
                        }
                    } else {
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                        PrintWriter writer = response.getWriter();
                        writer.println("<font color=red>Either username or password is not in database!</font>");
                        rd.include(request, response);
                    }
                }
                rs.close();
                pst.close();
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                PrintWriter writer = response.getWriter();
                writer.println("<font color=red>No database connection!</font>");
                rd.include(request, response);
            }
        } catch (SQLException ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }

        /*
        if (userID.equals(user) && password.equals(pwd)) {
            request.getSession().invalidate(); //invalidate current session
            HttpSession session = request.getSession(true); //create new session ID to prevent hijacking
            session.setAttribute("user", "Pankaj");
            session.setAttribute("userID", user);
            //setting session to expire in 30 mins
            //session.setMaxInactiveInterval(30*60);
            //setting session to expire in 60 seconds
            session.setMaxInactiveInterval(60);
            Cookie userName = new Cookie("user", user);
            //userName.setMaxAge(30*60);
            userName.setMaxAge(60);
            response.addCookie(userName);
            response.sendRedirect("LoginSuccess.jsp");
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter writer = response.getWriter();
            writer.println("<font color=red>Either username or password is wrong!</font>");
            rd.include(request,response);
        }
        */
    }

    /*private byte[] createPasswordHash(String username, byte[] password) {
        try (Connection con = ConnectionManager.getConnection()) {
            if (con != null) {
                PreparedStatement pst = con.prepareStatement("SELECT salt FROM user WHERE username = ?");
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                int rowcount = 0;
                if (rs != null) {
                    if (rs.last()) {
                        rowcount = rs.getRow();
                        rs.beforeFirst();
                        if (rowcount == 1) {
                            if (rs.next()) {
                                try {
                                    MessageDigest digest = MessageDigest.getInstance("SHA3-512");
                                    digest.reset();
                                    digest.update(rs.getBytes(1));
                                    byte[] hashbytes = digest.digest(password);
                                    //System.out.println("Hash: " + bytesToHex(hashbytes) + " | " + hashbytes);
                                    return hashbytes;
                                } catch (NoSuchAlgorithmException ex) {
                                    LOGGER.error("Error Message Logged !!!", ex.getMessage(), ex);
                                }
                            }
                        }
                    }
                }
                rs.close();
                pst.close();
            }
        } catch (SQLException ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }
        return null;
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }*/
}
