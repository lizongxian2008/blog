/*
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.common.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuanwu.web.common.cache.SyncTask;
import com.xuanwu.web.common.dao.impl.BizDataDaoImpl;
import com.xuanwu.web.common.entity.ConfigRecord;
import com.xuanwu.web.common.entity.Platform;
import com.xuanwu.web.common.service.BizDataService.SyncVersion;

/**
 * @Description: 全局配置文件
 * @Author <a href="hw86xll@163.com">Wei.Huang</a>
 * @Date 2012-9-20
 * @Version 1.0.0
 */
@Component
public class Config implements SyncTask {

	private static final Logger logger = LoggerFactory.getLogger(Config.class);

	private Map<String, Object> configMap = new HashMap<String, Object>();

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	private int prevVersion = -1;
	
	private static String contextPath;

	private BizDataDaoImpl bizDao;
	
	private PlatformMode mode;

	@PostConstruct
	public void init() {
		sync();
	}

	@Override
	public void run() {
		sync();
	}

	public void sync() {
		try {
			int ver = bizDao.findSyncVersion(SyncVersion.SYS_CONFIG);
			logger.info(
					"Begin to sync system configuration, current version[{}], new version[{}]...",
					prevVersion, ver);
			if (prevVersion < ver) {
				syncConfigs(ver);
				logger.info("Sync system configuration to version [{}]", ver);
			}
			logger.info("End to sync system configuration.");
		} catch (Exception e) {
			logger.error("Sync system configuration fail, ", e);
		}
	}

	private void syncConfigs(int version) throws ParseException {
		List<ConfigRecord> configs = bizDao.findAllConfigs();
		for (ConfigRecord config : configs) {
			if (getPlatformId() != config.getPlatformId()) {
				continue;
			}
			putConfig(config);
		}
		prevVersion = version;
	}

	public int getPlatformId() {
		return getPlatform().getIndex();
	}

	public Platform getPlatform() {
		return mode.getPlatform();
	}

	public String getPlatformName() {
		return mode.getName();
	}

	public String getVersion() {
		return mode.getVersion();
	}

	/**
	 * 0-MOS 1-UMP
	 * 
	 * @return
	 */
	public int getRunMode() {
		return 0; // only mos model
	}


	public boolean updateConfig(ConfigRecord config) {
		return updateConfig(config, getPlatform());
	}

	public boolean updateConfig(ConfigRecord config, Platform platform) {
		if (config == null)
			return false;
		config.setPlatformId(platform.getIndex());
		try {
			int ret = bizDao.updateConfig(config);
			if (ret > 0)
				putConfig(config);
			return ret > 0;
		} catch (Exception e) {
			logger.error("Update config failed: ");
		}
		return false;
	}

	private void putConfig(ConfigRecord config) throws ParseException {
		if ("int".equalsIgnoreCase(config.getType())) {
			configMap.put(config.getKey(), Integer.parseInt(config.getValue()));
		} else if ("TimeSpan".equalsIgnoreCase(config.getType())) {
			configMap.put(config.getKey(), sdf.parse(config.getValue()));
		} else {
			configMap.put(config.getKey(), config.getValue());
		}
	}


	public static void setContextPath(String contextPath) {
		Config.contextPath = contextPath;
	}

	public static String getContextPath() {
		return Config.contextPath;
	}
	
	@Autowired
	public void setBizDao(BizDataDaoImpl bizDao) {
		this.bizDao = bizDao;
	}

	@Autowired
	public void setMode(PlatformMode mode) {
		this.mode = mode;
	}
	
	@Override
	public long getPeriod() {
		return 600000;
	}




}
