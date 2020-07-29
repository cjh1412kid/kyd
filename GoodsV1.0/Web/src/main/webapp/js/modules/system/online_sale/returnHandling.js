$(function () {
  $("#jqGrid").jqGrid({
    url: baseURL + 'order/',
    datatype: "json",
    mtype: "POST",
    shrinkToFit: false,
    autoScroll: true,          //shrinkToFit: false,autoScroll: true,这两个属性产生水平滚动条
    autowidth: true,
    postData: {
      'orderStatus': vm.orderStatus,
      'keywords': vm.keywords
    },
    colModel:
      [{
        label: '图片',
        name: 'img1',
        width: 100,
        align: "center",
        formatter: function (cellvalue, options, rowObject) {
          var detail = '<image src="'
            + cellvalue
            + '" style="width: 80px;height: 100px;"/>';
          return detail;
        }
      },
        {
          label: '货号',
          name: 'goodId',
          width: 120,
          align: "center"
        },
        {
          label: '颜色',
          name: 'color',
          width: 60,
          align: "center"
        },
        {
          label: '尺码',
          name: 'shoesSize',
          width: 60,
          align: "center"
        },
        {
          label: '数量',
          name: 'buyCount',
          width: 100,
          align: "center"
        },
        {
          label: '订单号',
          name: 'orderNum',
          width: 120,
          align: "center"
        },
        {
          label: '已付金额',
          name: 'paid',
          width: 100,
          align: "center",
          cellattr: function (rowId, value, rowObject, colModel, arrData) {
            return 'id=\'paid' + rowId + "\'";
          }
        },
        {
          label: '应付金额',
          name: 'orderPrice',
          width: 100,
          align: "center",
          cellattr: function (rowId, value, rowObject, colModel, arrData) {
            return 'id=\'orderPrice' + rowId + "\'";
          }
        },
        {
          label: '订单状态',
          name: 'statusName',
          width: 80,
          align: "center",
          cellattr: function (rowId, value, rowObject, colModel, arrData) {
            return 'id=\'statusName' + rowId + "\'";
          }
        },
        {
          label: '要求到货时间',
          name: 'requireTime',
          width: 160,
          align: "center",
          cellattr: function (rowId, value, rowObject, colModel, arrData) {
            return 'id=\'requireTime' + rowId + "\'";
          }
        },
        {
          label: '快递公司',
          name: 'expressName',
          width: 80,
          align: "center",
          cellattr: function (rowId, value, rowObject, colModel, arrData) {
            return 'id=\'expressName' + rowId + "\'";
          }
        },
        {
          label: '快递单号',
          name: 'expressNo',
          width: 160,
          align: "center",
          cellattr: function (rowId, value, rowObject, colModel, arrData) {
            return 'id=\'expressNo' + rowId + "\'";
          }
        },
        {
          label: '发货时间',
          name: 'deliverTime',
          width: 160,
          align: "center",
          cellattr: function (rowId, value, rowObject, colModel, arrData) {
            return 'id=\'deliverTime' + rowId + "\'";
          }
        },
        {
          label: '收货时间',
          name: 'receiveTime',
          width: 160,
          align: "center",
          cellattr: function (rowId, value, rowObject, colModel, arrData) {
            return 'id=\'receiveTime' + rowId + "\'";
          }
        },
        {
          label: '备注',
          name: ' remark',
          width: 100,
          align: "center",
          cellattr: function (rowId, value, rowObject, colModel, arrData) {
            return 'id=\'remark' + rowId + "\'";
          }
        },
        {
          label: '留言',
          name: ' suggestion',
          width: 100,
          align: "center",
          cellattr: function (rowId, value, rowObject, colModel, arrData) {
            return 'id=\'suggestion' + rowId + "\'";
          }
        },
        {
          label: '操作',
          name: 'action',
          width: 120,
          align: "center",
          formatter: function (cellvalue, options, rowObject) {
            switch (rowObject.orderStatus) {
              case 0:
                return '<button class="operation-btn" onclick="makeSure(' + rowObject.seq + ')">接单</button>&nbsp;<button onclick="cancelOrder(' + rowObject.seq + ')">取消</button>';
              case 1:
                return '<button onclick="payButton(' + rowObject.seq + ')">付款</button>&nbsp;<button onclick="changePrice(' + rowObject.seq + ')">修改价格</button>';
              case 2:
                if (rowObject.orderPrice > rowObject.paid) {
                  return '<button onclick="payButton(' + rowObject.seq + ')">付款</button>&nbsp;<button onclick="shipButton(' + rowObject.seq + ')">发货</button>';
                } else {
                  return '<button class="operation-btn" onclick="shipButton(' + rowObject.seq + ')">发货</button>';
                }
              case 3:
              case 4:
                if (rowObject.orderPrice > rowObject.paid) {
                  return '<button onclick="payButton(' + rowObject.seq + ')">付款</button>';
                }
              case 5:
                return '<button onclick="del(' + rowObject.seq + ')">删除</button>';
            }
          },
          cellattr: function (rowId, value, rowObject, colModel, arrData) {
            return 'id=\'action' + rowId + "\'";
          }
        }],
    viewrecords: false,
    height: 'auto',
    rowNum: 10,
    rowList: [10, 30, 50],
    rownumbers: true,
    rownumWidth: 25,
    multiselect: false,
    pager: "#jqGridPager",
    jsonReader: {
      root: "page.list",
      page: "page.currPage",//数据页码(当前页码currPage)
      total: "page.totalPage",//数据总页码(总页数totalPage)
      records: "page.totalCount"//数据总记录数(totalCount 总记录数)
    },
    prmNames: {
      page: "page",
      rows: "limit",
      order: "order"
    },
    grouping: true,
    groupingView: {
      groupField: ['orderNum'],
      groupColumnShow: [false],
      groupText: ['订单编号：{0}']
    },
    loadComplete: function () {
      Merger("paid");
      Merger("orderPrice");
      Merger("requireTime");
      Merger("statusName");
      Merger("expressName");
      Merger("expressNo");
      Merger("deliverTime");
      Merger("receiveTime");
      Merger("remark");
      Merger("suggestion");
      Merger("action");
    }
  });
});

