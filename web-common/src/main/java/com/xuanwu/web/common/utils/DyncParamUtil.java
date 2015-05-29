package com.xuanwu.web.common.utils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DyncParamUtil {

	public static Object match(Object a, Map<String, Object> map) {
		Set<String> keySet = map.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (!key.startsWith("_")) {
				replaceValue(a, key, map.get(key));
			}
		}
		return a;
	}

	private static Object replaceValue(Object a, String fieldName, Object v) {
		Field field = getField(a.getClass(), fieldName);
		field.setAccessible(true);
		try {
			field.set(a, v);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return a;
	}

	private static Field getField(Class<?> clazz, String name) {
		if (null == clazz) {
			return null;
		}
		Field field = null;
		try {
			field = clazz.getDeclaredField(name);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			Class<?> superclazz = clazz.getSuperclass();
			if (null != superclazz) {
				return getField(superclazz, name);
			}
		}
		return field;
	}
}
