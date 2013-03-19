package com.cjs.basicweb.modules.usergroupmaintenance.action;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cjs.basicweb.model.module.Module;
import com.cjs.basicweb.model.user.SimpleUser;
import com.cjs.basicweb.modules.login.Privilege;
import com.cjs.basicweb.modules.login.PrivilegeUtils;
import com.cjs.basicweb.modules.usergroupmaintenance.UserGroupMaintenanceForm;
import com.cjs.basicweb.utility.CommonUtils;
import com.cjs.core.exception.AppException;

public class UserGroupMaintenanceCreateAction extends
		UserGroupMaintenanceAction {

	private static final long serialVersionUID = 8114275581397242184L;

	public UserGroupMaintenanceCreateAction() throws AppException {
		super();
	}

	public String executeCreate() throws AppException {
		SimpleUser user = (SimpleUser) getUserSession().getUser();

		UserGroupMaintenanceForm form = getForm();
		getBL().create(form.getNewName(), form.getNewDescription(),
				user.getId(), CommonUtils.getCurrentTimestamp(),
				form.getNewModuleIds());

		return SUCCESS;
	}

	public String prepareCreate() throws AppException {
		UserGroupMaintenanceForm form = getForm();
		form.clearForm("new");

		if (form.getNewModuleIds() == null) {
			form.setNewModuleIds(new ArrayList<String>());
		} else {
			form.getNewModuleIds().clear();
		}

		form.setSelectedId(null);

		return SUCCESS;
	}

	public String validateCreate() throws AppException {
		if (validateForm()) {

			@SuppressWarnings("unchecked")
			List<Module> rootModules = (List<Module>) getModuleSession().get(
					"rootModules");

			List<String> newModuleIds = getForm().getNewModuleIds();
			TreeMap<String, Privilege> newTreeMap = PrivilegeUtils
					.generateTree(newModuleIds.toArray(new String[] {}),
							rootModules);
			getRequest().setAttribute("newTreeMap", newTreeMap);

			return SUCCESS;
		} else {
			return ERROR;
		}
	}
}
