package com.lefu.xml;

import java.lang.reflect.Field;

import org.dom4j.Element;

public class GeneralParseHandler extends AbstractParseHandler{
	@Override
	protected  Element parse(Request request, ParseDispatcher dispatcher) {
		Object obj =  request.getTarget();
		Class<? extends Object> clazz = obj.getClass();
		Element rootEle = factory.createElement(request.getRootName());
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			XMLCell xmlCell = field.getAnnotation(XMLCell.class);
			if(xmlCell != null && xmlCell.ignore()) continue;
			Class<?> type = field.getType();
			//如果是基本数据类型可以进行封装了。
			if(Util.isBaseDataType(type)){
				Element element = factory.createElement(field.getName());
				Object object;
				try {
					object = field.get(obj);
					if(object!=null && object.toString()!=null){
						element.addText(object.toString());
					}
					rootEle.add(element);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}else{
				//如果是复杂数据类型就 把对应的对象封装到request里面 , 丢给dispatcher 继续处理 。
				Request newRequest = new Request();
				if(xmlCell !=null){
					//设置处理等级
					newRequest.setLevel(xmlCell.level());
					//设置根标签
					newRequest.setRootName(xmlCell.value());
				}else{
					newRequest.setLevel(Level.DEFAULT);
					newRequest.setRootName(field.getName());
				}
				try {
					newRequest.setTarget(field.get(obj));
					Element disEle = dispatcher.doDispatch(newRequest);
					if(disEle!=null){
						rootEle.add(disEle);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return rootEle;
	}

	@Override
	protected Level handleLevel() {
		return Level.COMPLEX;
	}

}
