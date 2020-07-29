var ztree, rMenu;
var setting = {
  view: {
    addDiyDom: addDiyDom,
    dblClickExpand: false,
  },
  check: {
    enable: true
  },
  data: {
    keep: {
      leaf: true,// 属性配置 leaf: true, 表示叶子节点不能变成根节点。parent: true 表示
      // 根节点不能变成叶子节点
      parent: true
    },
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
  edit: {
    drag: {
      isCopy: false,
      isMove: true
    },
    enable: true,// 设置是否处于编辑状态
    editNameSelectAll: true,
    showRemoveBtn: false,
    showRenameBtn: false
  },
  callback: {
    onRightClick: categoryRightClick,
  }
};
var vm = new Vue({
  el: '#rrapp',
  data: {
    classification: false,
    node: false,
    add: true,
    nOcategoryLsit: [],
    category: {}
  },
  methods: {
    /* 同步分类 */
    syncFromErp: function () {
      parent.window.showLoading();
      $.get(baseURL + "system/erp/sync/category", function (r) {
        parent.window.hideLoading();
        alert("同步结束", function () {
          location.reload();
        });
      });
    }
  }
});
$(function () {
  nOcategoryLsit();
  zTreelist();
});

function reload() {
  nOcategoryLsit();
  zTreelist();
}

function zTreelist() {
  $.get(baseURL + "system/goodsCategory/list", function (r) {
    var categorys = r.list;
    categorys.push({
      seq: 0,
      pIdKey: -1,
      name: "所有分类"
    });
    ztree = $.fn.zTree.init($("#categoryTree"), setting, categorys);
    ztree.expandAll(true);
  });
  rMenu = $("#rMenu");
}

function nOcategoryLsit() {
  $.get(baseURL + "system/goodsCategory/nOcategoryLsit", function (r) {
    vm.nOcategoryLsit = r.list;
  });
}

function categoryRightClick(event, treeId, treeNode) {
  if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
    ztree.cancelSelectedNode();
    showRMenu("root", event.clientX, event.clientY);
  } else if (treeNode && !treeNode.noR) {
    ztree.selectNode(treeNode);
    showRMenu("node", event.clientX, event.clientY);
  }
}

function showRMenu(type, x, y) {
  vm.add = true;

  if (!ztree.getSelectedNodes()[0]) {
    return;
  }
  for (var prop in vm.nOcategoryLsit) {
    if (ztree.getSelectedNodes()[0].seq == vm.nOcategoryLsit[prop]) {
      vm.add = false;
    }
  }
  $("#rMenu ul").show();
  if (type == "root") {
    $("#m_del").hide();
    $("#m_check").hide();
    $("#m_unCheck").hide();
  } else {
    $("#m_del").show();
    $("#m_check").show();
    $("#m_unCheck").show();
  }
  y += $(document).scrollTop();
  x += $(document).scrollLeft();
  rMenu.css({
    "top": y + "px",
    "left": x + "px",
    "visibility": "visible"
  });
  $("body").bind("mousedown", onBodyMouseDown);
}

function hideRMenu() {
  if (rMenu)
    rMenu.css({
      "visibility": "hidden"
    });
  $("body").unbind("mousedown", onBodyMouseDown);
}

function onBodyMouseDown(event) {
  if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
    rMenu.css({
      "visibility": "hidden"
    });
  }
}

function addTreeNode() {
  vm.category = {};
  layer.open({
    type: 1,
    offset: '50px',
    skin: 'layui-layer-molv',
    title: "新增",
    area: ['300px', '280px'],
    shade: 0,
    shadeClose: false,
    content: jQuery("#menuLayer"),
    btn: ['确定', '取消'],
    btn1: function (index) {
      if (isBlank(vm.category.name)) {
        alert("分类名不能为空！");
        return;
      }
      if (isBlank(vm.category.visible)) {
        alert("请选择可见类型！");
        return;
      }
      $.post(baseURL + 'system/goodsCategory/add?', {
        parentSeq: ztree.getSelectedNodes()[0].seq,
        name: vm.category.name,
        visible: vm.category.visible,
      }, function (r) {
        vm.category = {};
        alert(r.msg);
        zTreelist();
      }, 'json');
      layer.close(index);
    }
  });

  nOcategoryLsit();
}

function removeTreeNode() {
  hideRMenu();
  var nodes = ztree.getSelectedNodes();
  if (nodes && nodes.length > 0) {
    if (nodes[0].children && nodes[0].children.length > 0) {
      confirm('要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！？', function () {
        $.get(baseURL + 'system/goodsCategory/delete?seq=' + ztree.getSelectedNodes()[0].seq, function (r) {
          if (r.code == 0) {
            alert(r.msg);
            reload();
          } else {
            alert(r.msg);
          }
        }, 'json');
        /* location.reload() */
        /*
         * ztree.removeNode(nodes[0]); zTreelist();
         */
      });
    } else {
      confirm('确定要删除节点！？', function () {
        $.get(baseURL + 'system/goodsCategory/delete?seq=' + ztree.getSelectedNodes()[0].seq, function (r) {
          if (r.code == 0) {
            alert(r.msg);
            reload();
          } else {
            alert(r.msg);
          }
        }, 'json');

        /* location.reload() */
        /*
         * ztree.removeNode(nodes[0]); zTreelist();
         */
      });
    }
  }

}

function beforeEditName() {
  vm.$set(vm.category, "name", ztree.getSelectedNodes()[0].name);
  vm.$set(vm.category, "visible", ztree.getSelectedNodes()[0].visible);
  layer.open({
    type: 1,
    offset: '50px',
    skin: 'layui-layer-molv',
    title: "修改",
    area: ['300px', '280px'],
    shade: 0,
    shadeClose: false,
    content: jQuery("#menuLayer"),
    btn: ['确定', '取消'],
    btn1: function (index) {
      $.post(baseURL + 'system/goodsCategory/update?', {
        seq: ztree.getSelectedNodes()[0].seq,
        parentSeq: ztree.getSelectedNodes()[0].parentSeq,
        name: vm.category.name,
        visible: vm.category.visible,
      }, function (r) {
        alert(r.msg);
        zTreelist();
      }, 'json');
      layer.close(index);
    }
  });
}

function addDiyDom(treeId, treeNode) {
  var aObj = $("#" + treeNode.tId);
  if (treeNode.visible == 0) {
    var editStr = '&nbsp<button class="operation-btn-security" >可见</button>'
  }
  if (treeNode.visible == 1) {
    var editStr = '&nbsp<button class="operation-btn-dangery" >不可见</button>'
  }
  if (!treeNode.children) {
    aObj.append(editStr);
  }
}