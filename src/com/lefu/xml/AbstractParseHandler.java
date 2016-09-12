package com.lefu.xml;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;

/**
 * xml解析处理器类
 * @author babos
 *
 */
public abstract class AbstractParseHandler{
	private AbstractParseHandler nextHandler;
	
	protected DocumentFactory factory = DocumentFactory.getInstance();
	
	//模板方法。判断是不是当前处理器可以处理的请求。如果不是则往下传递。
	public final Element handlerParse(Request request,ParseDispatcher dispatcher){
		Element element = null;
		if(this.handleLevel()==request.getLevel()){
			element= this.parse(request,dispatcher);
		}else{
			if(nextHandler!=null){
				 element = nextHandler.parse(request,dispatcher);
			}
		}
		return element;
	}
	
	protected abstract Element parse(Request request,ParseDispatcher dispatcher);
	/**
	 * 设置当前处理器类可以处理的请求级别。
	 * @return
	 */
	protected abstract Level handleLevel();

	public AbstractParseHandler getNextHandler() {
		return nextHandler;
	}
	public void setNextHandler(AbstractParseHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
}
