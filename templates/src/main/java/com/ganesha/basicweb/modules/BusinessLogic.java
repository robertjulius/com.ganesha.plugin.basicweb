package com.ganesha.basicweb.modules;

import java.util.Map;

import org.hibernate.Session;

import com.ganesha.basicweb.model.activitylog.ActivityLog;
import com.ganesha.basicweb.model.user.SimpleUser;
import com.ganesha.basicweb.utility.CommonUtils;
import com.ganesha.basicweb.utility.GeneralConstants;
import com.ganesha.basicweb.utility.GeneralConstants.ActionType;
import com.ganesha.core.UserSession;
import com.ganesha.core.exception.AppException;
import com.ganesha.core.utils.MappingUtils;
import com.ganesha.hibernate.HibernateUtil;
import com.opensymphony.xwork2.ActionContext;

public abstract class BusinessLogic {

	public void beginTransaction() throws AppException {
		try {
			HibernateUtil.beginTransaction();
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public final void commit() throws AppException {
		try {
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public final Session getSession() throws AppException {
		try {
			return HibernateUtil.getCurrentSession();
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public final void rollback() throws AppException {
		try {
			HibernateUtil.rollbackTransaction();
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public final void saveActivityLog(ActionType actionType,
			Object affectedObject) throws AppException {
		String description = MappingUtils.getObjectValues(affectedObject);
		saveActivityLog(actionType, description);
	}

	public final void saveActivityLog(ActionType actionType, String description)
			throws AppException {

		Map<String, Object> session = ActionContext.getContext().getSession();
		UserSession userSession = (UserSession) session
				.get(GeneralConstants.USER_SESSION);
		SimpleUser user = (SimpleUser) userSession.getUser();

		ModuleSession moduleSession = (ModuleSession) session
				.get(GeneralConstants.MODULE_SESSION);

		ActivityLog log = new ActivityLog();
		log.setUser(user.getId());
		log.setUserId(user.getUserId());
		log.setUserName(user.getName());
		log.setActionUrl(moduleSession.getUrl());
		log.setActionType(String.valueOf(actionType));
		log.setDescription(description);
		log.setActionDate(CommonUtils.getCurrentTimestamp());

		getSession().save(log);
	}
}
