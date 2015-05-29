package com.xuanwu.web.common.service;

import java.util.List;

import com.xuanwu.web.common.entity.DynamicParam;
import com.xuanwu.web.common.entity.Role;
import com.xuanwu.web.common.entity.SimpleUser;
import com.xuanwu.web.common.entity.User;

/**
 * @Description 用户数据服务
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-7
 * @Version 1.0.0
 */
public interface UserService {

	public SimpleUser findSimpleUserById(int id);

	/**
	 * 根据用户名查找用户，一般登录时使用
	 *
	 * @param username
	 * @return
	 */
	public SimpleUser findUserByName(String username);

	/**
	 * 根据企业帐号或企业名称查询企业
	 *
	 * @param domain
	 * @param enterpriseName
	 * @return
	 */
	public List<SimpleUser> findUserByParam(String domain, String enterpriseName);

	/**
	 * 根据用户ID查找该用户所分配的角色ID，一般登录时使用
	 *
	 * @param userId
	 * @return
	 */
	public List<Integer> findRoleIds(int userId);

	/**
	 * 查找企业下超级管理员
	 *
	 * @param entId
	 * @return
	 */
	public User findAdminUser(int entId);

	public SimpleUser findAdminSimpleUser(int entId);

	/**
	 * 查找企业下的所有用户
	 *
	 * @param entId
	 * @return
	 */
	public List<User> findEntUsers(int entId);

	/**
	 * 根据用户ID查找所绑定的角色
	 *
	 * @param id
	 * @param pId
	 * @return
	 */
	public List<Role> findRoles(int id, int pId);

	/**
	 * 根据ID查找该用户详情
	 *
	 * @param id
	 * @return
	 */
	public User findUserById(int id);

	/**
	 * 根据ID查找该用户名字
	 *
	 * @param id
	 * @return
	 */
	public String findUserNameById(int id);

	/**
	 * 查找用户数
	 *
	 * @param user
	 *            查找的用户条件
	 * @param param
	 *            动态参数
	 * @return
	 */
	public int findUserCount(User user, DynamicParam param);

	/**
	 * 查找用户列表
	 *
	 * @param user
	 *            查找的用户条件
	 * @param param
	 *            动态参数
	 * @return
	 */
	public List<User> findUsers(User user, DynamicParam param);

	/**
	 * 增加用户
	 *
	 * @param user
	 */
	public boolean addUser(User user);

	/**
	 * 删除用户
	 *
	 * @param user
	 * @return true:成功，false:失败
	 */
	public boolean deleteUser(User user);

	/**
	 * 启用/停用 用户
	 *
	 * @param user
	 * @return
	 */
	public boolean stopUser(User user);

	/**
	 * 批量删除用户
	 *
	 * @param users
	 * @return 成功数
	 */
	public int deleteUsers(List<User> users);

	/**
	 * 更新用户
	 *
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user);

	/**
	 * 更新用户 用于指定字段更新
	 *
	 * @param user
	 * @param batch
	 *            是否批量更新（异步）
	 * @return
	 */
	public boolean updateUserExt(User user, boolean batch);

	/**
	 * 更新企业 用于指定字段更新
	 *
	 * @param user
	 * @return
	 */
	public boolean updateExtEnterprise(User user);

	/**
	 * 用户是否存在
	 *
	 * @param user
	 * @return
	 */
	public boolean isUserNameExist(User user);

	/**
	 * 正常状态用户是否存在
	 *
	 * @param user
	 * @return
	 */
	public boolean isNormalUserNameExist(User user);

	/**
	 * 企业用户名是否存在
	 *
	 * @param user
	 * @return
	 */
	public boolean isEntNameExist(User user);


	public boolean isDeptNameExist(User user);

	/**
	 * 查找子用户的状态集合
	 *
	 * @param parentId
	 *            父ID
	 * @return
	 */
	public List<Integer> findChildStates(int parentId);


	public String findUserDeptName(int userId);

	public String findPathById(int id);

	/**
	 * 删除用户角色
	 *
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public int deleteUserRole(int userId, int roleId);

	/**
	 * 新增用户角色
	 *
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public int addUserRole(int userId, int roleId);

	/**
	 * 简单用户查询，一般用于自动补全
	 *
	 * @param user
	 * @param param
	 * @param states
	 *            要获取GSMS用户的状态，为NULL时表示全部
	 * @return
	 */
	public List<User> findUsersSimple(User user, DynamicParam param,
			User.State[] states);

	/**
	 * 简单联系人查询，用于所属销售自动补全查询
	 * 
	 * @param user
	 * @param param
	 * @param states
	 * @return
	 */
	public List<User> findLinkMansSimple(User user, DynamicParam param,
			User.State[] states);

	/**
	 *
	 * @param entId
	 * @param userName
	 * @return
	 */
	public List<Integer> findUserIdsByUserName(int entId, String userName);

	/**
	 * 查找前端账号
	 * 
	 * @param user
	 * @param param
	 * @return
	 */
	public int findFronkitUserCount(User user, DynamicParam param);

	/**
	 * 查找前端state==0|state==1的前端账号
	 * 
	 * @param user
	 * @param param
	 * @return
	 */
	public List<User> findFronkitUsers(User user, DynamicParam param);
}
