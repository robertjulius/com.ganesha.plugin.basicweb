package com.cjs.basicweb.modules.usergroupmaintenance.action;

import com.cjs.basicweb.model.user.SimpleUser;
import com.cjs.basicweb.modules.usergroupmaintenance.UserGroupMaintenanceForm;
import com.cjs.basicweb.utility.CommonUtils;
import com.cjs.core.exception.AppException;
import com.cjs.core.exception.UserException;

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
