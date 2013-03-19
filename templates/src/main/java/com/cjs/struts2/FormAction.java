package com.cjs.struts2;

import org.slf4j.LoggerFactory;

import com.cjs.basicweb.model.FormBean;
import com.cjs.basicweb.utility.PropertiesConstants;
import com.cjs.core.exception.AppException;
import com.opensymphony.xwork2.ModelDriven;

public abstract class FormAction<T, U> extends BaseAction<U> implements
		ModelDriven<T> {

	private static final long serialVersionUID = -3643549719278354411L;

	private Class<T> clazz;

	public FormAction(Class<T> formClass, Class<U> logicClass)
			throws AppException {
		super(logicClass);
		if (FormBean.class.isAssignableFrom(formClass)) {
			this.clazz = formClass;
		} else {
			throw new AppException(PropertiesConstants.ERROR_CREATE_FORM_BEAN);
		}
	}

	@Override
	public final T getModel() {

		T formBean = getFromModuleSession(clazz);

		if (formBean == null) {
			try {
				formBean = clazz.newInstance();
				getModuleSession().put(clazz.getName(), formBean);
			} catch (InstantiationException e) {
				LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
			}
		}

		return formBean;
	}

	protected final T getForm() {
		return getModel();
	}

	protected final boolean validateForm() throws AppException {
		((FormBean) getForm()).validate(this);
		if (hasErrors()) {
			return false;
		} else {
			return true;
		}
	}

	private T getFromModuleSession(Class<T> clazz) {
		Object object = getModuleSession().get(clazz.getName());

		@SuppressWarnings("unchecked")
		T t = (T) object;
		return t;
	}
}
