package com.ganesha.basicweb.modules.usergroupmaintenance.action;

import java.util.List;

import com.ganesha.basicweb.modules.usergroupmaintenance.UserGroupMaintenanceBL;
import com.ganesha.basicweb.modules.usergroupmaintenance.UserGroupMaintenanceForm;
import com.ganesha.core.exception.AppException;
import com.ganesha.struts2.FormAction;

public abstract class UserGroupMaintenanceAction extends
		FormAction<UserGroupMaintenanceForm, UserGroupMaintenanceBL> {

	private static final long serialVersionUID = 8114275581397242184L;

	private List<String> listAccessPaths;

	public UserGroupMaintenanceAction() throws AppException {
		super(UserGroupMaintenanceForm.class, UserGroupMaintenanceBL.class);
	}

	public List<String> getListAccessPaths() {
		return listAccessPaths;
	}

	public void setListAccessPaths(List<String> listAccessPaths) {
		this.listAccessPaths = listAccessPaths;
	}
}
