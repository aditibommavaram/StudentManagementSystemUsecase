package com.training.studentmanagement.dto;

public class Marks {

	private int value;
	private String subject;

	public Marks(int value, String subject) {
		this.value = value;
		this.subject = subject;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "Marks [value=" + value + ", subject=" + subject + "]";
	}

	public Marks() {

	}
}
