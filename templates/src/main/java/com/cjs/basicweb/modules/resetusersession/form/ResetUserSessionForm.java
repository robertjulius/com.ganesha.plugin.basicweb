package com.cjs.basicweb.modules.resetusersession.form;

import java.util.List;

import com.cjs.basicweb.model.FormBean;
import com.cjs.basicweb.modules.login.usersession.SimpleUserSession;

public class ResetUserSessionForm extends FormBean {

	private static final long serialVersionUID = -497930027977555847L;

	private String userId;
	private List<SimpleUserSession> userSessions;
	private SimpleUserSession tobeReset;

	public SimpleUserSession getTobeReset() {
		return tobeReset;
	}

	public String getUserId() {
		return userId;
	}

	public List<SimpleUserSession> getUserSessions() {
		return userSessions;
	}

	public void setTobeReset(SimpleUserSession tobeReset) {
		this.tobeReset = tobeReset;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserSessions(List<SimpleUserSession> userSessions) {
		this.userSessions = userSessions;
	}

}
