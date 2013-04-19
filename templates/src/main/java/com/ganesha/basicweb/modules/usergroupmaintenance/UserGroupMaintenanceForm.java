package com.ganesha.basicweb.modules.usergroupmaintenance;

import java.util.List;
import java.util.TreeMap;

import com.ganesha.basicweb.model.FormBean;
import com.ganesha.basicweb.model.Pagination;
import com.ganesha.basicweb.model.usergroup.UserGroup;
import com.ganesha.basicweb.modules.login.Privilege;

public class UserGroupMaintenanceForm extends FormBean {

	private static final long serialVersionUID = 7952657167875968415L;

	private String searchId;
	private String searchName;
	private String searchDescription;

	private Pagination pagination;
	private List<UserGroup> searchResult;

	private String selectedId;

	private UserGroup old;

	private String newId;
	private String newName;

	private String newDescription;
	private List<String> newModuleIds;
	private TreeMap<String, Privilege> treeMap;
	private TreeMap<String, Privilege> oldTreeMap;

	public String getNewDescription() {
		return newDescription;
	}

	public String getNewId() {
		return newId;
	}

	public List<String> getNewModuleIds() {
		return newModuleIds;
	}

	public String getNewName() {
		return newName;
	}

	public UserGroup getOld() {
		return old;
	}

	public TreeMap<String, Privilege> getOldTreeMap() {
		return oldTreeMap;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public String getSearchDescription() {
		return searchDescription;
	}

	public String getSearchId() {
		return searchId;
	}

	public String getSearchName() {
		return searchName;
	}

	public List<UserGroup> getSearchResult() {
		return searchResult;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public TreeMap<String, Privilege> getTreeMap() {
		return treeMap;
	}

	public void setNewDescription(String newDescription) {
		this.newDescription = newDescription;
	}

	public void setNewId(String newId) {
		this.newId = newId;
	}

	public void setNewModuleIds(List<String> newModuleIds) {
		this.newModuleIds = newModuleIds;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public void setOld(UserGroup old) {
		this.old = old;
	}

	public void setOldTreeMap(TreeMap<String, Privilege> oldTreeMap) {
		this.oldTreeMap = oldTreeMap;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setSearchDescription(String searchDescription) {
		this.searchDescription = searchDescription;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setSearchResult(List<UserGroup> searchResult) {
		this.searchResult = searchResult;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public void setTreeMap(TreeMap<String, Privilege> treeMap) {
		this.treeMap = treeMap;
	}

}
