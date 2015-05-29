package com.xuanwu.web.frontkit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description 全局异常处理
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2013-2-21
 * @Version 1.0.0
 */
public class FrontkitHandlerExceptionResolver implements
		HandlerExceptionResolver {

	private static Logger logger = LoggerFactory
			.getLogger(FrontkitHandlerExceptionResolver.class);
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if ("org.apache.catalina.connector.ClientAbortException".equals(ex
				.getClass().getName())) {
			return null;
		}
		logger.error("Catch exception: ", ex);
		// TODO
		return null;
	}

}
