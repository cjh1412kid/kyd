$(function () {
  $("#jqGrid").jqGrid({
    url: baseURL + 'system/factory/sub/list',
    datatype: "json",
    colModel: [
      {label: 'seq', name: 'seq', hidden: true, hidedlg: true, key: true},
      {label: '账号名', name: 'accountName', width: 100, align: "center"},
      {label: '用户名称', name: 'userName', width: 100, align: "center"},
      {label: '电话', name: 'telephone', width: 100, align: "center"},
      {label: '建立时间', name: 'inputTime', width: 150, align: "center"}],
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

var setting = {
  data: {
    simpleData: {
      enable: true,
      idKey: "seq",
      pIdKey: "parentSeq",
      rootPId: -1
    },
    key: {
      url: "nourl"
    }
  },
  check: {
    enable: true,
    nocheckInherit: true
  }
};
var ztree;

var vm = new Vue({
  el: '#rrapp',
  data: {
    showList: true,
    title: '',
    subAccount: {}
  },
  methods: {
    add: function () {
      vm.showList = false;
      vm.subAccount = {};
      vm.getMenuTree(null);
    },
    update: function () {
      var accountId = getSelectedRow();
      if (accountId == null) {
        return;
      }
      vm.getMenuTree(accountId);

    },
    del: function () {
      var accountId = getSelectedRow();
      if (accountId == null) {
        return;
      }

      confirm('确定要删除选中的记录？', function () {
        $.ajax({
          type: "POST",
          url: baseURL + "system/factory/sub/delete?seq=" + accountId,
          success: function (r) {
            if (r.code === 0) {
              alert('操作成功', function () {
                vm.reload();
              });
            } else {
              alert(r.msg);
            }
          }
        });
      });
    },
    saveOrUpdate: function () {
      if (isBlank(vm.subAccount.accountName)) {
        alert("请输入登录账号");
        return;
      }
      if (isBlank(vm.subAccount.userName)) {
        alert("请输入用户名");
        return;
      }
      if (isBlank(vm.subAccount.telephone)) {
        alert("请输入手机号");
        return;
      }
      //新增，密码必填
      if(vm.subAccount.seq == null){
		  if (isBlank(vm.subAccount.password)) {
			alert("密码不能为空");
			return;
		  }
		  if (isBlank(vm.subAccount.confirmPassword)) {
			alert("确认密码不能为空");
			return;
		  }
	  }
	  if (!isBlank(vm.subAccount.password) && vm.subAccount.password != vm.subAccount.confirmPassword) {
		alert("两次密码不一致");
		return;
	  }
      // 获取选择的菜单
      var nodes = ztree.getCheckedNodes(true);
      var menuIdList = [];
      for (var i = 0; i < nodes.length; i++) {
        menuIdList.push(nodes[i].seq);
      }
      if (isBlank(vm.subAccount.permissions)) {
        vm.subAccount.permissions = [];
      }

      var url = vm.subAccount.seq == null ? "system/factory/sub/save" : "system/factory/sub/update";
      $.ajax({
        type: "POST",
        url: baseURL + url,
        contentType: "application/json",
        data: JSON.stringify({account: vm.subAccount, menu: menuIdList}),
        success: function (r) {
          if (r.code === 0) {
            alert('操作成功', function () {
              vm.reload();
            });
          } else {
            alert(r.msg);
          }
        }
      });
    },
    reload: function (event) {
      vm.showList = true;
      $("#jqGrid").jqGrid('setGridParam', {}).trigger("reloadGrid");
    },
    getMenuTree: function (seq) {
      // 加载菜单树
      $.get(baseURL + "system/menu/mylist", function (r) {
        ztree = $.fn.zTree.init($("#menuTree"), setting, r);
        // 展开所有节点
        ztree.expandAll(true);
        if (seq) {
          lineEdit(seq);
        }
      });
    }
  }
});

function lineEdit(seq) {
  $.get(baseURL + "system/factory/sub/info?seq=" + seq, function (r) {
    vm.showList = false;
    vm.title = "修改";

    vm.subAccount = r.account;

    var menuIds = r.menu;
    for (var i = 0; i < menuIds.length; i++) {
      var node = ztree.getNodeByParam("seq", menuIds[i]);
      if (node) {
        ztree.checkNode(node, true, false);
      }
    }
  });
}

