package com.lefu.xml;

import java.io.StringReader;

import org.dom4j.Document;

public class Dom4jAndDomUtil {
	/**
	 * 实现dom4j向org.w3c.dom.Document的转换
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static org.w3c.dom.Document parse(Document doc) throws Exception {
		if (doc == null) {
			return (null);
		}
		StringReader reader = new StringReader(doc.toString());
		org.xml.sax.InputSource source = new org.xml.sax.InputSource(reader);
		javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		javax.xml.parsers.DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return (documentBuilder.parse(source));
	}

	/**
	 * 实现 org.w3c.dom.Document到dom4j的转换
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static Document parse(org.w3c.dom.Document doc) throws Exception {
		if (doc == null) {
			return (null);
		}
		org.dom4j.io.DOMReader xmlReader = new org.dom4j.io.DOMReader();
		return (xmlReader.read(doc));
	}
}
