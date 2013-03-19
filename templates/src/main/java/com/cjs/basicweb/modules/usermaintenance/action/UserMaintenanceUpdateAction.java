package com.cjs.basicweb.modules.usermaintenance.action;

import com.cjs.basicweb.model.user.SimpleUser;
import com.cjs.basicweb.modules.usermaintenance.UserMaintenanceForm;
import com.cjs.basicweb.utility.CommonUtils;
import com.cjs.core.exception.AppException;

public class UserMaintenanceUpdateAction extends UserMaintenanceAction {

	private static final long serialVersionUID = 8114275581397242184L;

	public UserMaintenanceUpdateAction() throws AppException {
		super();
	}

	public String executeUpdate() throws AppException {
		SimpleUser user = (SimpleUser) getUserSession().getUser();

		UserMaintenanceForm form = getForm();
		getBL().update(form.getSelectedId(), form.getNewUserId(),
				form.getNewName(), form.getNewUserGroupId(), user.getId(),
				CommonUtils.getCurrentTimestamp());

		return SUCCESS;
	}

	public String prepareUpdate() throws AppException {
		UserMaintenanceForm form = getForm();
		form.clearForm("new");
		form.assignFromEntity("new", form.getOld());

		form.setNewUserGroupId(form.getOld().getUserGroup().getId());
		form.setNewUserGroupName(form.getOld().getUserGroup().getName());

		return SUCCESS;
	}

	public String validateUpdate() throws AppException {
		if (validateForm()) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

}
