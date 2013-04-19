package com.ganesha.basicweb.modules.usermaintenance;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ganesha.basicweb.model.Pagination;
import com.ganesha.basicweb.model.user.SimpleUser;
import com.ganesha.basicweb.model.usergroup.UserGroup;
import com.ganesha.basicweb.modules.BusinessLogic;
import com.ganesha.basicweb.utility.GeneralConstants;
import com.ganesha.basicweb.utility.GeneralConstants.ActionType;
import com.ganesha.basicweb.utility.PropertiesConstants;
import com.ganesha.core.User;
import com.ganesha.core.exception.AppException;
import com.ganesha.core.exception.UserException;

public class UserMaintenanceBL extends BusinessLogic {

	public void create(String newUserId, String newName, String newPassword,
			String newUserGroupId, String createBy, Timestamp createDate)
			throws AppException {

		beginTransaction();

		SimpleUser user = new SimpleUser();
		user.setUserId(newUserId);
		user.setName(newName);
		user.setPassword(newPassword);
		user.setCreateBy(createBy);
		user.setCreateDate(createDate);
		user.setUpdateBy(createBy);
		user.setUpdateDate(createDate);
		user.setRecStatus(GeneralConstants.REC_STATUS_ACTIVE);

		UserGroup userGroup = (UserGroup) getSession().load(UserGroup.class,
				newUserGroupId);
		user.setUserGroup(userGroup);

		getSession().save(user);
		saveActivityLog(ActionType.CREATE, user);

		commit();
	}

	public void delete(String id, String updateBy, Timestamp updateDate)
			throws UserException, AppException {
		beginTransaction();

		SimpleUser user = (SimpleUser) getSession().load(SimpleUser.class, id);

		user.setUpdateBy(updateBy);
		user.setUpdateDate(updateDate);
		user.setRecStatus(GeneralConstants.REC_STATUS_NONACTIVE);

		getSession().update(user);
		saveActivityLog(ActionType.DELETE, "Delete user with id " + id);

		commit();
	}

	public List<UserGroup> getAllUserGroup() throws AppException {
		Criteria criteria = getSession().createCriteria(UserGroup.class);
		criteria.add(Restrictions.eq("recStatus",
				GeneralConstants.REC_STATUS_ACTIVE));

		@SuppressWarnings("unchecked")
		List<UserGroup> userGroups = criteria.list();
		return userGroups;
	}

	public SimpleUser getDetail(String id) throws AppException {
		if (id == null || id.trim().isEmpty()) {
			throw new AppException(
					PropertiesConstants.ERROR_PRIMARY_KEY_REQUIRED);
		}

		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.eq("recStatus",
				GeneralConstants.REC_STATUS_ACTIVE));

		return (SimpleUser) criteria.uniqueResult();
	}

	public List<SimpleUser> search(String userId, String name,
			String userGroupName, Pagination pagination) throws AppException {

		Criteria criteria = getSession().createCriteria(SimpleUser.class);

		if (userId != null && !userId.trim().isEmpty()) {
			criteria.add(Restrictions.like("userId", "%" + name + "%"));
		}

		if (name != null && !name.trim().isEmpty()) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}

		if (userGroupName != null && !userGroupName.trim().isEmpty()) {
			criteria.add(Restrictions.like("userGroup.name", "%"
					+ userGroupName + "%"));
		}

		criteria.setFirstResult((pagination.getPageNumber() - 1)
				* pagination.getRowsPerPage());
		criteria.setMaxResults(pagination.getRowsPerPage());

		criteria.add(Restrictions.eq("recStatus",
				GeneralConstants.REC_STATUS_ACTIVE));

		@SuppressWarnings("unchecked")
		List<SimpleUser> users = criteria.list();

		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		int rowCount = (int) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		pagination.setRowCount(rowCount);

		return users;
	}

	public void update(String id, String newUserId, String newName,
			String newUserGroupId, String updateBy, Timestamp updateDate)
			throws AppException {

		beginTransaction();

		SimpleUser user = (SimpleUser) getSession().load(SimpleUser.class, id);
		user.setUserId(newUserId);
		user.setName(newName);
		user.setUpdateBy(updateBy);
		user.setUpdateDate(updateDate);

		UserGroup userGroup = (UserGroup) getSession().load(UserGroup.class,
				newUserGroupId);
		user.setUserGroup(userGroup);

		getSession().save(user);
		saveActivityLog(ActionType.UPDATE, user);

		commit();
	}
}
