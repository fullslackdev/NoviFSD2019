package dev.fullslack.servlet;

import dev.fullslack.db.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/PasswordServlet")
public class CreatePasswordServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(CreatePasswordServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pwd = request.getParameter("pass");
        byte[] salt = generateSalt();
        byte[] pwdHash = createPasswordHash(pwd,salt);
        String query = "UPDATE user SET password = ?, salt = ? WHERE username = ?";

        try (Connection con = ConnectionManager.getConnection()) {
            if (con != null) {
                PreparedStatement pst = con.prepareStatement(query);
                pst.setBytes(1,pwdHash);
                pst.setBytes(2,salt);
                pst.setString(3, pwd);
                pst.executeUpdate();
                pst.close();
            }
        } catch (SQLException ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }
    }

    private byte[] createPasswordHash(String pwd, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-512");
            digest.reset();
            digest.update(salt);
            byte[] hashbytes = digest.digest(pwd.getBytes(StandardCharsets.UTF_8));
            return hashbytes;
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }
        return null;
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }
}
