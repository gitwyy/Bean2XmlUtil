package com.lefu.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
/**
 * @Description: 这里用一句话描述这个类的作用
 * @see: XMLParse 此处填写需要参考的类
 * @version 2016年9月1日 下午3:25:37
 * @author yangyang.wang
 */
public class XMLParse {

	private static final DocumentFactory documentFactory = DocumentFactory.getInstance();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Element beanToXmlElement(T t) throws XMLCellException {
		if (t == null) return null;
		// 获取当前对象的Class
		Class clazz = t.getClass();
		if (clazz.isAnnotationPresent(XMLCell.class)) {
			XMLCell xml = (XMLCell) clazz.getAnnotation(XMLCell.class);
			// 获取注解上的value值用来生成Element
			String root = xml.value();
			Element rootEle = documentFactory.createElement(root);
			// 获取并遍历当前类所有的Field
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				// 通过每个field上面标注的注解确定field在生成的element中的节点位置,父子关系。
				XMLCell xmlCell = field.getAnnotation(XMLCell.class);
				if (xmlCell != null && xmlCell.parent().equals(XMLCell.XMLCELL_DEF_PARENT)) {
					Element element = null;
					try {
						element = createElement(t, field, fields);
						rootEle.add(element);
					} catch (XMLCellException e) {
						e.printStackTrace();
						// 打印日志，记录哪个元素创建的时候出错
						throw new XMLCellException("生成" + field);
					}
				} else {
				}
			}
			return rootEle;
		} else {
			// 打印日志 该类没有声明注解类@XMLCell
			return null;
		}
	}

	/**
	 * 递归方法 使用请注意考虑系统性能,默认阈值递归1000次，如需手动修改阈值可以使用重载方法进行设置。
	 * 递归创建元素，直到创建完该元素的所有子元素。
	 * @param field
	 * @param fields
	 * @return
	 *         递归方法设置阈值,防止死循环导致内存溢出。
	 */
	public static <T> Element createElement(T t, Field field, Field[] fields, int valve) throws XMLCellException {
		Element element = documentFactory.createElement("FiledIsNull");
		String text = null;
		if (field == null) {
			// element.addText("没有这个元素");
			// return element;
			throw new XMLCellException(XMLParse.class.getName() + "  createElement(t,field,fields,valve):没有找到元素节点请检查各节点子父关系。");
		}
		if (valve < 0) {
			throw new XMLCellException(XMLParse.class.getName() + "  createElement(t,field,fields,valve):超出递归阈值.warning out of memory!!");
		}
		
		try {
			field.setAccessible(true);
			Object obj = field.get(t);
			text = obj != null ? obj.toString() : null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		XMLCell xmlCell = field.getAnnotation(XMLCell.class);
		if(xmlCell == null){
			element = documentFactory.createElement(field.getName()).addText(text);
			return element;
		}
		String[] childs = xmlCell.children();
		String value = xmlCell.value();
		
		
		
		// 优先使用注解上的值进行匹配，如果注解上的值是默认值，则使用field name 进行匹配
		element = documentFactory.createElement((XMLCell.XMLCELL_DEF_ELEMENT.equals(value)) ? field.getName() : value);
		if (text != null && !text.trim().equals("")) element.addText(text);
		
		if (childs !=null &&!childs[0].equals(XMLCell.XMLCELL_DEF_CHILDREN) || childs.length > 1) {
			for (String child : childs) {
				Field children = getFieldByChildren(child, fields);
				element.add(createElement(t, children, fields, --valve));
			}
		}
		return element;
	}

	/**
	 * 重载方法 使用默认递归阈值1000
	 * @param t
	 * @param field
	 * @param fields
	 * @return
	 * @throws XMLCellException
	 */
	public static <T> Element createElement(T t, Field field, Field[] fields) throws XMLCellException {
		return createElement(t, field, fields, 1000);
	}

	/**
	 * 根据注解属性查找子元素field
	 * @param children
	 * @param clazz
	 * @return
	 */
	public static Field getFieldByChildren(String children, Field[] fields) {
		for (Field field : fields) {
			XMLCell xmlCell = field.getAnnotation(XMLCell.class);
			String value = xmlCell.value();
			// 验证注解value 是否为默认值，优先取注解value值进行匹配，如果是默认值则使用field name进行匹配
			if ((XMLCell.XMLCELL_DEF_ELEMENT.equals(value) ? field.getName() : value).equals(children)) {
				return field;
			}
		}
		return null;
	}

	// 拼装生成element
	public static Element createChildElement(String rootName, Element... elements) {
		if (rootName == null) return null;

		Element rootElement = documentFactory.createElement(rootName);

		if (elements == null || elements.length == 0) return rootElement;
		for (Element element : elements) {
			rootElement.add(element);
		}
		return rootElement;
	}

	public static Element createChildElement(String rootName, List<Element> elements) {
		if (rootName == null) return null;

		Element rootElement = documentFactory.createElement(rootName);

		if (elements == null || elements.isEmpty()) return rootElement;
		for (Element element : elements) {
			rootElement.add(element);
		}
		return rootElement;
	}

	// 以上两个方法合成一个。
	@SuppressWarnings("rawtypes")
	public static Element createXmlElement(String rootName, Object... elements) {
		if (rootName == null) return null;

		Element rootElement = documentFactory.createElement(rootName);

		if (elements == null || elements.length == 0) return rootElement;

		for (Object element : elements) {
			if (element instanceof Element) {
				rootElement.add((Element) element);
			} else if (element instanceof List) {
				for (Object obj : (List) element) {
					if (obj instanceof Element) {
						rootElement.add((Element) obj);
					}
				}
			}

		}
		return rootElement;
	}

	public static Document createDocument(Element element) {

		return documentFactory.createDocument(element);
	}

	// 输出document
	public static void write(String xmlPath, Document document) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		boolean flag = xmlPath == null ? false : true;
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(xmlPath == null ? System.out : new FileOutputStream(xmlPath), format);
			writer.write(document);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null && flag) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
