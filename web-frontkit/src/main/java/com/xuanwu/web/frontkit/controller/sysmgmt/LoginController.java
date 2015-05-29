package com.xuanwu.web.frontkit.controller.sysmgmt;

import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xuanwu.web.common.config.Config;
import com.xuanwu.web.common.entity.SimpleUser;
import com.xuanwu.web.common.entity.SystemLog;
import com.xuanwu.web.common.entity.User;
import com.xuanwu.web.common.service.SysLogService;
import com.xuanwu.web.common.service.UserService;
import com.xuanwu.web.common.utils.JsonUtil;
import com.xuanwu.web.common.utils.SessionUtil;
import com.xuanwu.web.common.utils.WebConstants;
import com.xuanwu.web.frontkit.controller.BaseController;
import com.xuanwu.web.frontkit.controller.Keys;

@Controller
public class LoginController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	private Config config;
	private UserService userService;
	private SysLogService sysLogService;
	private Random rand = new Random();
	//private FrontkitPackSender packSender;


	@RequestMapping(value = Keys.LOGIN, method = RequestMethod.GET)
	public void index(Model model) {
		
	}

	@RequestMapping(value = Keys.LOGIN, method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest hreq, String username,
			String password) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String md5Pwd = DigestUtils.md5Hex(password);
			UsernamePasswordToken token = new UsernamePasswordToken(username,
					md5Pwd);
			subject.login(token);
			SimpleUser user = SessionUtil.getCurUser();
			if (user.getPlatformId() == config.getPlatformId()) {
				// set user service for lazy load user extend info
				user.setUserService(userService);

				// update last login time
				User updateUser = new User();
				updateUser.setId(user.getId());
				updateUser.setLastLoginTime(new Date());
				userService.updateUserExt(updateUser, true);// 异步更新

				// set system configuration to the session
				Session session = subject.getSession();
				session.setAttribute(WebConstants.KEY_CONFIG, config);
				String entName = "", deadline = "无限制";
				session.setAttribute(WebConstants.KEY_ENT_NAME, entName);
				session.setAttribute(WebConstants.KEY_DEADLINE, deadline);

				SystemLog sysLog = new SystemLog();
				sysLog.setUserId(user.getId());
				sysLog.setUserName(user.getUsername());
				sysLog.setOperateTime(new Date());
				sysLog.setAreaName("public");
				sysLog.setControllerName("login");
				sysLog.setActionName("doLogin");
				sysLog.setOperationType("登录");
				sysLog.setOperationObj("系统");
				sysLog.setContent("登录[" + hreq.getRemoteAddr() + "]");
				sysLog.setFormMethod(1);
				sysLog.setEnterpriseId(user.getEnterpriseId());
				String agent = hreq.getHeader("User-Agent");
				if (StringUtils.isNotBlank(agent)) {
					sysLog.setRemark("User-Agent: "
							+ (agent.length() > 160 ? agent.substring(0, 160)
									: agent));
				}
				sysLogService.addSysLog(sysLog);
				hreq.getSession().setAttribute("fromLogin", true);
				return "redirect:" + Keys.MAIN;
			}
			logger.warn("Invalid user [" + username + "]");
			subject.logout();
		} catch (Exception e) {
			logger.error("Login fail: {}", e.getMessage());
		}
		model.addAttribute("verifyMsg", "账号或密码错误 ");
		return Keys.LOGIN;
	}

	@RequestMapping(value = Keys.LOGIN, params = "action=resetPassword", method = RequestMethod.POST)
	public String resetPassword(Model model, String username, String mobile) {
		String jsonData = JsonUtil.toJSON(-1);
		SimpleUser suser = userService.findUserByName(username);

		if (suser == null) {
			jsonData = JsonUtil.toJSON("该用户名不存在！", 1);
		} else {
			User user = (User) userService.findUserById(suser.getId());
			if (user.getPhone() != null && user.getPhone().equals(mobile)) {
				String password = getRandPassWord();// TODO 生成随机密码
				User updateUser = new User();
				updateUser.setId(user.getId());
				updateUser.setPassword(DigestUtils.md5Hex(password));
				if (userService.updateUserExt(updateUser, false)) {
					jsonData = JsonUtil.toJSON(0);
					/*String msg = "尊敬的用户，您的账号(" + username + ")的登录密码已经重置为："
							+ password + "，请及时登录系统和修改，并牢记新密码。";
					PMsgPack msgPack = buildPack(msg, user.getPhone());
					User u = (User) userService.findUserById(2);// admin
					MTResp resp = checkPack(msgPack);
					if (resp == null) {
						try {
							resp = packSender.send(u.getUserName(),
									u.getPassword(), msgPack);
						} catch (CoreException e) {
							jsonData = JsonUtil.toJSON("重置密码失败！", 1);
							logger.error("重置密码失败:", e);
						}
					}// end resp
*/				}
			} else {
				jsonData = JsonUtil.toJSON("您输入的手机号码和系统登记的不一致！", 1);
			}
		}
		model.addAttribute(Keys.JSON_DATA, jsonData);
		return Keys.AJAX_JSON;
	}

	@RequestMapping(value = Keys.LOGOUT)
	public String logout(HttpServletRequest hreq) {
		Subject subject = SecurityUtils.getSubject();
		SimpleUser user = (SimpleUser) subject.getSession().getAttribute(
				WebConstants.KEY_USER);
		subject.logout();
		if (user != null) {
			SystemLog sysLog = new SystemLog();
			sysLog.setUserId(user.getId());
			sysLog.setUserName(user.getUsername());
			sysLog.setOperateTime(new Date());
			sysLog.setAreaName("public");
			sysLog.setControllerName("logout");
			sysLog.setActionName("doLogout");
			sysLog.setOperationType("登出");
			sysLog.setOperationObj("系统");
			sysLog.setContent("登出[" + hreq.getRemoteAddr() + "]");
			sysLog.setFormMethod(1);
			sysLog.setEnterpriseId(user.getEnterpriseId());
			sysLogService.addSysLog(sysLog);
		}
		return "redirect:" + Keys.LOGIN;
	}

	public String getRandPassWord() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sb.append(rand.nextInt(10));
		}
		return sb.toString();
	}

	/*public PMsgPack buildPack(String msg, String phone) {
		PMsgPack pack = new PMsgPack();
		pack.setMsgType(MsgType.LONGSMS);
		pack.setBizType(0);
		pack.setDistinct(false);
		pack.setSendTypeIndex(MsgPack.SendType.GROUP.getIndex());

		GroupMsgFrame frame = new GroupMsgFrame();
		PMsgContent msgContent = new PMsgContent();
		msgContent.setContent(msg);
		List<MsgSingle> msgs = new ArrayList<MsgSingle>();
		msgs.add(new PMsgSingle(MsgType.LONGSMS, phone, msgContent, null, null,
				false, 0));
		frame.setBizType(pack.getBizType());
		frame.setMsgType(pack.getMsgType());
		frame.setReportState(true);
		frame.setAllMsgSingle(msgs);
		pack.getFrames().add(frame);

		return pack;
	}

	private MTResp checkPack(MsgPack pack) {
		if (pack == null || ListUtil.isBlank(pack.getFrames())) {
			return MTResp.build(MTResult.INVALID_PARAM, "信息包或信息帧为空");
		}
		// 只有联系人为空时才会发生
		if (ListUtil.isBlank(pack.getFrames().get(0).getAllMsgSingle())) {
			return MTResp.build(MTResult.INVALID_PARAM, "联系人信息已被删除，号码列表为空");
		}
		return null;
	}*/

	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setConfig(Config config) {
		this.config = config;
	}
}
