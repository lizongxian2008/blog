function doAsyncTotal(g, p, url, action) {
	if(p.page > 1) {
		p.newp = 1;
		return;
	}
	
	if(p.total<p.rp){//只有一页，无需重计总数
		return;
	}
		
	$('.pPageStat', g.pDiv).html("统计总记录数中, 请稍候 ...");
	$('.pReload', g.pDiv).addClass('loading');
	g.loading = true;

	var params = {};
	for( var o in p.params){
		var param = p.params[o];
		params[param.name] = param.value;
	};
	if(action!=undefined){
		params.action = action;
	}
	
	$.post(url, params, function(json){
		if(json.ret!=0){
			alert(json.msg);
		} else {
			p.total = json.data;
			p.pages = Math.ceil(p.total / p.rp);
			$('.pReload', this.pDiv).removeClass('loading');
			p.page = p.newp;
			p.newp = 1;
			g.loading = false;
			g.buildpager(); 
		}
	});
}