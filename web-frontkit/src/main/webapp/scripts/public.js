/**
 * 页面通用脚本
 * @author Xuefang.Xu
 * @email jiji@javawind.com
 * @Data 2012-09-05
 * @Version 1.0.0
 */
var msie = /MSIE/.test(navigator.userAgent);
var ie6  = /MSIE 6.0/.test(navigator.userAgent);
var ie7  = /MSIE 7.0/.test(navigator.userAgent);
var context = "";
var navHide = false;

/**
 * 初始化导航和菜单
 */
function initNavMenu(){
	toggleNavigator(true);
	var menuId = $.cookie("pMenuId");
	if(menuId!=null && ""!=menuId){
		//展开上次打开的菜单
		hitMenu(menuId);
	}else{
		var uls = $("#side > ul");
		if(uls.length>0){
			var firstMenuId = uls.eq(0).attr("id").replace("menu_","");
			hitMenu(firstMenuId);
		}
	}
	
	var menuCur = $.cookie("menuCur");
	if(fromLogin || menuCur==null){
		fgoto('0','&nbsp;&gt;&nbsp;系统首页', context + '/main?action=showMainPage');
	}else{
		var items = menuCur.split(",");
		if(items.length == 3){
			fgoto(items[0], items[1], items[2]);
		}
	}
}

/**
 * 展开与关闭导航
 * @param cookies 是否自动
 */
function toggleNavigator(cookies) {
	var navCookieName = "navigator";
	var leftnav = $(".idx_nav");
	var titlenav = $(".nav_title");
	var samllnav = $("#nav_samll");
	var navtop = $("#nav_top");	
	var hide = leftnav.is(":hidden");
	if(cookies){
		var c = $.cookie(navCookieName);
		if(c==null){
			return;
		}else{
			hide = "true"==c?true:false;
		}
	}else{
		$.cookie(navCookieName,hide);
	}
	if (hide) {//展开
		navHide = false;
		leftnav.show();
		titlenav.show();
		samllnav.hide();
		navtop.css("width","190px");
	}else{
		navHide = true;
		leftnav.hide();
		titlenav.hide();
		samllnav.show();
		navtop.css("width","33px");
	}
	fixWindows();
}

window.onresize = function(){
	fixWindows();
};

function fixWindows(){
	var html = $("html");
	var mmsListTree = $("#list_tree");
	var content = $("#idx_content");
	var mms = $("#mmsContainer .editForm");
	var sms = $("#smsContainer .editForm");
	var minW=1000, smsW=230, mmsW=mmsListTree.length > 0 ? 420 : 230;
	var windowW=$(window).width();
	if(windowW < minW){
		html.css({'width':minW + 'px','overflow-x':'auto'});
		windowW = minW;
	} else {
		html.css({'width':'100%','overflow-x':'hidden'});
	}
	if (navHide) {
		var contentW = windowW - 30;
		content.css({"padding-left":"15px",'width':contentW + 'px'});
		sms.css({'width':(contentW - smsW) + 'px'});
		mms.css({'width':(contentW - mmsW) + 'px'});
	}else{
		var contentW = windowW - 200;
		sms.css({'width':(contentW - smsW) + 'px'});
		mms.css({'width':(contentW - mmsW) + 'px'});
		content.css({"padding-left":"190px",'width':contentW + 'px'});
	}
}

/**
 * 展开与关闭菜单
 * @param id
 */
function hitMenu(id){
	$("[id^='menu_']").each(function(){
		var menu = $(this);
		var tid = menu.attr("id").replace("menu_","");
		var menuimg = $("#menuimg_"+tid);
		var src = menuimg.attr("src");
		if(id == tid){
			menu.show();
			menuimg.attr("src",src.replace("bt_block","bt_show"));
		} else {
			menuimg.attr("src",src.replace("bt_show","bt_block"));
			menu.hide();
		}
	});
	$.cookie("pMenuId", id);
}

/**
 * 菜单打开页面
 * @param id
 * @param url
 */
