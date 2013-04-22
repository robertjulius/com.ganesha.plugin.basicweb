package com.ganesha.basicweb.modules.usermaintenance.action;

import com.ganesha.basicweb.modules.usermaintenance.UserMaintenanceBL;
import com.ganesha.basicweb.modules.usermaintenance.UserMaintenanceForm;
import com.ganesha.core.exception.AppException;
import com.ganesha.struts2.FormAction;

public abstract class UserMaintenanceAction extends
		FormAction<UserMaintenanceForm, UserMaintenanceBL> {

	private static final long serialVersionUID = 8114275581397242184L;

	public UserMaintenanceAction() throws AppException {
		super(UserMaintenanceForm.class, UserMaintenanceBL.class);
	}
}
