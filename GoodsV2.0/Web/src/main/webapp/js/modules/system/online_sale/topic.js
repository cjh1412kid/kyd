$(function () {
  $("#jqGrid").jqGrid({
    url: baseURL + '/online/topic/list',
    datatype: "json",
    colModel: [
      {label: '专题名称', name: 'topicName', width: 50, align: "center"},
      {
        label: '专题图片', name: 'topicImage', width: 150, align: "center", formatter: function (cellvalue) {
          var detail = '<image src="' + cellvalue + '" style="width: 200px;height: 70px;"/>';
          return detail;
        }
      },
      {
        label: '专题类型', name: 'topicType', width: 150, align: "center", formatter: function (cellvalue) {
          switch (parseInt(cellvalue)) {
            case 0:
              return "热销爆款";
            case 1:
              return "新品特推";
            case 2:
              return "商家促销";
            case 3:
              return "明星同款";
            case 4:
              return "精选专题";
            case 5:
              return "品牌活动";
          }
          return cellvalue;
        }
      },
      {
        label: '操作', name: 'createDate', width: 90, align: "center",
        formatter: function (cellvalue, options, rowObject) {
          var detail = '<button class="operation-btn-security" onclick="lineEdit(' + rowObject.seq + ')">编辑</button>';
          return detail;
        }
      }
    ],
    viewrecords: true,
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
      $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
    }
  });


  $("#jqGoodsGrid").jqGrid({
    url: baseURL + "system/ols/sowing/goodList",
    datatype: "json",
    colNames: ['序号', '货号', '描述', '图片'],
    colModel: [
      {name: 'seq', width: 50, align: "center", key: true},
      {name: 'goodID', width: 120, align: "center"},
      {name: 'introduce', width: 200, align: "center"},
      {
        name: 'img1', width: 180, align: "center", formatter: function (cellvalue) {
          var detail = '<image src="' + cellvalue + '" style="width: 100px;height: 70px;"/>';
          return detail;
        }
      }
    ],
    height: 'auto',
    rowNum: 10,
    rownumWidth: 25,
    autowidth: true,
    multiselect: true,
    pager: "#jqGoodsGridPager",
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
      $("#jqGoodsGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
    },
    loadComplete: function () {
      // 设置选择项
      if (vm && vm.topicDetail.linkSeq && vm.topicDetail.linkSeq.length > 0) {
        var rows = vm.topicDetail.linkSeq.split(',');
        for (var i = 0; i < rows.length; i++) {
          $("#jqGoodsGrid").jqGrid('setSelection', rows[i]);
        }
      }
    }
  });
});

function getGridParam(param) {
  return $("#jqGrid").jqGrid('getGridParam', param);
}

var vm = new Vue({
  el: '#rrapp',
  data: {
    showList: true,
    title: '',
    topicDetail: {}
  },
  methods: {
    add: function () {
      vm.showList = false;
      vm.title = "新增";
      vm.topicDetail = {};
    },
    reload: function (event) {
      vm.showList = true;
      var page = getGridParam('page');
      $("#jqGrid").jqGrid('setGridParam', {
        page: page
      }).trigger("reloadGrid");
    },
    handleFileChange: function (value) {
      var inputDOM = value.target;
      // 通过DOM取文件数据
      vm.topicDetail.topicImageFile = inputDOM.files[0];

      var size = Math.floor(vm.topicDetail.topicImageFile.size / 1024 / 1024);

      if (size > 10) {
        alert("文件超过10M啦！");
        return;
      }
      vm.imgPreview(vm.topicDetail.topicImageFile);
    },
    imgPreview: function (file) {
      if (!file || !window.FileReader) return;
      if (/^image/.test(file.type)) {
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = function () {
          vm.$set(vm.topicDetail, "topicImage", this.result);
        }
      }
    },
    saveOrUpdate: function () {
      console.log(vm.topicDetail);
      if (vm.validator()) {
        return;
      }

      var url = "online/topic/save";
      var formData = new FormData();
      if (vm.topicDetail.topicImageFile) {
        formData.append("topicImageFile", vm.topicDetail.topicImageFile);
      }
      formData.append("topicImage", vm.topicDetail.topicImage.indexOf("data:image") > -1 ? "" : vm.topicDetail.topicImage);
      formData.append("topicName", vm.topicDetail.topicName);
      formData.append("topicType", vm.topicDetail.topicType);
      formData.append("topicLink", vm.topicDetail.linkSeq);
      formData.append("seq", vm.topicDetail.seq || "");
      $.ajax({
        type: "POST",
        url: baseURL + url,
        contentType: false,
        processData: false,
        data: formData,
        enctype: 'multipart/form-data',
        cache: false,
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
    validator: function () {
      if (isBlank(vm.topicDetail.topicName)) {
        alert("专题名称不能为空");
        return true;
      }
      if (isBlank(vm.topicDetail.topicType)) {
        alert("专题类型不能为空");
        return true;
      }
      if (isBlank(vm.topicDetail.topicImage)) {
        if (!vm.topicDetail.topicImageFile) {
          alert("专题图片不能为空");
          return true;
        }
      }
      if (isBlank(vm.topicDetail.linkSeq)) {
        alert("专题关联内容为空");
        return true;
      }
    },
    // 重新加载物品列表
    loadGoodList: function () {
      $("#jqGoodsGrid").trigger("reloadGrid");
    },
    contentSelect: function () {
      vm.loadGoodList();
      layer.open({
        type: 1,
        offset: '50px',
        skin: 'layui-layer-molv',
        title: "选择菜单",
        area: ['600px', '450px'],
        shade: 0,
        shadeClose: false,
        content: jQuery("#goodsLayer"),
        btn: ['确定', '取消'],
        btn1: function (index) {
          var ids = $("#jqGoodsGrid").jqGrid("getGridParam", "selarrrow");
          console.log(ids);
          vm.$set(vm.topicDetail, "linkName", "已选择" + ids.length + "商品");
          vm.topicDetail.linkSeq = ids.join(',');
          layer.close(index);
        }
      });

    }
  }
});

function lineEdit(seq) {
  $.get(baseURL + "online/topic/edit?seq=" + seq, function (r) {
    vm.showList = false;
    vm.title = "修改";
    vm.topicDetail = r.detail;
  });
}