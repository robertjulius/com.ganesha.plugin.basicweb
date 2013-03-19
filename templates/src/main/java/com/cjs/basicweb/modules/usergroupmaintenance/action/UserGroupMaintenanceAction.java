package com.cjs.basicweb.modules.usergroupmaintenance.action;

import java.util.List;

import com.cjs.basicweb.modules.usergroupmaintenance.UserGroupMaintenanceBL;
import com.cjs.basicweb.modules.usergroupmaintenance.UserGroupMaintenanceForm;
import com.cjs.core.exception.AppException;
import com.cjs.struts2.FormAction;

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
