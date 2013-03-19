package com.cjs.basicweb.modules.usergroupmaintenance;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.cjs.basicweb.model.module.Module;
import com.cjs.basicweb.model.usergroup.UserGroup;
import com.cjs.basicweb.modules.BusinessLogic;
import com.cjs.basicweb.utility.GeneralConstants;
import com.cjs.basicweb.utility.GeneralConstants.ActionType;
import com.cjs.basicweb.utility.PropertiesConstants;
import com.cjs.core.exception.AppException;
import com.cjs.core.exception.UserException;

public class UserGroupMaintenanceBL extends BusinessLogic {

	public void create(String newName, String newDescription, String createBy,
			Timestamp createDate, List<String> newModules) throws AppException {

		beginTransaction();

		UserGroup userGroup = new UserGroup();
		userGroup.setName(newName);
		userGroup.setDescription(newDescription);
		userGroup.setCreateBy(createBy);
		userGroup.setCreateDate(createDate);
		userGroup.setUpdateBy(createBy);
		userGroup.setUpdateDate(createDate);
		userGroup.setRecStatus(GeneralConstants.REC_STATUS_ACTIVE);

		userGroup.setModules(new ArrayList<Module>());
		for (String moduleId : newModules) {
			Module module = (Module) getSession().load(Module.class, moduleId);
			userGroup.getModules().add(module);
		}

		getSession().save(userGroup);
		saveActivityLog(ActionType.CREATE, userGroup);

		commit();
	}

	public void delete(String id, String updateBy, Timestamp updateDate)
			throws UserException, AppException {
		beginTransaction();
		UserGroup userGroup = (UserGroup) getSession()
				.load(UserGroup.class, id);

		if (!userGroup.getUsers().isEmpty()) {
			throw new UserException(
					PropertiesConstants.USERGROUP_MAINTENANCE_DELETE_FAILED_USERS);
		}

		userGroup.setUpdateBy(updateBy);
		userGroup.setUpdateDate(updateDate);
		userGroup.setRecStatus(GeneralConstants.REC_STATUS_NONACTIVE);

		getSession().update(userGroup);
		saveActivityLog(ActionType.DELETE, "Delete usergroup with id " + id);

		commit();
	}

	public List<Module> getChildModules() throws AppException {
		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.or(Restrictions.isNull("childs"),
				Restrictions.isEmpty("childs")));

		criteria.add(Restrictions.eq("recStatus",
				GeneralConstants.REC_STATUS_ACTIVE));

		@SuppressWarnings("unchecked")
		List<Module> modules = criteria.list();
		return modules;
	}

	public UserGroup getDetail(String userGroupId) throws AppException {
		if (userGroupId == null || userGroupId.trim().isEmpty()) {
			throw new AppException(
					PropertiesConstants.ERROR_PRIMARY_KEY_REQUIRED);
		}

		Criteria criteria = getSession().createCriteria(UserGroup.class);
		criteria.add(Restrictions.eq("id", userGroupId).ignoreCase());
		criteria.add(Restrictions.eq("recStatus",
				GeneralConstants.REC_STATUS_ACTIVE));

		return (UserGroup) criteria.uniqueResult();
	}

	public List<Module> getRootModules() throws AppException {
		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.isNull("parent.id"));
		criteria.add(Restrictions.eq("recStatus",
				GeneralConstants.REC_STATUS_ACTIVE));

		@SuppressWarnings("unchecked")
		List<Module> modules = criteria.list();
		return modules;
	}

	public List<UserGroup> search(String name, String description)
			throws AppException {

		Criteria criteria = getSession().createCriteria(UserGroup.class);

		if (name != null && !name.trim().isEmpty()) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}

		if (description != null && !description.trim().isEmpty()) {
			criteria.add(Restrictions.like("description", "%" + description
					+ "%"));
		}

		criteria.add(Restrictions.eq("recStatus",
				GeneralConstants.REC_STATUS_ACTIVE));

		@SuppressWarnings("unchecked")
		List<UserGroup> userGroups = criteria.list();
		return userGroups;
	}

	public void update(String id, String newName, String newDescription,
			String updateBy, Timestamp updateDate, List<String> newModules)
			throws AppException {

		beginTransaction();

		UserGroup userGroup = (UserGroup) getSession()
				.load(UserGroup.class, id);
		userGroup.setName(newName);
		userGroup.setDescription(newDescription);
		userGroup.setUpdateBy(updateBy);
		userGroup.setUpdateDate(updateDate);

		userGroup.getModules().clear();
		for (String moduleId : newModules) {
			Module module = (Module) getSession().load(Module.class, moduleId);
			userGroup.getModules().add(module);
		}

		getSession().save(userGroup);
		saveActivityLog(ActionType.UPDATE, userGroup);

		commit();
	}
}
