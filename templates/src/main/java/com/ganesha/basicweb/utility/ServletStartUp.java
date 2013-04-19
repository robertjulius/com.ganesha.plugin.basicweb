package com.ganesha.basicweb.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;

import com.ganesha.basicweb.model.systemsetting.SystemSetting;
import com.ganesha.hibernate.HibernateUtil;

public class ServletStartUp extends HttpServlet {
	private static final long serialVersionUID = -789475497301128186L;

	public ServletStartUp() {
		try {
			loadSystemSetting();
		} catch (Exception e) {
			LoggerFactory.getLogger(HibernateUtil.class).error(e.getMessage(),
					e);
		}
	}

	private void loadSystemSetting() throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;

		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		if (!session.isOpen()) {
			session = sessionFactory.openSession();
		}

		try {
			Criteria criteria = session.createCriteria(SystemSetting.class);
			@SuppressWarnings("unchecked")
			List<SystemSetting> systemSettings = criteria.list();
			for (SystemSetting systemSetting : systemSettings) {
				SystemSetting.setProperty(systemSetting.getId(),
						systemSetting.getValue());
			}
			synchConstants();
		} finally {
			session.close();
		}
	}

	private void synchConstants() throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;

		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		if (!session.isOpen()) {
			session = sessionFactory.openSession();
		}

		try {
			session.beginTransaction();
			Field[] fields = PropertiesConstants.class.getDeclaredFields();
			for (Field field : fields) {
				if (!SystemSetting.containsId(field.getName())) {
					if (Modifier.isStatic(field.getModifiers())
							&& Modifier.isFinal(field.getModifiers())
							&& field.getName().equals(
									field.getName().toUpperCase())) {

						String id = (String) field.get(null);

						SystemSetting systemSetting = new SystemSetting();
						systemSetting.setId(id);
						systemSetting.setValue(GeneralConstants.EMPTY_STRING);
						session.save(systemSetting);

						SystemSetting.setProperty(id,
								GeneralConstants.EMPTY_STRING);
					}
				}
			}
		} finally {
			session.getTransaction().commit();
			session.close();
		}
	}
}
