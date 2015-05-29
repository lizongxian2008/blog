package com.xuanwu.web.common.file;

import java.util.LinkedHashMap;

public class FileHead {
	
	private LinkedHashMap<Integer, String> headMap = new LinkedHashMap<Integer, String>();

	public LinkedHashMap<Integer, String> getHeadMap() {
		return headMap;
	}

	public void putCell(int index, String name) {
		headMap.put(index, name);
	}
}
