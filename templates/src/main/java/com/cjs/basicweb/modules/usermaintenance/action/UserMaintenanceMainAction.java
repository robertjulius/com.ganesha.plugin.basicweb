package com.cjs.basicweb.modules.usermaintenance.action;

import java.util.List;

import com.cjs.basicweb.model.user.SimpleUser;
import com.cjs.basicweb.model.usergroup.UserGroup;
import com.cjs.basicweb.modules.usermaintenance.UserMaintenanceForm;
import com.cjs.core.exception.AppException;

public class UserMaintenanceMainAction extends UserMaintenanceAction {

	private static final long serialVersionUID = 8114275581397242184L;

	public UserMaintenanceMainAction() throws AppException {
		super();
	}

	public String initial() throws AppException {
		UserMaintenanceForm form = getForm();
		List<UserGroup> userGroups = getBL().getAllUserGroup();
		userGroups.add(0, new UserGroup());
		form.setSelectListUserGroup(userGroups);
		return SUCCESS;
	}

	public String prepareDetail() throws AppException {
		String selectedId = getForm().getSelectedId();

		SimpleUser user = getBL().getDetail(selectedId);
		UserMaintenanceForm form = getForm();
		form.setOld(user);

		return SUCCESS;
	}

	public String search() throws AppException {
		String userId = getForm().getSearchUserId();
		String name = getForm().getSearchUserName();
		String userGroupName = getForm().getSearchUserGroupName();

		List<SimpleUser> users = getBL().search(userId, name, userGroupName);
		getForm().setSearchResult(users);
		return SUCCESS;
	}
}
