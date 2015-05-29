package com.xuanwu.web.common.service.impl;

import gnu.trove.map.hash.TLongObjectHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuanwu.web.common.cache.CacheObject;
import com.xuanwu.web.common.cache.SyncTask;
import com.xuanwu.web.common.dao.impl.BizDataDaoImpl;
import com.xuanwu.web.common.dao.impl.UserDaoImpl;
import com.xuanwu.web.common.entity.Enterprise;
import com.xuanwu.web.common.entity.Permission;
import com.xuanwu.web.common.entity.Platform;
import com.xuanwu.web.common.entity.RolePermission;
import com.xuanwu.web.common.entity.User;
import com.xuanwu.web.common.service.BizDataService;
import com.xuanwu.web.common.utils.ListUtil;

/**
 * @Description 默认的基本业务数据服务实现，基于版本号的缓存实现
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-5
 * @Version 1.0.0
 */
@Component("bizDataService")
public class DefaultBizDataService implements BizDataService, SyncTask {

	private Logger logger = LoggerFactory
			.getLogger(DefaultBizDataService.class);

	private BizDataDaoImpl bizDao;

	private UserDaoImpl userDao;

	private Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();

	private TLongObjectHashMap<List<RolePermission>> rolePermMap;

	private Map<String, Permission> urlPermMap = new HashMap<String, Permission>();

	private Map<Integer, CacheObject<Map<Integer, User>>> entMap = new HashMap<Integer, CacheObject<Map<Integer, User>>>();

	private int rolePermVer = -1;

	private volatile int userVer = -1;

	private ReentrantReadWriteLock rolePermLock = new ReentrantReadWriteLock();

	private ReentrantReadWriteLock userLock = new ReentrantReadWriteLock();

	private long period = 10 * 60 * 1000;

	@PostConstruct
	public void init() {
		// permission items, load once is OK
		List<Permission> perms = bizDao.findAllPermissions();
		for (Permission perm : perms) {
			permissionMap.put(perm.getId(), perm);
			urlPermMap.put(perm.getPermUrl(), perm);
		}
		sync();
	}

	@Override
	public void run() {
		sync();
	}

	public void sync() {
		try {
			syncRolePerm();
		} catch (Exception e) {
			logger.error("Sync role permission fail, ", e);
		}
		try {
			syncUser();
		} catch (Exception e) {
			logger.error("Sync user fail, ", e);
		}
	}

	@Override
	public void syncRolePerm() {
		int ver = bizDao.findSyncVersion(SyncVersion.ROLE_PERM);
		logger.info(
				"Begin to sync role permission, current version[{}], new version[{}]...",
				rolePermVer, ver);
		if (rolePermVer >= ver) {
			logger.info("End to sync role permission.");
			return;
		}
		rolePermLock.writeLock().lock();
		try {
			if (rolePermVer >= ver) {
				logger.info("End to sync role permission.");
				return;
			}
			TLongObjectHashMap<List<RolePermission>> rolePermMap = new TLongObjectHashMap<List<RolePermission>>();
			List<RolePermission> rolePerms = bizDao.findAllRolePermissions();
			for (RolePermission rolePerm : rolePerms) {
				Permission perm = permissionMap.get(rolePerm.getPermissionId());
				if (perm == null)
					continue;
				rolePerm.setPlatform(perm.getPlatform());
				rolePerm.setBaseUrl(perm.getPermUrl());
				int key = tranRolePermKey(rolePerm.getRoleId(),
						rolePerm.getPlatform());
				List<RolePermission> value = rolePermMap.get(key);
				if (value == null) {
					value = new ArrayList<RolePermission>();
					rolePermMap.put(key, value);
				}
				value.add(rolePerm);
			}
			this.rolePermMap = rolePermMap;
			rolePermVer = ver;
			logger.info("Sync role permission to version [{}]", ver);
		} finally {
			rolePermLock.writeLock().unlock();
		}
		logger.info("End to sync role permission.");
	}	

	private long entCacheTime = 3600;// 1 hour

	public void syncUser() {
		int ver = bizDao.findSyncVersion(SyncVersion.USER);
		logger.info(
				"Begin to sync user, current version[{}], new version[{}]...",
				userVer, ver);
		if (userVer >= ver) {
			logger.info("End to sync user.");
			return;
		}
		userLock.writeLock().lock();
		try {
			if (userVer >= ver) {
				logger.info("End to sync user.");
				return;
			}
			Iterator<Map.Entry<Integer, CacheObject<Map<Integer, User>>>> it = entMap
					.entrySet().iterator();
			while (it.hasNext()) {
				CacheObject<Map<Integer, User>> obj = it.next().getValue();
				if (obj.isExpired()) {
					it.remove();
				}
			}
			userVer = ver;
			logger.info("Sync user to version [{}]", ver);
		} finally {
			userLock.writeLock().unlock();
		}
		logger.info("End to sync user.");
	}

