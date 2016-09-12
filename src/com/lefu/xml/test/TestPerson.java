package com.lefu.xml.test;

import com.lefu.xml.Level;
import com.lefu.xml.XMLCell;

@XMLCell(level = Level.COMPLEX)
public class TestPerson {
	private String name;
	private String gender;
	private int age;
	
	
	public String getName() {
		return name;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
