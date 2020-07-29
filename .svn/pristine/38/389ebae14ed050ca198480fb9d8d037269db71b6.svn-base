$(function() {
	$("#jqGrid").jqGrid(
			{
				url : baseURL + 'order/notice/list',
				datatype : "json",
				colModel : [
						{
							label : '公告类型',
							name : 'type',
							width : 80,
							align : "center",
							formatter : function(cellvalue, options, rowObject) {
								if (cellvalue == 0) {
									return "其他";
								}
								if (cellvalue == 1) {
									return "新品";
								}
								if (cellvalue == 2) {
									return "直播";
								}
							}
						},
						{
							label : '内容',
							name : 'content',
							width : 280,
							align : "center"
						},
						{
							label : '创建时间',
							name : 'inputTime',
							width : 80,
							align : "center"
						},
						{
							label : '过期时间',
							name : 'expirationTime',
							width : 80,
							align : "center"
						},
						{
							label : '操作',
							name : 'createDate',
							width : 80,
							align : "center",
							formatter : function(cellvalue, options, rowObject) {
								var detail = '<button onclick="lineEdit(' + rowObject.seq + ')" class="operation-btn-security" ">编辑</button>'
										+ '<button class="operation-btn-dangery" onclick="del(' + rowObject.seq + ')">删除</button>';
								return detail;
							}
						} ],
				viewrecords : true,
				height : 'auto',
				rowNum : 10,
				rowList : [ 10, 30, 50 ],
				rownumbers : true,
				rownumWidth : 25,
				autowidth : true,
				multiselect : false,
				pager : "#jqGridPager",
				jsonReader : {
					root : "page.list",
					page : "page.currPage",
					total : "page.totalPage",
					records : "page.totalCount"
				},
				prmNames : {
					page : "page",
					rows : "limit",
					order : "order"
				},
				gridComplete : function() {
					// 隐藏grid底部滚动条
					$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
						"overflow-x" : "hidden"
					});
				}
			});

	$('#config-demo').daterangepicker({
		singleDatePicker : true,
		showDropdowns : true,
		autoUpdateInput : false,
		timePicker24Hour : true,
		timePicker : true,
		locale : {
			format : 'YYYY/MM/DD HH:mm:ss',
			applyLabel : "应用",
			cancelLabel : "取消",
			customRangeLabel : '选择时间',
			daysOfWeek : [ "日", "一", "二", "三", "四", "五", "六" ],
			monthNames : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
			firstDay : 0
		}
	}).on('apply.daterangepicker', function(ev, picker) {
		vm.$set(vm.announcement, "expirationTime", picker.startDate.format(picker.locale.format));
	});
});

function getGridParam(param) {
	return $("#jqGrid").jqGrid('getGridParam', param);
}

var vm = new Vue({
	el : '#rrapp',
	data : {
		showList : true,
		announcement : {},
		title : '',
		typeOfAnnouncement : [ {
			type : 0,
			typeName : "其他"
		}, {
			type : 1,
			typeName : "新品"
		}, {
			type : 2,
			typeName : "直播"
		} ]
	},
	methods : {
		reload : function(event) {
			vm.announcement = {};
			vm.showList = true;
			var page = getGridParam('page');
			$("#jqGrid").jqGrid('setGridParam', {
				page : page
			}).trigger("reloadGrid");
		},

		add : function() {
			vm.reload();
			vm.showList = false;
			vm.title = '新增公告';
		},

		saveOrUpdate : function() {
			if (vm.validator()) {
				return;
			}
			var url = "order/notice/saveOrUpdate"
			var formData = new FormData();
			if (vm.announcement.seq) {
				formData.append("seq", vm.announcement.seq);
			}
			formData.append("type", vm.announcement.type);
			formData.append("content", vm.announcement.content);
			formData.append("expirationTime", vm.announcement.expirationTime);
			$.ajax({
				type : "POST",
				url : baseURL + url,
				contentType : false,
				processData : false,
				data : formData,
				enctype : 'multipart/form-data',
				cache : false,
				success : function(r) {
					if (r.code === 0) {
						alert('操作成功', function() {
							vm.reload();
						});
					} else {
						alert(r.msg);
					}
				},
				error : function() {
					parent.window.hideLoading();
					alert('服务器出错啦');
				}
			});
		},
		validator : function() {
			if (vm.announcement.type != 0 && isBlank(vm.announcement.type)) {
				alert("请选择公告类型！");
				return true;
			}
			if (isBlank(vm.announcement.content)) {
				alert("公告内容不能为空！");
				return true;
			}
			if (isBlank(vm.announcement.expirationTime)) {
				alert("过期时间不能为空！");
				return true;
			}
		},
		categoryChoosetOpic : function(item) {
			vm.$set(vm.announcement, "typeName",item.typeName);
			vm.$set(vm.announcement,"type",item.type);
		}
	}
});

function del(seq) {
    confirm('确定要删除此公告？', function () {
		$.get(baseURL + "order/notice/del?seq=" + seq, function(r) {
            if (r.code === 0) {
            	alert('操作成功', function () {
        			vm.reload();
            	});
            } else {
            	alert(r.msg);
            }
		});
    });
}

function lineEdit(seq) {
	$.get(baseURL + "order/notice/lineEdit?seq=" + seq, function(r) {
		vm.showList = false;
		vm.title = "修改";
		if (r.announcement.type == 0) {
			r.announcement.typeName = "其他";
		}
		if (r.announcement.type == 1) {
			r.announcement.typeName = "新品";
		}
		if (r.announcement.type == 2) {
			r.announcement.typeName = "直播";
		}
		vm.announcement = r.announcement;

	});
}
