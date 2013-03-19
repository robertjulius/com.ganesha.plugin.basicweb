package com.cjs.basicweb.modules.module;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.cjs.basicweb.model.Pagination;
import com.cjs.basicweb.model.accesspath.AccessPath;
import com.cjs.basicweb.model.module.Module;
import com.cjs.basicweb.modules.BusinessLogic;
import com.cjs.basicweb.utility.GeneralConstants;
import com.cjs.basicweb.utility.GeneralConstants.ActionType;
import com.cjs.basicweb.utility.PropertiesConstants;
import com.cjs.core.exception.AppException;
import com.cjs.core.exception.UserException;

public class ModuleBL extends BusinessLogic {

	public void create(String newFirstEntry, String newName,
			String newDescription, String newParentId,
			List<String> newAccesssPaths, String createBy, Timestamp createDate)
			throws AppException {

		beginTransaction();

		Module module = new Module();
		if (newFirstEntry != null && newFirstEntry.trim().isEmpty()) {
			newFirstEntry = null;
		}
		module.setFirstEntry(newFirstEntry);
		module.setName(newName);
		module.setDescription(newDescription);
		module.setCreateBy(createBy);
		module.setCreateDate(createDate);
		module.setUpdateBy(createBy);
		module.setUpdateDate(createDate);
		module.setRecStatus(GeneralConstants.REC_STATUS_ACTIVE);

		if (newParentId == null || newParentId.trim().isEmpty()) {
			module.setParent(null);
		} else {
			Module parent = (Module) getSession().load(Module.class,
					newParentId);
			module.setParent(parent);
		}

		for (String url : newAccesssPaths) {
			AccessPath accessPath = new AccessPath();
			accessPath.setModule(module);
			accessPath.setUrl(url);
			getSession().save(accessPath);
		}

		getSession().save(module);
		saveActivityLog(ActionType.CREATE, module);

		commit();
	}

	public void delete(String id) throws UserException, AppException {
		checkDependencyToMsPrivilege(id);

		beginTransaction();

		Module module = (Module) getSession().load(Module.class, id);
		recursiveDelete(module);

		saveActivityLog(ActionType.DELETE, "Delete module with id " + id);

		commit();
	}

	public List<Module> getAllModules() throws AppException {
		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("recStatus",
				GeneralConstants.REC_STATUS_ACTIVE));

		@SuppressWarnings("unchecked")
		List<Module> modules = criteria.list();
		return modules;
	}

	public Module getDetail(String moduleId) throws AppException {
		if (moduleId == null || moduleId.trim().isEmpty()) {
			throw new AppException(
					PropertiesConstants.ERROR_PRIMARY_KEY_REQUIRED);
		}

		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("id", moduleId).ignoreCase());
		criteria.add(Restrictions.eq("recStatus",
				GeneralConstants.REC_STATUS_ACTIVE));

		return (Module) criteria.uniqueResult();
	}

	public List<Module> search(String name, String firstEntry, String parentId,
			Pagination pagination) throws AppException {

		Criteria criteria = getSession().createCriteria(Module.class);

		if (name != null && !name.trim().isEmpty()) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}

		if (firstEntry != null && !firstEntry.trim().isEmpty()) {
			criteria.add(Restrictions
					.like("firstEntry", "%" + firstEntry + "%"));
		}

		if (parentId != null && !parentId.trim().isEmpty()) {
			criteria.add(Restrictions.like("parent.id", "%" + parentId + "%"));
		}

		criteria.setFirstResult((pagination.getPageNumber() - 1)
				* pagination.getRowsPerPage());
		criteria.setMaxResults(pagination.getRowsPerPage());

		criteria.add(Restrictions.eq("recStatus",
				GeneralConstants.REC_STATUS_ACTIVE));

		@SuppressWarnings("unchecked")
		List<Module> modules = criteria.list();

		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		int rowCount = (int) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		pagination.setRowCount(rowCount);

		return modules;
	}

	public void update(String id, String newFirstEntry, String newName,
			String newDescription, String newParentId,
			List<String> newAccesssPaths, String updateBy, Timestamp updateDate)
			throws AppException {

		beginTransaction();

		Module module = (Module) getSession().load(Module.class, id);
		if (newFirstEntry != null && newFirstEntry.trim().isEmpty()) {
			newFirstEntry = null;
		}
		module.setFirstEntry(newFirstEntry);
		module.setName(newName);
		module.setDescription(newDescription);
		module.setUpdateBy(updateBy);
		module.setUpdateDate(updateDate);

		if (newParentId == null || newParentId.trim().isEmpty()) {
			module.setParent(null);
		} else {
			Module parent = (Module) getSession().load(Module.class,
					newParentId);
			module.setParent(parent);
		}

		deleteMsAccessPath(id);
		for (String url : newAccesssPaths) {
			AccessPath accessPath = new AccessPath();
			accessPath.setModule(module);
			accessPath.setUrl(url);
			getSession().save(accessPath);
		}

		getSession().save(module);
		saveActivityLog(ActionType.UPDATE, module);

		commit();
	}

	private void checkDependencyToMsPrivilege(String moduleId)
			throws UserException, AppException {
		SQLQuery sqlQuery = getSession()
				.createSQLQuery(
						"select COUNT(1) FROM ms_privilege WHERE module_id = :moduleId");
		sqlQuery.setString("moduleId", moduleId);
		BigInteger count = (BigInteger) sqlQuery.list().get(0);
		if (count.intValue() > 0) {
			throw new UserException(
					PropertiesConstants.MODULE_MAINTENANCE_DELETE_FAILED_DEPENDENCY_ACCESSPATH);
		}
	}

	private void deleteMsAccessPath(String moduleId) throws AppException {
		SQLQuery sqlQuery = getSession().createSQLQuery(
				"DELETE FROM ms_access_path WHERE module_id = :moduleId");
		sqlQuery.setString("moduleId", moduleId);
		sqlQuery.executeUpdate();
	}

	private void recursiveDelete(Module module) throws UserException,
			AppException {
		if (!module.getChilds().isEmpty()) {
			List<Module> childs = module.getChilds();
			for (Module child : childs) {
				recursiveDelete(child);
			}
		}
		deleteMsAccessPath(module.getId());
		getSession().delete(module);
	}
}
