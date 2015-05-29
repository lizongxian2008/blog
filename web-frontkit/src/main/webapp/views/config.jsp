<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xuanwu.web.common.utils.SessionUtil" %>
<%@ page trimDirectiveWhitespaces="true"%>
<%
final String context = request.getContextPath();
final String source = context;
final String theme = "default";//风格主题样式
final String webTitle = "集团短信通用平台";//网站标题
final String releaseTime = "2013-03-01 09:30:00"; //发布时间

//folder
final String css = source+"/css/"+theme;
final String images = source+"/images/"+theme;
final String scripts = source+"/scripts";
final String datePicker = scripts+"/my97DatePicker";

//js
final String jquery_js = scripts+"/jquery-1.11.2.min.js";
final String jquery_cookie_js = scripts+"/jquery-cookie.min.js";
final String jquery_ui_js = scripts+"/jquery-ui-1.9.2.custom.min.js";
final String blockUI_js = scripts+"/jquery.blockUI.js";
final String belatedPNG_js = scripts+"/DD_belatedPNG_0.0.8a-min.js";
final String flexigrid_js = scripts+"/flexigrid.pack.js";
final String flexigrid_total_js = scripts+"/flexigrid.async.total.js";
final String zTree_js = scripts+"/jquery.ztree-3.5.min.js";
final String ajaxfileupload_js = scripts+ "/ajaxfileupload.2.1.js";
final String datePicker_js = datePicker+ "/WdatePicker.js";
final String inputSelectStyle_js = scripts+ "/input-select.style.js";
final String dropdownlist_js = scripts + "/dropdownlist.js";
final String autocomplete_js = scripts + "/jquery.autocomplete.min.js";
final String highcharts_js = scripts + "/highcharts/highcharts.js";
final String bootstrap_js = scripts +"/bootstrap.min.js";

//css
final String flexigrid_css = source+"/css/flexigrid/flexigrid.pack.css";
final String zTree_css = source+"/css/zTree/zTreeStyle.css";
final String dropdownlist_css = css+"/dropdownlist.css";
final String autocomplete_css = source + "/css/autocomplete/jquery.autocomplete.css";
final String bootstrap_css = source+"/css/bootstrap/bootstrap.min.css";
final String bootstarp_theme_css = source+"/css/bootstrap/bootstrap-theme.css";

pageContext.setAttribute("source",source);
pageContext.setAttribute("context",context);
pageContext.setAttribute("webTitle",webTitle);
pageContext.setAttribute("releaseTime",releaseTime);
pageContext.setAttribute("css",css);
pageContext.setAttribute("images",images);
pageContext.setAttribute("scripts",scripts);
pageContext.setAttribute("datePicker",datePicker);
pageContext.setAttribute("jquery_js",jquery_js);
pageContext.setAttribute("jquery_cookie_js",jquery_cookie_js);
pageContext.setAttribute("jquery_ui_js",jquery_ui_js);
pageContext.setAttribute("blockUI_js",blockUI_js);
pageContext.setAttribute("belatedPNG_js",belatedPNG_js);
pageContext.setAttribute("flexigrid_js",flexigrid_js);
pageContext.setAttribute("flexigrid_total_js",flexigrid_total_js);
pageContext.setAttribute("zTree_js", zTree_js);
pageContext.setAttribute("ajaxfileupload_js", ajaxfileupload_js);
pageContext.setAttribute("datePicker_js", datePicker_js);
pageContext.setAttribute("inputSelectStyle_js", inputSelectStyle_js);
pageContext.setAttribute("dropdownlist_js",dropdownlist_js);
pageContext.setAttribute("flexigrid_css",flexigrid_css);
pageContext.setAttribute("zTree_css", zTree_css);
pageContext.setAttribute("dropdownlist_css", dropdownlist_css);
pageContext.setAttribute("autocomplete_js", autocomplete_js);
pageContext.setAttribute("autocomplete_css", autocomplete_css);
pageContext.setAttribute("highcharts_js", highcharts_js);
pageContext.setAttribute("bootstrap_css", bootstrap_css);
pageContext.setAttribute("bootstarp_theme_css", bootstarp_theme_css);
pageContext.setAttribute("bootstrap_js", bootstrap_js);
%>