package com.ganesha.basicweb.modules.usergroupmaintenance.action;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.ganesha.basicweb.model.Pagination;
import com.ganesha.basicweb.model.module.Module;
import com.ganesha.basicweb.model.usergroup.UserGroup;
import com.ganesha.basicweb.modules.login.Privilege;
import com.ganesha.basicweb.modules.login.PrivilegeUtils;
import com.ganesha.basicweb.modules.usergroupmaintenance.UserGroupMaintenanceForm;
import com.ganesha.core.exception.AppException;

public class UserGroupMaintenanceMainAction extends UserGroupMaintenanceAction {

	private static final long serialVersionUID = 8114275581397242184L;

	public UserGroupMaintenanceMainAction() throws AppException {
		super();
	}

	public String initial() throws AppException {

		UserGroupMaintenanceForm form = getForm();

		List<Module> rootModules = getBL().getRootModules();

		List<Module> modules = getBL().getChildModules();
		List<String> privilegeIds = new ArrayList<>();
		for (Module module : modules) {
			privilegeIds.add(module.getId());
		}
		TreeMap<String, Privilege> treeMap = PrivilegeUtils.generateTree(
				privilegeIds.toArray(new String[] {}), rootModules);

		form.setTreeMap(treeMap);
		getModuleSession().put("rootModules", rootModules);

		form.setPagination(new Pagination(10));

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
		UserGroupMaintenanceForm form = getForm();

		String name = form.getSearchName();
		String description = form.getSearchDescription();

		Pagination pagination = getForm().getPagination();
		List<UserGroup> userGroups = getBL().search(name, description,
				pagination);
		form.setSearchResult(userGroups);

		return SUCCESS;
	}
}
