package com.lefu.xml;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 这里用一句话描述这个类的作用
 * @see: XMLCell 此处填写需要参考的类
 * @version 2016年9月1日 下午3:25:25
 * @author yangyang.wang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.TYPE })
public @interface XMLCell {
	public static final String XMLCELL_DEF_ELEMENT = "XMLCELL_ELEMENT";

	public static final String XMLCELL_DEF_PARENT = "XMLCELL_PARENT";

	public static final String XMLCELL_DEF_CHILDREN = "XMLCELL_CHILDREN";

	public static final String XMLCELL_DEF_PREV = "XMLCELL_PREV";

	public static final String XMLCELL_DEF_NEXT = "XMLCELL_NEXT";

	// 设置生成元素名称如果没设置则使用默认值,xml文档将会使用bean的属性名
	public String value() default XMLCELL_DEF_ELEMENT;

	// 设置元素在xml元素节点的排序位置
	public int index() default 0;

	// 设置元素的父元素节点,使用默认值会将当前root元素当作父节点.
	public String parent() default XMLCELL_DEF_PARENT;

	// 设置元素的子元素节点,使用默认值则任务没有子元素。
	public String[] children() default { XMLCELL_DEF_CHILDREN };

	// 设置当前元素的前一个元素节点
	public String prev() default XMLCELL_DEF_PREV;

	// 设置当前元素的后一个元素节点
	public String next() default XMLCELL_DEF_NEXT;

	// 设置当前元素的类型。可以处理复杂数据类型。
	public Class<?> property() default String.class;

	boolean ignore()default false;
	
	public Level level() default Level.DEFAULT;
}