function fgoto(id, navTitle, url){
	$.unblockUI();
	$.fn.pageInit = null;
	$("#fgoing").show();
	$.ajax({
		type:"GET",
		url:url,
		dataType:"html",
		cache:false,
		success:function(data, textStatus, jqXHR){
			$("#idx_content").html(data);
			if($.fn.pageInit){//页面初始化方法
				$.fn.pageInit();
			}
			fixWindows();
			initInputSelectStyle();
			$("#fgoing").hide();
		},
		error:function(jqXHR, textStatus, errorThrown){
			if(errorThrown!="Unauthorized" && errorThrown!="Not Found"){
				$("#idx_content").html(errorThrown);
			}
			$("#fgoing").hide();
		}
	});
	$("#nav_path").html(navTitle);
	menuCur(id);
	$.cookie("menuCur", id + "," +  navTitle + "," + url);
	if(searchInterval){
		clearInterval(searchInterval);
	}
	//window.scrollTo(0, 0);
}

/**
 * 当前菜单
 * @param id
 */
function menuCur(id){
	$("[id^='s_menu_']").each(function(){
		$(this).removeClass("menu_cur");
	});
	$("#s_menu_"+id).addClass("menu_cur");
	var index = $("#s_menu_"+id).parents("ul").index();
	$("#side h3").each(function (){
		$(this).removeClass("h3_cur");
	});
	$("#side h3").eq(parseInt(index/2)).addClass("h3_cur");
	//$.cookie("menuCurId", id);
}

function fixInnerDiv(h){
	var btnsHeight = 30;
	//$("#inner_div_btns").height(btnsHeight);
	$("#inner_div_content").height(h - btnsHeight - 10);
}

/**
 * 弹出网页
 * @param w
 * @param h
 * @param title
 * @param url
 * @param onUnblock
 */
function showUrlDiag(w,h,title,url,data,onUnblock){
	$.fn.popPageInit = null;
	$.post(url,data, function(html){
		showHtmlDiag(w,h,title,html,onUnblock);
		if($.fn.popPageInit){//弹出页面初始化方法
			$.fn.popPageInit();
		}
	}, 'html');
}

/**
 * 弹出信息(只适用于展示静态html数据，如数据有js等动态操作，请使用原生$.blockUI)
 * @param w
 * @param h
 * @param title
 * @param html
 * @param onUnblock
 */
function showHtmlDiag(w,h,title,html,onUnblock){
	$.unblockUI();//must close other before open a new one!
	
	var pop = new popDiag(w,h,title);
	var div = null,parent = null;
	if(typeof(html) == "string"){
		pop.content.append(html);
	}else{
		div = $(html);
		parent = $(html).parent();
		pop.content.append($(div).show());
	}
	var unBlock = function(){
		if (typeof(onUnblock) == 'function'){
			$(onUnblock);
		}
		if(div){
			$(parent).append($(div).hide());
		}
		if(ie6){
			$("select").css("visibility","");
			$("#ie6height").height(0);
			$("html").css({"overflow-y":""});
		}
	};
	if(ie6){
		$("#idx_content select").css("visibility","hidden");
		$("#ie6height").height(Math.max($(".idx_nav").height(),$("#idx_content").height()+10));
		$("html").css({"overflow-y":"hidden","padding-right":"20px"});
		$("html").css({"height":"100%"});
	}
	$.blockUI({"message":pop.box,css:{top:pop.offset.top,left:pop.offset.left},"onUnblock":unBlock});
	fixInnerDiv(h);
	initInputSelectStyle();
}
/**
 * 弹出层，适用于showUrlDiag或者showHtmlDiag后，二次遮盖弹出
 * @param w
 * @param h
 * @param title
 * @param url
 */
function showPopUrlDiag(w,h,title,url){
	$.fn.popPageInit = null;
	$.post(url,function(html){
		var pop = new popDiag(w,h,title,true);
		pop.content.append(html);
		popDiv($(pop.box),pop.offset.top,pop.offset.left);
		if($.fn.popPageInit){//弹出页面初始化方法
			$.fn.popPageInit();
		}
		fixInnerDiv(h);
		initInputSelectStyle();
	},"html");
}
/**
 * 弹出层，适用于showUrlDiag或者showHtmlDiag后，二次遮盖弹出
 * @param w
 * @param h
 * @param title
 * @param html
 */
