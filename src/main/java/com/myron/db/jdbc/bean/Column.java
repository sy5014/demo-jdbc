package com.myron.db.jdbc.bean;

import com.myron.common.util.DataTypeUtil;
import com.myron.common.util.StringUtils;




/**
 * mysql表的字段信息类
 * @author lrx
 *
 */
public class Column {
	//表的字段原始信息
	/** 表字段名*/
	private String field;

	/** mysql字段类型*/
	private String type;

	/** 未知*/
	private String collation;

	/** 是否允许为空:YES/NO*/
	private String isNull;

	/** 是否为主键外键:PRI/MUL/"" */
	private String key;
	/***/
	private String extra;
	/** 表字段备注信息*/
	private String comment;
	
	//对应的column对应的java属性信息
	private String camelField;  		//驼峰命名
	private String capitalizeCamelField;//大写驼峰命名
	private String javaType;			//java类型
	private String jdbcType;

	/**用例数据*/
	private Object value;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
		this.camelField=StringUtils.toCamelCase(this.field);
		this.capitalizeCamelField=StringUtils.toCapitalizeCamelCase(this.field);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		this.jdbcType=DataTypeUtil.mySqlToJdbc(this.type);
		this.javaType=DataTypeUtil.jdbcTypeToJavaType(this.jdbcType);
	}
	public String getCollation() {
		return collation;
	}
	public void setCollation(String collation) {
		this.collation = collation;
	}
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	public String getCamelField() {
		return camelField;
	}
	public String getCapitalizeCamelField() {
		return capitalizeCamelField;
	}
	public String getJavaType() {
		return javaType;
	}
	
	public String getJdbcType() {
		return jdbcType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Column [field=" + field
				+ ", camelField="+ camelField 
				+ ", capitalizeCamelField=" + capitalizeCamelField
				+ ", type=" + type 
				+ ", jdbcType=" + jdbcType 
				+ ", javaType=" + javaType 
				+ ", collation="+ collation 
				+ ", isNull=" + isNull 
				+ ", key=" + key
				+ ", extra=" + extra 
				+ ", comment=" + comment 
				+ "]";
	}


	
	
	

}