	private CacheObject<Map<Integer, User>> loadUsers(int entId) {
		userLock.writeLock().lock();
		try {
			CacheObject<Map<Integer, User>> obj = entMap.get(entId);
			if (obj != null && obj.getVersion() >= userVer) {
				return obj;
			}
			List<User> depts = new ArrayList<User>();
			User ent = userDao.findUser(entId);
			if (ent != null) {
				((Enterprise) ent).setForTree(true);
				depts.add(ent);
			}
			List<User> ret = userDao.findAllEntDepts(entId);
			if (ListUtil.isNotBlank(ret)) {
				depts.addAll(ret);
			}
			Map<Integer, User> map = new LinkedHashMap<Integer, User>();
			obj = new CacheObject<Map<Integer, User>>(map);
			for (User dept : depts) {
				map.put(dept.getId(), dept);
			}
			obj.setAttachment(depts);
			obj.setAliveTime(entCacheTime, TimeUnit.SECONDS);
			obj.setVersion(userVer);
			entMap.put(entId, obj);
			return obj;
		} finally {
			userLock.writeLock().unlock();
		}
	}

	@Override
	public Permission getPermission(Integer id) {
		return permissionMap.get(id);
	}

	@Override
	public Permission getPermission(String permUrl) {
		return urlPermMap.get(permUrl);
	}

	@Override
	public List<RolePermission> getRolePermissions(Integer roleID,
			Platform platform) {
		rolePermLock.readLock().lock();
		try {
			return rolePermMap.get(tranRolePermKey(roleID, platform));
		} finally {
			rolePermLock.readLock().unlock();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getDepts(int entId, boolean includeDel) {
		CacheObject<Map<Integer, User>> obj;
		userLock.readLock().lock();
		try {
			obj = entMap.get(entId);
			if (obj != null && obj.getVersion() >= userVer) {
				return filterDepts((List<User>) obj.getAttachment(),
						includeDel);
			}
		} finally {
			userLock.readLock().unlock();
		}
		obj = loadUsers(entId);
		if (obj == null)
			return null;
		return filterDepts((List<User>) obj.getAttachment(), includeDel);
	}

	private List<User> filterDepts(List<User> depts, boolean includeDel) {
		if (includeDel)
			return depts;
		if (ListUtil.isBlank(depts)) {
			return null;
		}
		List<User> targets = new ArrayList<User>();
		for (User dept : depts) {
			if (User.State.Normal.getIndex() == dept.getState()) {
				targets.add(dept);
			}
		}
		return targets;
	}

	@Override
	public User getDeptById(int entId, int id) {
		CacheObject<Map<Integer, User>> obj;
		userLock.readLock().lock();
		try {
			obj = entMap.get(entId);
			if (obj != null && obj.getVersion() >= userVer) {
				return obj.getObject().get(id);
			}
		} finally {
			userLock.readLock().unlock();
		}
		obj = loadUsers(entId);
		return obj == null ? null : obj.getObject().get(id);
	}

	@Override
	public boolean updateDept(int entId, User dept) {
		userLock.writeLock().lock();
		try {
			CacheObject<Map<Integer, User>> obj = entMap.get(entId);
			if (obj == null) {
				Map<Integer, User> map = new LinkedHashMap<Integer, User>();
				obj = new CacheObject<Map<Integer, User>>(map);
				entMap.put(entId, obj);
			}
			User ret = userDao.findUser(dept.getId());
			if (ret == null
					|| User.State.Normal != User.State.getType(ret
							.getState())) {
				obj.getObject().remove(dept.getId());
			} else {
				obj.getObject().put(dept.getId(), ret);
			}
			obj.setAttachment(ListUtil.map2List(obj.getObject().values()));
		} finally {
			userLock.writeLock().unlock();
		}
		return true;
	}


	private int tranRolePermKey(Integer roleID, Platform platform) {
		if (roleID == null || platform == null) {
			throw new NullPointerException("roleID or platform");
		}
		return roleID << 4 | (platform.getIndex() & 0xF);
	}

	@Autowired
	public void setBizDao(BizDataDaoImpl bizDao) {
		this.bizDao = bizDao;
	}

	@Autowired
	public void setUserDao(UserDaoImpl userDao) {
		this.userDao = userDao;
	}

	@Override
	public long getPeriod() {
		return period;
	}
}
