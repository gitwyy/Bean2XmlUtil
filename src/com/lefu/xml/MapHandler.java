package com.lefu.xml;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;

public class MapHandler extends AbstractParseHandler{
	/**
	 * 处理Map类型的请求。
	 */
	@Override
	protected Element parse(Request request, ParseDispatcher dispatcher) {
		Element element = null;
		Element rootEle = factory.createElement(request.getRootName());
		Object target = request.getTarget();
		if(target instanceof Map){
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String,Object>)target;
			for (String key : map.keySet()) {
				Object object = map.get(key);
				if(Util.isBaseDataType(object.getClass())){
					element = factory.createElement(key);
					String text = object.toString();
					if(text !=null) element.addText(text);
				}else{
					Request newRequest = new Request();
					if(object instanceof List){
						newRequest.setLevel(Level.LIST);
					}else if(object instanceof Map){
						newRequest.setLevel(Level.MAP);
					}else{
						newRequest.setLevel(Level.COMPLEX);
					}
					newRequest.setRootName(key);
					element = dispatcher.doDispatch(newRequest);
				}
				rootEle.add(element);
			}
		}
		return rootEle;
	}

	@Override
	protected Level handleLevel() {
		return Level.MAP;
	}
	
}
