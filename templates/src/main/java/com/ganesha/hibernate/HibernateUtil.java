package com.ganesha.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new AnnotationConfiguration().configure()
					.buildSessionFactory();

		} catch (Throwable e) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + e);
			LoggerFactory.getLogger(HibernateUtil.class).error(
					"Initial SessionFactory creation failed:" + e.getMessage(),
					e);
			throw e;
		}
	}
}