package com.xuanwu.web.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xuanwu.web.common.entity.Department;
import com.xuanwu.web.common.entity.DynamicParam;
import com.xuanwu.web.common.entity.GsmsUser.UserType;
import com.xuanwu.web.common.entity.Role;
import com.xuanwu.web.common.entity.SimpleUser;
import com.xuanwu.web.common.entity.User;

public interface UserMapper {

	public SimpleUser findUserById(@Param("id") int id);

	public SimpleUser findUserByName(@Param("name") String username);

	public List<SimpleUser> findUserByParam(@Param("domain") String domain,
			@Param("enterpriseName") String enterpriseName);

	public List<Integer> findRoleIds(@Param("userId") int userId);

	public User findAdminUser(@Param("entId") int enterpriseId);

	public SimpleUser findAdminSimpleUser(@Param("entId") int enterpriseId);

	public List<User> findEntUsers(@Param("entId") int entId);

	public List<Role> findRoles(@Param("userId") int userId);

	public List<Role> findUsersRoles(@Param("users") List<User> users);

	public User findUser(@Param("id") int id);

	public List<Integer> findChildTypes(@Param("parentId") int parentId);

	public int addDepartment(User user);

	public int addUser(User user);

	public int addUserRole(@Param("userId") int user, @Param("roleId") int role);

	public List<User> findDepartments(@Param("user") User user,
			@Param("param") DynamicParam param);

	public int findDeptCount(@Param("user") User user,
			@Param("param") DynamicParam param);

	public List<User> findUsers(@Param("user") User user,
			@Param("param") DynamicParam param);

	public int findUserCount(@Param("user") User user,
			@Param("param") DynamicParam param);

	public int deleteUser(@Param("user") User user);

	public int deleteUserRole(@Param("userId") int userId,
			@Param("roleId") int roleId);

	public int deleteUserRoles(@Param("user") User user);

	public int deleteUserAccounts(@Param("user") User user);

	public int updateDepartment(@Param("user") User user);

	public int updateUser(@Param("user") User user);

	public int updateUserExt(@Param("user") User user);

	public int updateUserState(@Param("user") User user);

	public int findUserNameExist(@Param("user") User user);

	public int findNormalUserNameExist(@Param("user") User user);

	public int findEntNameExist(@Param("user") User user);

	public int findIdentifyExist(@Param("user") User user);

	public int findDeptNameExist(@Param("user") User user);

	public List<Integer> findChildStates(@Param("parentId") int parentId);

	public List<Integer> findUserIdsByUserName(@Param("entId") int entId,
			@Param("userName") String userName);

	public List<User> findUserByEntId(@Param("entId") int entId,
			@Param("pointId") int pointId, @Param("maxSize") int maxSize);

	public String findUserNameById(@Param("id") int id);

	public String findLinkManById(@Param("id") int id);

	public int findLastInsertId();

	public List<Integer> findChildIdsByParent(@Param("pId") int pId,
			@Param("path") String path,
			@Param("containChild") boolean containChild,
			@Param("type") UserType type);

	public String findPathById(@Param("id") int id);

	public List<Department> findCurrentDept(@Param("deptId") int deptId,
			@Param("path") String path);

	public String findDeptName(@Param("deptId") int deptId);

	public List<Integer> findUserID(@Param("userName") String userName);

	public List<Integer> findEnterpriseID(
			@Param("enterpriseName") String enterpriseName);

	public List<User> findAllEntDepts(@Param("entId") int entId);

	public List<User> findUsersSimple(@Param("user") User user,
			@Param("param") DynamicParam param,
			@Param("states") User.State[] states);

	public List<User> findLinkMansSimple(@Param("user") User user,
			@Param("param") DynamicParam param,
			@Param("states") User.State[] states);


	public int findUserState(@Param("id") int id);

	/**
	 * 查找前端用户账号数
	 * 
	 * @param user
	 * @param param
	 * @return
	 */
	public int findFronkitUserCount(@Param("ent") User user,
			@Param("param") DynamicParam param);

	/**
	 * 查找前端用户账号
	 * 
	 * @param user
	 * @param param
	 * @return
	 */
	public List<User> findFronkitUsers(@Param("ent") User user,
			@Param("param") DynamicParam param);


	public List<SimpleUser> findAllStateUserByParam(
			@Param("domain") String domain,
			@Param("enterpriseName") String enterpriseName);

}
