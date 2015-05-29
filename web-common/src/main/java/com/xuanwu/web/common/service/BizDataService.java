/*
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.common.service;

import java.util.List;

import com.xuanwu.web.common.entity.Permission;
import com.xuanwu.web.common.entity.Platform;
import com.xuanwu.web.common.entity.RolePermission;
import com.xuanwu.web.common.entity.User;

/**
 * @Description 基本业务数据服务
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-5
 * @Version 1.0.0
 */
public interface BizDataService {
	
	public enum SyncVersion{
		/** 对应于通道端口等版本号 */
		SPEC_SVS(1),
		/** 对应于号码段版本号 */
		PHONE_SEG(2),
		/** 对应于系统配置版本号 */
		SYS_CONFIG(3),
		/** 对应于优先级版本号 */
		PRIORITY(4),
		/** 对应于用户版本号 */
		USER(5),
		/** 对应于角色权限绑定关系版本号 */
		ROLE_PERM(6);
		private final int index;
		
		private SyncVersion(int index){
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}
		
		public static SyncVersion getType(int index){
			switch(index){
			case 1: return SPEC_SVS;
			case 2: return PHONE_SEG;
			case 3: return SYS_CONFIG;
			case 4: return PRIORITY;
			case 5: return USER;
			case 6: return ROLE_PERM;
			default: throw new RuntimeException("Unsupport gsms sync version type");
			}
		}
	}
	
	/**
	 * 同步角色权限关系
	 */
	public void syncRolePerm();
	
	/**
	 * Get permission by ID
	 * @param id
	 * @return permission
	 */
	public Permission getPermission(Integer id);
	
	/**
	 * Get permission by permUrl
	 * @param permUrl
	 * @return
	 */
	public Permission getPermission(String permUrl);
	
	/**
	 * get role-permission by roleID and platform
	 * @param roleID
	 * @param platform
	 * @return
	 */
	public List<RolePermission> getRolePermissions(Integer roleID, Platform platform);
	
	/**
	 * 通过企业ID取得所有部门信息，包含企业本身
	 * @param entId
	 * @param includeDel 
	 * @return
	 */
	public List<User> getDepts(int entId, boolean includeDel);
	
	/**
	 * 通过企业ID及部门ID取得部门信息
	 * @param entId 企业ID
	 * @param id 部门ID
	 * @return
	 */
	public User getDeptById(int entId, int id);
	
	/**
	 * 根据企业ID更新对应的部门信息
	 * @param entId 企业ID
	 * @param dept 更新的部门
	 * @return
	 */
	public boolean updateDept(int entId, User dept);
}
