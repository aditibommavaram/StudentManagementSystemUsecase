package com.training.studentmanagement.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.training.studentmanagement.dto.ContactDetails;
import com.training.studentmanagement.dto.Marks;
import com.training.studentmanagement.dto.Student;
import com.training.studentmanagement.services.IStudentServices;
import com.training.studentmanagement.services.StudentServices;

/**
 * Servlet implementation class Controller
 */

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IStudentServices iservices = new StudentServices();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equals("dropDownData")) {
			try {
				Map<String, List<String>> dropDownData = iservices.getDropDownData();

				if (dropDownData == null || dropDownData.isEmpty()) {
					request.setAttribute("message", "No dropdown data found.");
					request.getRequestDispatcher("home.jsp").forward(request, response);
				} else {
					request.setAttribute("bloodGroups", dropDownData.get("bloodGroups"));
					request.setAttribute("grades", dropDownData.get("grades"));
					request.setAttribute("countries", dropDownData.get("countries"));
					request.setAttribute("states", dropDownData.get("states"));
					request.setAttribute("cities", dropDownData.get("cities"));

					request.getRequestDispatcher("addStudent.jsp").forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", "Error retrieving dropdown data: " + e.getMessage());
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}
		}
		if (action.equals("addStudent")) {
			String name = request.getParameter("name");
			String parentName = request.getParameter("parentName");
			String gender = request.getParameter("gender");
			String bloodGroup = request.getParameter("bloodGroup");
			String grade = request.getParameter("grade");
			String email = request.getParameter("email");
			String mobile = request.getParameter("mobile");
			String address = request.getParameter("address");
			String country = request.getParameter("country");
			String state = request.getParameter("state");
			String city = request.getParameter("city");

			try {
				ContactDetails contact = new ContactDetails(Long.parseLong(mobile), email, address, country, state,
						city);

				Student student = new Student(name, gender, contact, bloodGroup, grade, parentName);

				IStudentServices studentServices = new StudentServices();
				int result = studentServices.addStudent(student);

				if (result > 0) {
					request.setAttribute("message", "Successful to add student.");
					response.sendRedirect("home.jsp");
				} else {
					request.setAttribute("message", "Failed to add student.");
					request.getRequestDispatcher("home.jsp").forward(request, response);
				}

			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", "Error processing student data: " + e.getMessage());
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}
		}
		if (action.equals("getStudent")) {
			Map<String, List<String>> dropDownData = iservices.getDropDownData();

			if (dropDownData == null || dropDownData.isEmpty()) {
				request.setAttribute("message", "No dropdown data found.");
				request.getRequestDispatcher("home.jsp").forward(request, response);
			} else {
				request.setAttribute("bloodGroups", dropDownData.get("bloodGroups"));
				request.setAttribute("grades", dropDownData.get("grades"));
				request.setAttribute("countries", dropDownData.get("countries"));
				request.setAttribute("states", dropDownData.get("states"));
				request.setAttribute("cities", dropDownData.get("cities"));
				request.setAttribute("subjects", dropDownData.get("subjects"));

			}
			String rollNumber = request.getParameter("selectedStudent");

			if (rollNumber == null || rollNumber.trim().isEmpty()) {
				request.setAttribute("message", "Roll number is required.");
				request.getRequestDispatcher("listStudent.jsp").forward(request, response);
				return;
			}

			try {

				Student student = iservices.getStudent(rollNumber);
				if (student == null) {
					request.setAttribute("message", "No student found with roll number: " + rollNumber);
					request.getRequestDispatcher("listPage.jsp").forward(request, response);
				} else {
					request.setAttribute("student", student);
					request.getRequestDispatcher("updateStudent.jsp").forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", "An error occurred while retrieving the student.");
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}
		}

		if (action.equals("listStudents")) {
			Map<String, List<String>> dropDownData = iservices.getDropDownData();

			if (dropDownData == null || dropDownData.isEmpty()) {
				request.setAttribute("message", "No dropdown data found.");
				request.getRequestDispatcher("home.jsp").forward(request, response);
			} else {
				request.setAttribute("bloodGroups", dropDownData.get("bloodGroups"));
				request.setAttribute("grades", dropDownData.get("grades"));
				request.setAttribute("countries", dropDownData.get("countries"));
				request.setAttribute("states", dropDownData.get("states"));
				request.setAttribute("cities", dropDownData.get("cities"));

			}
			List<Student> students = iservices.listStudents();

			if (students != null && !students.isEmpty()) {
				request.setAttribute("students", students);
				request.getRequestDispatcher("listStudent.jsp").include(request, response);
			} else {
				request.setAttribute("message", "An uexpected error in retrieval!");
				request.getRequestDispatcher("home.jsp").include(request, response);

			}

		}

		if (action.equals("deleteStudent")) {
			String rollno = request.getParameter("selectedStudent");
			if (rollno == null || rollno.trim().isEmpty()) {
		        request.setAttribute("message", "Roll number is required.");
		        request.getRequestDispatcher("listStudent.jsp").forward(request, response);
		        return;
		    }
			System.out.println(rollno);
			int result = iservices.deleteStudent(rollno);
			if (result > 0) {
				request.setAttribute("message", "Student details deleted successfully!");
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}
			request.setAttribute("message", "Student details deleted failure!");
		}

		if (action.equals("updateStudent")) {
			try {
				String name = request.getParameter("name");
				String rollNo = request.getParameter("rollNo");
				String parentName = request.getParameter("parentName");
				String gender = request.getParameter("gender");
				String bloodGroup = request.getParameter("bloodGroup");
				String grade = request.getParameter("grade");
				String email = request.getParameter("email");
				Long mobile = Long.parseLong(request.getParameter("mobile"));
				String address = request.getParameter("address");
				String country = request.getParameter("country");
				String state = request.getParameter("state");
				String city = request.getParameter("city");

				List<Marks> marksList = new ArrayList<>();

				for (String parameterName : request.getParameterMap().keySet()) {
					if (parameterName.startsWith("marks[")) {
						String subjectName = parameterName.substring(6, parameterName.length() - 1); // Extract the
																										// subject name
						Integer markValue = Integer.parseInt(request.getParameter(parameterName));
						Marks mark = new Marks(markValue, subjectName);
						marksList.add(mark);
					}
				}

				ContactDetails contact = new ContactDetails(mobile, email, address, country, state, city);
				Student student = new Student(name, rollNo, parentName, gender, bloodGroup, grade, contact, marksList);
				int res = iservices.updateStudent(student);

				if (res > 0) {
					request.setAttribute("message", "Student details updated successfully!");
				} else {
					request.setAttribute("message", "Failed to update student details.");
				}
				request.getRequestDispatcher("home.jsp").forward(request, response);

			} catch (NumberFormatException e) {
				request.setAttribute("message", "Invalid input format for mobile or marks.");
				request.getRequestDispatcher("home.jsp").forward(request, response);
			} catch (Exception e) {
				request.setAttribute("message", "An unexpected error occurred.");
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}
		}
		if (action.equals("login")) {
			String uname = request.getParameter("username");
			String pwd = request.getParameter("password");

			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);

			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}

			session = request.getSession(true);
			session.setMaxInactiveInterval(15 * 60);

			if (uname.equals(pwd)) {
				session.setAttribute("username", uname);

				Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
				sessionCookie.setHttpOnly(true);
				sessionCookie.setSecure(request.isSecure());
				response.addCookie(sessionCookie);

				request.getRequestDispatcher("home.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "Invalid credentials");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}
		if (action.equals("logout")) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}

			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (javax.servlet.http.Cookie cookie : cookies) {
					if (cookie.getName().equals("JSESSIONID")) {
						cookie.setMaxAge(0);
						cookie.setValue(null);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
			}
			request.setAttribute("message", "successful logout.");
			request.getRequestDispatcher("index.jsp").forward(request, response);

		}
		if (action.equals("filter")) {
			String criteria = null;
			String option = null;

			if (request.getParameter("gender") != null && !request.getParameter("gender").isEmpty()) {
				criteria = "s.gender";
				option = request.getParameter("gender");
			} else if (request.getParameter("bloodGroup") != null && !request.getParameter("bloodGroup").isEmpty()) {
				criteria = "bg.NAME";
				option = request.getParameter("bloodGroup");
			} else if (request.getParameter("result") != null && !request.getParameter("result").isEmpty()) {
				criteria = "result";
				option = request.getParameter("result");
			} else if (request.getParameter("grade") != null && !request.getParameter("grade").isEmpty()) {
				criteria = "g.NAME";
				option = request.getParameter("grade");
			}
			try {
				if (criteria != null && option != null) {
					List<Student> filteredStudents = iservices.filterStudent(criteria, option);

					HttpSession session = request.getSession();
					session.setAttribute("students", filteredStudents);
					request.getRequestDispatcher("filterStudent.jsp").forward(request, response);
				} else {
					request.setAttribute("message", "No valid filter selected. Please select a filter option.");
					request.getRequestDispatcher("listStudent.jsp").forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", "An error occurred while filtering students.");
				request.getRequestDispatcher("listStudent.jsp").forward(request, response);
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