function showPopHtmlDiag(w,h,title,html){
	var pop = new popDiag(w,h,title,true);
	if(typeof(html) != "string"){
		var parent = html.parent();
		var div = html.clone();
		pop.content.append($(html).show());
		var popx = $(pop.box).find(".popx");
		var _hidDiv = function(){
			$(parent).append($(div).hide());
			hidDiv();
		};
		$(popx)[0].onclick = _hidDiv;
	}else{
		pop.content.append(html);
	}
	popDiv($(pop.box),pop.offset.top,pop.offset.left);
	fixInnerDiv(h);
	initInputSelectStyle();
	return pop;
}

function popDiag(w,h,title,x){
	var xclick = x?"hidDiv();":"$.unblockUI();";
	this.box = $('<div class="popbox"/>');
	this.title = $('<div class="popTitle"/>').append('<a href="javascript:;" onclick="'+xclick+'" class="popx" title="关闭">&nbsp;</a>').append(title);
	this.content = $('<div class="popContent"/>').width(w).height(h);
	var popM = $('<div class="center_m" />').append(this.title).append(this.content);
	this.box.append('<div class="left_l" />').append(popM).append('<div class="right_r" />');
	this.offset = {left:0,top:0};
	this.calcOffset();
}
popDiag.prototype.calcOffset = function(){
	var l = ($(window).width() - this.content.width() - 32)/2;
	var t = ($(window).height() - this.content.height() - 32)/2;
	var scrollTop = $(window).scrollTop();
	if(ie6 && scrollTop){
		t += $(window).scrollTop();
	}
	if(l < 0) l = 0;
	if(t < 0) t = 0;
	this.offset.top = t;
	this.offset.left = l;
};

function popDiv(div,t,l){
	$("#pop_overflowDiv").show();
	$("#pop_overMsgDiv").empty().append($(div).show()).css({top:t,left:l}).fadeIn("normal");
	//$(div).find(".popx").hide();
	if(ie6){
		$("#idx_content select").css("visibility","hidden");
	}
}
function hidDiv(){
	$("#pop_overflowDiv").hide();
	$("#pop_overMsgDiv").empty().fadeOut("fast");
	if(ie6){
		if($(".blockUI").length > 0){
			$(".blockUI").find("select").css("visibility","");
			return;
		}//if blockUI show
		$("select").css("visibility","");
	}
}

function sendPosting(msg){
	msg = '' + msg;
	msg = msg.replace(/\n/g,"<br/>");
	var w = 360, h = 80;
	var pop = new popDiag(w,h,"请稍候...",true);
	var popMsg = $("<div/>", {"class":"popMsg"}).html('<table align="center" style="height:64px;vertical-align:middle;"><tr><td style="padding:0 10px;"><img src="images/default/loading32.gif" /></td><td><span class="prompt_msg">'+ msg +'</span></td></tr></table>');
	pop.content.append(popMsg);
	popDiv($(pop.box),pop.offset.top,pop.offset.left);
};

window.alert = function showAlert(msg,ok){
	msg = '' + msg;
	msg = msg.replace(/\n/g,"<br/>");
	var w = 360, h = 120;
	var pop = new popDiag(w,h,"系统提示信息",true);
	var popMsg = $("<div/>", {"class":"popMsg"}).html('<table align="center" style="height:64px;vertical-align:middle;"><tr><td><span class="prompt_icon_info"></span></td><td><span class="prompt_msg">'+ msg +'</span></td></tr></table>');
	var popBtns = $("<div/>", {"class":"buttons"});
	popBtns.append('<input id="btn_alert_ok" class="mms_confirm" type="button" onclick="$.fn.ok()" value="" />');
	pop.content.append(popMsg).append(popBtns);	
	popDiv($(pop.box),pop.offset.top,pop.offset.left);
	//$("#btn_alert_ok").focus();	
	$.fn.ok = function(){
		hidDiv();
		if(ok){$(ok);}
	};
};
function showConfirm(msg,ok,cancel){
	msg = '' + msg;
	msg = msg.replace(/\n/g,"<br/>");
	var w = 360, h = 120;
	var pop = new popDiag(w,h,"系统确认信息",true);
	var popMsg = $("<div/>", {"class":"popMsg"}).html('<table align="center" style="height:64px;vertical-align:middle;"><tr><td><span class="prompt_icon_ask"></span></td><td><span class="prompt_msg">'+ msg +'</span></td></tr></table>');
	var popBtns = $("<div/>", {"class":"buttons"});
	popBtns.append('<input id="btn_confirm_ok" class="mms_confirm" type="button" onclick="$.fn.ok()" value="" />');
	popBtns.append('<input id="btn_confirm_cancel" class="cancel_btn" type="button" onclick="$.fn.cancel()" value="" />');
	pop.content.append(popMsg).append(popBtns);
	popDiv($(pop.box),pop.offset.top,pop.offset.left);
	//$("#btn_confirm_cancel").focus();
	$.fn.ok = function(){
		hidDiv();
		if(ok){$(ok);}
	};
	$.fn.cancel = function(){
		hidDiv();
		if(cancel){$(cancel);}
	};
}

