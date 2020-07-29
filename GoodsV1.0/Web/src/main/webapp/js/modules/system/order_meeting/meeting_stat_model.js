$(function () {

	$('#lineFiled-select').multiSelect({
		afterSelect: function(values){
		  vm.lineFiled = vm.lineFiled + values + ',';
		},
		afterDeselect: function(values){
		  vm.lineFiled = vm.lineFiled.replace(values + "," , "");
		},
		keepOrder: true
	});
	
	$('#summaryField-select').multiSelect({
		afterSelect: function(values){
		  vm.summaryField = vm.summaryField + values + ',';
		},
		afterDeselect: function(values){
		  vm.summaryField = vm.summaryField.replace(values + "," , "");
		},
		keepOrder: true
	});
	  
	  
	$("#jqGrid").jqGrid({
		url: baseURL + 'system/meetingStatisticsModel/list',
		datatype: "json",
		colModel: [
		  {label: '模板名称', name: 'modelName', width: 250, align: "center"},
		  {label: '建立时间', name: 'inputTime', width: 200, align: "center"},
		  {
		    label: '操作', name: '', width: 150, align: "center",
		    formatter: function (cellvalue, options, rowObject) {
		      var buttonHtml = '';
		      buttonHtml += ('<button class="operation-btn-security" onclick="lineEdit(' + rowObject.seq + ')">修改</button>' + '&nbsp;');
		      buttonHtml += ('<button class="operation-btn-dangery" onclick="del(' + rowObject.seq + ')">删除</button>');
		      return buttonHtml;
		    }
		  }],
		height: 'auto',
		rowNum: 10,
		rowList: [10, 30, 50],
		rownumbers: true,
		rownumWidth: 25,
		autowidth: true,
		multiselect: false,
		pager: "#jqGridPager",
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
		  $("#jqGrid").closest(".ui-jqgrid-bdiv").css({
		    "overflow-x": "hidden"
		  });
		}
	});
	  
	  
});


var vm = new Vue({
  el: '#rrapp',
  data: {
	  showList: true,
	  title: '',
	  sxArray: [],
	  meetingStatisticsModel: {},
	  lineFiled:'',
	  summaryField:''
  },
  methods: {
	  
		reload : function(event) {
			vm.showList = true;
			vm.meetingStatisticsModel = {};
			$("#jqGrid").jqGrid('setGridParam', {
				page : 1
			}).trigger("reloadGrid");
		},
  
	    add: function () {
		  vm.showList = false;
	      vm.title = '新增模板';
		  $('#lineFiled-select').multiSelect('deselect_all').multiSelect('refresh');
		  vm.lineFiled = '';
		  $('#summaryField-select').multiSelect('deselect_all').multiSelect('refresh');
		  vm.summaryField = '';
	    },
	    
	    saveOrUpdate: function () {
	      if (isBlank(vm.meetingStatisticsModel.modelName)) {
	        alert("模板名称不能为空！");
	        return;
	      }
	      if(isBlank(vm.lineFiled)){
		     alert("行字段不能为空！");
		     return;
	      }
	      if(isBlank(vm.summaryField)){
			 alert("汇总字段不能为空！");
			 return;
		  }
	      
	      var url = vm.meetingStatisticsModel.seq ? "system/meetingStatisticsModel/update" : "system/meetingStatisticsModel/add";
	      if (vm.meetingStatisticsModel.seq) {
	        var data = {
	          seq: vm.meetingStatisticsModel.seq,
	          modelName: vm.meetingStatisticsModel.modelName,
	          lineField: vm.lineFiled.substr(0, vm.lineFiled.length-1),
	          summaryField: vm.summaryField.substr(0, vm.summaryField.length-1)
	        }
	      } else {
	        var data = {
	          modelName: vm.meetingStatisticsModel.modelName,
	          lineField: vm.lineFiled.substr(0, vm.lineFiled.length-1),
	          summaryField: vm.summaryField.substr(0, vm.summaryField.length-1)
	        }
	      }
	      $.ajax({
	        type: "POST",
	        url: baseURL + url,
	        data: data,
	        enctype: 'multipart/form-data',
	        cache: false,
	        success: function (r) {
	          if (r.code === 0) {
	            alert('操作成功');
	            vm.reload();
	          } else {
	            alert(r.msg);
	          }
	        }
	      });
	    },
	    
	    loadGoodsSX: function () {
	        $.get(baseURL + "order/goods/getGoodsSXOption", function (r) {
	          if (r.code === 0 || r.code === '0') {
	            vm.sxArray = r.result;
	          }
	        });
	     },
	    
  },

  created: function () {
	 // this.loadGoodsSX();
  }
});

function lineEdit(seq) {
	$.get(baseURL + "system/meetingStatisticsModel/edit?seq=" + seq, function (r) {
	  vm.showList = false;
	  vm.title = "修改模板";
	  vm.meetingStatisticsModel = r.meetingStatisticsModelEntity;
	  console.log(r)
	  $('#lineFiled-select').multiSelect('deselect_all').multiSelect('refresh');
	  vm.lineFiled = '';
      $.each(r.meetingStatisticsModelEntity.lineField.split(","), function (key, value) {
    	  $('#lineFiled-select').multiSelect('select', value);
      });
      
	  $('#summaryField-select').multiSelect('deselect_all').multiSelect('refresh');
	  vm.summaryField = '';
      $.each(r.meetingStatisticsModelEntity.summaryField.split(","), function (key, value) {
    	  $('#summaryField-select').multiSelect('select', value);
      });
	});
}

function del(seq) {
	confirm('确定要删除选中的记录？', function () {
	  $.get(baseURL + "system/meetingStatisticsModel/delete?seq=" + seq, function (r) {
	    if (r.code === 0) {
	      alert('删除成功');
	      vm.reload();
	    } else {
	      alert("删除失败");
	    }
	  });
	});
}


