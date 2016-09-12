package com.lefu.xml;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Util {
	/**  
	  * 判断一个类是否为基本数据类型。  
	  * @param clazz 要判断的类。  
	  * @return true 表示为基本数据类型。  
	  */ 
	 @SuppressWarnings({"rawtypes" })
	public static boolean isBaseDataType(Class clazz){   
	     return (   
	         clazz.equals(String.class) ||
	         clazz.equals(Integer.class)||
	         clazz.equals(Byte.class) ||
	         clazz.equals(Long.class) ||
	         clazz.equals(Double.class) ||
	         clazz.equals(Float.class) ||
	         clazz.equals(Character.class) ||
	         clazz.equals(Short.class) ||
	         clazz.equals(BigDecimal.class) ||
	         clazz.equals(BigInteger.class) ||
	         clazz.equals(Boolean.class) ||
	         clazz.equals(Date.class) ||
	         clazz.isPrimitive()
	     );
	 }
}