//公共调用方法
function Merger(CellName) {
  //得到显示到界面的id集合
  var mya = $("#jqGrid").getDataIDs();
  //当前显示多少条
  var length = mya.length;
  for (var i = 0; i < length; i++) {
    //从上到下获取一条信息
    var before = $("#jqGrid").jqGrid('getRowData', mya[i]);
    //定义合并行数
    var rowSpanTaxCount = 1;
    for (var j = i + 1; j <= length; j++) {
      //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
      var end = $("#jqGrid").jqGrid('getRowData', mya[j]);
      console.log(before.orderNum + "," + end.orderNum);
      if (before.orderNum == end.orderNum) {
        rowSpanTaxCount++;
        $("#jqGrid").setCell(mya[j], CellName, '', {display: 'none'});
      } else {
        rowSpanTaxCount = 1;
        break;
      }
      $("#" + CellName + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount).css("vertical-align", "middle");
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
          $.post(baseURL + "order/changePrice", {seq: vm.orderDetail.seq, price: price}, function (r) {
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
          $.post(baseURL + "order/payPrice", {seq: vm.orderDetail.seq, price: price}, function (r) {
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
    // this.loadPeriod();
    // this.loadCategory();
  }
});

//取消订单
function cancelOrder(seq) {
  confirm("确定要取消订单？", function () {
    $.post(baseURL + "order/updateOederStatus", {seq: seq, orderStatus: 5}, function (r) {
      if (r.code == 0) {
        vm.statusName(5);
      }
    });
  });
}

//订单确认
function makeSure(seq) {
  confirm("确定接单？", function () {
    $.post(baseURL + "order/updateOederStatus", {seq: seq, orderStatus: 1}, function (r) {
      if (r.code == 0) {
        vm.statusName(1);
      }
    });
  });
}

//修改总价
function changePrice(seq) {
  $.get(baseURL + "order/getOrder?seq=" + seq, function (r) {
    vm.showList = false;
    vm.showChangePrice = true;
    vm.showPay = false;
    vm.orderDetail = r.order;
  });
}

//支付
function payButton(seq) {
  $.get(baseURL + "order/getOrder?seq=" + seq, function (r) {
    vm.showList = false;
    vm.showChangePrice = false;
    vm.showPay = true;
    vm.orderDetail = r.order;
  });
}

//发货
function shipButton(seq) {
  confirm("订单已经发货了？", function () {
    $.post(baseURL + "order/updateOederStatus", {seq: seq, orderStatus: 3}, function (r) {
      if (r.code == 0) {
        vm.statusName(3);
      }
    });
  });
}

function del(id) {
  $.get(baseURL + "order/deleteOeder?seq=" + id, function (r) {
    if (r.meg = 1) {
      parent.location.reload();
    }
  });
}

function testButton(text) {
  console.log(text);
}
