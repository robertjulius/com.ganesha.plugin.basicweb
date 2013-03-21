package com.ganesha.core;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.ganesha.core.exception.AppException;

public interface SessionManager {

	public HttpSession getBySessionId(String sessionId);

	public HttpSession getByUserId(String userId);

	public List<HttpSession> getSessions();

	public void registerSession(HttpSession httpSession) throws AppException;
	
	public void unregisterSession(HttpSession httpSession) throws AppException;
}
