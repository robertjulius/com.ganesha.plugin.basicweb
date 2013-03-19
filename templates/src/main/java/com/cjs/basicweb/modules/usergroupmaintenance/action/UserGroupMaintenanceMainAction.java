package com.cjs.basicweb.modules.usergroupmaintenance.action;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cjs.basicweb.model.module.Module;
import com.cjs.basicweb.model.usergroup.UserGroup;
import com.cjs.basicweb.modules.login.Privilege;
import com.cjs.basicweb.modules.login.PrivilegeUtils;
import com.cjs.basicweb.modules.usergroupmaintenance.UserGroupMaintenanceForm;
import com.cjs.core.exception.AppException;

public class UserGroupMaintenanceMainAction extends UserGroupMaintenanceAction {

	private static final long serialVersionUID = 8114275581397242184L;

	public UserGroupMaintenanceMainAction() throws AppException {
		super();
	}

	public String initial() throws AppException {

		List<Module> rootModules = getBL().getRootModules();

		List<Module> modules = getBL().getChildModules();
		List<String> privilegeIds = new ArrayList<>();
		for (Module module : modules) {
			privilegeIds.add(module.getId());
		}
		TreeMap<String, Privilege> treeMap = PrivilegeUtils.generateTree(
				privilegeIds.toArray(new String[] {}), rootModules);

		getForm().setTreeMap(treeMap);
		getModuleSession().put("rootModules", rootModules);

		return SUCCESS;
	}

	public String prepareDetail() throws AppException {

		@SuppressWarnings("unchecked")
		List<Module> rootModules = (List<Module>) getModuleSession().get(
				"rootModules");
		String selectedId = getForm().getSelectedId();

		UserGroup userGroup = getBL().getDetail(selectedId);
		UserGroupMaintenanceForm form = getForm();
		form.setOld(userGroup);

		List<Module> modules = form.getOld().getModules();
		List<String> privilegeIds = new ArrayList<>();
		for (Module module : modules) {
			privilegeIds.add(module.getId());
		}
		TreeMap<String, Privilege> oldTreeMap = PrivilegeUtils.generateTree(
				privilegeIds.toArray(new String[] {}), rootModules);
		getForm().setOldTreeMap(oldTreeMap);

		return SUCCESS;
	}

	public String search() throws AppException {
		String name = getForm().getSearchName();
		String description = getForm().getSearchDescription();

		List<UserGroup> userGroups = getBL().search(name, description);
		getForm().setSearchResult(userGroups);
		return SUCCESS;
	}
}
