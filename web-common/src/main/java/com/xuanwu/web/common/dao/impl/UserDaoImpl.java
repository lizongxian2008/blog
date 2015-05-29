package com.xuanwu.web.common.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.xuanwu.web.common.dao.O2ODao;
import com.xuanwu.web.common.dao.mapper.RoleMapper;
import com.xuanwu.web.common.dao.mapper.UserMapper;
import com.xuanwu.web.common.entity.Department;
import com.xuanwu.web.common.entity.DynamicParam;
import com.xuanwu.web.common.entity.GsmsUser.UserType;
import com.xuanwu.web.common.entity.Platform;
import com.xuanwu.web.common.entity.Role;
import com.xuanwu.web.common.entity.SimpleUser;
import com.xuanwu.web.common.entity.User;
import com.xuanwu.web.common.utils.Delimiters;
import com.xuanwu.web.common.utils.ListUtil;

@Component
public class UserDaoImpl extends O2ODao {

	public SimpleUser findUserById(int id) {
		SqlSession session = sessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			SimpleUser user = mapper.findUserById(id);
			if (user != null) {// enterprise state check
				int entState = mapper.findUserState(user.getEnterpriseId());
				if (User.State.Normal.getIndex() != entState) {
					return null;
				}
			}
			return user;
		} finally {
			session.close();
		}
	}

	/**
	 * 根据用户名查找用户，一般用于登录
	 * 
	 * @param username
	 * @return
	 */
	public SimpleUser findUserByName(String username) {
		SqlSession session = sessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			SimpleUser user = mapper.findUserByName(username);
			if (user != null) {// enterprise state check
				int entState = mapper.findUserState(user.getEnterpriseId());
				if (User.State.Normal.getIndex() != entState) {
					return null;
				}
			}
			return user;
		} finally {
			session.close();
		}
	}

	public List<SimpleUser> findUserByParam(String domain, String enterpriseName) {
		SqlSession session = sessionFactory.openSession(true);
		try {
			return session.getMapper(UserMapper.class).findUserByParam(domain,
					enterpriseName);
		} finally {
			session.close();
		}
	}

	public List<SimpleUser> findAllStateUserByParam(String domain,
			String enterpriseName) {
		SqlSession session = sessionFactory.openSession(true);
		try {
			return session.getMapper(UserMapper.class).findAllStateUserByParam(
					domain, enterpriseName);
		} finally {
			session.close();
		}
	}

	/**
	 * 查找角色ID列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> findRoleIds(int userId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findRoleIds(userId);
		} finally {
			session.close();
		}
	}

	public User findAdminUser(int enterpriseId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findAdminUser(
					enterpriseId);
		} finally {
			session.close();
		}
	}

	public SimpleUser findAdminSimpleUser(int enterpriseId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findAdminSimpleUser(
					enterpriseId);
		} finally {
			session.close();
		}
	}

	public List<User> findEntUsers(int entId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findEntUsers(entId);
		} finally {
			session.close();
		}
	}

	public List<Role> findRoles(int userId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findRoles(userId);
		} finally {
			session.close();
		}
	}

	public List<Role> findUsersRoles(List<User> users) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findUsersRoles(users);
		} finally {
			session.close();
		}
	}

	public User findUser(int id) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findUser(id);
		} finally {
			session.close();
		}
	}

	public List<Integer> findChildTypes(int parentId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findChildTypes(parentId);
		} finally {
			session.close();
		}
	}

	public List<User> findDepartments(User user, DynamicParam param) {
		SqlSession session = sessionFactory.openSession(true);
		try {
			return session.getMapper(UserMapper.class).findDepartments(user,
					param);
		} finally {
			session.close();
		}
	}

	public int findDeptCount(User user, DynamicParam param) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findDeptCount(user,
					param);
		} finally {
			session.close();
		}
	}

	public List<User> findUsers(User user, DynamicParam param) {
		SqlSession session = sessionFactory.openSession(true);
		try {
			return session.getMapper(UserMapper.class).findUsers(user, param);
		} finally {
			session.close();
		}
	}

	public int findUserCount(User user, DynamicParam param) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findUserCount(user,
					param);
		} finally {
			session.close();
		}
	}

	public void cloneDefaultRole(User _ent, User admin, RoleMapper roleMapper)
			throws Exception {
		List<Role> roles = roleMapper.findDefaultRoles();
		List<Role> clones = new ArrayList<Role>();
		clones.addAll(admin.getRoles());
		for (Role _role : roles) {
			boolean canClone = false;
			switch (_role.getBaseType()) {
			case 0:// backend ent
				if (_ent.getPlatformId() == Platform.Backend.getIndex()
						&& _ent.getType() == User.UserType.Enterprise) {
					canClone = true;
				}
				break;
			case 1:// fronkit ent
				if (_ent.getPlatformId() == Platform.Frontkit.getIndex()
						&& _ent.getType() == User.UserType.Enterprise) {
					canClone = true;
				}
				break;
			case 2:// frontkit agent
				if (_ent.getType() == User.UserType.Agent) {
					canClone = true;
				}
				break;
			default:
				break;
			}
			// add
			if (canClone) {
				int oldRoleId = _role.getId();
				_role.setId(0);
				_role.setEnterpriseId(_ent.getId());
				roleMapper.addRole(_role);
				int roleId = roleMapper.findLastInsertId();
				_role.setId(roleId);
				int newRoleId = _role.getId();
				roleMapper.cloneRolePermission(oldRoleId, newRoleId);
				clones.add(_role);
			}
		}
		admin.setRoles(clones);
	}

	public void addDepartment(User user) {
		SqlSession session = sessionFactory.openSession(ExecutorType.BATCH);
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.addDepartment(user);
			int userId = mapper.findLastInsertId();
			user.setId(userId);
			if (StringUtils.isBlank(user.getIdentify())) {
				user.setIdentify(((Department) user).getDeptNoPrefix() + userId);
				mapper.updateDepartment(user);
			}
			addUserExt(user, mapper);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void addUser(User user) {
		SqlSession session = sessionFactory.openSession(ExecutorType.BATCH);
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.addUser(user);
			int userId = mapper.findLastInsertId();
			user.setId(userId);
			addUserExt(user, mapper);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void addUserExt(User user, UserMapper mapper) {
		if (ListUtil.isNotBlank(user.getRoles())) {
			for (Role role : user.getRoles()) {
				mapper.addUserRole(user.getId(), role.getId());
			}
		}
	}

	public int deleteUser(User user, boolean updateState) {
		SqlSession session = sessionFactory.openSession(ExecutorType.BATCH);
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			if (updateState) {
				mapper.updateUserState(user);
			} else {
				mapper.deleteUser(user);
			}
			mapper.deleteUserRoles(user);
			session.commit();
		} finally {
			session.close();
		}
		return 0;
	}

	public int stopUser(User user) {
		SqlSession session = sessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			int ret = mapper.updateUserState(user);
			session.commit();
			return ret;
		} finally {
			session.close();
		}
	}

	public int updateDepartment(Department dept) {
		SqlSession session = sessionFactory.openSession(ExecutorType.BATCH);
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			dept.setIdentify(null);
			List<Integer> subIds = null;
			if (ListUtil.isNotBlank(dept.getDelBizs())
					|| ListUtil.isNotBlank(dept.getDelRoles())) {
				subIds = mapper.findChildIdsByParent(dept.getId(),
						dept.getPath() + dept.getId() + Delimiters.DOT, true,
						null);
			}
			mapper.updateDepartment(dept);
			mapper.deleteUserRoles(dept);
			addUserExt(dept, mapper);
			if (ListUtil.isNotBlank(subIds)) {
				if (ListUtil.isNotBlank(dept.getDelRoles())) {
					for (Integer userId : subIds) {
						for (Integer roleId : dept.getDelRoles()) {
							mapper.deleteUserRole(userId, roleId);
						}
					}
				}
			}
			session.commit();
		} finally {
			session.close();
		}
		return 0;
	}

	public int updateUser(User user) {
		SqlSession session = sessionFactory.openSession(ExecutorType.BATCH);
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.updateUser(user);
			mapper.deleteUserRoles(user);
			addUserExt(user, mapper);
			session.commit();
		} finally {
			session.close();
		}
		return 0;
	}

	public int updateUserExt(User user) {
		SqlSession session = sessionFactory.openSession();
		try {
			int ret = session.getMapper(UserMapper.class).updateUserExt(user);
			session.commit();
			return ret;
		} finally {
			session.close();
		}
	}

	public void updateUserExtBatch(List<User> users) {
		SqlSession session = sessionFactory.openSession(ExecutorType.BATCH);
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			for (User user : users) {
				mapper.updateUserExt(user);
			}
			session.commit();
		} finally {
			session.close();
		}
	}

	public int findUserNameExist(User user) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findUserNameExist(user);
		} finally {
			session.close();
		}
	}

	public int findNormalUserNameExist(User user) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findNormalUserNameExist(
					user);
		} finally {
			session.close();
		}
	}

	public int findEntNameExist(User user) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findEntNameExist(user);
		} finally {
			session.close();
		}
	}

	public String findUserNameById(int userID) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findUserNameById(userID);
		} finally {
			session.close();
		}
	}

	public int findDeptNameExist(User user) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findDeptNameExist(user);
		} finally {
			session.close();
		}
	}

	public List<Integer> findChildStates(int parentId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class)
					.findChildStates(parentId);
		} finally {
			session.close();
		}
	}

	public List<Integer> findUserIdsByUserName(int entId, String userName) {
		SqlSession session = sessionFactory.openSession();
		if (StringUtils.isNotBlank(userName)) {
			try {
				return session.getMapper(UserMapper.class)
						.findUserIdsByUserName(entId, userName);
			} finally {
				session.close();
			}
		}

		return Collections.emptyList();
	}

	public int findIdentifyExist(User user) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findIdentifyExist(user);
		} finally {
			session.close();
		}
	}

	public List<Integer> findChildIdsByParent(int pId, String path,
			boolean containChild, UserType type) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findChildIdsByParent(
					pId, path, containChild, type);
		} finally {
			session.close();
		}
	}

	public int deleteUserRole(int userId, int roleId) {
		SqlSession session = sessionFactory.openSession();
		int ret = 0;
		try {
			ret = session.getMapper(UserMapper.class).deleteUserRole(userId,
					roleId);
			session.commit();
		} finally {
			session.close();
		}
		return ret;
	}

	public int addUserRole(int userId, int roleId) {
		SqlSession session = sessionFactory.openSession();
		int ret = 0;
		try {
			ret = session.getMapper(UserMapper.class).addUserRole(userId,
					roleId);
			session.commit();
		} finally {
			session.close();
		}
		return ret;
	}

	public String findDeptName(int deptId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findDeptName(deptId);
		} finally {
			session.close();
		}
	}

	public String findPathById(int id) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findPathById(id);
		} finally {
			session.close();
		}
	}

	public List<Department> findCurrentDept(int deptID, String path) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findCurrentDept(deptID,
					path);
		} finally {
			session.close();
		}
	}

	public List<User> findUsersSimple(User user, DynamicParam param,
			User.State[] states) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findUsersSimple(user,
					param, states);
		} finally {
			session.close();
		}
	}

	/**
	 * 简单联系人查询，用于所属销售自动补全查询
	 * 
	 * @param user
	 * @param param
	 * @param states
	 * @return
	 */
	public List<User> findLinkMansSimple(User user, DynamicParam param,
			User.State[] states) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findLinkMansSimple(user,
					param, states);
		} finally {
			session.close();
		}
	}

	public List<User> findAllEntDepts(int entId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(UserMapper.class).findAllEntDepts(entId);
		} finally {
			session.close();
		}
	}
	
	public int findFronkitUserCount(User user, DynamicParam param) {
		SqlSession session = sessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.findFronkitUserCount(user, param);
		} finally {
			session.close();
		}
	}

	public List<User> findFronkitUsers(User user, DynamicParam param) {
		SqlSession session = sessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.findFronkitUsers(user, param);
		} finally {
			session.close();
		}
	}
}
