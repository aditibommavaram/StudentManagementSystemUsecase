<%@ page import="java.util.List"%>
<%@ page import="com.training.studentmanagement.dto.Student"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>List of Students</title>
<script>
    function toggleFilter(filterType) {
        const filters = ['gender', 'bloodGroup', 'result', 'grade', 'percentage'];
        filters.forEach(type => {
            if (type !== filterType) {
                const inputs = document.querySelectorAll(`[name="${type}"], [name="${type}Filter"]`);
                inputs.forEach(input => input.disabled = true);
            } else {
                const inputs = document.querySelectorAll(`[name="${type}"], [name="${type}Filter"]`);
                inputs.forEach(input => input.disabled = false);
            }
        });
    }
 
    function resetFilters() {
        const inputs = document.querySelectorAll('select, input[type="number"]');
        inputs.forEach(input => input.disabled = false);
    }
 
        function showPopup(event, studentDetails) {
            const popup = document.getElementById('popup');
            popup.style.display = 'block';
            popup.innerHTML = studentDetails;
            popup.style.left = event.pageX + 'px';
            popup.style.top = event.pageY + 'px';
        }
 
        function hidePopup() {
            const popup = document.getElementById('popup');
            popup.style.display = 'none';
        }
 
        function toggleCheckbox(currentCheckbox) {
            const checkboxes = document.getElementsByName('selectedStudent');
            checkboxes.forEach(function(checkbox) {
                if (checkbox !== currentCheckbox) {
                    checkbox.checked = false;
                }
            });
        }
       
   
        setTimeout(() => {
            const messageElement = document.getElementById("message");
            if (messageElement) {
                messageElement.style.display = "none";
            }
        }, 10000);
        function showFilterPopup() {
            const popup = document.getElementById('filterPopup');
            popup.style.display = 'block';
        }
     
        function hideFilterPopup() {
            const popup = document.getElementById('filterPopup');
            popup.style.display = 'none';
        }
    </script>


<style>
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

body {
	margin: 0;
	padding: 0;
	font-family: Arial, sans-serif;
	background-image: url('images/liststudent.png');
	background-size: cover;
	background-position: center;
	height: 100vh;
	overflow: auto;
	color: #333;
}

h2 {
	text-align: center;
	margin-top: 20px;
	color: #ff6600;
}

#popup {
	display: none;
	position: absolute;
	background-color: #fff7e6;
	border: 1px solid #ff9933;
	padding: 10px;
	border-radius: 5px;
	z-index: 1000;
	font-size: 12px;
}

table {
	width: 90%;
	margin: 20px auto;
	border-collapse: collapse;
	background-color: #fff7e6;
}

thead {
	background-color: #ff9933;
	color: white;
}

thead th {
	padding: 10px;
	text-align: left;
}

tbody tr:nth-child(even) {
	background-color: #ffe6cc;
}

tbody tr:nth-child(odd) {
	background-color: #fff;
}

tbody td {
	padding: 10px;
	text-align: left;
}

tbody tr:hover {
	background-color: #ffcc99;
}

button {
	background-color: #ff6600;
	color: white;
	font-size: 14px;
	border: none;
	padding: 10px 20px;
	border-radius: 5px;
	cursor: pointer;
	margin: 10px 5px;
}

button:hover {
	background-color: #e65c00;
}

