$(function () {
  $("#jqGrid").jqGrid({
    url: baseURL + 'online/orderlist',
    datatype: "json",
    mtype: "POST",
    /*
     * shrinkToFit : false, autoScroll : true, //
     * shrinkToFit: false,autoScroll: // true,这两个属性产生水平滚动条
     * autowidth : true,
     */
    postData: {
      'orderStatus': vm.orderStatus,
      'keywords': vm.keywords
    },
    colModel: [
      {label: 'seq', name: 'seq', width: 0.1, align: "center"},
      {label: '订单号', name: 'orderNum', width: 70, align: "center"},
      {
        label: '已付金额', name: 'paid', width: 70, align: "center",
        cellattr: function (rowId, value, rowObject, colModel, arrData) {
          return 'id=\'paid' + rowId + "\'";
        }
      },
      {
        label: '应付金额', name: 'orderPrice', width: 70, align: "center",
        cellattr: function (rowId, value, rowObject, colModel, arrData) {
          return 'id=\'orderPrice' + rowId + "\'";
        }
      },
      {label: '生成时间', name: 'inputTime', width: 84, hidden: true, align: "center"},
      {
        label: '订单状态', name: 'statusName', width: 70, align: "center",
        cellattr: function (rowId, value, rowObject, colModel, arrData) {
          return 'id=\'statusName' + rowId + "\'";
        }
      },
      {
        label: '买家', name: 'orderingparty', width: 80, align: "center",
        formatter: function (cellvalue, options, rowObject) {
          if (isBlank(cellvalue)) {
            return "匿名用户";
          } else {
            return cellvalue;
          }
        }
      },
      {
        label: '快递公司', name: 'expressName', width: 80, align: "center",
        cellattr: function (rowId, value, rowObject, colModel, arrData) {
          return 'id=\'expressName' + rowId + "\'";
        }
      },
      {
        label: '快递单号', name: 'expressNo', width: 80, align: "center",
        cellattr: function (rowId, value, rowObject, colModel, arrData) {
          return 'id=\'expressNo' + rowId + "\'";
        }
      },
      {
        label: '发货时间', name: 'deliverTime', width: 84, align: "center",
        cellattr: function (rowId, value, rowObject, colModel, arrData) {
          return 'id=\'deliverTime' + rowId + "\'";
        }
      },
      /*{
        label: '收货时间', name: 'receiveTime', width: 84, align: "center",
        cellattr: function (rowId, value, rowObject, colModel, arrData) {
          return 'id=\'receiveTime' + rowId + "\'";
        }
      },
      {label: '备注', name: 'remark', width: 80, align: "center"},
      {
        label: '留言', name: 'suggestion', width: 80, align: "center",
        cellattr: function (rowId, value, rowObject, colModel, arrData) {
          return 'id=\'suggestion' + rowId + "\'";
        }
      },*/
      {
        label: '操作', name: 'action', width: 120, align: "center",
        formatter: function (cellvalue, options, rowObject) {
          switch (rowObject.orderStatus) {
            case 0:
              return '<button class="operation-btn-security"  onclick="payButton(' + rowObject.seq + ')">支付</button>' + '&nbsp;'
                + '<button class="operation-btn-warn" onclick="cancelOrder(' + rowObject.seq + ')">取消</button>';
            case 1:
              return '<button class="operation-btn-security"  onclick="shipButton(' + rowObject.seq + ')">发货</button>';
            case 4:
              return '<button class="operation-btn-dangery" onclick="del(' + rowObject.seq + ')">删除</button>';
            default:
              return '';
          }
        },
        cellattr: function (rowId, value, rowObject, colModel, arrData) {
          return 'id=\'action' + rowId + "\'";
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
      page: "page.currPage",// 数据页码(当前页码currPage)
      total: "page.totalPage",// 数据总页码(总页数totalPage)
      records: "page.totalCount"// 数据总记录数(totalCount
      // 总记录数)
    },
    prmNames: {
      page: "page",
      rows: "limit",
      order: "order"
    },
    subGrid: true,// 开启子表格支持
    // 子表格的id；当子表格展开的时候，在主表格中会创建一个div元素用来容纳子表格，subgrid_id就是这个div的id
    subGridRowExpanded: function (subgrid_id, row_id) {// 子表格容器的id和需要展开子表格的行id
      var before = $("#jqGrid").jqGrid('getRowData',
        row_id);
      bindSubGrid(subgrid_id, before.seq);
    }
  });

});

function bindSubGrid(subgrid_id, seq) {
  var subgrid_table_id;
  subgrid_table_id = subgrid_id + "_t"; // (3)根据subgrid_id定义对应的子表格的table的id
  var subgrid_pager_id;
  subgrid_pager_id = subgrid_id + "_pgr" // (4)根据subgrid_id定义对应的子表格的pager的id
  // (5)动态添加子报表的table和pager
  $("#" + subgrid_id).html(
    "<table id=" + subgrid_table_id + " class='scroll'></table>");

  // (6)创建jqGrid对象
  $("#" + subgrid_table_id).jqGrid({
    url: baseURL + 'online/goodsList',
    datatype: "json",
    mtype: "POST",
    shrinkToFit: false,
    autoScroll: true, // shrinkToFit: false,autoScroll:
    // true,这两个属性产生水平滚动条
    autowidth: true,
    postData: {
      'seq': seq
    },
    colModel: [
      {
        label: '图片', name: 'img1', width: 200, align: "center",
        formatter: function (cellvalue, options, rowObject) {
          return '<image src="' + cellvalue + '" style="width: 80px;height: 100px;"/>';
        }
      },
      {label: '货号', name: 'goodId', width: 220, align: "center"},
      {label: '颜色', name: 'color', width: 120, align: "center"},
      {label: '尺码', name: 'shoesSize', width: 120, align: "center"},
      {label: '数量', name: 'buyCount', width: 120, align: "center"}
    ],
    viewrecords: false,
    height: 'auto',
    rownumbers: true,
    rownumWidth: 25,
    multiselect: false,
    jsonReader: {
      root: "page.list",
      page: "page.currPage",// 数据页码(当前页码currPage)
      total: "page.totalPage",// 数据总页码(总页数totalPage)
      records: "page.totalCount"// 数据总记录数(totalCount
      // 总记录数)
    },
    prmNames: {
      page: "page",
      rows: "limit",
      order: "order"
    },
  });

}

// 公共调用方法
function Merger(CellName) {
  // 得到显示到界面的id集合
  var mya = $("#jqGrid").getDataIDs();
  // 当前显示多少条
  var length = mya.length;
  for (var i = 0; i < length; i++) {
    // 从上到下获取一条信息
    var before = $("#jqGrid").jqGrid('getRowData', mya[i]);
    // 定义合并行数
    var rowSpanTaxCount = 1;
    for (var j = i + 1; j <= length; j++) {
      // 和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
      var end = $("#jqGrid").jqGrid('getRowData', mya[j]);
      console.log(before.orderNum + "," + end.orderNum);
      if (before.orderNum == end.orderNum) {
        rowSpanTaxCount++;
        $("#jqGrid").setCell(mya[j], CellName, '', {
          display: 'none'
        });
      } else {
        rowSpanTaxCount = 1;
        break;
      }
      $("#" + CellName + "" + mya[i] + "").attr("rowspan",
        rowSpanTaxCount).css("vertical-align", "middle");
    }
  }
}

var vm = new Vue({
  el: '#rrapp',
  data: {
    orderStatus: '',
    keywords: '',
    showList: true,
    showChangePrice: false,
    showPay: false,
    title: '',
    orderTotalPrice: '',
    orderPayPrice: '',
    selectSeq: '',
    orderDetail: {}
  },
  methods: {
    statusName: function (type) {
      vm.keywords = '';
      vm.orderStatus = type;
      vm.reloadOrder();
    },

    reloadOrder: function () {
      // 初始化参数
      vm.showList = true;
      vm.showChangePrice = false;
      vm.showPay = false;
      vm.orderTotalPrice = 0;
      vm.orderPayPrice = 0;
      vm.orderDetail = {};

      setTimeout(function () {
        $("#jqGrid").jqGrid('setGridParam', {
          postData: {
            'keywords': vm.keywords,
            'orderStatus': vm.orderStatus
          }
        }).trigger('reloadGrid');
      }, 500);
    },
    saveOrderPrice: function () {
      var price = parseFloat(vm.orderTotalPrice);
      if (price) {
        confirm("确认修改金额为:" + price + "？", function () {
          $.post(baseURL + "online/changePrice", {
            seq: vm.orderDetail.seq,
            price: price
          }, function (r) {
            vm.reloadOrder();
          });
        });
      } else {
        alert("请输入正确的金额");
      }
    },
    saveOrderPay: function () {
      var price = parseFloat(vm.orderPayPrice);
      if (price) {
        confirm("确认支付金额为:" + price + "？", function () {
          $.post(baseURL + "online/payPrice", {
            seq: vm.orderDetail.seq,
            price: price
          }, function (r) {
            vm.reloadOrder();
          });
        });
      } else {
        alert("请输入正确的金额");
      }
    },
    search: function () {
      $("#jqGrid").jqGrid('setGridParam', {
        postData: {
          'keywords': vm.keywords,
          'orderStatus': vm.orderStatus
        }
      }).trigger('reloadGrid');
    }
  },

  created: function () {
  }
});

// 取消订单
function cancelOrder(seq) {
  confirm("确定要取消订单？", function () {
    $.post(baseURL + "online/updateOederStatus", {
      seq: seq,
      orderStatus: 4
    }, function (r) {
      if (r.code == 0) {
        vm.statusName(4);
      }
    });
  });
}

// 订单确认
function payButton(seq) {
  confirm("确定已付款？", function () {
    $.post(baseURL + "online/updateOederStatus", {
      seq: seq,
      orderStatus: 1
    }, function (r) {
      if (r.code == 0) {
        vm.statusName(1);
      }
    });
  });
}

// 发货
function shipButton(seq) {
  confirm("订单已经发货了？", function () {
    $.post(baseURL + "online/updateOederStatus", {
      seq: seq,
      orderStatus: 2
    }, function (r) {
      if (r.code == 0) {
        vm.statusName(2);
      }
    });
  });
}

function del(id) {
  $.get(baseURL + "online/deleteOeder?seq=" + id, function (r) {
    if (r.meg = 1) {
      parent.location.reload();
    }
  });
}
