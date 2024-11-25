package com.training.studentmanagement.constants;

public class Constants {

	private final String createCountryTable = "create table COUNTRY(PK int primary key auto_increment,NAME varchar(255) not null)\r\n";
	private final String createStateTable = "CREATE TABLE STATE (PK INT AUTO_INCREMENT PRIMARY KEY,NAME VARCHAR(255) NOT NULL,FK_COUNTRY INT NOT NULL,FOREIGN KEY (FK_COUNTRY) REFERENCES COUNTRY(PK)\r\n"
			+ "ON DELETE CASCADE ON UPDATE CASCADE)";
	private final String createCityTable = "CREATE TABLE CITY (\r\n" + "    PK INT AUTO_INCREMENT PRIMARY KEY,\r\n"
			+ "    NAME VARCHAR(255),\r\n" + "    FK_STATE INT,\r\n" + "    FK_COUNTRY INT,\r\n"
			+ "    FOREIGN KEY (FK_STATE) REFERENCES State(PK),\r\n"
			+ "    FOREIGN KEY (FK_COUNTRY) REFERENCES Country(PK)\r\n" + ")";
	private final String createBloodGroupTable = "CREATE TABLE BloodGroup (\r\n"
			+ "    PK INT AUTO_INCREMENT PRIMARY KEY,\r\n" + "    NAME VARCHAR(10) NOT NULL\r\n" + ");";
	private final String createSubjectTable = "CREATE TABLE Subject (\r\n"
			+ "    PK INT AUTO_INCREMENT PRIMARY KEY,\r\n" + "    NAME VARCHAR(50) NOT NULL,\r\n"
			+ "    CODE VARCHAR(10) NOT NULL\r\n" + ")";
	private final String createGradeTable = "CREATE TABLE Grade (\r\n" + "    PK INT AUTO_INCREMENT PRIMARY KEY,\r\n"
			+ "    NAME VARCHAR(20) NOT NULL,\r\n" + "    CODE INT NOT NULL\r\n" + ");";
	private final String createContactDetailsTable = "CREATE TABLE contactdetails (\r\n"
			+ "    PK INT AUTO_INCREMENT PRIMARY KEY,\r\n" + "    PHONE_NUMBER VARCHAR(15) NOT NULL,\r\n"
			+ "    EMAIL VARCHAR(100) NOT NULL,\r\n" + "    ADDRESS TEXT NOT NULL,\r\n" + "    FK_COUNTRY INT,\r\n"
			+ "    FK_STATE INT,\r\n" + "    FK_CITY INT,\r\n"
			+ "    FOREIGN KEY (FK_COUNTRY) REFERENCES Country(PK),\r\n"
			+ "    FOREIGN KEY (FK_STATE) REFERENCES State(PK),\r\n"
			+ "    FOREIGN KEY (FK_CITY) REFERENCES City(PK)\r\n" + ")";
	private final String createStudentTable = "CREATE TABLE student (\r\n"
			+ "    PK INT AUTO_INCREMENT PRIMARY KEY,\r\n" + "    NAME VARCHAR(100) NOT NULL,\r\n"
			+ "    PARENT_NAME VARCHAR(100) NOT NULL,\r\n" + "    ROLL_NO VARCHAR(50) NOT NULL UNIQUE,\r\n"
			+ "    FK_CD INT,\r\n" + "    FK_GRADE INT,\r\n"
			+ "    FOREIGN KEY (FK_CD) REFERENCES contactdetails(PK)\r\n" + "        ON DELETE CASCADE\r\n"
			+ "        ON UPDATE CASCADE,\r\n" + "    FOREIGN KEY (FK_GRADE) REFERENCES grade(PK)\r\n"
			+ "        ON DELETE CASCADE\r\n" + "        ON UPDATE CASCADE\r\n" + ");";
	private final String createMarksTable = "CREATE TABLE Marks (\r\n" + "	    PK INT AUTO_INCREMENT PRIMARY KEY,\r\n"
			+ "	    VALUE INT NOT NULL,\r\n" + "	    FK_SUBJECT INT,\r\n" + "	    FK_STUDENT INT,\r\n"
			+ "	    FOREIGN KEY (FK_SUBJECT) REFERENCES SUBJECT(PK)\r\n" + "	        ON DELETE CASCADE\r\n"
			+ "	        ON UPDATE CASCADE,\r\n" + "	    FOREIGN KEY (FK_STUDENT) REFERENCES Student(PK)\r\n"
			+ "	        ON DELETE CASCADE\r\n" + "	        ON UPDATE CASCADE\r\n" + "	);";
	private final String createRollnoCounterTable = "CREATE TABLE rollnocounter (\r\n"
			+ "    GRADE INT PRIMARY KEY,\r\n" + "    COUNTER INT NOT NULL\r\n" + ");";
	private final String createTrigger = "DELIMITER //\r\n" + " \r\n" + "CREATE TRIGGER BEFORE_STUDENT_INSERT\r\n"
			+ "BEFORE INSERT ON STUDENT\r\n" + "FOR EACH ROW\r\n" + "BEGIN\r\n"
			+ "    DECLARE NEW_ROLLNO VARCHAR(50);\r\n" + "    DECLARE CURRENT_COUNTER INT;\r\n" + " \r\n"
			+ "    SELECT COUNTER INTO CURRENT_COUNTER\r\n" + "    FROM ROLLNOCOUNTER\r\n"
			+ "    WHERE GRADE = NEW.FK_GRADE;\r\n" + " \r\n"
			+ "    SET NEW_ROLLNO = CONCAT(NEW.FK_GRADE, 'R', CURRENT_COUNTER);\r\n" + " \r\n"
			+ "    SET NEW.ROLL_NO = NEW_ROLLNO;\r\n" + " \r\n" + "    UPDATE ROLLNOCOUNTER\r\n"
			+ "    SET COUNTER = COUNTER + 1\r\n" + "    WHERE GRADE = NEW.FK_GRADE;\r\n" + "END //\r\n" + " \r\n"
			+ "DELIMITER ;\r\n";
	public static final String PERCENTAGE = "SELECT FK_STUDENT, SUM(VALUE) AS TotalMarks, "
			+ "(SUM(VALUE) / (COUNT(FK_SUBJECT) * 100.0)) * 100 AS Percentage " + "FROM marks " + "GROUP BY FK_STUDENT";
	public static final String DD_BLOOD_GROUP = "SELECT Name FROM BloodGroup";

