package com.xuanwu.web.common.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.PathMatchingFilter;

import com.xuanwu.web.common.utils.WebConstants;

/**
 * @Description 
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-12-19
 * @Version 1.0.0
 */
public class ShiroAnonymousFilter extends PathMatchingFilter {

	@Override
	protected boolean preHandle(ServletRequest req, ServletResponse resp)
			throws Exception {
		HttpServletResponse hresp = (HttpServletResponse)resp;
		hresp.setHeader(WebConstants.HEADER_ACCESS_STATE, "login");
		return true;
	}
	
}
