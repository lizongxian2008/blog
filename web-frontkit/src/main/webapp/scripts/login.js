function checkForm(){
	if(""==$.trim($("#username").val())) {
		$("#username").focus();
		$("#checkUserName").text("账号不能为空");
		return false;
	}else{
		$("#checkUserName").text("");
	}
	if(""==$.trim($("#password").val())) {
		$("#password").focus();
		$("#checkPassword").text("密码不能为空");
		return false;
	}else{
		$("#checkPassword").text("");
	}
}
function resetForm(){
	$("#username").val("");
	$("#password").val("");
	$("#checkUserName").text("");
	$("#checkPassword").text("");
}
function swapImg(o,t){
	var src = $(o).attr("src");
	if(t=="over"){
		$(o).attr("src",src.replace("_out.gif","_over.gif"));
	}else{
		$(o).attr("src",src.replace("_over.gif","_out.gif"));
	}
}
function resetpwd(){
	showHtmlDiag(420,160,"忘记密码和重置密码",$("#resetPwdDiv"));
	var obj = $("#username");
	obj.removeClass("idleField");
	obj.addClass("txt");
	obj.unbind("focus");
	obj.unbind("blur");
	$("#reset_username").val($("#username").val());
}
function resetPassword(){
	if(""==$.trim($("#reset_username").val())) {
		$("#reset_username").focus();
		alert("账号不能为空");
		return;
	}
	var mobile = $.trim($("#reset_mobile").val());
	if(!(/^(86|\+86)?((0((10[0-9]{7,8})|([2-9]{1}[0-9]{8,10})))|(0?1[3-8]{1}[0-9]{9}))$/.test(mobile))){ 
		$("#reset_mobile").focus();
		alert("手机号码格式无效");
		return;
	}
	var form = $("#resetPwdForm");
	$.post(form.attr("action"), form.serialize(),
		function(json){
			if(json.ret==0){
				$.unblockUI();
				alert("重置密码成功，密码已经发送到您的手机，请注意查收。");
			}else{
				alert("登录名或手机号与已有信息不一致，不能取回密码，请联系管理员。");
			}
		});
}