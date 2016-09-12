package com.lefu.xml;

import org.dom4j.Element;

public class XmlParseDispatcher implements ParseDispatcher{
	private HandlerChain handlers = null;
	
	public Element doDispatch(Request request){
		try {
			return handlers.getParseHandler().handlerParse(request, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public HandlerChain getHandlers() {
		return handlers;
	}
	
	public void setHandlers(HandlerChain handlers) {
		this.handlers = handlers;
	}
	
}
