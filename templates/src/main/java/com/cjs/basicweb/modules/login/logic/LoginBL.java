package com.cjs.basicweb.modules.login.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;

import com.cjs.basicweb.model.accesspath.AccessPath;
import com.cjs.basicweb.model.module.Module;
import com.cjs.basicweb.model.user.SimpleUser;
import com.cjs.basicweb.modules.BusinessLogic;
import com.cjs.basicweb.modules.login.Privilege;
import com.cjs.basicweb.modules.login.PrivilegeUtils;
import com.cjs.basicweb.modules.login.usersession.SimpleUserSession;
import com.cjs.basicweb.utility.CommonUtils;
import com.cjs.basicweb.utility.GeneralConstants;
import com.cjs.basicweb.utility.GeneralConstants.ActionType;
import com.cjs.basicweb.utility.PropertiesConstants;
import com.cjs.core.User;
import com.cjs.core.UserSession;
import com.cjs.core.exception.AppException;
import com.cjs.core.exception.UserException;

public class LoginBL extends BusinessLogic {

	public SimpleUser getByUserId(String userId) throws AppException {
		if (userId == null || userId.trim().isEmpty()) {
			throw new AppException(
					PropertiesConstants.ERROR_PRIMARY_KEY_REQUIRED);
		}

		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("userId", userId).ignoreCase());
		criteria.add(Restrictions.eq("recStatus",
				GeneralConstants.REC_STATUS_ACTIVE));

		return (SimpleUser) criteria.uniqueResult();
	}

	public SimpleUser performLogin(String userId, String password,
			UserSession userSession) throws UserException, AppException {

		SimpleUser user = getByUserId(userId);
		if (user == null) {
			throw new AppException(PropertiesConstants.INVALID_LOGIN_USERID);
		}

		validatePassword(user, password);

		prepareTreeMenu(user, userSession);

		((SimpleUserSession) userSession)
				.setAccessPath(prepareAccessPath(user));

		userSession.setUser(user);
		userSession.setLoginTime(CommonUtils.getCurrentTimestamp());

		beginTransaction();
		saveActivityLog(ActionType.OTHER, "");
		commit();

		return user;
	}

	private List<Module> getRootModules() throws AppException {
		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.isNull("parent.id"));

		@SuppressWarnings("unchecked")
		List<Module> modules = criteria.list();
		return modules;
	}

	private String[] prepareAccessPath(User user) {

		LoggerFactory.getLogger(getClass()).debug(
				"Begin to prepare access path");

		List<String> result = new ArrayList<>();
		List<Module> modules = ((SimpleUser) user).getUserGroup().getModules();
		for (Module module : modules) {
			List<AccessPath> accessPaths = module.getAccessPaths();
			for (AccessPath accessPath : accessPaths) {
				result.add(accessPath.getUrl());
			}
		}

		LoggerFactory.getLogger(getClass())
				.debug("Prepare access path success");

		return result.toArray(new String[] {});
	}

	private void prepareTreeMenu(User user, UserSession userSession)
			throws AppException {

		LoggerFactory.getLogger(getClass()).debug("Begin to prepare tree menu");

		List<Module> modules = ((SimpleUser) user).getUserGroup().getModules();

		List<String> privilegeIds = new ArrayList<>();
		for (Module module : modules) {
			privilegeIds.add(module.getId());
		}

		TreeMap<String, Privilege> treeMap = PrivilegeUtils.generateTree(
				privilegeIds.toArray(new String[] {}), getRootModules());

		((SimpleUserSession) userSession).setTreeMap(treeMap);

		LoggerFactory.getLogger(getClass()).debug("Prepare tree menu success");
	}

	private void validatePassword(User user, String password)
			throws UserException {

		LoggerFactory.getLogger(getClass()).debug("Begin to validate password");

		if (!user.getPassword().equals(password)) {
			throw new UserException(PropertiesConstants.INVALID_LOGIN_PASSWORD);
		}

		LoggerFactory.getLogger(getClass()).debug("Validate password success");
	}
}
