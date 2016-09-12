package com.lefu.xml;

public class Request{
	
	private Level level;
	private Object target;
	private String rootName;
	
	Level getLevel(){
		if(level ==null){
			return target.getClass().getAnnotation(XMLCell.class).level();
		}
		return this.level;
	}
	public void setLevel(Level level){
		this.level = level;
	}

	Object getTarget(){
		return this.target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	
	public String getRootName() {
		return rootName;
	}
	
	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
	
}
