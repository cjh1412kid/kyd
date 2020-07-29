$(function () {

  //初始化表格

  $("#jqGrid").jqGrid({
    url: baseURL + 'system/meetingArea/list',
    datatype: "json",
    mtype: "POST",
    postData: {},
    colModel: [
      {label: '编号', name: 'seq', width: 60, hidden: true, align: "center"},
      {label: '地区名称', name: 'areaName', width: 150, align: "center"},
      {label: '创建时间', name: 'inputTime', width: 150, align: "center"},
      {
        label: '操作', width: 100, align: "center",
        formatter: function (cellvalue, options, rowObject) {
          return '<button class="operation-btn-security" onclick="lineEdit(' + rowObject.seq + ')">修改</button>'
            + '<button class="operation-btn-dangery" onclick="lineDel(' + rowObject.seq + ')">删除</button>';
        }
      },
      {
    	  label: '区域用户管理', width: 100, align: "center", 
    	  formatter: function (cellvalue, options, rowObject) {
    		  return '<button class="operation-btn-warn" onclick="userManager(' + rowObject.seq + ')">区域用户管理</button>';
    	  }
      }
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

    $.get(baseURL + 'system/meetingArea/delete/' + seq, function (data) {
      if (data.code == 0) {
        layer.msg("删除成功")
        reloadGrid()
      } else {
        layer.alert(data.msg)
      }
    })
  })
}
function userManager(seq) {
	 if (!seq) {
		    layer.alert("功能异常：未获取到序号，请刷新页面后重试")
		    return;
	}
location.href="meeting_area_user.html?seq="+seq;
}

/**
 * 编辑表格行
 */
function lineEdit(seq) {
	
  if (!seq) {
    layer.alert("功能异常：未获取到序号，请刷新页面后重试")
    return;
  }

  $.get(baseURL + 'system/meetingArea/info/' + seq, function (data) {
    if (data.code == 0) {
      vm.device = {};
      vm.showList = false;
      vm.title = '修改设备';
      vm.area = data.meetingArea;
    } else {
      layer.alert("信息获取失败：" + data.msg)
    }
  })
}

var vm = new Vue({
  el: '#rrapp',
  data: {
    showList: true,
    title: '新增地区',
    area: {
      areaName: '',
    },
  },
  methods: {
    /*添加一条新记录*/
    add: function () {
      this.title = '增加地区';
      this.area = {};
      this.showList = false;
    },
    hideEditPanel: function () {
      this.showList = true;
    },
    saveOrUpdate: function () {

      if (!this.validatePostParam()) {
        return;
      }

      $.post(baseURL + 'system/meetingArea/save', this.area, function (data) {
        if (data.code == 0) {
          layer.msg("保存成功")
          reloadGrid();
          vm.hideEditPanel();
        } else {
          layer.alert("保存失败：" + data.msg)
        }
      })

    },
    /*返回键*/
    goBack: function () {
      this.showList = true;
    },
    validatePostParam: function () {
      if (isBlank(this.area.areaName)) {
        layer.alert("地区名称不能为空")
        return false;
      }
      return true;
    },
    exportQRCode: function () {
      var form = document.getElementById('exportQRCode');
      form.action = baseURL + "system/meetingArea/exportQRCode";
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
