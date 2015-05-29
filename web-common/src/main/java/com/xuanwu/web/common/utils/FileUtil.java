/*   
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

/**
 * @Description: 文件操作工具类
 * @Author <a href="hw86xll@163.com">Wei.Huang</a>
 * @Date 2012-9-13
 * @Version 1.0.0
 */
public class FileUtil {
	public static final String DIR_TMP = "/tmp/";

	public static final String DIR_FILES = "/files/";

	private static AtomicInteger genrator = new AtomicInteger();

	public static final int MAX_MEDIA_FILE_LENGTH = 1 * 1024 * 1024; // 1M

	public static final FastDateFormat fileFormat = FastDateFormat
			.getInstance("yyyyMMddHHmmssSSS");

	public static File createTmpFile(String contextPath, String fileType) {
		StringBuilder sb = new StringBuilder();
		sb.append(contextPath);
		sb.append(DIR_TMP);
		File dir = new File(sb.toString());
		if (!dir.exists())
			dir.mkdirs();
		sb.append(fileFormat.format(new Date()));
		sb.append(getSerialNum());
		sb.append(fileType);
		return new File(sb.toString());
	}

	public static File getTmpFile(String contextPath, String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(contextPath);
		sb.append(DIR_TMP);
		sb.append(fileName);
		return new File(sb.toString());
	}

	private static String getSerialNum() {
		int num = genrator.getAndIncrement();
		num = Math.abs(num % 100);
		return String.format("%02d", num);
	}

	public static String subFileName(String fileName) {
		if (StringUtils.isBlank(fileName))
			throw new NullPointerException("File name is null");
		int idx = fileName.lastIndexOf('.');
		if (idx < 0) {
			return "";
		}
		return fileName.substring(0, idx);
	}

	public static String getType(String fileName) {
		int idx = fileName.lastIndexOf('.');
		if (idx < 0) {
			return "";
		}
		return fileName.substring(idx);
	}

	public static byte[] readFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		try {
			int fileLen = fis.available();
			if (fileLen > MAX_MEDIA_FILE_LENGTH) {
				return null;
			}

			byte[] data = new byte[fileLen];
			fis.read(data);
			return data;
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	public static void closeOutputStrem(OutputStream out) {
		if (out == null)
			return;
		try {
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: Ignore it
		}
	}

	public static void closeInputStrem(InputStream in) {
		if (in == null)
			return;
		try {
			in.close();
		} catch (Exception e) {
			// TODO: Ignore it
		}
	}

	public static void closeReader(Reader reader) {
		if (reader == null)
			return;
		try {
			reader.close();
		} catch (Exception e) {
			// TODO: Ignore it
		}
	}

	public static void closeWriter(Writer writer) {
		if (writer == null)
			return;
		try {
			writer.close();
		} catch (Exception e) {
			// TODO: Ignore it
		}
	}

	/**
	 * Check that the source file/folder exists.
	 * 
	 * @param source
	 * @return
	 */
	public static boolean isExists(File source) {
		if (!source.exists()) {
			throw new IllegalArgumentException("The source for the "
					+ source.getName() + "cannot be found.");
		}
		return true;
	}

	public static String getCharset(File file) {
		String code = "GBK";
		FileInputStream fis = null;
		try {
			byte[] head = new byte[3];
			fis = new FileInputStream(file);
			fis.read(head);
			code = getCharset(head);
		} catch (Exception e) {
			//
		} finally {
			closeInputStrem(fis);
		}
		return code;
	}

	public static String getCharset(byte[] head) {
		String code = "GBK";
		if (head[0] == (byte) 0xFF && head[1] == (byte) 0xFE) {
			code = "UTF-16LE";
		} else if (head[0] == (byte) 0xFE && head[1] == (byte) 0xFF) {
			code = "UTF-16BE";
		} else if (head[0] == (byte) 0xEF && head[1] == (byte) 0xBB
				&& head[2] == (byte) 0xBF) {
			code = "UTF-8";
		}
		return code;
	}

	public static void zipDirectory(String dir, File file) throws IOException,
			IllegalArgumentException {
		ZipOutputStream out = null;
		try {
			File d = new File(dir);
			if (!d.isDirectory())
				throw new IllegalArgumentException("Not a directory:" + dir);

			File[] files = d.listFiles();
			out = new ZipOutputStream(new FileOutputStream(file));

			int bytesRead;
			byte[] buffer = new byte[8192];
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.isDirectory())
					continue;
				FileInputStream in = null;
				try {
					in = new FileInputStream(f);
					ZipEntry entry = new ZipEntry(f.getName());
					out.putNextEntry(entry);
					while ((bytesRead = in.read(buffer)) != -1)
						out.write(buffer, 0, bytesRead);
				} finally {
					closeInputStrem(in);
				}
			}
			FileUtils.deleteDirectory(d);
		} finally {
			closeOutputStrem(out);
		}
	}
}
