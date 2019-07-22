package dev.fullslack.security;

import dev.fullslack.db.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActiveUserValidator {
    private static final Logger LOGGER = LogManager.getLogger(ActiveUserValidator.class.getName());

    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ActiveUserValidator(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        session = request.getSession(false);
    }

    public boolean isUserActive() {
        boolean userActive = false;
        String query = "SELECT active FROM user WHERE id = ? AND username = ?";
        try (Connection con = ConnectionManager.getConnection()) {
            if (con != null) {
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, (int) session.getAttribute("userid"));
                pst.setString(2, (String) session.getAttribute("username"));
                ResultSet rs = pst.executeQuery();
                int rowCount = 0;
                if (rs != null) {
                    if (rs.last()) {
                        rowCount = rs.getRow();
                        rs.beforeFirst();
                        if (rowCount == 1) {
                            if (rs.next()) {
                                userActive = rs.getBoolean(1);
                            }
                        } else {
                            invalidateSession(HttpServletResponse.SC_UNAUTHORIZED); //HTTP 401
                        }
                    } else {
                        invalidateSession(HttpServletResponse.SC_CONFLICT); //HTTP 409
                    }
                } else {
                    invalidateSession(HttpServletResponse.SC_BAD_GATEWAY); //HTTP 502
                }
                rs.close();
                pst.close();
            } else {
                invalidateSession(HttpServletResponse.SC_SERVICE_UNAVAILABLE); //HTTP 503
            }
        } catch (SQLException ex) {
            LOGGER.error("Error Message Logged !!!", ex.getMessage(), ex);
        }
        return userActive;
    }

    private void invalidateSession(int httpStatus) {
        if (request.isRequestedSessionIdValid()) {
            session.invalidate();
        }
        request.getSession(true);
        response.setStatus(httpStatus);
    }
}
