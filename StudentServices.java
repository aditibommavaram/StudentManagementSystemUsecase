package com.training.studentmanagement.services;

import java.util.List;
import java.util.Map;

import com.training.studentmanagement.dao.IStudentDao;
import com.training.studentmanagement.dao.StudentDao;
import com.training.studentmanagement.dto.Student;

public class StudentServices implements IStudentServices {

	IStudentDao isdao = new StudentDao();

	@Override
	public List<Student> listStudents() {

		return isdao.listStudents();
	}

	@Override
	public Map<Integer, Double> getAllStudentsPercentage() {

		return isdao.getAllStudentsPercentage();
	}

	@Override
	public int addStudent(Student student) {

		return isdao.addStudent(student);
	}

	public Map<String, List<String>> getDropDownData() {

		return isdao.getDropDownData();
	}

	@Override
	public Student getStudent(String rollNumber) {

		return isdao.getStudent(rollNumber);
	}

	public int updateStudent(Student student) {

		return isdao.updateStudent(student);
	}

	@Override
	public int deleteStudent(String rollno) {

		return isdao.deleteStudent(rollno);
	}

	@Override
	public List<Student> filterStudent(String criteria, String option) {

		return isdao.filterStudent(criteria, option);
	}

}
