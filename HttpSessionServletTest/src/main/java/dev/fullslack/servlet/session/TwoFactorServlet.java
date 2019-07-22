package dev.fullslack.servlet.session;

import dev.fullslack.db.ConnectionManager;
import dev.fullslack.security.TOTPSecretUtil;
import dev.fullslack.security.TimeBasedOneTimePasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/TwoFactorServlet")
public class TwoFactorServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(TwoFactorServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = "SELECT u.salt, u.secret, i.firstname, i.lastname, i.email, g.id, g.name FROM user AS u " +
                "JOIN user_info AS i ON i.user_id = u.id " +
                "JOIN `group` AS g ON u.group_id = g.id " +
                "WHERE u.id = ? AND u.username = ?";
        try (Connection con = ConnectionManager.getConnection()) {
            int code = Integer.parseInt(request.getParameter("code"));
            if (con != null) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    if (session.getAttribute("tempuser") == null) {
                        if (request.isRequestedSessionIdValid()) {
                            session.invalidate();
                        }
                        request.getSession(true);
                        response.sendRedirect("login.html");
                    } else {
                        PreparedStatement pst = con.prepareStatement(query);
                        int userId = (int) session.getAttribute("tempid");
                        String userName = (String) session.getAttribute("tempuser");
                        pst.setInt(1, userId);
                        pst.setString(2, userName);
                        ResultSet rs = pst.executeQuery();
                        if (rs.next()) {
                            TOTPSecretUtil secretUtil = new TOTPSecretUtil();
                            String secretKeySpec = rs.getString(1);
                            String encryptedBase32Secret = rs.getString(2);
                            String base32Secret = secretUtil.decrypt(encryptedBase32Secret, secretKeySpec);
                            boolean validCode = TimeBasedOneTimePasswordUtil.validateCurrentNumber(base32Secret, code, 2000);
                            if (validCode) {
                                session.removeAttribute("tempid");
                                session.removeAttribute("tempuser");
                                session.setAttribute("userid", userId);
                                session.setAttribute("username", userName);
                                session.setAttribute("firstname", rs.getString(3));
                                session.setAttribute("lastname", rs.getString(4));
                                session.setAttribute("email", rs.getString(5));
                                session.setAttribute("groupid", rs.getInt(6));
                                session.setAttribute("groupname", rs.getString(7));
                                //setting session to expire in 30 mins
                                //session.setMaxInactiveInterval(30*60);
                                //setting session to expire in 60 seconds
                                session.setMaxInactiveInterval(60);
                                Cookie userCookie = new Cookie("user", userName);
                                //userName.setMaxAge(30*60);
                                userCookie.setMaxAge(60);
                                response.addCookie(userCookie);
                                response.sendRedirect("LoginSuccess.jsp");
                            } else {
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/2falogin.html");
                                PrintWriter writer = response.getWriter();
                                writer.println("<font color=red>Code is not valid!</font>");
                                rd.include(request, response);
                            }
                        }
                    }
                } else {
                    response.sendRedirect("login.html");
                }
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/2falogin.html");
                PrintWriter writer = response.getWriter();
                writer.println("<font color=red>No database connection!</font>");
                rd.include(request, response);
            }
        } catch (SQLException | GeneralSecurityException ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        } catch (NumberFormatException ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/2falogin.html");
            PrintWriter writer = response.getWriter();
            writer.println("<font color=red>Not a valid 6 digit number code!</font>");
            rd.include(request, response);
        }
    }
}
