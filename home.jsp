<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
 
request.getSession(false);
if (session == null || session.getAttribute("username") == null) {
	request.setAttribute("message", "successful logout.");
	request.getRequestDispatcher("./controller?action=logout").forward(request, response);
    return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Planon School</title>
<style>
body {
	margin: 0;
	padding: 0;
	font-family: Arial, sans-serif;
	background-image: url('images/homebgbg.png');
	background-size: cover;
	background-position: center;
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
}

.logo {
	position: absolute;
	top: 10px;
	left: 10px;
}

.logo img {
	width: 200px; /* Adjust as needed */
	height: 100px;
}

.image-container {
	text-align: center;
}

.image-container a {
	display: block;
	margin-bottom: 20px;
}

.image-container img {
	width: 300px;
	height: 80px;
	border-radius: 8px;
	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
	cursor: pointer;
	transition: transform 0.2s, box-shadow 0.2s;
}

.image-container img:hover {
	transform: scale(1.05);
	box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.5);
}

.button-container {
	position: absolute;
	top: 10px;
	right: 10px;
	display: flex;
	gap: 10px;
}

.home-button {
	background-color: #4CAF50;
	color: white;
	font-size: 16px;
	border: none;
	padding: 10px 20px;
	border-radius: 5px;
	text-align: center;
	text-decoration: none;
	cursor: pointer;
}

.home-button:hover {
	background-color: #45a049;
}

#message {
	color: red;
	font-size: 16px;
	text-align: center;
	margin: 20px auto;
	background-color: #ffcccc;
	padding: 10px;
	border: 1px solid #ff0000;
	border-radius: 5px;
	width: 80%;
	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
}
</style>
<script>
        setTimeout(() => {
            const messageElement = document.getElementById("message");
            if (messageElement) {
                messageElement.style.display = "none";
            }
        }, 5000);
    </script>
</head>
<body>
	<%
        String message = (String) request.getAttribute("message");
    %>
	<% if (message != null) { %>
	<div id="message"><%= message %></div>
	<%
            session.removeAttribute("message");
        %>
	<% } %>
	<div class="button-container">
		<a href="home.jsp" class="home-button">HOME</a> <a
			href="./controller?action=logout" class="home-button">LOGOUT</a>
	</div>
	<div class="logo">
		<img src="images/logo.png" alt="Planon School Logo">
	</div>

	<div>
		<div class="image-container">
			<a href="./controller?action=listStudents"> <img
				src="images/viewstudents.png" alt="View Students">
			</a>
			<a href="./controller?action=dropDownData"> <img
				src="images/addstudent.png" alt="Add Student">
			</a>
		</div>
	</div>
</body>
</html>