form {
	text-align: center;
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
	<button type="button" onclick="showFilterPopup()">Filter
		Students</button>

	<div id="filterPopup"
		style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: #fff7e6; padding: 20px; border: 1px solid #ff9933; border-radius: 5px; z-index: 1000;">
		<h3 style="text-align: center; color: #ff6600;">Filter Options</h3>
		<form action="./controller?action=filter" method="post">
			<div style="margin: 10px 0;">
				<label for="genderFilter"> <input type="radio"
					name="filterType" onclick="toggleFilter('gender')"> Gender:
				</label> <select id="genderFilter" name="gender">
					<option value="">--Select Gender--</option>
					<option value="Male">Male</option>
					<option value="Female">Female</option>
				</select>
			</div>
			<div style="margin: 10px 0;">
				<label for="bloodGroupFilter"> <input type="radio"
					name="filterType" onclick="toggleFilter('bloodGroup')">
					Blood Group:
				</label> <select id="bloodGroupFilter" name="bloodGroup">
					<option value="">Select Blood Group</option>
					<c:forEach var="bloodGroup" items="${bloodGroups}">
						<option value="${bloodGroup}">${bloodGroup}</option>
					</c:forEach>
				</select>
			</div>
			<div style="margin: 10px 0;">
				<label for="resultFilter"> <input type="radio"
					name="filterType" onclick="toggleFilter('result')"> Result:
				</label> <select id="resultFilter" name="result">
					<option value="">--Select Result--</option>
					<option value="Pass">Pass</option>
					<option value="Fail">Fail</option>
				</select>
			</div>
			<div style="margin: 10px 0;">
				<label for="gradeFilter"> <input type="radio"
					name="filterType" onclick="toggleFilter('grade')"> Grade:
				</label> <select id="gradeFilter" name="grade">
					<option value="">Select Grade</option>
					<c:forEach var="grade" items="${grades}">
						<option value="${grade}">${grade}</option>
					</c:forEach>
				</select>
			</div>
			<div style="text-align: center; margin-top: 20px;">
				<button type="submit" name="action" value="applyFilters">Apply
					Filters</button>
				<button type="button" onclick="hideFilterPopup(); resetFilters();">Cancel</button>
			</div>
		</form>
	</div>

	<div class="button-container">
		<a href="home.jsp" class="home-button">HOME</a> <a href="./controller?action=logout"
			class="home-button">LOGOUT</a>
	</div>
	<h2>Student List</h2>
	<div id="popup"></div>
	<form action="./controller" method="post">
		<table border="1">
			<thead>
				<tr>
					<th>Roll No</th>
					<th>Name</th>
					<th>Grade</th>
					<th>Total Percentage</th>
					<th>Result</th>
					<th>Select</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty students}">
					<c:forEach var="student" items="${students}">
						<tr>
							<td><span
								onmouseover="showPopup(event,
                                        'Address: ${student.contact.address}, ' +
                                        '${student.contact.country}, ' +
                                        '${student.contact.state}, ' +
                                        '${student.contact.city}<br/>' +
                                        'Gender: ${student.gender}<br/>'+
                                        'Parent Name: ${student.parentName}<br/>' +
                                        'Mobile: ${student.contact.mobile}<br/>' +
                                        'Email: ${student.contact.email}<br/>' +
                                        'Blood Group: ${student.bloodGroup}')"
								onmouseout="hidePopup()"> ${student.rollNo} </span></td>
							<td>${student.name}</td>
							<td>${student.grade}</td>
							<td><span
								onmouseover="showPopup(event,
                                        'Marks: <br/>' +
                                        '<ul>' +
                                        '<li>Science: ${student.marks[0].value}</li>' +
                                        '<li>English: ${student.marks[1].value}</li>' +
                                        '<li>Social Studies: ${student.marks[2].value}</li>' +
                                        '<li>History: ${student.marks[3].value}</li>' +
                                        '<li>Geography: ${student.marks[4].value}</li>' +
                                        '<li>Physics: ${student.marks[5].value}</li>' +
                                        '<li>Chemistry: ${student.marks[6].value}</li>' +
                                        '<li>Biology: ${student.marks[7].value}</li>' +
                                        '<li>Computer Science: ${student.marks[8].value}</li>' +
                                        '</ul>')"
								onmouseout="hidePopup()"> ${student.percentage}% </span></td>
							<td>${student.result}</td>
							<td><input type="checkbox" name="selectedStudent"
								value="${student.rollNo}" id="checkbox${student.rollNo}"
								onclick="toggleCheckbox(this)"></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty students}">
					<tr>
						<td colspan="6">No students found.</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<button type="submit" name="action" value="getStudent">Edit
			Student</button>
		<button type="submit" name="action" value="deleteStudent">Delete
			Student</button>
	</form>
</body>
</html>