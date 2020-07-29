$(function() {
	$("#jqGrid").jqGrid({
		url : baseURL + 'order/meetingPlan/list',
	    datatype: "local",
		colModel : [
				{
					label : 'seq',
					name : 'seq',
					width : 120,
					align : "center",
					hidden :true
				},
				{
					label : '所属类型',
					name : 'attachTypeName',
					width : 120,
					align : "center"
				},
				{
					label : '所属公司/代理名称',
					name : 'attachCompanyName',
					width : 280,
					align : "center"
				},
				{
					label : '用户名',
					name : 'accountName',
					width : 85,
					align : "center"
				},
				{
					label : '昵称',
					name : 'userName',
					width : 85,
					align : "center"
				},
				{
					label : '操作'+
						'<button class="operation-btn-warn" style="margin-left: 30px; width:100px;" onclick="batchUpload()">批量上传</button>'+
						'<button class="operation-btn-dangery" style="margin-left: 20px; width:100px;" onclick="batchDel()">批量删除</button>',
					name : 'operation',
					width : 200,
					align : "center",
					formatter : function(cellvalue, options, rowObject) {
						var detailOne = '';
							if (rowObject.isUpload == 0) {
//								detailOne += ('<button disabled="disabled" class="operation-btn-security" style="background-color: gray;" onclick="lineEdit(' + rowObject.seq + ')">查看</button>' + '&nbsp');
								detailOne += ('<button class="operation-btn-warn" onclick="upload(' + rowObject.seq + ')">上传</button>' + '&nbsp');
							}
							if (rowObject.isUpload == 1) {
								detailOne += ('<button class="operation-btn-security" onclick="detail(' + rowObject.seq + ')">查看</button>' + '&nbsp');
								detailOne += ('<button class="operation-btn-warn" onclick="upload(' + rowObject.seq + ')">重新上传</button>' + '&nbsp');
								detailOne += ('<button class="operation-btn-dangery" onclick="del(' + rowObject.seq + ')">删除</button>' + '&nbsp;');
							}
						return detailOne;
					}
				} ],
		height : 'auto',
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		rownumbers : true,
		rownumWidth : 25,
		autowidth : true,
		multiselect : true,
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
			
			$("#jqgh_jqGrid_operation").css("height","25px");
		}
	});
	
	
	$("#jqPlanDetailGrid").jqGrid({
		url: baseURL + "order/meetingPlan/detail",
		datatype: "local",
//		colNames: ['大类','品类','风格','计划订货量','计划订货金额','计划订货款数','实际订货量','实际订货金额','实际订货款数'],
		colNames: ['大类','品类','风格','计划订货量','计划订货金额','计划订货款数'],
		colModel: [
		  {name: 'categoryName', width: 80, align: "center"},
		  {name: 'SX2Value', width: 100, align: "center"},
		  {name: 'SX3Value', width: 120, align: "center"},
		  {name: 'planNum', width: 100, align: "center"},
		  {name: 'planMoney', width: 100, align: "center"},
		  {name: 'planGoodsIDs', width: 100, align: "center"},
		  /*{name: 'actualNum', width: 100, align: "center"},
		  {name: 'actualMoney', width: 100, align: "center"},
		  {name: 'actualGoodsIDs', width: 100, align: "center"}*/],
		height: 'auto',
		rowNum: 10,
		rownumWidth: 25,
		autowidth: true,
		multiselect: false,
		pager: "#jqPlanDetailGridPager",
		jsonReader: {
		  root: "page.list",
		  page: "page.currPage",
		  total: "page.totalPage",
		  records: "page.totalCount"
		},
		prmNames: {
		  page: "page",
		  rows: "limit",
		  order: "order"
		},
		gridComplete: function () {
		  // 隐藏grid底部滚动条
		  $("#jqPlanDetailGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
		}
	});
	
});



