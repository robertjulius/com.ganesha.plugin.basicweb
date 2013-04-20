package com.ganesha.struts2;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.ganesha.basicweb.modules.ModuleSession;
import com.ganesha.basicweb.utility.GeneralConstants;
import com.ganesha.core.UserSession;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = -3643549719278354411L;

	private SessionMap<String, Object> sessionMap;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public final String chainAction() {
		return SUCCESS;
	}

	public final ModuleSession getModuleSession() {
		return (ModuleSession) sessionMap.get(GeneralConstants.MODULE_SESSION);
	}

	public final HttpServletRequest getRequest() {
		return request;
	}

	public final HttpServletResponse getResponse() {
		return response;
	}

	public final SessionMap<String, Object> getSession() {
		return sessionMap;
	}

	public final UserSession getUserSession() {
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
	public final void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public final void setSession(Map<String, Object> map) {
		sessionMap = (SessionMap<String, Object>) map;
	}
}
