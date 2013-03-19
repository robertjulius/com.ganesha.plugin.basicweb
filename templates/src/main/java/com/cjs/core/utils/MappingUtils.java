package com.cjs.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.cjs.basicweb.utility.PropertiesConstants;
import com.cjs.core.exception.AppException;

public class MappingUtils {

	public static String getObjectValues(Object object) throws AppException {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			Method[] methods = object.getClass().getMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {

					if (method.getName().equals("getHibernateLazyInitializer")
							|| method.getName().equals("getClass")) {
						continue;
					}

					if (method.getParameterTypes().length != 0) {
						throw new AppException(
								PropertiesConstants.ERROR_REFLECTION);
					}

					String name = method.getName().substring(3, 4)
							.toLowerCase()
							+ method.getName().substring(4);

					stringBuilder.append(";").append(name).append("=");

					Object value = method.invoke(object);
					if (value != null
							&& Collection.class.isAssignableFrom(value
									.getClass())) {
						stringBuilder.append("#")
								.append(value.getClass().getSimpleName())
								.append("#");
					} else {
						stringBuilder.append(value);
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

		return stringBuilder.toString().replaceFirst(";", "");
	}

	public static <T> T mapToPojo(Map<String, Object> map, Class<T> clazz)
			throws AppException {
		try {
			T newInstance = clazz.newInstance();
			mapToPojo(map, newInstance);
			return newInstance;
		} catch (InstantiationException e) {
			throw new AppException(e);
		} catch (IllegalAccessException e) {
			throw new AppException(e);
		}
	}

	public static <T> void mapToPojo(Map<String, Object> map, T pojo)
			throws AppException {
		try {
			Method[] methods = pojo.getClass().getMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("set")) {
					if (method.getParameterTypes().length != 1) {
						throw new AppException(
								PropertiesConstants.ERROR_REFLECTION);
					}

					String name = method.getName().substring(3, 4)
							.toLowerCase()
							+ method.getName().substring(4);

					if (map.containsKey(name)) {
						Object value = map.get(name);
						if (value != null) {
							Class<?> valueClass = value.getClass();
							Class<?> paramClass = method.getParameterTypes()[0];
							if (!paramClass.isAssignableFrom(valueClass)) {
								StringBuilder message = new StringBuilder();
								message.append("Method ").append(
										method.getName());
								message.append("(")
										.append(paramClass.getSimpleName())
										.append(") ")
										.append("cannot be applied for ");
								message.append(method.getName()).append("(")
										.append(valueClass.getSimpleName())
										.append(") ");

								throw new AppException(message.toString());
							}
						}
						method.invoke(pojo, value);
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

	public static Map<String, Object> pojoToMap(Object pojo)
			throws AppException {
		try {
			Map<String, Object> map = new HashMap<>();
			Method[] methods = pojo.getClass().getMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					if (method.getParameterTypes().length != 0) {
						throw new AppException(
								PropertiesConstants.ERROR_REFLECTION);
					}

					Object value = method.invoke(pojo);
					String name = method.getName().substring(3, 4)
							.toLowerCase()
							+ method.getName().substring(4);
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
