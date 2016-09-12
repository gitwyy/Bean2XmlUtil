package com.lefu.xml;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;

/**
 * 处理List数据类型的解析工作。
 * @author babos
 *
 */
@SuppressWarnings("rawtypes")
public class ListHandler extends AbstractParseHandler{

	@Override
	protected Element parse(Request request, ParseDispatcher dispatcher) {
		
		Object target = request.getTarget();
		Element rootEle = factory.createElement(request.getRootName());
		Element element = null;
		if(target instanceof List){
			List list = (List) target;
			for (Object object : list) {
				if(Util.isBaseDataType(object.getClass())){
					element = factory.createElement(object.toString());
				}else{
					Request newRequest = new Request();
					if(object instanceof List){
						newRequest.setLevel(Level.LIST);
					}else if(object instanceof Map){
						newRequest.setLevel(Level.MAP);
					}else{
						newRequest.setLevel(Level.COMPLEX);
					}
					newRequest.setRootName(object.getClass().getAnnotation(XMLCell.class).value());
					element = dispatcher.doDispatch(newRequest);
				}
				rootEle.add(element);
			}
		}
		return rootEle;
	}

	@Override
	protected Level handleLevel() {
		return Level.LIST;
	}
	
	
	
}
