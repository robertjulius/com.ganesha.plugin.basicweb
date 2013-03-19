package com.cjs.struts2;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.cjs.basicweb.modules.BusinessLogic;
import com.cjs.basicweb.modules.ModuleSession;
import com.cjs.basicweb.utility.GeneralConstants;
import com.cjs.basicweb.utility.PropertiesConstants;
import com.cjs.core.UserSession;
import com.cjs.core.exception.AppException;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction<T> extends ActionSupport implements
		SessionAware, ServletRequestAware {

	private static final long serialVersionUID = -3643549719278354411L;

	private SessionMap<String, Object> sessionMap;
	private HttpServletRequest request;
	private T logic;

	public BaseAction(Class<T> clazz) throws AppException {
		try {
			if (BusinessLogic.class.isAssignableFrom(clazz)) {
				this.logic = clazz.newInstance();
			} else {
				throw new AppException(
						PropertiesConstants.ERROR_CREATE_BUSINESS_LOGIC);
			}
		} catch (InstantiationException e) {
			throw new AppException(
					PropertiesConstants.ERROR_CREATE_BUSINESS_LOGIC);
		} catch (IllegalAccessException e) {
			throw new AppException(
					PropertiesConstants.ERROR_CREATE_BUSINESS_LOGIC);
		}
	}

	public final String chainAction() {
		return SUCCESS;
	}

	public T getBL() {
		return logic;
	}

	public ModuleSession getModuleSession() {
		return (ModuleSession) sessionMap.get(GeneralConstants.MODULE_SESSION);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public SessionMap<String, Object> getSession() {
		return sessionMap;
	}

	public UserSession getUserSession() {
		return (UserSession) getSession().get(GeneralConstants.USER_SESSION);
	}

	public final String redirectAction() {
		return SUCCESS;
	}

	@Override
	public final void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public final void setSession(Map<String, Object> map) {
		sessionMap = (SessionMap<String, Object>) map;
	}
}
