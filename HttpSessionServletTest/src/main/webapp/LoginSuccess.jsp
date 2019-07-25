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
    <link rel="icon" type="image/png" sizes="192x192" href="images/android-icon-192x192.png">
    <link rel="icon" type="image/png" sizes="32x32" href="images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="96x96" href="images/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <link rel="stylesheet" href="resources/bootstrap.min.css">
	<link rel="stylesheet" href="resources/style.css">
</head>

<body>
<div class="bgMainLogo">
	<nav class="navbar navbar-expand-md bg-dark navbar-dark sticky-top">
		<a class="navbar-brand" href="#">DJ Don Diablo</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<ul class="navbar-nav">
				<li class="nav-item">
					<a class="nav-link disabled" href="#">Registration</a>
				</li>
				<li class="nav-item">
					<a class="nav-link disabled" href="#">Comment</a>
				</li>
				<li class="nav-item">
					<a class="nav-link disabled" href="#">Upload</a>
				</li>
				<li class="nav-item">
					<!--<form id="logout_form" action="LogoutServlet" method="post"><button id="logout_button" class="btn btn-dark rounded-1" type="submit">Logout</button></form>-->
					<a class="nav-link" href="LogoutServlet">Logout</a>
				</li>
			</ul>
		</div>
	</nav>
	
	<div class="container py-5">
		<div class="row">
			<div class="offset-md-2 col-md-8 offset-md-2">
				<div class="card text-white bg-secondary">
					<div class="card-header bg-dark text-white">
						<h4 class="card-title text-uppercase mt-1 mb-1">Welcome <%=firstName%> <%=lastName%></h4>
					</div>
					<div class="card-body">
						<div class="row">
							<div class="col-md-4 col-sm-4 col-xs-12">
								<div class="float-left">
									Login successful.
								</div>
							</div>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<div class="float-right">
									Session ID: <%=sessionID%>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-sm-6 col-xs-12">
								<div class="float-left">
									Username: <%=userName%>
								</div>
							</div>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<div class="float-right">
									Email: <%=email%>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4 col-sm-4 col-xs-12">
								<div class="float-left">
									User ID: <%=userID%>
								</div>
							</div>
							<div class="col-md-4 col-sm-4 col-xs-12">
								<div class="float-center">
									Group ID: <%=groupID%>
								</div>
							</div>
							<div class="col-md-4 col-sm-4 col-xs-12">
								<div class="float-right">
									Group name: <%=groupName%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="resources/jquery.slim.min.js"></script>
<script src="resources/popper.min.js"></script>
<script src="resources/bootstrap.min.js"></script>
<script src="resources/javascript.js"></script>

</body>
</html>