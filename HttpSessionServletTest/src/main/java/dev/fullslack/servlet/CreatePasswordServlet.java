package dev.fullslack.servlet;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Helper;
import dev.fullslack.db.ConnectionManager;
import dev.fullslack.security.TOTPSecretUtil;
import dev.fullslack.security.TimeBasedOneTimePasswordUtil;
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
import java.sql.SQLException;

@WebServlet("/PasswordServlet")
public class CreatePasswordServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(CreatePasswordServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TOTPSecretUtil secretUtil = new TOTPSecretUtil();
        String pwd = request.getParameter("pass");
        //byte[] pwd = request.getParameter("pass").getBytes(StandardCharsets.UTF_8);
        //byte[] salt = generateSalt();
        //String saltString = bytesToHex(generateSalt());
        String secretKeySpec = secretUtil.createSecretKeySpec();
        String base32Secret = TimeBasedOneTimePasswordUtil.generateBase32Secret(32);
        String encryptedBase32Secret = secretUtil.encrypt(base32Secret, secretKeySpec);
        //byte[] pwdHash = createPasswordHash(pwd,salt);
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String pwdHash = argon2.hash(8, 100 * 1024, 2, pwd);
        String query = "UPDATE user SET password = ?, salt = ?, secret = ? WHERE username = ?";

        /*Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        int iterations1 = Argon2Helper.findIterations(argon2, 1000, 100 * 1024, 2);
        int iterations2 = Argon2Helper.findIterations(argon2, 1000, 50 * 1024, 2);
        int iterations3 = Argon2Helper.findIterations(argon2, 1000, 75 * 1024, 2);

        System.out.println("Optimal number of iterations1 (100MB): " + iterations1);
        System.out.println("Optimal number of iterations2 (50MB): " + iterations2);
        System.out.println("Optimal number of iterations3 (75MB): " + iterations3);*/

        try (Connection con = ConnectionManager.getConnection()) {
            if (con != null) {
                PreparedStatement pst = con.prepareStatement(query);
                //pst.setBytes(1,pwdHash);
                //pst.setBytes(2,salt);
                //pst.setBytes(3, pwd);
                pst.setString(1, pwdHash);
                pst.setString(2, secretKeySpec);
                pst.setString(3, encryptedBase32Secret);
                pst.setString(4, pwd);
                //pwdHash = new byte[0];
                //salt = new byte[0];
                //pwd = new byte[0];
                pst.executeUpdate();
                pst.close();
            }
        } catch (SQLException ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }
    }

    /*private byte[] createPasswordHash(byte[] pwd, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-512");
            digest.reset();
            digest.update(salt);
            byte[] hashbytes = digest.digest(pwd);
            return hashbytes;
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }
        return null;
    }*/

    /*private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
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
