package com.training.studentmanagement.dao;

import java.util.List;
import java.util.Map;

import com.training.studentmanagement.dto.Student;

public interface IStudentDao {

	public List<Student> listStudents();

	public Map<Integer, Double> getAllStudentsPercentage();

	public int addStudent(Student student);

	public Map<String, List<String>> getDropDownData();

	public Student getStudent(String rollNumber);

	public int updateStudent(Student student);

	public int deleteStudent(String rollno);

	public List<Student> filterStudent(String criteria, String option);

}
