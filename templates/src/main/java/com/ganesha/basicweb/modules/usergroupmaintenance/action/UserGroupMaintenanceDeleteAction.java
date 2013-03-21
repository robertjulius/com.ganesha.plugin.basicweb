package com.ganesha.basicweb.modules.usergroupmaintenance.action;

import com.ganesha.basicweb.model.user.SimpleUser;
import com.ganesha.basicweb.modules.usergroupmaintenance.UserGroupMaintenanceForm;
import com.ganesha.basicweb.utility.CommonUtils;
import com.ganesha.core.exception.AppException;
import com.ganesha.core.exception.UserException;

public class UserGroupMaintenanceDeleteAction extends
		UserGroupMaintenanceAction {

	private static final long serialVersionUID = 8114275581397242184L;

	public UserGroupMaintenanceDeleteAction() throws AppException {
		super();
	}

	public String executeDelete() throws AppException {
		SimpleUser user = (SimpleUser) getUserSession().getUser();

		UserGroupMaintenanceForm form = getForm();
		try {
			getBL().delete(form.getSelectedId(), user.getId(),
					CommonUtils.getCurrentTimestamp());

			return SUCCESS;
		} catch (UserException e) {
			addActionError(e.getMessageId());
			return ERROR;
		}
	}
}
