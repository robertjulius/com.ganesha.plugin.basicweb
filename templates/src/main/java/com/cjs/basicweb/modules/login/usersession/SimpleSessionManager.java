package com.cjs.basicweb.modules.login.usersession;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import com.cjs.basicweb.utility.GeneralConstants;
import com.cjs.basicweb.utility.PropertiesConstants;
import com.cjs.core.SessionManager;
import com.cjs.core.UserSession;
import com.cjs.core.exception.AppException;

public class SimpleSessionManager implements SessionManager {

	private Map<String, HttpSession> mapByUserId = new ConcurrentHashMap<>();

	@Override
	public HttpSession getBySessionId(String sessionId) {
		List<HttpSession> sessions = getSessions();
		for (HttpSession httpSession : sessions) {
			if (httpSession.getId().equals(sessionId)) {
				return httpSession;
			}
		}
		return null;
	}

	@Override
	public HttpSession getByUserId(String userId) {
		return mapByUserId.get(userId);
	}

	@Override
	public List<HttpSession> getSessions() {
		List<HttpSession> httpSessions = new ArrayList<>();
		Iterator<String> iterator = mapByUserId.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			HttpSession httpSession = mapByUserId.get(key);
			httpSessions.add(httpSession);
		}
		return httpSessions;
	}

	public List<UserSession> getUserSessions() {
		List<UserSession> userSessions = new ArrayList<>();
		Iterator<String> iterator = mapByUserId.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			HttpSession httpSession = mapByUserId.get(key);
			userSessions.add((UserSession) httpSession
					.getAttribute(GeneralConstants.USER_SESSION));
		}
		return userSessions;
	}

	@Override
	public void registerSession(HttpSession session) throws AppException {

		String userId = ((UserSession) session
				.getAttribute(GeneralConstants.USER_SESSION)).getUser()
				.getUserId();

		if (mapByUserId.containsKey(userId)) {
			throw new AppException(
					PropertiesConstants.ERROR_DUPLICATE_REGISTER_USERSESSION);
		}

		mapByUserId.put(userId, session);
	}

	@Override
	public void unregisterSession(HttpSession httpSession) {
		UserSession userSession = (UserSession) httpSession
				.getAttribute(GeneralConstants.USER_SESSION);

		if (userSession != null) {
			mapByUserId.remove(userSession.getUser().getUserId());
		}
	}
}
