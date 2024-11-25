package com.training.studentmanagement.services;

import java.util.List;
import java.util.Map;

import com.training.studentmanagement.dto.Marks;
import com.training.studentmanagement.dto.Student;

public interface IStudentServices {

	public List<Student> listStudents();

	public Map<Integer, Double> getAllStudentsPercentage();

	public int addStudent(Student student);

	public Student getStudent(String rollNumber);

	public Map<String, List<String>> getDropDownData();

	public int updateStudent(Student student);

	public int deleteStudent(String rollno);

	public List<Student> filterStudent(String criteria, String option);

}
