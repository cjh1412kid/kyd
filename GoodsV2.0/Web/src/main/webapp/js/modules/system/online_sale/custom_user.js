$(function() {
	$("#jqGrid").jqGrid(
			{
				url : baseURL + 'online/customUser/list',
				datatype : "json",
			    postData: {
			        'nickNameState': vm.nickNameState,
			        'telephoneState': vm.telephoneState,
			        'startTime': vm.startTime,
			        'endTime': vm.endTime
			    },
				colModel : [
						{
							label : '昵称',
							name : 'nickName',
							width : 80,
							align : "center"
						},
						{
							label : '手机号',
							name : 'telephone',
							width : 100,
							align : "center"
						},
						{
							label : '添加时间',
							name : 'inputTime',
							width : 120,
							align : "center"
						},
						{
							label : '总订单数量',
							name : 'orderNum',
							width : 60,
							align : "center"
						},
						{
							label : '总订单金额',
							name : 'totalOrderPrice',
							width : 60,
							align : "center"
						},
						{
							label : '是否预付款',
							name : 'totalPaidPrice',
							width : 60,
							align : "center",
							formatter : function(cellvalue, options, rowObject) {
								if (cellvalue > 0) {
									return "是";
								} else {
									return "否";
								}
							}
						},
						{
							label : '总预付款金额',
							name : 'totalPaidPrice',
							width : 60,
							align : "center"
						},
						{
							label : '最后下单日期',
							name : 'lastOrderTime',
							width : 120,
							align : "center"
						},
						{
							label : '操作',
							name : '',
							width : 80,
							align : "center",
							formatter : function(cellvalue, options, rowObject) {
								var detail = '';
					            if (rowObject.isUse === 1) {
					                detail += ('<button class="operation-btn-warn" onclick="enableUser(' + rowObject.seq + ')">启用</button><br/>');
					             } else if (rowObject.isUse === 0) {
					                detail += ('<button class="operation-btn-security" onclick="disableUser(' + rowObject.seq + ')">停用</button><br/>');
					             }
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
			        var records = getGridParam('records');
			        vm.recordsNum = records;
				}
	  });
	
	
	  $('#config-demo').daterangepicker({
		    showDropdowns: true,
		    autoUpdateInput: false,
		    timePicker24Hour: true,
		    timePicker: true,
		    locale: {
		      format: 'YYYY/MM/DD HH:mm:ss',
		      applyLabel: "应用",
		      cancelLabel: "取消",
		      customRangeLabel: '选择时间',
		      daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
		      monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
		      firstDay: 0
		    }
		  }).on('apply.daterangepicker', function (ev, picker) {
		    vm.startTime = picker.startDate.format(picker.locale.format);
		    vm.endTime = picker.endDate.format(picker.locale.format);
		    vm.start_endTime = vm.startTime + " ~ " + vm.endTime;
	  });
	  
});

function getGridParam(param) {
	return $("#jqGrid").jqGrid('getGridParam', param);
}

var vm = new Vue({
	el : '#rrapp',
	data : {
	    nickNameState: '',
	    telephoneState: '',
	    selectPeriodName: '',
	    periods: {},
	    periodSeq: -1,
	    selectPeriodName: '',
	    startTime: '',
	    endTime: '',
	    start_endTime: '',
	    recordsNum:''
	  },
	methods : {
	    loadPeriod: function () {
	        $.get(baseURL + "order/goods/periods", function (r) {
	          vm.periods = r.periods;
	        });
	    },
	    
	    periodSelect: function (item) {
	        if (item == -1) {
	           vm.periodSeq = -1;
	           vm.selectPeriodName = "所有波次";
	        } else {
	           vm.periodSeq = item.seq;
	           vm.selectPeriodName = item.name;
			   vm.startTime = item.meetingStartTime;
			   vm.endTime = item.meetingEndTime;
			   vm.start_endTime = vm.startTime + " ~ " + vm.endTime;
	        }
	    },
	    
	    search: function () {
	        $("#jqGrid").jqGrid('setGridParam', {
	          datatype: 'json',
	          postData: {
			        'nickNameState': vm.nickNameState,
			        'telephoneState': vm.telephoneState,
			        'startTime': vm.startTime,
			        'endTime': vm.endTime
	          },
	          page: 1,
	        }).trigger('reloadGrid');
	        var records = getGridParam('records');
	        vm.recordsNum = records;
	    },
	      
		reload : function(event) {
			var page = getGridParam('page');//获取当前页
			$("#jqGrid").jqGrid('setGridParam', {
				datatype: 'json',
		        postData: {
				      'nickNameState': vm.nickNameState,
				      'telephoneState': vm.telephoneState,
				      'startTime': vm.startTime,
				      'endTime': vm.endTime
		        },
				page : page
			}).trigger("reloadGrid");
	        var records = getGridParam('records');
	        vm.recordsNum = records;
		}
	},
	  
	created: function () {
		this.loadPeriod();
	}
});


function enableUser(seq) {
	$.get(baseURL + "/online/customUser/enableUser?seq=" + seq, function(r) {
		if (r.code === 0) {
            alert('操作成功', function () {
            	vm.reload();
            });
         } else {
        	 alert(r.msg);
         }
	});
}


function disableUser(seq) {
    confirm('确定要停用此用户？', function () {
		$.get(baseURL + "/online/customUser/disableUser?seq=" + seq, function(r) {
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
