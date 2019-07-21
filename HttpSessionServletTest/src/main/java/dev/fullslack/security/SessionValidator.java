package dev.fullslack.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionValidator {

    private HttpSession session;
    private Cookie[] cookies;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public SessionValidator(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        session = request.getSession(false);
        cookies = request.getCookies();
    }

    public HttpSession getSession() {
        return session;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public boolean isValidSession() throws IOException {
        if (session != null) {
            if (session.getAttribute("firstname") == null) {
                invalidateSession("login.html");
                return false;
            } else {
                ActiveUserValidator validator = new ActiveUserValidator(request, response);
                if (validator.isUserActive()) {
                    return true;
                } else {
                    invalidateSession("login.html");
                    return false;
                }
            }
        } else {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
            response.sendRedirect("login.html");
            return false;
        }
    }

    public boolean isValidCookies() throws IOException {
        if (cookies != null) {
            int cookieCounter = 0;
            int sessionCookieCounter = 0;
            if (cookies.length == 2) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("ID")) {
                        if (cookie.getValue().equals(session.getId())) {
                            cookieCounter++;
                            sessionCookieCounter++;
                        } else {
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                            invalidateSession("cookie.html");
                            return false;
                        }
                    }
                    if (cookie.getName().equals("user")) {
                        if (cookie.getValue().equals(session.getAttribute("username"))) {
                            cookie.setMaxAge(60);
                            response.addCookie(cookie);
                            cookieCounter++;
                        } else {
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                            invalidateSession("login.html");
                            return false;
                        }
                    }
                }
            } else {
                for (Cookie cookie : cookies) {
                    if (!cookie.getName().equals("ID")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
                invalidateSession("cookie.html");
                return false;
            }
            if ((cookieCounter == 2) && (sessionCookieCounter == 1)) {
                return true;
            } else {
                for (Cookie cookie : cookies) { // code block not working for session cookies
                    cookie.setMaxAge(0);
                    cookie.setValue("");
                    cookie.setPath(request.getContextPath()+"/");
                    response.addCookie(cookie);
                }
                invalidateSession("cookie.html");
                return false;
            }
        }
        return false;
    }

    private void invalidateSession(String redirect) throws IOException {
        if (request.isRequestedSessionIdValid()) {
            session.invalidate();
        }
        request.getSession(true);
        response.sendRedirect(redirect);
    }
}