	public static final String DD_GRADE = "SELECT Name FROM Grade";

	public static final String DD_COUNTRY = "SELECT Name FROM Country";

	public static final String DD_STATE = "SELECT Name FROM State";

	public static final String DD_CITY = "SELECT Name FROM City";

	public static final String DD_SUBJECT = "select name from subject";

	public static final String CHECK_STUDENT = "SELECT COUNT(*) FROM student WHERE NAME = ?";

	public static final String INSERT_STUDENT = "INSERT INTO student (NAME, PARENT_NAME, FK_CD, FK_GRADE, FK_BLOOD_GROUP, GENDER) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";

	public static final String CONTACT_DETAILS = "INSERT INTO contactdetails (phone_number, EMAIL, ADDRESS, FK_COUNTRY, FK_STATE, FK_CITY) "
			+ "VALUES (?, ?, ?, " + "  (SELECT PK FROM country WHERE NAME = ?), "
			+ "  (SELECT s.PK FROM state s JOIN country c ON s.FK_COUNTRY = c.PK WHERE s.NAME = ? AND c.NAME = ?), "
			+ "  (SELECT ci.PK FROM city ci JOIN state st ON ci.FK_STATE = st.PK WHERE ci.NAME = ? AND st.NAME = ?))";

	public static final String CHECK_OR_INSERT = "INSERT INTO grade (NAME) SELECT ? WHERE NOT EXISTS (SELECT PK FROM grade WHERE NAME = ?)";

	public static final String GET_GRADE_PK = "SELECT PK FROM grade WHERE NAME = ?";

	public static final String GET_BLOOD_GROUP_PK = "SELECT PK FROM bloodgroup WHERE NAME = ?";

