package com.xuanwu.web.frontkit.controller;

import org.springframework.stereotype.Component;

import com.xuanwu.web.common.config.PlatformMode;
import com.xuanwu.web.common.entity.Platform;

@Component
public class FrontKit implements PlatformMode {

	@Override
	public Platform getPlatform() {
		return Platform.Frontkit;
	}
	
	@Override
	public String getName() {
		return "O2O";
	}

	@Override
	public String getVersion() {
		return "2.0";
	}
}
