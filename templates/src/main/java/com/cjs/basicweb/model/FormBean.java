package com.cjs.basicweb.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.cjs.basicweb.utility.PropertiesConstants;
import com.cjs.core.exception.AppException;
import com.cjs.core.utils.MappingUtils;
import com.opensymphony.xwork2.ActionSupport;

public abstract class FormBean implements Serializable {

	private enum PrefixMode {
		ADD, REMOVE;
	}

	private static final long serialVersionUID = 1L;

	public void clearForm(String prefix) throws AppException {
		try {
			Method[] methods = this.getClass().getMethods();
			for (Method method : methods) {
				if (method.getParameterTypes().length == 1) {
					if (method.getName().startsWith(
							"set" + prefix.substring(0, 1).toUpperCase()
									+ prefix.substring(1))) {
						Object[] params = new Object[1];
						params[0] = null;
						method.invoke(this, params);
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw new AppException(e);
		} catch (IllegalArgumentException e) {
			throw new AppException(e);
		} catch (InvocationTargetException e) {
			throw new AppException(e);
		}
	}

	public <T> void assignFromEntity(String prefix, T entity)
			throws AppException {
		Map<String, Object> map = toMap(entity, PrefixMode.ADD, prefix);
		MappingUtils.mapToPojo(map, this);
	}

	public <T> void assignFromEntity(T entity) throws AppException {
		assignFromEntity(null, entity);
	}

	public <T> void assignToEntity(String prefix, T entity) throws AppException {

		Map<String, Object> map = toMap(this, PrefixMode.REMOVE, prefix);
		MappingUtils.mapToPojo(map, entity);
	}

	public <T> void assignToEntity(T entity) throws AppException {
		assignToEntity(null, entity);
	}

	public void validate(ActionSupport action) throws AppException {
		/*
		 * Do nothing. Lets the subclass implement it.
		 */
	}

	private <T> Map<String, Object> toMap(T entity, PrefixMode prefixMode,
			String prefix) throws AppException {
		try {
			Map<String, Object> map = new HashMap<>();
			Method[] methods = entity.getClass().getMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					if (method.getParameterTypes().length != 0) {
						throw new AppException(
								PropertiesConstants.ERROR_REFLECTION);
					}

					Object value = method.invoke(entity);
					String name = method.getName().substring(3);

					if (prefixMode == PrefixMode.ADD) {
						name = prefix + name;
					} else if (prefixMode == PrefixMode.REMOVE) {
						name = name.substring(0, 1).toLowerCase()
								+ name.substring(1);
						name = name.replaceFirst(prefix, "");
						name = name.substring(0, 1).toLowerCase()
								+ name.substring(1);
					} else {
						throw new AppException(
								PropertiesConstants.ERROR_REFLECTION);
					}

					map.put(name, value);
				}
			}
			return map;
		} catch (IllegalAccessException e) {
			throw new AppException(e);
		} catch (IllegalArgumentException e) {
			throw new AppException(e);
		} catch (InvocationTargetException e) {
			throw new AppException(e);
		}
	}
}
