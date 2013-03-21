package com.ganesha.basicweb.modules.usermaintenance.action;

import java.util.List;

import com.ganesha.basicweb.modules.usermaintenance.UserMaintenanceBL;
import com.ganesha.basicweb.modules.usermaintenance.UserMaintenanceForm;
import com.ganesha.core.exception.AppException;
import com.ganesha.struts2.FormAction;

public abstract class UserMaintenanceAction extends
		FormAction<UserMaintenanceForm, UserMaintenanceBL> {

	private static final long serialVersionUID = 8114275581397242184L;

	private List<String> listAccessPaths;

	public UserMaintenanceAction() throws AppException {
		super(UserMaintenanceForm.class, UserMaintenanceBL.class);
	}

	public List<String> getListAccessPaths() {
		return listAccessPaths;
	}

	public void setListAccessPaths(List<String> listAccessPaths) {
		this.listAccessPaths = listAccessPaths;
	}
}
