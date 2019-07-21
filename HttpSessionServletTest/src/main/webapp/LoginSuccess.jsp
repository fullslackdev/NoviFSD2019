<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ page import="dev.fullslack.security.SessionValidator" %>

<%
//allow access only if session exists
String firstName = null;
String lastName = null;
String email = null;
String groupName = null;
int userID = 0;
int groupID = 0;
HttpSession session = null;
Cookie[] cookies = null;
String userName = null;
String sessionID = null;

SessionValidator validator = new SessionValidator(request, response);
if (validator.isValidSession()) {
    if (validator.isValidCookies()) {
        session = validator.getSession();
        cookies = validator.getCookies();
        firstName = (String) session.getAttribute("firstname");
        lastName = (String) session.getAttribute("lastname");
        email = (String) session.getAttribute("email");
        groupName = (String) session.getAttribute("groupname");
        userID = (int) session.getAttribute("userid");
        groupID = (int) session.getAttribute("groupid");
        sessionID = session.getId();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) {
                userName = cookie.getValue();
            }
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