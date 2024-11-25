package com.training.studentmanagement.dto;

import java.util.List;

public class Student {

	private String name;
	private String gender;
	private String rollNo;
	private ContactDetails contact;
	private List<Marks> marks;
	private String bloodGroup;
	private String grade;
	private String parentName;
	private double percentage;
	private String result;

	public Student(String name, String gender, ContactDetails contact, String bloodGroup, String grade,
			String parentName) {
		this.name = name;
		this.gender = gender;
		this.contact = contact;
		this.bloodGroup = bloodGroup;
		this.grade = grade;
		this.parentName = parentName;
	}

	public Student() {

	}

	public String getName() {
		return name;
	}

	public Student(String name, String gender, String rollNo, ContactDetails contact, List<Marks> marks,
			String bloodGroup, String grade, String parentName, double percentage, String result) {
		this.name = name;
		this.gender = gender;
		this.rollNo = rollNo;
		this.contact = contact;
		this.marks = marks;
		this.bloodGroup = bloodGroup;
		this.grade = grade;
		this.parentName = parentName;
		this.percentage = percentage;
		this.result = result;
	}

	public Student(String name, String rollNo, String parentName, String gender, String bloodGroup, String grade,
			ContactDetails contact, List<Marks> marks) {
		this.name = name;
		this.rollNo = rollNo;
		this.gender = gender;
		this.rollNo = rollNo;
		this.contact = contact;
		this.marks = marks;
		this.bloodGroup = bloodGroup;
		this.grade = grade;
		this.parentName = parentName;
		this.percentage = percentage;
		this.result = result;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public ContactDetails getContact() {
		return contact;
	}

	public void setContact(ContactDetails contact) {
		this.contact = contact;
	}

	public List<Marks> getMarks() {
		return marks;
	}

	public void setMarks(List<Marks> marks) {
		this.marks = marks;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", gender=" + gender + ", rollNo=" + rollNo + ", contact=" + contact
				+ ", marks=" + marks + ", bloodGroup=" + bloodGroup + ", grade=" + grade + ", parentName=" + parentName
				+ ", percentage=" + percentage + ", result=" + result + "]";
	}

}
