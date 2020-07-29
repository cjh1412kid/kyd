$(function () {

  //初始化表格

  $("#jqGrid").jqGrid({
    url: baseURL + 'system/meetingDevice/list',
    datatype: "json",
    mtype: "POST",
    postData: {},
    colModel: [
      {label: '编号', name: 'seq', width: 60, hidden: true, align: "center"},
      {label: '名称', name: 'deviceName', width: 150, align: "center"},
      {label: '类型', name: 'deviceType', width: 80, align: "center",
    	  formatter: function (cellvalue, options, rowObject) {
    		  if(cellvalue==0){
    			  return '安全信号设备';
    		  }else{
    			  return '手机设备';
    		  }
    	  }
      },
      {label: 'MAC地址', name: 'address', width: 150, align: "center"},
      {label: 'uuId', name: 'uUID', width: 150, align: "center"},
      {label: '创建时间', name: 'inputTime', width: 150, align: "center"},
      {
        label: '操作', width: 100, align: "center",
        formatter: function (cellvalue, options, rowObject) {
          return '<button class="operation-btn-security" onclick="lineEdit(' + rowObject.seq + ')">修改</button>'
            + '<button class="operation-btn-dangery" onclick="lineDel(' + rowObject.seq + ')">删除</button>';
        }
      },
    ],
    height: 'auto',
    rowNum: 10,
    rowList: [10, 30, 50],
    rownumbers: true,
    rownumWidth: 30,
    autowidth: true,
    multiselect: false,
    viewrecords: true,
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

})


/**
 * 重载表格，刷新表格数据
 */
function reloadGrid() {
  $("#jqGrid").setGridParam({
    datatype: 'json',
    postData: {},
    page: 1,
  }).trigger('reloadGrid');
}

/**
 * 删除表格行
 */
function lineDel(seq) {

  confirm("您确定要删除吗?", function () {

    $.get(baseURL + 'system/meetingDevice/delete/' + seq, function (data) {
      if (data.code == 0) {
        layer.msg("删除成功")
        reloadGrid()
      } else {
        layer.alert("删除失败：" + data.msg)
      }
    })
  })
}

/**
 * 编辑表格行
 */
function lineEdit(seq) {
	
  if (!seq) {
    layer.alert("功能异常：未获取到序号，请刷新页面后重试")
    return;
  }

  $.get(baseURL + 'system/meetingDevice/info/' + seq, function (data) {
    if (data.code == 0) {
      vm.device = {};
      vm.showList = false;
      vm.title = '修改设备';
      vm.device = data.meetingDevice;
      vm.device.address=vm.device.address;
      vm.device.uUID=vm.device.uUID;
      var deviceType= vm.device.deviceType;
      if(deviceType==0){
  		vm.typeName='安全信号设备';
  	}else if(deviceType==1){
  		vm.typeName='手机设备';
  	}
    } else {
      layer.alert("信息获取失败：" + data.msg)
    }
  })
}

var vm = new Vue({
  el: '#rrapp',
  data: {
    showList: true,
    title: '新建订货会',
    device: {
      deviceName: '',
      deviceType:'',
      uUID:'',
      address:'',
    },
    typeName:'',
  },
  methods: {
    /*添加一条新记录*/
    add: function () {
      this.title = '增加设备';
      this.device = {};
      this.showList = false;
    },
    hideEditPanel: function () {
      this.showList = true;
    },
    saveOrUpdate: function () {

      if (!this.validatePostParam()) {
        return;
      }

      $.post(baseURL + 'system/meetingDevice/save', this.device, function (data) {
        if (data.code == 0) {
          layer.msg("保存成功")
          reloadGrid();
          vm.hideEditPanel();
        } else {
          layer.alert("保存失败：" + data.msg)
        }
      })

    },
    saleTypeChoose:function(deviceType){
    	this.device.deviceType=deviceType;
    	if(deviceType==0){
    		this.typeName='安全信号设备';
    	}else if(deviceType==1){
    		this.typeName='手机设备';
    	}
    },
    /*返回键*/
    goBack: function () {
      this.showList = true;
    },
    validatePostParam: function () {
      if (isBlank(this.device.deviceName)) {
        layer.alert("名称不能为空")
        return false;
      }
      
      if (this.device.deviceType!=0&&this.device.deviceType!=1) {
      layer.alert("分类不能为空")
     return false;
    }
  
     if (isBlank(this.device.address)) {
     layer.alert("MAC地址不能为空")
      return false;
    }
     if(this.device.deviceType==0){
    	if(isBlank(this.device.uUID)){
    		 layer.alert("安全设备的uuid不能为空")
    	     return false;	
    	} 
     }
      return true;
    },
    exportQRCode: function () {
      var form = document.getElementById('exportQRCode');
      form.action = baseURL + "system/meetingDevice/exportQRCode";
      form.token.value = token;
      form.submit();
    },
    listYearOptions: function () {
      var currYear = new Date().getFullYear();
      this.yearOpts = [currYear, currYear + 1];
      this.$emit(this.device, 'year', currYear);
    }
  },
  created: function () {
    this.listYearOptions();
  }
})
