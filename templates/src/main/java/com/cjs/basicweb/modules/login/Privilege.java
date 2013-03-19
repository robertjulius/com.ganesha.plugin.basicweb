package com.cjs.basicweb.modules.login;

import java.util.TreeMap;

public class Privilege {
	private String id;
	private String name;
	private String parentId;
	private String action;
	private TreeMap<String, Privilege> childs;

	public Privilege(String id, String name, String parentId, String action) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.action = action;
		childs = new TreeMap<String, Privilege>();
	}

	public void addChild(Privilege privilege) {
		childs.put(privilege.getId(), privilege);
	}

	public String getAction() {
		return action;
	}

	public TreeMap<String, Privilege> getChilds() {
		return childs;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return getName();
	}
}
