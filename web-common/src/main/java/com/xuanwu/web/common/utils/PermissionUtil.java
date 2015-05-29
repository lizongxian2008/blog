/*   
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.common.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.xuanwu.web.common.entity.Permission;

/**
 * @Description: 父子权限归属处理和排序
 * @Author <a href="jiji@javawind.com">Xuefang.Xu</a>
 * @Date 2012-9-7
 * @Version 1.0.0
 */
public class PermissionUtil {

	private final static String CHILD = "：";
	private final static String COM = "，";
	private final static String SEM = "；";
	private final static String LP = "(";
	private final static String RP = ")";

	/**
	 * 排列和归类权限
	 * 
	 * @param permissions
	 * @return
	 */
	public static List<Permission> sortPermissions(List<Permission> permissions) {
		List<Permission> list = new ArrayList<Permission>();
		if (permissions == null || permissions.size() == 0) {
			return list;
		}

		LinkedHashMap<Integer, Permission> map = new LinkedHashMap<Integer, Permission>();
		for (Permission p : permissions) {
			map.put(p.getId(), p);
		}

		for (Permission p : permissions) {
			int parentId = p.getParentId();
			if (parentId > 0) {
				Permission parent = map.get(parentId);
				if (parent != null) {
					map.get(p.getId()).setParent(parent);
					parent.addToChildren(p);
				}
			}
		}

		for (Entry<Integer, Permission> entry : map.entrySet()) {
			if (entry.getValue().getParentId() == 0) {
				list.add(entry.getValue());
			}
		}

		return list;
	}

	/**
	 * 选中权限
	 * 
	 * @param permissions
	 * @param checked
	 * @return
	 */
	public static List<Permission> checkPermissions(
			List<Permission> permissions, List<Permission> checked) {
		if (checked == null || checked.size() == 0) {
			return permissions;
		}

		for (Permission p : permissions) {
			for (Permission c : checked) {
				if (c.getId() == p.getId()) {
					p.setChecked(true);
					p.setDataScopeId(c.getDataScopeId());
					break;
				}
			}
			List<Permission> children = p.getChildren();
			if (children != null && children.size() > 0) {
				checkPermissions(children, checked);
			}
		}

		return permissions;
	}

	/**
	 * 生成权限字符串
	 * 
	 * @param permissions
	 * @return
	 */
	public static String getPermissionsStr(List<Permission> permissions) {
		if (permissions == null || permissions.size() == 0) {
			return "";
		}

		List<Permission> list = sortPermissions(permissions);

		StringBuffer sb = new StringBuffer();
		for (Permission p : list) {
			sb.append(getPermissionStr(p) + SEM);
		}
		return sb.toString();
	}

	/**
	 * 生成权限字符串
	 * 
	 * @param permission
	 * @return
	 */
	public static String getPermissionStr(Permission permission) {
		if (permission == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		sb.append(permission.getDisplayName());
		if (permission.getParentId() == 0) {
			sb.append(CHILD);
		}

		if (permission.getChildren() != null) {
			if (permission.getParentId() > 0) {
				sb.append(LP);
			}
			for (Permission c : permission.getChildren()) {
				String str = sb.toString();
				if (!str.endsWith(CHILD) && !str.endsWith(LP)) {
					sb.append(COM);
				}
				sb.append(getPermissionStr(c));
			}
			if (permission.getParentId() > 0) {
				sb.append(RP);
			}
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		List<Permission> list = new ArrayList<Permission>();
		Permission a = new Permission();
		a.setId(1);
		a.setParentId(0);
		a.setDisplayName("根目录");
		list.add(a);

		Permission b = new Permission();
		b.setId(2);
		b.setParentId(1);
		b.setDisplayName("一级目录");
		list.add(b);

		Permission c = new Permission();
		c.setId(3);
		c.setParentId(1);
		c.setDisplayName("一级目录");
		list.add(c);

		Permission d = new Permission();
		d.setId(4);
		d.setParentId(3);
		d.setDisplayName("二级目录");
		list.add(d);

		Permission e = new Permission();
		e.setId(5);
		e.setParentId(1);
		e.setDisplayName("一级目录");
		list.add(e);

		Permission f = new Permission();
		f.setId(6);
		f.setParentId(5);
		f.setDisplayName("二级目录");
		list.add(f);

		Permission g = new Permission();
		g.setId(7);
		g.setParentId(6);
		g.setDisplayName("三级目录");
		list.add(g);

		Permission h = new Permission();
		h.setId(8);
		h.setParentId(0);
		h.setDisplayName("根目录");
		list.add(h);

		List<Permission> ls = sortPermissions(list);
		System.out.println(getPermissionsStr(ls));
		System.out.println("----------------------------------");
		for (Permission p : ls) {
			System.out.println(getPermissionStr(p));
		}
	}
}
