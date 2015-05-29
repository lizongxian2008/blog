/**
 * 输入框样式和数据校验通用脚本
 * @author Xuefang.Xu
 * @email jiji@javawind.com
 * @Data 2012-09-05
 * @Version 1.0.0
 */
var charWidth = 12, maxCharLen = 20;
String.prototype.charsLen=function(){//获取字符串的字节长度
	return this.length + (this.match(/[^\x00-\xff]/g) || "").length;// 加上匹配到的全角字符长度
};
String.prototype.cutMaxChar=function(){//截取超长字符串
	var text = $.trim(this);
	if(text.length>maxCharLen){
		return text.substring(0,(maxCharLen-3))+"...";
	}
	return text;
};
$.fn.extend({
	jselect : function() {
		return $(this).each(function() {
			//var width = $(this).width()>0?$(this).width():this.style.width;// 因为ff取不到auto
			var opt = $("option", $(this));
			if(opt.length==0){
				return false;
			}			
			var maxCharSize = 0;
			opt.each(function(i,o) {
				var charsSize = $(o).text().cutMaxChar().charsLen();
				maxCharSize = charsSize>maxCharSize?charsSize:maxCharSize;
			});
			var width = parseInt(maxCharSize/2)*charWidth + 30;
			var span = $("<span/>").css({"display":"inline-block"}).html("&nbsp;").width(width);
			$(this).after(span).after('<div><input type="hidden" /><div class="jslct"><em></em><dl></dl></div></div>');
			var div = $(this).next("div").css({"position":"absolute","display":"inline-block","z-index":"50"}).width(width);
			var ipt = div.find("input");
			var lst = div.find("div").width(width);
			var itms = $("dl", lst).width(width-2);
			var itm = null;
			//var opt = $("option", $(this));
			var opts = $("option:selected", $(this));
			var opts_index = opt.index(opts);
			var em = $("em", lst);
			var fn_change = $(this).attr("onchange");
			ipt.attr("id", $(this).attr("id")).attr("name", $(this).attr("name")).val($(this).val());
			em.text($("option:selected", $(this)).text().cutMaxChar());
			opt.each(function(i) {
				itms.append("<dd></dd>");
				itm = $("dd", itms);
				var dd = itm.eq(i);
				var stxt = $(this).text().cutMaxChar();
				dd.attr({"val":$(this).val(),"title":$(this).text()}).text(stxt);
			});
			itm.eq(0).addClass("noborder");
			itm.eq(opts_index).addClass("jslcted");
			$(this).remove();
			lst.hover(function() {
				$(this).addClass("jslct_hover");
				return false;
			}, function() {
				$(this).removeClass("jslct_hover");
				return false;
			});
			itm.hover(function() {
				$(this).addClass("hover");
			}, function() {
				$(this).removeClass("hover");
			});
			itm.width(itms.width() + 2);
			itms.css({
				width : itms.width(),
				"overflow-x" : "hidden",
				"overflow-y" : "auto"
			});
			lst.click(function() {
				lstshow();
			});
			$(document).mouseup(function() {
				lsthide();
			});
			itm.click(function() {				
				itm.removeClass("jslcted");
				$(this).addClass("jslcted");
				em.text($(this).text());
				ipt.val($(this).attr("val"));
				lsthide();
				
				if (fn_change) {
					try{
						(eval(fn_change))();
					}catch (e) {}
				}
				return false;
			});
			function lstshow() {
				var maxheight = $(document).height() - 200;
				itms.css({height:"auto"});
				maxheight = itms.height() > maxheight ? maxheight : "auto";
				itms.css({height:maxheight});
				itms.show();
				//lst.css("z-index", "1000");
			};
			function lsthide() {
				$(".jslct dl").hide();
				//$(".jslct").css("z-index", "0");
			};
		});
	}
});
var _vPrefix = "_verify_";
$.fn.extend({
	verifyMsg : function(vcheck,msg) {
		if($(this).attr("titlemsg")){
			$(this).attr("title",msg);
		}else if(vcheck){
			$(vcheck).css("color","#ff0000").text(" "+msg).show();
		}
	},
	verifyInput : function() {
		var type = $(this).attr("type");
		var isText = ("text"==type);
		var name = $(this).attr("name");
		var id = $(this).attr("id");
		var vid = (id==undefined?name:id);
		if(!vid){//没有id和name属性的表单不作校验
			return true;
		}
		vid = vid.replace(new RegExp("\\[\\]","gm"),"");
		vid = vid.replace(new RegExp("\\.","gm"),"");
		
		var vcheck = $("#"+_vPrefix+vid);
		var titlemsg = $(this).attr("titlemsg");
		if(!titlemsg || "false"==titlemsg){
			if(vcheck.length==0){
				vcheck = $("<"+(isText?"span":"div")+"/>").attr("id",_vPrefix+vid);
				$(this).after(vcheck);
			}
			vcheck.text("").hide();
		} else {
			$(this).removeAttr("title");
		}
		$(this).removeClass("errorField");
		
		var val = $.trim($(this).val());
		var valLen = val.length;
		var notempty = $(this).attr("notempty");
		if(valLen==0){
			if(notempty && notempty != "false"){
				$(this).addClass("errorField");
				var msg = "此文本框为必填项";
				if($(this).attr("emptymsg")){
					msg = $(this).attr("emptymsg");
				}
				$(this).verifyMsg(vcheck,msg);
				return false;
			}
		}else{//The input has text!
			var maxLength = $(this).attr("maxlength");
			if(!isText && maxLength!=undefined && valLen>maxLength){
				$(this).addClass("errorField");
				var msg = "此文本框的文字不能超过"+maxLength+"个字符";
				if($(this).attr("maxlenmsg")){
					msg = $(this).attr("maxlenmsg");
				}
				$(this).verifyMsg(vcheck,msg);
				return false;
			}
			
			var regpat = $(this).attr("regpat");
			var regmsg = $(this).attr("regmsg");
			if(regpat!=undefined && regmsg!=undefined && !val.match(regpat)){
				$(this).addClass("errorField");
				$(this).verifyMsg(vcheck,regmsg);
				return false;
			}
			
			var intReg = /^(-?[1-9]\d*|0)$/;//int:正负整数
			var floatReg = /^(-?)(\d*\.)?\d+$/;//float:整数+浮点数
			var numReg = /^[0-9]*$/;//num:纯数字
			var signlessInt = /^[0-9]*[1-9][0-9]*$/;//正整数
			var numType = $(this).attr("numtype");
			var reg = "";
			if("int"==numType){
				reg = intReg;
			}else if("num"==numType){
				reg = numReg;
			}else if("signless"==numType){
				reg = signlessInt;
			}else{
				reg = floatReg;
			}
			if(numType!=undefined && !reg.test(val)){
				$(this).addClass("errorField");
				var msg = "此文本框的数值必须是数字";
				if($(this).attr("nummsg")){
					msg = $(this).attr("nummsg");
				}
				$(this).verifyMsg(vcheck,msg);
				return false;
			}
			
			var max = $(this).attr("max");
			if(max!=undefined && parseInt(val)>parseInt(max)){
				$(this).addClass("errorField");
				var msg = "此文本框的数值不能大于"+max;
				if($(this).attr("maxmsg")){
					msg = $(this).attr("maxmsg");
				}
				$(this).verifyMsg(vcheck,msg);
				return false;
			}
			
			var min = $(this).attr("min");
			if(min!=undefined && parseInt(val)<parseInt(min)){
				$(this).addClass("errorField");
				var msg = "此文本框的数值不能小于"+min;
				if($(this).attr("minmsg")){
					msg = $(this).attr("minmsg");
				}
				$(this).verifyMsg(vcheck,msg);
				return false;
			}
		}
		
		return true;
	},
	verifyForm : function() {
		var flag = true;
		$(this).find(":text").each(function(){
			if(!$(this).verifyInput()){
				flag = false;
			}
		});
		$(this).find("textarea").each(function(){
			if(!$(this).verifyInput()){
				flag = false;
			}
		});
		return flag;
	},
	emptyForm : function(){
		$(this).find(":text").each(function(){
			if($(this).attr("titlemsg")){
				$(this).removeAttr("title");
			}
			$(this).removeClass("errorField").val("");
		});
		$(this).find(":radio").each(function(){
			if($(this).val()==0){
				$(this).attr("checked","checked");
			}
		});
		$(this).find(":checkbox").each(function(){
			if($(this).val()==0){
				$(this).attr("checked","checked");
			}
		});
		$(this).find("textarea").each(function(){
			$(this).val("");
		});
		$(this).find("[id^='"+_vPrefix+"']").each(function(){
			$(this).text("").hide();
		});
	}
});
/**
 * 输入框样式
 */
function initInputStyle() {
	$(":text").addClass("idleField");
	$(":text").focus(function() {
		$(this).removeClass("errorField");
		$(this).removeClass("idleField").addClass("focusField");
	}).blur(function() {
		$(this).removeClass("focusField").addClass("idleField");
		$(this).verifyInput();
	});
	$(":radio").addClass("radio");
	$(":checkbox").addClass("chkbox");
	
	$("textarea").blur(function() {
		$(this).verifyInput();
	});
}
/**
 * select下拉框样式,仅对class="jselect"有效
 */
function initSelectStyle() {
	$("select").each(function(i, o) {
		if("jselect"==$(o).attr("class")){
			$(o).jselect();
		}
	});
}
/**
 * 初始化输入框和下拉框样式
 */
function initInputSelectStyle() {
	initInputStyle();
	initSelectStyle();
}