	public static final String GET_STUDENT_QUERY = "SELECT s.PK AS studentId, s.NAME AS studentName, s.gender, s.ROLL_NO, s.PARENT_NAME, "
			+ "g.NAME AS gradeName, bg.NAME AS bloodGroup, cd.phone_number, cd.EMAIL, cd.ADDRESS, "
			+ "c.NAME AS country, st.NAME AS state, ct.NAME AS city " + "FROM student s "
			+ "LEFT JOIN contactdetails cd ON s.FK_CD = cd.PK " + "LEFT JOIN bloodgroup bg ON s.FK_BLOOD_GROUP = bg.PK "
			+ "LEFT JOIN grade g ON s.FK_GRADE = g.PK " + "LEFT JOIN country c ON cd.FK_COUNTRY = c.PK "
			+ "LEFT JOIN state st ON cd.FK_STATE = st.PK " + "LEFT JOIN city ct ON cd.FK_CITY = ct.PK "
			+ "WHERE s.ROLL_NO = ?";

	public static final String GET_MARKS_QUERY = "SELECT m.VALUE AS markValue, sub.NAME AS subjectName "
			+ "FROM marks m " + "LEFT JOIN subject sub ON m.FK_SUBJECT = sub.PK " + "WHERE m.FK_STUDENT = ?";

	public static final String LIST_STUDENTS_QUERY = "SELECT s.PK AS studentId, s.NAME AS studentName, s.gender, s.ROLL_NO, s.PARENT_NAME, "
			+ "g.NAME AS gradeName, bg.NAME AS bloodGroup, cd.phone_number, cd.EMAIL, cd.ADDRESS, "
			+ "c.NAME AS country, st.NAME AS state, ct.NAME AS city " + "FROM student s "
			+ "LEFT JOIN contactdetails cd ON s.FK_CD = cd.PK " + "LEFT JOIN bloodgroup bg ON s.FK_BLOOD_GROUP = bg.PK "
			+ "LEFT JOIN grade g ON s.FK_GRADE = g.PK " + "LEFT JOIN country c ON cd.FK_COUNTRY = c.PK "
			+ "LEFT JOIN state st ON cd.FK_STATE = st.PK " + "LEFT JOIN city ct ON cd.FK_CITY = ct.PK";

	public static final String LIST_MARKS_QUERY = "SELECT m.VALUE AS markValue, sub.NAME AS subjectName "
			+ "FROM marks m " + "LEFT JOIN subject sub ON m.FK_SUBJECT = sub.PK " + "WHERE m.FK_STUDENT = ?";

	public static final String DELETE_STUDENT = "DELETE FROM STUDENT WHERE ROLL_NO=?";

	public static final String UPDATE_STUDENT = "UPDATE student s " + "JOIN grade g ON g.NAME = ? "
			+ "JOIN bloodgroup bg ON bg.NAME = ? " + "JOIN contactdetails cd ON cd.EMAIL = ? "
			+ "SET s.NAME = ?, s.GENDER = ?, s.PARENT_NAME = ?, s.FK_GRADE = g.PK, "
			+ "s.FK_BLOOD_GROUP = bg.PK, s.FK_CD = cd.PK " + "WHERE s.ROLL_NO = ?";

	public static final String UPDATE_CONTACT = "UPDATE contactdetails cd " + "JOIN country c ON c.NAME = ? "
			+ "JOIN state st ON st.NAME = ? " + "JOIN city ct ON ct.NAME = ? "
			+ "SET cd.PHONE_NUMBER = ?, cd.ADDRESS = ?, cd.FK_COUNTRY = c.PK, "
			+ "cd.FK_STATE = st.PK, cd.FK_CITY = ct.PK "
			+ "WHERE cd.PK = (SELECT s.FK_CD FROM student s WHERE s.ROLL_NO = ?)";

	public static final String DELETE_MARKS = "DELETE FROM marks WHERE FK_STUDENT = (SELECT s.PK FROM student s WHERE s.ROLL_NO = ?)";

	public static final String INSERT_MARKS = "INSERT INTO marks (FK_STUDENT, FK_SUBJECT, VALUE) "
			+ "VALUES ((SELECT s.PK FROM student s WHERE s.ROLL_NO = ?), "
			+ "(SELECT sub.PK FROM subject sub WHERE sub.NAME = ?), ?)";

	public static final String FILTER_MARKS = "SELECT m.VALUE AS markValue, sub.NAME AS subjectName " + "FROM marks m "
			+ "LEFT JOIN subject sub ON m.FK_SUBJECT = sub.PK " + "WHERE m.FK_STUDENT = ?";

}
