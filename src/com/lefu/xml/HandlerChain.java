package com.lefu.xml;

import java.util.ArrayList;
import java.util.List;

public class HandlerChain {
	private List<AbstractParseHandler> chain;

	public void addHandler(AbstractParseHandler handler) {
		if (chain == null) {
			chain = new ArrayList<AbstractParseHandler>();
			chain.add(handler);
		} else {
			chain.add(handler);
			AbstractParseHandler parseHandler = chain.get(chain.size() - 1);
			parseHandler.setNextHandler(handler);
		}
	}

	public AbstractParseHandler getParseHandler() {
		return chain != null && chain.size() > 0 ? this.chain.get(0) : null;
	}

	public void setChain(List<AbstractParseHandler> chain) {
		this.chain = chain;
	}

	public List<AbstractParseHandler> getChain() {
		return chain;
	}
}
