package com.max256.morpho.common.test;

public class Student {

	protected String phone;
	protected String name;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Student(String phone, String name) {
		super();
		this.phone = phone;
		this.name = name;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

}
