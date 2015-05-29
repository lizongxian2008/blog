package com.xuanwu.web.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xuanwu.web.common.entity.ConfigRecord;
import com.xuanwu.web.common.entity.Permission;
import com.xuanwu.web.common.entity.RolePermission;
import com.xuanwu.web.common.service.BizDataService.SyncVersion;

public interface BizDataMapper {

	public List<Permission> findAllPermissions(@Param("platformId")int platformId);
	
	public List<RolePermission> findAllRolePermissions();
	
	public int findSyncVersion(@Param("type")SyncVersion type);
	
	public List<ConfigRecord> findAllConfigs();
	
	public int updateConfig(@Param("config")ConfigRecord config);
}
