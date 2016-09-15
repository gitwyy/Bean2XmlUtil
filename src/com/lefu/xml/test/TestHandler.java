package com.lefu.xml.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;

import com.lefu.xml.GeneralParseHandler;
import com.lefu.xml.HandlerChain;
import com.lefu.xml.Level;
import com.lefu.xml.ListHandler;
import com.lefu.xml.MapHandler;
import com.lefu.xml.Request;
import com.lefu.xml.Util;
import com.lefu.xml.XMLParse;
import com.lefu.xml.XmlParseDispatcher;

public class TestHandler {
	@Test
	public void testListHandler(){
		Field[] fields = "".getClass().getDeclaredFields();
		for (Field field : fields) {
			System.out.println(field.getType());
			System.out.println(field.getClass());
		}
		
	}
	@Test
	public void testType(){
		TestPerson person = new TestPerson();
		Class<? extends TestPerson> class1 = person.getClass();
		Field[] fields = class1.getDeclaredFields();
		for (Field field : fields) {
			System.out.println(""+Util.isBaseDataType(field.getType()));
		}
	}
	
	@Test
	public void testParseHandler(){
		TestPerson person = new TestPerson();
		person.setAge(11);
		person.setGender("aaa");
		person.setName("bbb");
		List<TestPerson> list = new ArrayList<TestPerson>();
		list.add(new TestPerson());
		list.add(new TestPerson());
		person.setList(list);
		XmlParseDispatcher xmlParseDispatcher = new XmlParseDispatcher();
		
		HandlerChain chain = new HandlerChain();
		chain.addHandler(new GeneralParseHandler());
		chain.addHandler(new ListHandler());
		chain.addHandler(new MapHandler());
		xmlParseDispatcher.setHandlers(chain);
		
		Request request = new Request();
		request.setTarget(person);
		request.setLevel(Level.COMPLEX);
		request.setRootName("aaaaa");
		Element element = xmlParseDispatcher.doDispatch(request);
		Document document = XMLParse.createDocument(element);
		XMLParse.write(null, document);
	}
	

}
