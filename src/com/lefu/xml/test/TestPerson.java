package com.lefu.xml.test;

import java.util.List;

import com.lefu.xml.HandlerChain;
import com.lefu.xml.Level;
import com.lefu.xml.XMLCell;

@XMLCell(level = Level.COMPLEX,value="Person")
public class TestPerson {
	private String name;
	private String gender;
	private int age;
	@XMLCell(level=Level.LIST,value="List")
	private List list;
	
	private HandlerChain chain;
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
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
}
