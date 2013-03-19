package com.cjs.basicweb.modules;

import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.cjs.basicweb.model.activitylog.ActivityLog;
import com.cjs.basicweb.model.user.SimpleUser;
import com.cjs.basicweb.utility.CommonUtils;
import com.cjs.basicweb.utility.GeneralConstants;
import com.cjs.basicweb.utility.GeneralConstants.ActionType;
import com.cjs.core.UserSession;
import com.cjs.core.exception.AppException;
import com.cjs.core.utils.MappingUtils;
import com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory;
import com.opensymphony.xwork2.ActionContext;

public abstract class BusinessLogic {

	public void beginTransaction() throws AppException {
		try {
			HibernateSessionFactory.getSession().getTransaction().begin();
		} catch (HibernateException e) {
			throw new AppException(e);
		}
	}

	public final void commit() throws AppException {
		try {
			HibernateSessionFactory.getSession().getTransaction().commit();
		} catch (HibernateException e) {
			throw new AppException(e);
		}
	}

	public final Session getSession() throws AppException {
		try {
			return HibernateSessionFactory.getSession();
		} catch (HibernateException e) {
			throw new AppException(e);
		}
	}

	public final void rollback() throws AppException {
		try {
			HibernateSessionFactory.getSession().getTransaction().rollback();
		} catch (HibernateException e) {
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
