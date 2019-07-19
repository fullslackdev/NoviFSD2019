<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="false" %>

<%
//allow access only if session exists
String firstName = null;
String lastName = null;
String email = null;
String groupName = null;
int userID = 0;
int groupID = 0;
HttpSession session = request.getSession(false);
Cookie[] cookies = request.getCookies();
if (session != null) {
    if (session.getAttribute("firstname") == null) {
        session.invalidate();
        request.getSession(true);
        response.sendRedirect("login.html");
    } else {
        firstName = (String) session.getAttribute("firstname");
        lastName = (String) session.getAttribute("lastname");
        email = (String) session.getAttribute("email");
        groupName = (String) session.getAttribute("groupname");
        userID = (int) session.getAttribute("userid");
        groupID = (int) session.getAttribute("groupid");
    }
} else {
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}
    response.sendRedirect("login.html");
}
String userName = null;
String sessionID = null;
int cookieCounter = 0;
boolean isValid = true;
if (cookies != null && session != null) {
	if (cookies.length == 2) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("ID")) {
				sessionID = cookie.getValue();
				cookieCounter++;
			}
			if (cookie.getName().equals("user")) {
				if (cookie.getValue().equals(session.getAttribute("username"))) {
					cookie.setMaxAge(60);
					response.addCookie(cookie);
					userName = cookie.getValue();
					cookieCounter++;
				} else {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					if (isValid) {
						session.invalidate();
                        request.getSession(true);
						isValid = false;
						response.sendRedirect("login.html");
					}
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
		if (isValid) {
			session.invalidate();
            request.getSession(true);
			isValid = false;
			response.sendRedirect("cookie.html");
		}
	}
	if (cookieCounter != 2) {
		for (Cookie cookie : cookies) {
			if (!cookie.getName().equals("ID")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		if (isValid) {
			session.invalidate();
            request.getSession(true);
			isValid = false;
			response.sendRedirect("cookie.html");
		}
	}
}
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Login Success Page</title>
</head>

<body>
    <h3>Hi <%=firstName%> <%=lastName%>, Login successful. Your Session ID=<%=sessionID%></h3>
    <ul>
    <li>Username: <%=userName%></li>
    <li>First name: <%=firstName%></li>
    <li>Last name: <%=lastName%></li>
    <li>Email: <%=email%></li>
    <li>User ID: <%=userID%></li>
    <li>Group ID: <%=groupID%></li>
    <li>Group name: <%=groupName%></li>
    </ul>
    <a href="CheckoutPage.jsp">Checkout Page</a>
    <form action="LogoutServlet" method="post">
        <input type="submit" value="Logout">
    </form>
</body>

</html>