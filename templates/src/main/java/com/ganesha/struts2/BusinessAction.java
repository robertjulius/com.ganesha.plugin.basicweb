package com.ganesha.struts2;

import com.ganesha.basicweb.modules.BusinessLogic;
import com.ganesha.basicweb.utility.PropertiesConstants;
import com.ganesha.core.exception.AppException;

public abstract class BusinessAction<T> extends BaseAction {

	private static final long serialVersionUID = -3643549719278354411L;
	private T logic;

	public BusinessAction(Class<T> clazz) throws AppException {
		try {
			if (BusinessLogic.class.isAssignableFrom(clazz)) {
				this.logic = clazz.newInstance();
			} else {
				throw new AppException(
						PropertiesConstants.ERROR_CREATE_BUSINESS_LOGIC);
			}
		} catch (InstantiationException e) {
			throw new AppException(
					PropertiesConstants.ERROR_CREATE_BUSINESS_LOGIC);
		} catch (IllegalAccessException e) {
			throw new AppException(
					PropertiesConstants.ERROR_CREATE_BUSINESS_LOGIC);
		}
	}

	public T getBL() {
		return logic;
	}
}
