package com.lefu.xml;
/* @Description: 这里用一句话描述这个类的作用
 * @see: XMLCellException 此处填写需要参考的类
 * @version 2016年9月1日 下午3:16:40
 * @author yangyang.wang
 */
public class XMLCellException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public XMLCellException() {
		super();
	}

	public XMLCellException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public XMLCellException(String message, Throwable cause) {
		super(message, cause);
	}

	public XMLCellException(String message) {
		super(message);
	}

	public XMLCellException(Throwable cause) {
		super(cause);
	}

}
