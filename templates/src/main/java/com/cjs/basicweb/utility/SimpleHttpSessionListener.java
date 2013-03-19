package com.cjs.basicweb.utility;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.LoggerFactory;

public class SimpleHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();

		LoggerFactory.getLogger(getClass()).info(
				"HttpSession '" + session + "' with ID '" + session.getId()
						+ "' created");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

		HttpSession session = se.getSession();

//		synchronized (session) {
//			Enumeration<String> attributeNamesEnum = session
//					.getAttributeNames();
//			while (attributeNamesEnum.hasMoreElements()) {
//				session.removeAttribute(attributeNamesEnum.nextElement());
//			}
//		}

		LoggerFactory.getLogger(getClass()).info(
				"HttpSession '" + session + "' with ID '" + session.getId()
						+ "' destroyed");
	}
}
