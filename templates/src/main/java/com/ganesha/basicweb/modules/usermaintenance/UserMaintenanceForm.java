package com.ganesha.basicweb.modules.usermaintenance;

import java.util.List;

import com.ganesha.basicweb.model.FormBean;
import com.ganesha.basicweb.model.Pagination;
import com.ganesha.basicweb.model.user.SimpleUser;
import com.ganesha.basicweb.model.usergroup.UserGroup;

public class UserMaintenanceForm extends FormBean {

	private static final long serialVersionUID = 7952657167875968415L;

	private String searchUserId;
	private String searchUserName;
	private String searchUserGroupName;
	private Pagination pagination;

	private List<SimpleUser> searchResult;

	private String selectedId;

	private SimpleUser old;

	private String newUserId;
	private String newName;

	private String newPassword;
	private String newUserGroupId;
	private String newUserGroupName;
	private List<UserGroup> selectListUserGroup;

	public String getNewName() {
		return newName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getNewUserGroupId() {
		return newUserGroupId;
	}

	public String getNewUserGroupName() {
		return newUserGroupName;
	}

	public String getNewUserId() {
		return newUserId;
	}

	public SimpleUser getOld() {
		return old;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public List<SimpleUser> getSearchResult() {
		return searchResult;
	}

	public String getSearchUserGroupName() {
		return searchUserGroupName;
	}

	public String getSearchUserId() {
		return searchUserId;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public List<UserGroup> getSelectListUserGroup() {
		return selectListUserGroup;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setNewUserGroupId(String newUserGroupId) {
		this.newUserGroupId = newUserGroupId;
	}

	public void setNewUserGroupName(String newUserGroupName) {
		this.newUserGroupName = newUserGroupName;
	}

	public void setNewUserId(String newUserId) {
		this.newUserId = newUserId;
	}

	public void setOld(SimpleUser old) {
		this.old = old;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setSearchResult(List<SimpleUser> searchResult) {
		this.searchResult = searchResult;
	}

	public void setSearchUserGroupName(String searchUserGroupName) {
		this.searchUserGroupName = searchUserGroupName;
	}

	public void setSearchUserId(String searchUserId) {
		this.searchUserId = searchUserId;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public void setSelectListUserGroup(List<UserGroup> selectListUserGroup) {
		this.selectListUserGroup = selectListUserGroup;
	}

}
