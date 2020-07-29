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

    /*右键菜单是否显示*/
    add: true,
    update: true,
    del: true,

    /*右键菜单项名称*/
    addMenu: '添加',
    updateMenu: '修改',
    deleteMenu: '删除',

    isAble:true,

    nOcategoryLsit: [],
    category: {},
    title: '',
    topAreaList: [],
    area: {
      parentName: null,
      parentSeq: 0,
      name: null,
    }
  },

  methods: {
    getTopArea: function (callback) {
      $.get(baseURL + "system/area/select", function (r) {
        vm.topAreaList = r;
        if (callback && typeof callback === "function") {
          callback();
        }
      });
    },

    resetMenuLayer: function () {
      this.area.parentSeq = 0;
      this.area.name = null;
    }
  }
});


$(function () {
  nOcategoryLsit();
  zTreelist();

  /*右键菜单打开后，在页面其他处点击隐藏*/
  $(document).click(function () {
    hideRMenu();
  });

});

function reload() {
  nOcategoryLsit();
  zTreelist();
}

function zTreelist() {
  $.get(baseURL + "system/area/list", function (r) {
    var categorys = r.list;
    categorys.push({
      seq: 0,
      pIdKey: -1,
      name: "所有大区"
    });
    ztree = $.fn.zTree.init($("#categoryTree"), setting, categorys);
    ztree.expandAll(true);
  });
  rMenu = $("#rMenu");
  vm.getTopArea();
}

function nOcategoryLsit() {
  $.get(baseURL + "system/area/nOcategoryLsit", function (r) {
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

  if (!ztree.getSelectedNodes()[0]) {
    return;
  }

  /*右键菜单项选择显示*/
  if (ztree.getSelectedNodes()[0].parentSeq == -1) {
    vm.addMenu = "添加大区";
    vm.isAble=true;
    vm.add = true;
    vm.update = false;
    vm.del = false;
  } else if (ztree.getSelectedNodes()[0].parentSeq == 0) {
    vm.addMenu = "添加子公司";
    vm.updateMenu = "修改大区名称";
    vm.deleteMenu = "删除大区";
    vm.isAble=true;
    vm.add = true;
    vm.update = true;
    vm.del = true;
  } else {
    vm.updateMenu = "修改子公司";
    vm.deleteMenu = "删除子公司";
    vm.isAble=false;
    vm.add = false;
    vm.update = true;
    vm.del = true;
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
  hideRMenu();
  vm.area.name=null;
  vm.$set(vm.area, "parentSeq", ztree.getSelectedNodes()[0].seq);
  vm.category = {};
  layer.open({
    type: 1,
    offset: '50px',
    skin: 'layui-layer-molv',
    title: "新增",
    area: ['400px', '300px'],
    shade: 0,
    shadeClose: false,
    content: jQuery("#menuLayer"),
    btn: ['确定', '取消'],
    btn1: function (index) {
      if (isBlank(vm.area.name)) {
        alert("名称不能为空！");
        return;
      }
      /*      if (isBlank(vm.category.visible)) {
              alert("请选择可见类型！");
              return;
            }*/
      var jsonObj = {
        parentSeq: vm.area.parentSeq,
        name: vm.area.name,
      }

      $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: baseURL + 'system/area/save',
        data: JSON.stringify(jsonObj),
        dataType: 'json',
        success: function (r) {
          vm.category = {};
          alert(r.msg);
          zTreelist();
        }
      });
      vm.resetMenuLayer;
      nOcategoryLsit();
      layer.close(index);
    }
  });

}

function removeTreeNode() {
  hideRMenu();
  var nodeName = '【' + ztree.getSelectedNodes()[0].name + '】';
  var nodes = ztree.getSelectedNodes();
  if (nodes && nodes.length > 0) {
    if (nodes[0].children && nodes[0].children.length > 0) {
      confirm('确定要删除' + nodeName + '！？\n将删掉所有子公司', function () {
        $.post(baseURL + 'system/area/delete/' + ztree.getSelectedNodes()[0].seq, function (r) {
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
      confirm('确定要删除' + nodeName + '吗！？', function () {
        $.post(baseURL + 'system/area/delete/' + ztree.getSelectedNodes()[0].seq, function (r) {
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
  hideRMenu();
  vm.$set(vm.area, "name", ztree.getSelectedNodes()[0].name);
  vm.$set(vm.area, "parentSeq", ztree.getSelectedNodes()[0].parentSeq);
  layer.open({
    type: 1,
    offset: '50px',
    skin: 'layui-layer-molv',
    title: "修改",
    area: ['400px', '300px'],
    shade: 0,
    shadeClose: false,
    content: jQuery("#menuLayer"),
    btn: ['确定', '取消'],
    btn1: function (index) {

      var jsonObj = {
        seq: ztree.getSelectedNodes()[0].seq,
        parentSeq: ztree.getSelectedNodes()[0].parentSeq,
        name: vm.area.name,
      }

      $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: baseURL + 'system/area/update',
        data: JSON.stringify(jsonObj),
        dataType: 'json',
        success: function (r) {
          alert(r.msg);
          zTreelist();
        }
      });
      vm.resetMenuLayer();
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