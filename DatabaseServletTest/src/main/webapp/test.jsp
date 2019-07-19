<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="EN">

<head>
    <meta charset="utf-8">
    <title>AJAX Database Test</title>
    <script>
        function ajaxAsyncRequest(reqURL) {
            //Creating a new XMLHttpRequest object
            var xmlhttp;
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
            } else {
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
            }
            //Create a asynchronous GET request
            xmlhttp.open("GET", reqURL, true);

            //When readyState is 4 then get the server output
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState == 4) {
                    if (xmlhttp.status == 200) {
                        //document.getElementById("message").innerHTML = xmlhttp.responseText;
                        //alert(xmlhttp.responseText);
                        var jsonObj = JSON.parse(xmlhttp.responseText); //JSON.parse() returns JSON object
                        document.getElementById("name").innerHTML = jsonObj[0].naam;
                        document.getElementById("city").innerHTML = jsonObj[0].woonplaats;
                    } else {
                        alert('Something went wrong !!');
                    }
                }
            };

            xmlhttp.send(null);
        }
    </script>
</head>

<body>
    <br/>
    <input type="button" value="Show Server Time" onclick='ajaxAsyncRequest("DatabaseTestServlet")'/>
    <br/><br/>
    Message from server :: <span id="message"></span><br/>
    Name: <span id="name"></span><br/>
    Time: <span id="city"></span><br/>
</body>

</html>