$(function() {
	$("#jqGrid")
			.jqGrid(
					{
						url : baseURL + '/online/user/list',
						datatype : "json",
						mtype : "POST",
						shrinkToFit : false,
						autoScroll : true, // shrinkToFit: false,autoScroll:
						// true,这两个属性产生水平滚动条
						autowidth : true,
						postData : {
							'keywords' : vm.keywords
						},
						colModel : [
								{
									label : '微信号',
									name : 'openid',
									width : 120,
									align : "center",
								},
								{
									label : '购买总金额/元',
									name : 'allPrice',
									width : 100,
									align : "center",
								},
								{
									label : '购买次数',
									name : 'allFrequency',
									width : 100,
									align : "center",
								},
								{
									label : '积分',
									name : 'allPrice',
									width : 120,
									align : "center",
								},
								{
									label : '推荐总收益/元',
									name : 'allPrice',
									width : 120,
									align : "center",
								},
								{
									label : '近期预期收益/元',
									name : 'allPrice',
									width : 130,
									align : "center",
								},
								{
									label : '已结总收益/元',
									name : 'allPrice',
									width : 120,
									align : "center",
								},
								{
									label : '已结提现金额/元',
									name : 'allPrice',
									width : 130,
									align : "center",
								},
								{
									label : '目前提现申请金额/元',
									name : 'allPrice',
									width : 150,
									align : "center",
								},
								{
									label : '操作',
									name : 'createDate',
									width : 280,
									align : "center",
									formatter : function(cellvalue, options,
											rowObject) {
										var detail = '<button class="operation-btn-security" onclick="lineEdit('
												+ rowObject.seq
												+ ')">查看近期的订单记录</button>'
												+ '&nbsp'
												+ '<button class="operation-btn-warn onclick="lineEdit('
												+ rowObject.seq
												+ ')">允许提现</button>'
												+ '&nbsp'
												+ '<button class="operation-btn-dangery onclick="del('
												+ rowObject.seq
												+ ')">拒绝</button>';
										return detail;
									}
								} ],
						viewrecords : false,
						height : 'auto',
						rowNum : 10,
						rowList : [ 10, 30, 50 ],
						rownumbers : true,
						rownumWidth : 25,
						multiselect : false,
						pager : "#jqGridPager",
						jsonReader : {
							root : "page.list",
							page : "page.currPage",// 数据页码(当前页码currPage)
							total : "page.totalPage",// 数据总页码(总页数totalPage)
							records : "page.totalCount"// 数据总记录数(totalCount
						// 总记录数)
						},
						prmNames : {
							page : "page",
							rows : "limit",
							order : "order"
						},
					});
});


var vm = new Vue({
	el : '#rrapp',
	data : {
		keywords : '',
		showList : true,
		title : ''
	},
	methods : {
		orderStatus :function (){
			
		},
		search :function (){
			
		}
	},
	created : function() {
	}
});

function del(id) {
	$.get(baseURL + "order/deleteOeder?seq=" + id, function(r) {
		if (r.meg = 1) {
			parent.location.reload();
		}
	});
}