var vm = new Vue({
	el : '#rrapp',
	data : {
		saleType : 2,
		uploadState : 0,
		periodSeq : '',
		periodList :''
	},
	methods : {
		selectSaleType : function(type) {
			vm.saleType = type;
			vm.reload();
		},
		
		selectUploadState : function(state) {
			vm.uploadState = state;
			vm.reload();
		},
		
		selectPeriod : function() {
			vm.reload();
		},

		reload : function() {
			setTimeout(function() {
				$("#jqGrid").jqGrid('setGridParam', {
			        datatype: 'json',
					postData : {
						'saleType' : vm.saleType,
						'uploadState' : vm.uploadState,
						'periodSeq' : vm.periodSeq
					},
					page:1,
				}).trigger('reloadGrid');
			}, 500);
		},
		
		/* 获取波次列表 */
		getPeriodList : function() {
			$.ajax({
		          type : "get",
		          url : baseURL + "order/meetingPlan/periodList",
		          success : function(r){
		        	  vm.periodList = r.list;
		      		  vm.periodSeq = vm.periodList[0].seq;
		      		  vm.reload();
		          }
		     });
		}
		
	},
	created : function() {
		this.getPeriodList();
	}
});

//详细
function detail(userSeq) {
	$("#jqPlanDetailGrid").jqGrid('setGridParam', {
        datatype: 'json',
		postData : {
			'userSeq' : userSeq,
			'periodSeq' : vm.periodSeq
		},
		page: 1,
	}).trigger('reloadGrid');
	
    layer.open({
      type: 1,
      offset: '50px',
      skin: 'layui-layer-molv',
      title: "订货计划详细",
      area: ['630px', '560px'],
      shade: 0,
      shadeClose: false,
      content: jQuery("#planDetailLayer")
    });
}

//上传
function upload(userSeq) {
	var seqArr = new Array();
	seqArr[0] = userSeq;
	$("#userSeqArr").val(seqArr);
	$('input[id=excelFile]').click();
}

//批量上传
function batchUpload() {
	var ids = $("#jqGrid").jqGrid("getGridParam", "selarrrow");
	if(ids.length == 0){
		alert("至少勾选一个订货方");
	    return false;
	}
	var seqArr = new Array();
    for (var i = 0; i < ids.length; i++) {
    	var rowData = $("#jqGrid").jqGrid("getRowData",ids[i]);
    	seqArr[i] = rowData.seq;
    }
    console.log(seqArr);
	$("#userSeqArr").val(seqArr);
	$('input[id=excelFile]').click();
}

//删除
function del(userSeq) {
	var seqArr = new Array();
	seqArr[0] = userSeq;
    confirm('确定要删除此账号的订货计划？', function () {
    	$.get(baseURL + "order/meetingPlan/delete?periodSeq=" + vm.periodSeq + "&userSeqArr=" + seqArr , function(r) {
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

//批量删除
function batchDel() {
	var ids = $("#jqGrid").jqGrid("getGridParam", "selarrrow");
	if(ids.length == 0){
		alert("至少勾选一个订货方");
	    return false;
	}
	var seqArr = new Array();
    for (var i = 0; i < ids.length; i++) {
    	var rowData = $("#jqGrid").jqGrid("getRowData",ids[i]);
    	seqArr[i] = rowData.seq;
    }
	confirm('确定要删除所有选中账号的订货计划？', function () {
		$.get(baseURL + "order/meetingPlan/delete?periodSeq=" + vm.periodSeq + "&userSeqArr=" + seqArr , function(r) {
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


//ajax 方式提交form表单，上传excel文件 
function uploadExcel(){
	var fileDir = $("#excelFile").val();
	var suffix = fileDir.substr(fileDir.lastIndexOf("."));
	if("" == fileDir){
		alert("选择需要导入的Excel文件！");
	    return false;
	}
	if(".xls" != suffix && ".xlsx" != suffix ){
	    alert("选择Excel格式的文件导入！");
	    return false;
	}
	  
	$('#form1').ajaxSubmit({
	    url: baseURL + "order/meetingPlan/upload?periodSeq=" + vm.periodSeq,
	    dataType: 'json',
	    success : function(r) {
			if (r.code === 0) {
				alert(r.msg);
				$("#userSeqArr").val("");
	            $("#excelFile").val("");
				vm.reload();
			} else {
				alert(r.msg);
				$("#userSeqArr").val("");
	            $("#excelFile").val("");
			}
		},
	    error: function(r) {
	    	alert("导入excel出错！");
	    	$("#userSeqArr").val("");
	    	$("#excelFile").val("");
		},
	});
}
