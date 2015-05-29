/*   
* Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
*             All rights reserved                         
*/
package com.xuanwu.web.frontkit.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.xuanwu.web.common.config.Config;

/**
 * @Description: 
 * @Author <a href="hw86xll@163.com">Wei.Huang</a>
 * @Date 2012-10-18
 * @Version 1.0.0
 */
public class FrontContextLoaderListener extends ContextLoaderListener{
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		Config.setContextPath(event.getServletContext().getRealPath(""));
	}
}
