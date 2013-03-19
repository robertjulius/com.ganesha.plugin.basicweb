package com.cjs.basicweb.utility;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.LoggerFactory;

import com.cjs.core.exception.AppException;

public class SimpleSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		String sessionId = session.getId();
		LoggerFactory.getLogger(getClass()).info(
				"HttpSession '" + session + "' with ID '" + sessionId
						+ "' created");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

		try {
			HttpSession httpSession = httpSessionEvent.getSession();

			AppContextManager.getSessionManager()
					.unregisterSession(httpSession);
			clearSession(httpSession);

			LoggerFactory.getLogger(getClass()).info(
					"HttpSession '" + httpSession + "' with ID '"
							+ httpSession.getId() + "' destroyed");

		} catch (AppException e) {
			LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
		}
	}

	private void clearSession(HttpSession httpSession) {
		synchronized (httpSession) {
			Enumeration<String> attributeNamesEnum = httpSession
					.getAttributeNames();
			while (attributeNamesEnum.hasMoreElements()) {
				httpSession.removeAttribute(attributeNamesEnum.nextElement());
			}
		}
	}
}
