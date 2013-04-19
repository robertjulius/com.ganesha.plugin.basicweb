package com.ganesha.basicweb.modules.usermaintenance.action;

import java.util.List;

import com.ganesha.basicweb.model.Pagination;
import com.ganesha.basicweb.model.user.SimpleUser;
import com.ganesha.basicweb.model.usergroup.UserGroup;
import com.ganesha.basicweb.modules.usermaintenance.UserMaintenanceForm;
import com.ganesha.core.exception.AppException;

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
		form.setPagination(new Pagination(10));
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
		UserMaintenanceForm form = getForm();

		String userId = form.getSearchUserId();
		String name = form.getSearchUserName();
		String userGroupName = form.getSearchUserGroupName();

		Pagination pagination = getForm().getPagination();
		List<SimpleUser> users = getBL().search(userId, name, userGroupName,
				pagination);
		form.setSearchResult(users);

		return SUCCESS;
	}
}
