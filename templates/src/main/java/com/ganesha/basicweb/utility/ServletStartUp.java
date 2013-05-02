package com.ganesha.basicweb.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.hibernate.Criteria;
import org.slf4j.LoggerFactory;

import com.ganesha.basicweb.model.systemsetting.SystemSetting;
import com.ganesha.hibernate.HibernateUtil;

public class ServletStartUp extends HttpServlet {
	private static final long serialVersionUID = -789475497301128186L;

	public ServletStartUp() {
		try {
			loadSystemSetting();
		} catch (Exception e) {
			LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
		}
	}

	private void loadSystemSetting() throws Exception {
		try {
			Criteria criteria = HibernateUtil.getCurrentSession()
					.createCriteria(SystemSetting.class);
			@SuppressWarnings("unchecked")
			List<SystemSetting> systemSettings = criteria.list();
			for (SystemSetting systemSetting : systemSettings) {
				SystemSetting.setProperty(systemSetting.getId(),
						systemSetting.getValue());
			}
			synchConstants();
		} finally {
			HibernateUtil.closeSession();
		}
	}

	private void synchConstants() throws Exception {
		try {
			HibernateUtil.beginTransaction();
			Field[] fields = PropertiesConstants.class.getDeclaredFields();
			for (Field field : fields) {
				String id = (String) field.get(null);
				if (!SystemSetting.containsId(id)) {
					if (Modifier.isStatic(field.getModifiers())
							&& Modifier.isFinal(field.getModifiers())
							&& field.getName().equals(
									field.getName().toUpperCase())) {

						SystemSetting systemSetting = new SystemSetting();
						systemSetting.setId(id);
						systemSetting.setValue(GeneralConstants.EMPTY_STRING);
						HibernateUtil.getCurrentSession().save(systemSetting);

						SystemSetting.setProperty(id,
								GeneralConstants.EMPTY_STRING);
					}
				}
			}
		} finally {
			HibernateUtil.commitTransaction();
			HibernateUtil.closeSession();
		}
	}
}
