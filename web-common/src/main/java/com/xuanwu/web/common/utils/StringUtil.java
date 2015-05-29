package com.xuanwu.web.common.utils;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

public class StringUtil {

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 格式化json字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String fixJsonStr(String src) {
		if (StringUtils.isBlank(src)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		escapeJson(src, sb);
		return sb.toString();
	}

	public static String fixInput(String src) {
		if (src == null) {
			return "";
		}
		if (StringUtils.isBlank(src)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		char ch;
		for (int i = 0; i < src.length(); i++) {
			ch = src.charAt(i);
			switch (ch) {
			case '\b':
				sb.append("\\b");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '"':
				sb.append("\\\"");
				break;
			case '\'':
				sb.append("\\\'");
				break;
			case '“':
				sb.append("“");
				break;
			case '”':
				sb.append("”");
				break;
			default:
				if ((ch >= '\u0000' && ch <= '\u001F')
						|| (ch >= '\u007F' && ch <= '\u009F')
						|| (ch >= '\u2000' && ch <= '\u20FF')) {
					String c = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - c.length(); k++) {
						sb.append('0');
					}
					sb.append(c.toUpperCase());
				} else {
					sb.append(ch);
				}

			}
		}
		return sb.toString();
	}

	/**
	 * 格式化json字符串 不转义/
	 * 
	 * @param src
	 * @return
	 */
	public static String fixJsonStrWithOutSlash(String src) {
		if (StringUtils.isBlank(src)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		escapeJsonWithOutSlash(src, sb);
		return sb.toString();
	}

	/**
	 * @param s
	 *            - Must not be null.
	 * @param sb
	 */
	static void escapeJson(String s, StringBuilder sb) {
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				// Reference: http://www.unicode.org/versions/Unicode5.1.0/
				if ((ch >= '\u0000' && ch <= '\u001F')
						|| (ch >= '\u007F' && ch <= '\u009F')
						|| (ch >= '\u2000' && ch <= '\u20FF')) {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}// for
	}

	static void escapeJsonWithOutSlash(String s, StringBuilder sb) {
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			// case '/':
			// sb.append("\\/");
			// break;
			default:
				// Reference: http://www.unicode.org/versions/Unicode5.1.0/
				if ((ch >= '\u0000' && ch <= '\u001F')
						|| (ch >= '\u007F' && ch <= '\u009F')
						|| (ch >= '\u2000' && ch <= '\u20FF')) {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}// for
	}

	/**
	 * 从后面截取字符串
	 * 
	 * @param str
	 *            需要截取的字符串
	 * @param length
	 *            要截取的长度
	 * @return
	 */
	public static String subStringOfAfter(String str, int length) {
		String sub = str.substring(str.length() - length, str.length());
		return sub;
	}

	/**
	 * 转换空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String trimNull(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		return str.trim();
	}

	/**
	 * 截取路径（如：1.3.5.）的前置部分
	 * 
	 * @param src
	 *            原路径
	 * @param ch
	 *            路径分隔字符
	 * @param limit
	 *            路径层次
	 * @return
	 */
	public static String prePath(String src, char ch, int limit) {
		if (src == null)
			return null;
		char[] arr = new char[src.length()];
		int l = 0;
		for (int i = 0; i < src.length(); i++) {
			arr[i] = src.charAt(i);
			if (arr[i] != ch) {
				continue;
			}
			l++;
			if (l == limit) {
				return new String(arr, 0, i + 1);
			}
		}
		return src;
	}

	/**
	 * 去除86，+86及0号码前缀
	 */
	public static String removeZhCnCode(String phone) {
		if (phone == null) {
			return null;
		}
		if (phone.length() < 10) {
			return phone;
		}
		if (phone.startsWith("+86")) {
			return phone.substring(3, phone.length());
		}
		if (phone.startsWith("86")) {
			return phone.substring(2, phone.length());
		}
		if (phone.startsWith("01") && phone.charAt(2) != '0') {
			return phone.substring(1);
		}

		return phone;
	}

	public static String replaceHtml(String str) {
		if (null != str) {
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
			str = str.replaceAll("\\\\'", "&#39;"); // 把json转义后的\'当做一个整体替换
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("\\\\\"", "&#34;");// 把json转义后的\"当做一个整体替换
			str = str.replaceAll("\"", "&#34;");
		}
		return str;
	}

	/**
	 * 
	 * @param value
	 *            待转义的值
	 * @param escapeHtml
	 *            是否转义html字符
	 * @param escapeJson
	 *            是否转义json字符
	 * @return
	 */
	public static String escapeValue(String value, boolean escapeHtml,
			boolean escapeJson) {
		if (value == null) {
			return value;
		}
		if (escapeHtml) {
			value = HtmlUtils.htmlEscape(value);
		}
		if (escapeJson) {
			value = JavaScriptUtils.javaScriptEscape(value);
		}
		return value;
	}

	public static String hidePhone(String phone) {
		if (null == phone || phone.length() < 11)
			return phone;

		String start = phone.substring(0, phone.length() - 8);
		String end = phone.substring(phone.length() - 4, phone.length());
		StringBuilder sb = new StringBuilder();
		sb.append(start);
		sb.append("****");
		sb.append(end);

		return sb.toString();
	}

	/**
	 * 科学计数转换成字符
	 */
	public static String fixDoubleStr(double dou) {
		DecimalFormat df = new DecimalFormat("#.####");
		String doubleStr = df.format(dou);
		return doubleStr;
	}

	public static String fixIntStr(double num) {
		DecimalFormat df = new DecimalFormat("#");
		String numStr = df.format(num);
		return numStr;
	}

	public static void main(String[] args) {
		String a = "\"ttes";
		a = fixJsonStr(a);
		System.out.println(a);
		a = replaceHtml(a);
		System.out.println(a);
	}
}
