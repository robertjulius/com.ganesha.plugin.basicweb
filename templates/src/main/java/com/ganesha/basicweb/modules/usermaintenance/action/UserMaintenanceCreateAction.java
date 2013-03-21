package com.ganesha.basicweb.modules.usermaintenance.action;

import com.ganesha.basicweb.model.user.SimpleUser;
import com.ganesha.basicweb.modules.usermaintenance.UserMaintenanceForm;
import com.ganesha.basicweb.utility.CommonUtils;
import com.ganesha.core.exception.AppException;

public class UserMaintenanceCreateAction extends UserMaintenanceAction {

	private static final long serialVersionUID = 8114275581397242184L;

	public UserMaintenanceCreateAction() throws AppException {
		super();
	}

	public String executeCreate() throws AppException {
		SimpleUser user = (SimpleUser) getUserSession().getUser();

		UserMaintenanceForm form = getForm();
		getBL().create(form.getNewUserId(), form.getNewName(),
				form.getNewPassword(), form.getNewUserGroupId(), user.getId(),
				CommonUtils.getCurrentTimestamp());

		return SUCCESS;
	}

	public String prepareCreate() throws AppException {
		UserMaintenanceForm form = getForm();
		form.clearForm("new");
		form.setNewUserGroupId(null);
		form.setNewUserGroupName(null);

		form.setSelectedId(null);

		return SUCCESS;
	}

	public String validateCreate() throws AppException {
		if (validateForm()) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
}
