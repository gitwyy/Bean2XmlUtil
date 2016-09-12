package com.lefu.xml;

import org.dom4j.Element;

public interface ParseDispatcher {
	public abstract Element doDispatch(Request request);
}
