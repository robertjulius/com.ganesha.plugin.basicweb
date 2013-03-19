package com.cjs.basicweb.utility;

import java.util.List;

public class PageFail {

	private String sessionExpiredPage;
	private String notAuthorizedPage;
	private String errorPage;
	private List<String> urlDoesntNeedSession;
	private List<String> urlDoesntNeedPrivilege;

	public PageFail(String sessionExpiredPage, String notAuthorizedPage,
			String errorPage) {
		this.sessionExpiredPage = sessionExpiredPage;
		this.notAuthorizedPage = notAuthorizedPage;
		this.errorPage = errorPage;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public String getNotAuthorizedPage() {
		return notAuthorizedPage;
	}

	public String getSessionExpiredPage() {
		return sessionExpiredPage;
	}

	public List<String> getUrlDoesntNeedPrivilege() {
		return urlDoesntNeedPrivilege;
	}

	public List<String> getUrlDoesntNeedSession() {
		return urlDoesntNeedSession;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public void setNotAuthorizedPage(String notAuthorizedPage) {
		this.notAuthorizedPage = notAuthorizedPage;
	}

	public void setSessionExpiredPage(String sessionExpiredPage) {
		this.sessionExpiredPage = sessionExpiredPage;
	}

	public void setUrlDoesntNeedPrivilege(List<String> urlDoesntNeedPrivilege) {
		this.urlDoesntNeedPrivilege = urlDoesntNeedPrivilege;
	}

	public void setUrlDoesntNeedSession(List<String> urlDoesntNeedSession) {
		this.urlDoesntNeedSession = urlDoesntNeedSession;
	}
}
