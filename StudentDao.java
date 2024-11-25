package com.training.studentmanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.training.studentmanagement.constants.Constants;
import com.training.studentmanagement.dto.ContactDetails;
import com.training.studentmanagement.dto.Marks;
import com.training.studentmanagement.dto.Student;

public class StudentDao implements IStudentDao {
	public Connection getMyConnection() {
		final String DB_URL = "jdbc:mysql://localhost:3306/trainingdb";
		final String USER = "root";
		final String PASSWORD = "Sranakad@21222609";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(DB_URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, List<String>> getDropDownData() {

		Map<String, List<String>> dropDownData = new HashMap<>();

		Connection conn = null;

		try {
			conn = getMyConnection();

			dropDownData.put("bloodGroups", fetchDropdownData(conn, Constants.DD_BLOOD_GROUP));

			dropDownData.put("grades", fetchDropdownData(conn, Constants.DD_GRADE));

			dropDownData.put("countries", fetchDropdownData(conn, Constants.DD_COUNTRY));

			dropDownData.put("states", fetchDropdownData(conn, Constants.DD_STATE));

			dropDownData.put("cities", fetchDropdownData(conn, Constants.DD_CITY));
			dropDownData.put("subjects", fetchDropdownData(conn, Constants.DD_SUBJECT));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return dropDownData;
	}

	private List<String> fetchDropdownData(Connection conn, String query) throws SQLException {
		List<String> data = new ArrayList<>();

		try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				data.add(rs.getString("Name"));
			}
		}
		return data;
	}

	@Override
	public int addStudent(Student student) {
		int result = 0;

		try (Connection conn = getMyConnection()) {
			conn.setAutoCommit(false);

			try (PreparedStatement psCheckStudentExists = conn.prepareStatement(Constants.CHECK_STUDENT)) {
				psCheckStudentExists.setString(1, student.getName());
				try (ResultSet rs = psCheckStudentExists.executeQuery()) {
					if (rs.next() && rs.getInt(1) > 0) {
						return 0;
					}
				}
			}
			int contactId = -1;
			try (PreparedStatement psContact = conn.prepareStatement(Constants.CONTACT_DETAILS,
					Statement.RETURN_GENERATED_KEYS)) {
				psContact.setLong(1, student.getContact().getMobile());
				psContact.setString(2, student.getContact().getEmail());
				psContact.setString(3, student.getContact().getAddress());
				psContact.setString(4, student.getContact().getCountry());
				psContact.setString(5, student.getContact().getState());
				psContact.setString(6, student.getContact().getCountry());
				psContact.setString(7, student.getContact().getCity());
				psContact.setString(8, student.getContact().getState());

				int contactResult = psContact.executeUpdate();
				if (contactResult > 0) {
					try (ResultSet generatedKeys = psContact.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							contactId = generatedKeys.getInt(1);
						}
					}
				}
			}

			if (contactId == -1) {
				throw new SQLException("Failed to insert contact details.");
			}

			int gradeId = -1;
			try (PreparedStatement psCheckOrInsertGrade = conn.prepareStatement(Constants.CHECK_OR_INSERT)) {
				psCheckOrInsertGrade.setString(1, student.getGrade());
				psCheckOrInsertGrade.setString(2, student.getGrade());
				psCheckOrInsertGrade.executeUpdate();
			}

			try (PreparedStatement psGetGrade = conn.prepareStatement(Constants.GET_GRADE_PK)) {
				psGetGrade.setString(1, student.getGrade());
				try (ResultSet rsGrade = psGetGrade.executeQuery()) {
					if (rsGrade.next()) {
						gradeId = rsGrade.getInt("PK");
					}
				}
			}

			if (gradeId == -1) {
				throw new SQLException("Failed to resolve grade ID.");
			}

			int bloodGroupId = -1;
			try (PreparedStatement psGetBloodGroup = conn.prepareStatement(Constants.GET_BLOOD_GROUP_PK)) {
				psGetBloodGroup.setString(1, student.getBloodGroup());
				try (ResultSet rsBloodGroup = psGetBloodGroup.executeQuery()) {
					if (rsBloodGroup.next()) {
						bloodGroupId = rsBloodGroup.getInt("PK");
					}
				}
			}

			if (bloodGroupId == -1) {
				throw new SQLException("Invalid blood group provided.");
			}

			try (PreparedStatement psStudent = conn.prepareStatement(Constants.INSERT_STUDENT)) {
				psStudent.setString(1, student.getName());
				psStudent.setString(2, student.getParentName());
				psStudent.setInt(3, contactId);
				psStudent.setInt(4, gradeId);
				psStudent.setInt(5, bloodGroupId);
				psStudent.setString(6, student.getGender());

				result = psStudent.executeUpdate();
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}

	@Override
	public Student getStudent(String rollNumber) {

		Student student = null;

		try (Connection connection = getMyConnection()) {
			PreparedStatement studentStmt = connection.prepareStatement(Constants.GET_STUDENT_QUERY);
			studentStmt.setString(1, rollNumber);
			ResultSet studentRs = studentStmt.executeQuery();

			if (studentRs.next()) {
				student = new Student();
				student.setName(studentRs.getString("studentName"));
				student.setGender(studentRs.getString("gender"));
				student.setRollNo(studentRs.getString("ROLL_NO"));
				student.setParentName(studentRs.getString("PARENT_NAME"));
				student.setGrade(studentRs.getString("gradeName"));
				student.setBloodGroup(studentRs.getString("bloodGroup"));

				ContactDetails contact = new ContactDetails();
				contact.setMobile(studentRs.getLong("phone_number"));
				contact.setEmail(studentRs.getString("EMAIL"));
				contact.setAddress(studentRs.getString("ADDRESS"));
				contact.setCountry(studentRs.getString("country"));
				contact.setState(studentRs.getString("state"));
				contact.setCity(studentRs.getString("city"));
				student.setContact(contact);

				int studentId = studentRs.getInt("studentId");
				PreparedStatement marksStmt = connection.prepareStatement(Constants.GET_MARKS_QUERY);
				marksStmt.setInt(1, studentId);
				ResultSet marksRs = marksStmt.executeQuery();

				List<Marks> marksList = new ArrayList<>();
				while (marksRs.next()) {
					Marks marks = new Marks();
					marks.setValue(marksRs.getInt("markValue"));
					marks.setSubject(marksRs.getString("subjectName"));
					marksList.add(marks);
				}
				student.setMarks(marksList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return student;
	}

	@Override
	public List<Student> listStudents() {

		List<Student> students = new ArrayList<>();

		try (Connection connection = getMyConnection()) {
			PreparedStatement studentStmt = connection.prepareStatement(Constants.LIST_STUDENTS_QUERY);
			ResultSet studentRs = studentStmt.executeQuery();

			while (studentRs.next()) {
				Student student = new Student();
				student.setName(studentRs.getString("studentName"));
				student.setGender(studentRs.getString("gender"));
				student.setRollNo(studentRs.getString("ROLL_NO"));
				student.setParentName(studentRs.getString("PARENT_NAME"));
				student.setGrade(studentRs.getString("gradeName"));
				student.setBloodGroup(studentRs.getString("bloodGroup"));

				Map<Integer, Double> percentageMap = getAllStudentsPercentage();
				double percentage = percentageMap.getOrDefault(studentRs.getInt("studentid"), 0.0);
				String result = percentage >= 40 ? "Pass" : "Fail";
				student.setPercentage(percentage);
				student.setResult(result);

				ContactDetails contact = new ContactDetails();
				contact.setMobile(studentRs.getLong("phone_number"));
				contact.setEmail(studentRs.getString("EMAIL"));
				contact.setAddress(studentRs.getString("ADDRESS"));
				contact.setCountry(studentRs.getString("country"));
				contact.setState(studentRs.getString("state"));
				contact.setCity(studentRs.getString("city"));
				student.setContact(contact);

				int studentId = studentRs.getInt("studentId");
				PreparedStatement marksStmt = connection.prepareStatement(Constants.LIST_MARKS_QUERY);
				marksStmt.setInt(1, studentId);
				ResultSet marksRs = marksStmt.executeQuery();

				List<Marks> marksList = new ArrayList<>();
				while (marksRs.next()) {
					Marks marks = new Marks();
					marks.setValue(marksRs.getInt("markValue"));
					marks.setSubject(marksRs.getString("subjectName"));
					marksList.add(marks);
				}
				student.setMarks(marksList);
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	@Override
	public int deleteStudent(String rollno) {
		int result = 0;

		try (Connection con = getMyConnection();
				PreparedStatement pstmt = con.prepareStatement(Constants.DELETE_STUDENT)) {
			pstmt.setString(1, rollno);

			result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int updateStudent(Student student) {

		int result = 0;
		try (Connection connection = getMyConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement studentStmt = connection.prepareStatement(Constants.UPDATE_STUDENT);
			studentStmt.setString(1, student.getGrade());
			studentStmt.setString(2, student.getBloodGroup());
			studentStmt.setString(3, student.getContact().getEmail());
			studentStmt.setString(4, student.getName());
			studentStmt.setString(5, student.getGender());
			studentStmt.setString(6, student.getParentName());
			studentStmt.setString(7, student.getRollNo());
			result += studentStmt.executeUpdate();
			PreparedStatement contactStmt = connection.prepareStatement(Constants.UPDATE_CONTACT);
			contactStmt.setString(1, student.getContact().getCountry());
			contactStmt.setString(2, student.getContact().getState());
			contactStmt.setString(3, student.getContact().getCity());
			contactStmt.setLong(4, student.getContact().getMobile());
			contactStmt.setString(5, student.getContact().getAddress());
			contactStmt.setString(6, student.getRollNo());
			result += contactStmt.executeUpdate();
			PreparedStatement deleteMarksStmt = connection.prepareStatement(Constants.DELETE_MARKS);
			deleteMarksStmt.setString(1, student.getRollNo());
			result += deleteMarksStmt.executeUpdate();
			PreparedStatement insertMarksStmt = connection.prepareStatement(Constants.INSERT_MARKS);
			for (Marks mark : student.getMarks()) {
				insertMarksStmt.setString(1, student.getRollNo());
				insertMarksStmt.setString(2, mark.getSubject());
				insertMarksStmt.setInt(3, mark.getValue());
				result += insertMarksStmt.executeUpdate();
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try (Connection connection = getMyConnection()) {
				connection.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public Map<Integer, Double> getAllStudentsPercentage() {

		Map<Integer, Double> studentPercentageMap = new HashMap<>();

		try (Connection connection = getMyConnection();
				PreparedStatement ps = connection.prepareStatement(Constants.PERCENTAGE);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int fkStudent = rs.getInt("FK_STUDENT");
				double percentage = rs.getDouble("Percentage");
				studentPercentageMap.put(fkStudent, percentage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studentPercentageMap;
	}

	@Override
	public List<Student> filterStudent(String criteria, String option) {
		String studentQuery = null;
		if (criteria != "result") {
			studentQuery = "SELECT s.PK AS studentId, s.NAME AS studentName, s.gender, s.ROLL_NO, s.PARENT_NAME, "
					+ "g.NAME AS gradeName, bg.NAME AS bloodGroup, cd.phone_number, cd.EMAIL, cd.ADDRESS, "
					+ "c.NAME AS country, st.NAME AS state, ct.NAME AS city " + "FROM student s "
					+ "LEFT JOIN contactdetails cd ON s.FK_CD = cd.PK "
					+ "LEFT JOIN bloodgroup bg ON s.FK_BLOOD_GROUP = bg.PK " + "LEFT JOIN grade g ON s.FK_GRADE = g.PK "
					+ "LEFT JOIN country c ON cd.FK_COUNTRY = c.PK " + "LEFT JOIN state st ON cd.FK_STATE = st.PK "
					+ "LEFT JOIN city ct ON cd.FK_CITY = ct.PK " + "WHERE " + criteria + " = ?";
			System.out.println(criteria);
		} else {
			studentQuery = "SELECT s.PK AS studentId, s.NAME AS studentName, s.gender, s.ROLL_NO, s.PARENT_NAME, "
					+ "g.NAME AS gradeName, bg.NAME AS bloodGroup, cd.phone_number, cd.EMAIL, cd.ADDRESS, "
					+ "c.NAME AS country, st.NAME AS state, ct.NAME AS city " + "FROM student s "
					+ "LEFT JOIN contactdetails cd ON s.FK_CD = cd.PK "
					+ "LEFT JOIN bloodgroup bg ON s.FK_BLOOD_GROUP = bg.PK " + "LEFT JOIN grade g ON s.FK_GRADE = g.PK "
					+ "LEFT JOIN country c ON cd.FK_COUNTRY = c.PK " + "LEFT JOIN state st ON cd.FK_STATE = st.PK "
					+ "LEFT JOIN city ct ON cd.FK_CITY = ct.PK ";
		}

		List<Student> students = new ArrayList<>();

		try (Connection connection = getMyConnection()) {
			PreparedStatement studentStmt = connection.prepareStatement(studentQuery);
			if (criteria != "result") {
				studentStmt.setString(1, option);
			}
			ResultSet studentRs = studentStmt.executeQuery();

			while (studentRs.next()) {
				Student student = new Student();
				student.setName(studentRs.getString("studentName"));
				student.setGender(studentRs.getString("gender"));
				student.setRollNo(studentRs.getString("ROLL_NO"));
				student.setParentName(studentRs.getString("PARENT_NAME"));
				student.setGrade(studentRs.getString("gradeName"));
				student.setBloodGroup(studentRs.getString("bloodGroup"));

				Map<Integer, Double> percentageMap = getAllStudentsPercentage();
				double percentage = percentageMap.getOrDefault(studentRs.getInt("studentId"), 0.0);
				String result = percentage >= 40 ? "Pass" : "Fail";
				student.setPercentage(percentage);
				student.setResult(result);

				ContactDetails contact = new ContactDetails();
				contact.setMobile(studentRs.getLong("phone_number"));
				contact.setEmail(studentRs.getString("EMAIL"));
				contact.setAddress(studentRs.getString("ADDRESS"));
				contact.setCountry(studentRs.getString("country"));
				contact.setState(studentRs.getString("state"));
				contact.setCity(studentRs.getString("city"));
				student.setContact(contact);

				int studentId = studentRs.getInt("studentId");
				PreparedStatement marksStmt = connection.prepareStatement(Constants.FILTER_MARKS);
				marksStmt.setInt(1, studentId);
				ResultSet marksRs = marksStmt.executeQuery();

				List<Marks> marksList = new ArrayList<>();
				while (marksRs.next()) {
					Marks marks = new Marks();
					marks.setValue(marksRs.getInt("markValue"));
					marks.setSubject(marksRs.getString("subjectName"));
					marksList.add(marks);
				}
				student.setMarks(marksList);
				if (criteria != "result") {
					students.add(student);
				}
				if (student.getResult().equals(option)) {
					students.add(student);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

}
