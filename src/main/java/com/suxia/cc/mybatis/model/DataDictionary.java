package com.suxia.cc.mybatis.model;

import java.io.Serializable;

/**
 * 类DataDictionary.java的实现描述：数据字典 
 * @author yibi 2017年6月5日 下午3:48:58
 */
public class DataDictionary implements Serializable {

	private static final long serialVersionUID = 5776429860449356562L;

	private String value;
	private String text;
	private String color;
	private boolean leaf = true;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	@Override
    public String toString() {
        return "DataDictionaryDTO [value=" + value + ", text=" + text + "]";
    }
}
