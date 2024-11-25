<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

String msg = request.getParameter("message") != null ? request.getParameter("message") : "";
String sessionExpired = request.getParameter("sessionExpired") != null ? "Session expired, please log in again." : "";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login Page</title>
<style>
body {
	margin: 0;
	padding: 0;
	height: 100%;
	background-image: url('images/loginbg.png');
	background-size: cover;
	background-position: center 20%;
	font-family: Arial, sans-serif;
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
}

.container {
	max-width: 280px;
	margin: 0 auto;
	background-color: rgba(255, 255, 255, 0.9);
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0px 6px 16px rgba(0, 0, 0, 0.3);
	display: flex;
	flex-direction: column;
	margin-top: -130px;
}

.logo {
	display: block;
	margin: 0 auto 20px;
	width: 250px;
	height: auto;
}

label {
	font-size: 1em;
	margin-bottom: 8px;
	color: #555;
	display: block;
}

input[type="text"] {
	width: 100%;
	padding: 12px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 1em;
	box-sizing: border-box;
}

button[type="submit"] {
	width: 100%;
	padding: 12px;
	background-color: #000080;
	color: white;
	border: none;
	border-radius: 4px;
	font-size: 1.1em;
	cursor: pointer;
	transition: background-color 0.3s;
	box-sizing: border-box;
}

button[type="submit"]:hover {
	background-color: white;
	color: #000080;
	border: 2px solid #000080;
}

@media ( max-width : 600px) {
	.container {
		margin-top: 100px;
		padding: 15px;
	}
	label, input[type="text"], button[type="submit"] {
		font-size: 1em;
	}
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
        document.addEventListener("DOMContentLoaded", function () {
            const form = document.querySelector("form");
 
            form.addEventListener("submit", function (event) {
                const username = document.getElementById("username").value.trim();
                const password = document.getElementById("password").value.trim();
 
                document.querySelectorAll(".error-message").forEach(el => el.remove());
 
                let isValid = true;
 
                if (!username) {
                    showError("username", "Enter your username.");
                    isValid = false;
                }
                if (!password) {
                    showError("password", "Enter your password.");
                    isValid = false;
                }
 
                if (!isValid) {
                    event.preventDefault();
                }
            });
 
            function showError(id, message) {
                const element = document.getElementById(id);
                const error = document.createElement("div");
                error.className = "error-message";
                error.style.color = "red";
                error.style.fontSize = "12px";
                error.textContent = message;
                element.parentNode.appendChild(error);
            }
        });
        function noBack() {
            window.history.forward();
        }
 
    </script>


</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();">



	<div class="container">
		<%
		String message = (String) request.getAttribute("message");
		%>
		<%
		if (message != null) {
		%>
		<div id="message"><%=message%></div>
		<%
		session.removeAttribute("message");
		%>
		<%
		}
		%>
		<img src="images/logo.png" alt="Logo" class="logo">

		<form action="./controller?action=login" method="post" novalidate>
			<label for="username">Enter your username:</label> <input type="text"
				id="username" name="username"><br> <label
				for="password">Enter your password:</label> <input type="text"
				id="password" name="password"><br>

			<button type="submit">Login</button>
		</form>
	</div>

</body>
</html>