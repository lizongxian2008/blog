/*   
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xuanwu.web.common.entity.DynamicParam;
import com.xuanwu.web.common.entity.PermissionDepends;
import com.xuanwu.web.common.entity.Role;
import com.xuanwu.web.common.entity.RolePermission;

/**
 * @Description: 角色数据映射
 * @Author <a href="jiji@javawind.com">Xuefang.Xu</a>
 * @Date 2012-9-6
 * @Version 1.0.0
 */
public interface RoleMapper {

	public int findRolesCount(@Param("role")Role role, @Param("param")DynamicParam param);

	public List<Role> findRoles(@Param("role")Role role, @Param("param")DynamicParam param);
	
	public List<Integer> findBasePermissionIds(@Param("baseType")int baseType);

	public Role findRoleById(@Param("id") int id);

	public Role findRoleByName(@Param("entId")int entId, @Param("roleName") String roleName, @Param("baseType") int baseType);

	public void addRole(Role role);
	
	public void updateUserRoleByEnt(@Param("oldRoleId")int oldRoleId, @Param("newRoleId")int newRoleId, @Param("entId")int entId);

	public void cloneRolePermission(@Param("oldRoleId")int oldRoleId, @Param("newRoleId")int newRoleId);
	
	public void addUserRole(@Param("userId") int userId,
			@Param("roleId") int roleId);

	public void addRolePermissions(RolePermission rolePermission);

	public int findLastInsertId();

	public void updateRole(Role role);

	public void deleteRole(@Param("id") int id);

	public void deleteUserRoleByRoleId(@Param("roleId") int roleId);

	public void deleteRolePermissionsByRoleId(@Param("roleId") int roleId);

	public List<PermissionDepends> findPermissionDepends();
	
	public List<Role> findDefaultRoles();

}
