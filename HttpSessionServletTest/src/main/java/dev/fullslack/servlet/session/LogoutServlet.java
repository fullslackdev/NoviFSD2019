package dev.fullslack.servlet.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/*@WebServlet(
        name = "LogoutServlet",
        urlPatterns = "/LogoutServlet"
)*/
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); //invalidate current session
            request.getSession(true); //create new session ID to prevent hijacking
            writer.println("Session invalidate<br>");
        }
        //deletes (set time=0) cookies if exists
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                writer.println("Cookie: "+ cookie.getName() +"<br>");
                if (cookie.getName().equals("user")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
                /*if (cookie.getName().equals("JSESSIONID")) {
                    //cookie.setMaxAge(0);
                    //response.addCookie(cookie);
                    Cookie cookie1 = new Cookie(cookie.getName(), "");
                    cookie1.setMaxAge(0);
                    cookie1.setValue(" ");
                    cookie1.setPath(request.getServletContext().getContextPath());
                    cookie1.setSecure(request.isSecure());
                    cookie1.setDomain(request.getServerName());
                    writer.println(request.getServletContext().getContextPath() + "<br>");
                    writer.println(request.getServerName() + "<br>");
                    response.addCookie(cookie1);
                }*/
            }
        }
        response.sendRedirect("login.html");
    }
}