function pageNavInit(){
	if($(".navB_bt_A").length > 0){
		return;
	}
	var obj = $(".page_nav_B_bg").children().first();
	if(obj.attr("class") == "navB_bt_B"){
		obj.attr("class", "navB_bt_A");
		obj.attr("onmouseover", "this.className='navB_bt_A_over'");
		obj.attr("onmouseout", "this.className='navB_bt_A'");
		return;
	}
	if(obj.attr("class") == "navB_bt_B_down"){
		obj.attr("class", "navB_bt_A_down");
	}
}

/**
 * 时间格式:yyyy-MM-dd HH:mm:ss
 * @param dateStr
 */
function toDate(dateStr){
	var arr = dateStr.substring(0, 10).split('-');
	var time = arr[1] + '/' + arr[2] + '/' + arr[0] + dateStr.substring(10, 19);
	return Date.parse(time);
}

String.prototype.replaceAll = function(token, newToken){
	var str, i = -1;
	if((str = this.toString()) && typeof(token) === "string") {
		while((i = str.indexOf(token, i >= 0 ? i + newToken.length : 0)) !== -1 ) {
			str = str.substring(0, i).concat(newToken).concat(str.substring(i + token.length));
		}
	}
	return str;
};

String.prototype.html2Text = function(){
	return this.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
};

/**
 * 有些情况下需要还原被转义的字符串
 */
String.prototype.resumeText=function() {
	var str = this.toString();
	str = str.replace(/&lt;/g,"<");
	str = str.replace(/&gt;/g,">");
	str = str.replace(/&#34;/g,"\"");
	str = str.replace(/&#39;/g,"\'");
	return str;
};

$.fn.extend({
	/**
	 * 初始化对象以支持光标处插入内容
	 */ 
	setCaret: function(){
		if(!$.browser.msie) return;
		var initSetCaret = function(){
			var textObj = $(this).get(0);
			textObj.caretPos = document.selection.createRange().duplicate();
		}; 
		$(this).click(initSetCaret).select(initSetCaret).keyup(initSetCaret);
	}, 
	/**
	 * 在当前对象光标处插入指定的内容
	 */ 
	insertAtCaret: function(textFeildValue){
		var textObj = $(this).get(0);
		if(document.all && textObj.createTextRange && textObj.caretPos){
			var caretPos=textObj.caretPos;
			caretPos.text = caretPos.text.charAt(caretPos.text.length-1) == '' ? textFeildValue+'' : textFeildValue;
		} else if(textObj.setSelectionRange){
			var rangeStart=textObj.selectionStart;
			var rangeEnd=textObj.selectionEnd;
			var tempStr1=textObj.value.substring(0,rangeStart);
			var tempStr2=textObj.value.substring(rangeEnd);
			textObj.value=tempStr1+textFeildValue+tempStr2;
			textObj.focus();
			var len=textFeildValue.length;
			textObj.setSelectionRange(rangeStart+len,rangeStart+len);
			textObj.blur();
		} else {
			textObj.value+=textFeildValue;
		}
	}
});

/**
 * 去除86，+86及0号码前缀
 */
function removeZhCnCode(phone){
	if(phone.length < 10) return phone;
	var sub = phone.substring(0, 2);
	if('86' == sub) return phone.substring(2);
	if('01' == sub && phone.charAt(2) != 0){
		return phone.substring(1);
	}
	sub = phone.substring(0, 3);
	if('+86' == sub) return phone.substring(3);
	return phone;
}

function formatTime(second){
	var minute = Math.floor(second/60);
	second = second%60;
	return checkTime(minute) + ":" + checkTime(second);
}

function checkTime(num){
	if(num < 10){
		return '0' + num;
	}
	return '' + num;
}