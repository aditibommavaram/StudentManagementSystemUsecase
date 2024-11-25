<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Add Student</title>
<style>
body {
	margin: 0;
	padding: 0;
	font-family: Arial, sans-serif;
	background-image: url('images/addstudentbg.jpg');
	background-size: cover;
	background-position: center;
	height: 100vh;
	overflow: hidden;
	display: flex;
	justify-content: flex-start;
	align-items: center;
}

.form-container {
	background-color: rgba(255, 255, 255, 0.9);
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	width: 90%;
	max-width: 500px;
	max-height: 95vh;
	margin-left: 10%;
	overflow-y: auto;
}

h2 {
	text-align: center;
	margin-bottom: 15px;
	font-size: 22px;
}

label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
	font-size: 14px;
}

input, select, textarea {
	width: 100%;
	padding: 8px;
	margin-bottom: 5px;
	border: 1px solid #ccc;
	border-radius: 5px;
	font-size: 14px;
}

.error-message {
	color: red;
	font-size: 12px;
	margin-bottom: 10px;
}

button {
	background-color: #4CAF50;
	color: white;
	font-size: 16px;
	border: none;
	padding: 10px;
	border-radius: 5px;
	cursor: pointer;
	width: 100%;
}

button:hover {
	background-color: #45a049;
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
</style>
<script>
        document.addEventListener("DOMContentLoaded", function () {
            const form = document.querySelector("form");

            const validators = {
                name: value => value.trim() !== "" || "Name cannot be empty.",
                parentName: value => value.trim() !== "" || "Parent name is required.",
                gender: value => value !== "" || "Please select a gender.",
                bloodGroup: value => value !== "" || "Please select a blood group.",
                grade: value => value !== "" || "Grade is mandatory.",
                email: value => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value) || "Please enter a valid email address.",
                mobile: value => /^[0-9]{10}$/.test(value) || "Mobile number must be 10 digits.",
                address: value => value.trim() !== "" || "Address cannot be empty.",
                country: value => value !== "" || "Select a country.",
                state: value => value !== "" || "State selection is required.",
                city: value => value !== "" || "City cannot be left unselected."
            };

            document.querySelectorAll("input, select, textarea").forEach(input => {
                input.addEventListener("blur", event => validateField(event.target));
                input.addEventListener("input", event => clearError(event.target));
            });

            form.addEventListener("submit", function (event) {
                let isValid = true;
                document.querySelectorAll("input, select, textarea").forEach(input => {
                    if (!validateField(input)) {
                        isValid = false;
                    }
                });
                if (!isValid) {
                    event.preventDefault();
                }
            });

            function validateField(input) {
                const value = input.value;
                const id = input.id;
                const validator = validators[id];
                if (validator) {
                    const errorMessage = validator(value);
                    if (errorMessage !== true) {
                        showError(input, errorMessage);
                        return false;
                    } else {
                        clearError(input);
                    }
                }
                return true;
            }

            function showError(input, message) {
                clearError(input);
                const error = document.createElement("div");
                error.className = "error-message";
                error.textContent = message;
                input.parentNode.insertBefore(error, input.nextSibling);
                input.style.borderColor = "red";
            }

            function clearError(input) {
                const error = input.parentNode.querySelector(".error-message");
                if (error) {
                    error.remove();
                }
                input.style.borderColor = "";
            }
        });
    </script>
</head>
<body>
	<div class="button-container">
		<a href="home.jsp" class="home-button">HOME</a> <a
			href="./controller?action=logout" class="home-button">LOGOUT</a>
	</div>
	<div class="form-container">
		<h2>Add Student Details</h2>
		<form action="./controller?action=addStudent" method="post" novalidate>
			<label for="name">Name:</label> <input type="text" id="name"
				name="name" required> <label for="parentName">Parent
				Name:</label> <input type="text" id="parentName" name="parentName" required>
			<label for="gender">Gender:</label> <select id="gender" name="gender"
				required>
				<option value="" disabled selected>Select Gender</option>
				<option value="Male">Male</option>
				<option value="Female">Female</option>
				<option value="Other">Other</option>
			</select> <label for="bloodGroup">Blood Group:</label> <select id="bloodGroup"
				name="bloodGroup" required>
				<option value="" disabled selected>Select Blood Group</option>
				<c:forEach var="bloodGroup" items="${bloodGroups}">
					<option value="${bloodGroup}">${bloodGroup}</option>
				</c:forEach>
			</select> <label for="grade">Grade:</label> <select id="grade" name="grade"
				required>
				<option value="" disabled selected>Select Grade</option>
				<c:forEach var="grade" items="${grades}">
					<option value="${grade}">${grade}</option>
				</c:forEach>
			</select> <label for="email">Email:</label> <input type="email" id="email"
				name="email" required> <label for="mobile">Mobile:</label> <input
				type="tel" id="mobile" name="mobile" pattern="[0-9]{10}" required>
			<label for="address">Address:</label>
			<textarea id="address" name="address" rows="2" required></textarea>
			<label for="country">Country:</label> <select id="country"
				name="country" required>
				<option value="" disabled selected>Select Country</option>
				<c:forEach var="country" items="${countries}">
					<option value="${country}">${country}</option>
				</c:forEach>
			</select> <label for="state">State:</label> <select id="state" name="state"
				required>
				<option value="" disabled selected>Select State</option>
				<c:forEach var="state" items="${states}">
					<option value="${state}">${state}</option>
				</c:forEach>
			</select> <label for="city">City:</label> <select id="city" name="city"
				required>
				<option value="" disabled selected>Select City</option>
				<c:forEach var="city" items="${cities}">
					<option value="${city}">${city}</option>
				</c:forEach>
			</select>
			<button type="submit">Submit</button>
		</form>
	</div>
</body>
</html>
