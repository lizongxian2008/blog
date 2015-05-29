/*
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.common.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Description GSMS抽象DAO（读写）
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-7
 * @Version 1.0.0
 */
public abstract class O2ODao {

	protected SqlSessionFactory sessionFactory;

	@Autowired
	@Qualifier("o2oSessionFactory")
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return sessionFactory
	 */
	public SqlSessionFactory getSessionFactory() {
		return sessionFactory;
	}	
}
