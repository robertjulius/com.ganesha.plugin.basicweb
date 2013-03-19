package com.cjs.basicweb.model;

import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = -5565669733375186921L;

	private String key;
	private Object value;

	public Item() {

	}

	public Item(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
