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
<title>Update Student</title>
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

.error-message {
	color: red;
	font-size: 12px;
	margin-top: 5px;
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
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 5px;
	font-size: 14px;
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
        const marksInputs = document.querySelectorAll("[id^='marks_']");
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const mobileRegex = /^[0-9]{10}$/;
 
        function showError(id, message) {
            const element = document.getElementById(id);
            const error = document.createElement("div");
            error.className = "error-message";
            error.style.color = "red";
            error.style.fontSize = "12px";
            error.textContent = message;
            element.parentNode.appendChild(error);
        }
 
        function validateMarks() {
            let isValid = true;
            marksInputs.forEach(input => {
                const marks = parseInt(input.value, 10);
                if (isNaN(marks) || marks < 0 || marks > 100) {
                    showError(input.id, "Marks must be between 0 and 100.");
                    isValid = false;
                }
            });
            return isValid;
        }
 
        function validateForm() {
            let isValid = true;
            document.querySelectorAll(".error-message").forEach(el => el.remove());
 
            const fieldsToValidate = [
                { id: "name", message: "Name cannot be empty." },
                { id: "parentName", message: "Parent name is required." },
                { id: "gender", message: "Please select a gender." },
                { id: "bloodGroup", message: "Please select a blood group." },
                { id: "grade", message: "Grade is mandatory." },
                { id: "email", message: "Please enter a valid email address.", regex: emailRegex },
                { id: "mobile", message: "Mobile number must be 10 digits.", regex: mobileRegex },
                { id: "address", message: "Address cannot be empty." },
                { id: "country", message: "Select a country." },
                { id: "state", message: "State selection is required." },
                { id: "city", message: "City cannot be left unselected." },
            ];
 
            fieldsToValidate.forEach(field => {
                const element = document.getElementById(field.id);
                const value = element.value.trim();
                if (!value || (field.regex && !field.regex.test(value))) {
                    showError(field.id, field.message);
                    isValid = false;
                }
            });
 
            if (!validateMarks()) {
                isValid = false;
            }
 
            return isValid;
        }
 
        marksInputs.forEach(input => {
            input.addEventListener("input", function () {
                const value = parseInt(input.value, 10);
                if (value > 100) input.value = 100;
                else if (value < 0) input.value = 0;
            });
        });
 
        form.addEventListener("submit", function (event) {
            if (!validateForm()) {
                event.preventDefault();
            }
        });
    });
 
  
</script>
</head>
<body>
	<div class="button-container">
		<a href="home.jsp" class="home-button">HOME</a> <a
			href="./controller?action=logout" class="home-button">LOGOUT</a>
	</div>
	<div class="form-container">
		<h2>Update Student Details</h2>
		<form
			action="./controller?action=updateStudent&rollNo=${student.rollNo}"
			method="post" novalidate>
			<label for="name">Name:</label> <input type="text" id="name"
				name="name" value="${student.name}" required> <label
				for="parentName">Parent Name:</label> <input type="text"
				id="parentName" name="parentName" value="${student.parentName}"
				required> <label for="gender">Gender:</label> <select
				id="gender" name="gender" required>
				<option value="" disabled>Select Gender</option>
				<option value="Male" ${student.gender == 'Male' ? 'selected' : ''}>Male</option>
				<option value="Female"
					${student.gender == 'Female' ? 'selected' : ''}>Female</option>
				<option value="Other" ${student.gender == 'Other' ? 'selected' : ''}>Other</option>
			</select> <label for="bloodGroup">Blood Group:</label> <select id="bloodGroup"
				name="bloodGroup" required>
				<option value="" disabled>Select Blood Group</option>
				<c:forEach var="bloodGroup" items="${bloodGroups}">
					<option value="${bloodGroup}"
						${student.bloodGroup == bloodGroup ? 'selected' : ''}>${bloodGroup}</option>
				</c:forEach>
			</select> <label for="grade">Grade:</label> <select id="grade" name="grade"
				required>
				<option value="" disabled>Select Grade</option>
				<c:forEach var="grade" items="${grades}">
					<option value="${grade}"
						${student.grade == grade ? 'selected' : ''}>${grade}</option>
				</c:forEach>
			</select> <label for="email">Email:</label> <input type="email" id="email"
				name="email" value="${student.contact.email}" required> <label
				for="mobile">Mobile:</label> <input type="tel" id="mobile"
				name="mobile" pattern="[0-9]{10}" value="${student.contact.mobile}"
				required> <label for="address">Address:</label>
			<textarea id="address" name="address" rows="3" required>${student.contact.address}</textarea>

			<label for="country">Country:</label> <select id="country"
				name="country" required>
				<option value="" disabled>Select Country</option>
				<c:forEach var="country" items="${countries}">
					<option value="${country}"
						${student.contact.country == country ? 'selected' : ''}>${country}</option>
				</c:forEach>
			</select> <label for="state">State:</label> <select id="state" name="state"
				required>
				<option value="" disabled>Select State</option>
				<c:forEach var="state" items="${states}">
					<option value="${state}"
						${student.contact.state == state ? 'selected' : ''}>${state}</option>
				</c:forEach>
			</select> <label for="city">City:</label> <select id="city" name="city"
				required>
				<option value="" disabled>Select City</option>
				<c:forEach var="city" items="${cities}">
					<option value="${city}"
						${student.contact.city == city ? 'selected' : ''}>${city}</option>
				</c:forEach>
			</select>

			<c:forEach var="subject" items="${subjects}">
				<label for="marks_${subject}">${subject}:</label>
				<c:set var="markValue" value="0" />
				<c:forEach var="mark" items="${student.marks}">
					<c:if test="${mark.subject == subject}">
						<c:set var="markValue" value="${mark.value}" />
					</c:if>
				</c:forEach>
				<input type="number" id="marks_${subject}" name="marks[${subject}]"
					value="${markValue}" min="0" max="100" required>
			</c:forEach>

			<button type="submit">Update</button>
		</form>
	</div>
</body>
</html>