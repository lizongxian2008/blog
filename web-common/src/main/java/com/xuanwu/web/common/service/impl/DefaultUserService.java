package com.xuanwu.web.common.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuanwu.web.common.cache.SyncTask;
import com.xuanwu.web.common.dao.impl.UserDaoImpl;
import com.xuanwu.web.common.entity.Department;
import com.xuanwu.web.common.entity.DynamicParam;
import com.xuanwu.web.common.entity.GsmsUser.UserType;
import com.xuanwu.web.common.entity.Role;
import com.xuanwu.web.common.entity.SimpleUser;
import com.xuanwu.web.common.entity.User;
import com.xuanwu.web.common.service.BizDataService;
import com.xuanwu.web.common.service.UserService;
import com.xuanwu.web.common.utils.Delimiters;
import com.xuanwu.web.common.utils.ListUtil;

@Component("userService")
public class DefaultUserService implements UserService, SyncTask, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(DefaultUserService.class);

	private LinkedBlockingQueue<User> updateUsers = new LinkedBlockingQueue<User>();

	private UserDaoImpl userDao;

	private BizDataService bizData;

	@Override
	public SimpleUser findSimpleUserById(int id) {
		return userDao.findUserById(id);
	}

	@Override
	public List<SimpleUser> findUserByParam(String domain, String enterpriseName) {
		return userDao.findUserByParam(domain, enterpriseName);
	}

	@Override
	public SimpleUser findUserByName(String username) {
		return userDao.findUserByName(username);
	}

	@Override
	public List<Integer> findRoleIds(int userId) {
		return userDao.findRoleIds(userId);
	}

	@Override
	public User findAdminUser(int entId) {
		return userDao.findAdminUser(entId);
	}

	@Override
	public SimpleUser findAdminSimpleUser(int entId) {
		return userDao.findAdminSimpleUser(entId);
	}

	@Override
	public List<User> findEntUsers(int entId) {
		return userDao.findEntUsers(entId);
	}

	@Override
	public List<Role> findRoles(int id, int pId) {
		if (pId <= 0) {
			return userDao.findRoles(id);
		}

		List<Role> pRoles = userDao.findRoles(pId);
		HashSet<Integer> roleSet = new HashSet<Integer>();
		List<Role> roles = userDao.findRoles(id);
		for (Role role : roles) {
			roleSet.add(role.getId());
			role.setBound(true);
		}

		for (Role role : pRoles) {
			if (roleSet.contains(role.getId()))
				continue;
			roles.add(role);
		}
		return roles;
	}

	@Override
	public User findUserById(int id) {
		if (id <= 0) {
			return null;
		}
		User user = userDao.findUser(id);
		if (user == null)
			return null;
		switch (user.getType()) {
		case User:
			break;
		case Department:
			Department dept = (Department) user;
			List<Integer> types = userDao.findChildTypes(dept.getId());
			UserType userType;
			if (ListUtil.isNotBlank(types)) {
				for (int type : types) {
					userType = UserType.getType(type);
					if (userType == UserType.Department) {
						dept.setHasChild(true);
					} else if (userType == UserType.User) {
						dept.setHasUser(true);
					}
				}
			}
			break;
		case Enterprise:
			break;
		default:
			break;
		}
		return user;
	}

	@Override
	public String findUserNameById(int id) {
		return userDao.findUserNameById(id);
	}

	@Override
	public int findUserCount(User user, DynamicParam param) {
		switch (user.getType()) {
		case Department:
			return userDao.findDeptCount(user, param);
		case User:
			return userDao.findUserCount(user, param);
		default:
			break;
		}
		return 0;
	}

	@Override
	public List<User> findUsers(User user, DynamicParam param) {
		switch (user.getType()) {
		case Department:
			List<User> depts = userDao.findDepartments(user, param);
			setUserRoleNames(depts);
			return depts;
		case User:
			List<User> users = userDao.findUsers(user, param);
			User dept;
			for (User u : users) {
				dept = bizData
						.getDeptById(u.getEnterpriseId(), u.getParentId());
				u.setEnterpriseName(dept == null ? "" : dept
						.getEnterpriseName());
				u.setParentIdentify(dept == null ? "" : dept.getIdentify());
			}
			setUserRoleNames(users);
			return users;
		default:
			break;
		}
		return Collections.emptyList();
	}

	private void setUserRoleNames(List<User> users) {
		if (ListUtil.isBlank(users))
			return;
		List<Role> roles = userDao.findUsersRoles(users);
		if (ListUtil.isBlank(roles))
			return;
		HashMap<Integer, StringBuilder> map = new HashMap<Integer, StringBuilder>();
		StringBuilder sb = null;
		for (Role role : roles) {
			sb = map.get(role.getUserId());
			if (sb == null) {
				sb = new StringBuilder();
				map.put(role.getUserId(), sb);
			}
			if (sb.length() > 0) {
				sb.append(Delimiters.COMMA).append(role.getName());
			} else {
				sb.append(role.getName());
			}
		}
		for (User user : users) {
			sb = map.get(user.getId());
			user.setRoleNames(sb == null ? "" : sb.toString());
		}
	}

	@Override
	public boolean addUser(User user) {
		try {
			switch (user.getType()) {
			case Department:
				userDao.addDepartment(user);
				break;
			case User:
				userDao.addUser((User) user);
				break;
			default:
				break;
			}
			return true;
		} catch (Exception e) {
			logger.error("Add gsms user fail: ", e);
		}
		return false;
	}

	@Override
	public boolean deleteUser(User user) {
		if (user == null || user.getId() <= 0) {
			return false;
		}
		try {
			boolean updateState = true;
			switch (user.getType()) {
			case Department:
				for (int state : userDao.findChildStates(user.getId())) {
					if (state == 0 || state == 1) {
						return false;
					}
					if (state == 2) {
						updateState = true;
					}
				}
				break;
			case User:
				// if (userDao.findUserHasSendMsg(user) > 0)
				// updateState = true;
				break;
			default:
				break;
			}
			if (updateState) {
				user.setState(User.State.Terminate.getIndex());
			}
			userDao.deleteUser(user, updateState);
			return true;
		} catch (Exception e) {
			logger.error("Delete gsms user fail: ", e);
		}
		return false;
	}

	public boolean stopUser(User user) {
		if (user == null || user.getId() <= 0) {
			return false;
		}
		try {
			userDao.stopUser(user);
			return true;
		} catch (Exception e) {
			logger.error("Delete gsms user fail: ", e);
		}
		return false;
	}

	@Override
	public int deleteUsers(List<User> users) {
		if (ListUtil.isBlank(users)) {
			return 0;
		}
		int suc = 0;
		for (User user : users) {
			if (deleteUser(user))
				suc++;
		}
		return suc;
	}

	@Override
	public boolean updateUser(User user) {
		try {
			switch (user.getType()) {
			case Department:
				userDao.updateDepartment((Department) user);
				break;
			case User:
				userDao.updateUser(user);
				break;
			default:
				break;
			}
			return true;
		} catch (Exception e) {
			logger.error("Update gsms user fail: ", e);
		}
		return false;
	}

	@Override
	public boolean updateUserExt(User user, boolean batch) {
		if (user == null || user.getId() <= 0)
			return false;
		if (batch) {
			return updateUsers.add(user);
		}
		try {
			userDao.updateUserExt(user);
			return true;
		} catch (Exception e) {
			logger.error("Update gsms user fail: ", e);
		}
		return false;
	}

	@Override
	public boolean isUserNameExist(User user) {
		return userDao.findUserNameExist(user) > 0;
	}

	@Override
	public boolean isNormalUserNameExist(User user) {
		return userDao.findNormalUserNameExist(user) > 0;
	}

	@Override
	public boolean isEntNameExist(User user) {
		return userDao.findEntNameExist(user) > 0;
	}

	@Override
	public boolean isDeptNameExist(User user) {
		return userDao.findDeptNameExist(user) > 0;
	}

	@Override
	public List<Integer> findChildStates(int parentId) {
		return userDao.findChildStates(parentId);
	}

	@Override
	public List<Integer> findUserIdsByUserName(int entId, String userName) {
		return userDao.findUserIdsByUserName(entId, userName);
	}

	@Override
	public String findUserDeptName(int userId) {
		if (userId <= 0) {
			return null;
		}
		User user = userDao.findUser(userId);
		if (user != null) {
			int deptId = user.getParentId();
			return userDao.findDeptName(deptId);
		}
		return null;
	}

	public String findPathById(int id) {
		return userDao.findPathById(id);
	}

	public List<User> findUsersSimple(User user, DynamicParam param,
			User.State[] states) {
		switch (user.getType()) {
		case User:
			return userDao.findUsersSimple(user, param, states);
		default:
			break;
		}
		return Collections.emptyList();
	}

	public List<User> findLinkMansSimple(User user, DynamicParam param,
			User.State[] states) {
		return userDao.findLinkMansSimple(user, param, states);
	}

	public User findUsersById(int id) {
		return userDao.findUser(id);
	}

	@Override
	public void run() {
		try {
			if (updateUsers.isEmpty()) {
				return;
			}
			List<User> users = new ArrayList<User>();
			updateUsers.drainTo(users);
			List<List<User>> subs = ListUtil.splitList(users, 1000);
			for (List<User> sub : subs) {
				userDao.updateUserExtBatch(sub);
			}
		} catch (Exception e) {
			logger.error("Update user batch fail: ", e);
		}
	}

	@Override
	public long getPeriod() {
		return 5000;
	}

	@Override
	public int deleteUserRole(int userId, int roleId) {
		return userDao.deleteUserRole(userId, roleId);
	}

	@Override
	public int addUserRole(int userId, int roleId) {
		return userDao.addUserRole(userId, roleId);
	}

	@Override
	public int findFronkitUserCount(User user, DynamicParam param) {
		return userDao.findFronkitUserCount(user, param);
	}

	@Override
	public List<User> findFronkitUsers(User user, DynamicParam param) {
		List<User> users = userDao.findFronkitUsers(user, param);
		setUserRoleNames(users);
		return users;
	}

	@Autowired
	public void setUserDao(UserDaoImpl userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setBizData(BizDataService bizData) {
		this.bizData = bizData;
	}

	@Override
	public boolean updateExtEnterprise(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
