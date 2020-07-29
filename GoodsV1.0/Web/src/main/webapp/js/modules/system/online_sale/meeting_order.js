$(function () {

  //初始化表格

  $("#jqGrid").jqGrid({
    url: baseURL + 'online/order/selectOrder',
    datatype: "json",
    mtype: "POST",
    postData: {},
    colModel: [
      {label: '序号', name: 'seq', width: 60, hidden: true, align: "center"},
      {label: '订单号', name: 'orderNum', width: 100, align: "center"},
      {label: '订货量', name: 'totalSelectNum', width: 60, align: "center"},
      {label: '订单总价', name: 'orderPrice', width: 60, align: "center"},
      {label: '预付款(订单总价30%)', name: 'mustPay', width: 90, align: "center"},
      {label: '已支付', name: 'paid', width: 60, align: "center"},
      {label: '订货方', name: 'customUser', width: 80, align: "center"},
      {label: '收件人', name: 'receiverName', width: 80, align: "center"},
      {label: '电话', name: 'telephone', width: 100, align: "center"},
      {label: '地址', name: 'fullAddress', width: 100, align: "center"},
      {label: '创建时间', name: 'inputTime', width: 100, align: "center"},
      {
        label: '是否支付', name: 'isPaid', width: 30, align: "center", formatter: function (cellvalue, options, rowObject) {
          return cellvalue?"已支付":"未支付"
        }
      },
      {
        label: '是否有贴牌LOGO', name: 'isOem', width: 100, align: "center", formatter: function (cellvalue, options, rowObject) {
          return cellvalue?"是":"否"
        }
      },
      {
        label: '贴牌LOGO', name: 'oemUrl', width: 60, align: "center", formatter: function (cellvalue, options, rowObject) {
          return rowObject.isOem?'<img src=" ' + cellvalue + '" style="width:80px;height: 80px; "/>':''
        }
      },
      {
        label: '贴牌LOGO下载', name: 'isOem', width: 80, align: "center", formatter: function (cellvalue, options, rowObject) {
          return rowObject.isOem?'<a href=" ' + rowObject.oemUrl + ' " download="">下载</a>':''
        }
      },
      /*{
       label: '备注', name: 'message', width: 200, align: "center", formatter: function (cellvalue, options, rowObject) {
       return '<span style="color: red">' + cellvalue + '</span>'
       }
       },*/
      {
        label: '操作', width: 80, align: "center",
        formatter: function (cellvalue, options, rowObject) {
          return '<button class="operation-btn-security" onclick="lineEdit(' + rowObject.seq + ')">编辑</button>';
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
    },
    /* subGrid: true,// 开启子表格支持
     // 子表格的id；当子表格展开的时候，在主表格中会创建一个div元素用来容纳子表格，subgrid_id就是这个div的id
     subGridRowExpanded: function (subgrid_id, row_id) {// 子表格容器的id和需要展开子表格的行id
       console.log("打开子表格")
       var before = $("#jqGrid").jqGrid('getRowData', row_id);
       bindSubGrid(subgrid_id, before.seq);
     }*/
  });


})

//一级子表格，所有订单货号列表
function bindSubGrid(subgrid_id, seq) {
  var subgrid_table_id = subgrid_id + "_t"; // (3)根据subgrid_id定义对应的子表格的table的id
  var subgrid_pager_id = subgrid_id + "_pgr"; // (4)根据subgrid_id定义对应的子表格的pager的id
  // (5)动态添加子报表的table和pager
  $("#" + subgrid_id).html($("#product_table").clone())

}


/**
 * 重载表格，刷新表格数据
 */
function reloadGrid() {
  $("#jqGrid").setGridParam({
    datatype: 'json',
    postData: {
      year: vm.selectYear,
      periodSeq: vm.selectPeriodSeq,
      customSeq: vm.selectCustomSeq,
      firstSeq: vm.firstCategorySeq,
      secondSeq: vm.secondCategorySeq,
      thirdSeq: vm.thirdCategorySeq
    },
    page: 1,
  }).trigger('reloadGrid');
}


/**
 * 编辑表格行
 */
function lineEdit(seq) {
  if (!seq) {
    layer.alert("功能异常：未获取到序号，请刷新页面后重试")
    return;
  }

  //订货会单号
  vm.orderSeq = seq;

  $.get(baseURL + 'online/order/selectOrderCart/' + seq, function (data) {
    debugger
    if (data.code == 0) {
      // console.log(data.result)
      vm.products = data.result;
      vm.showList = false;
    } else {
      layer.alert("信息获取失败：" + data.msg)
    }
  })
}

var vm = new Vue({
  el: '#rrapp',
  data: {
    /*统计类型 1按定货方统计 2按货号统计 3按区域统计*/
    tableType: 1,
    showList: true,
    title: '修改订货会订单货品',
    /*订单关联货品*/
    products: [
      /* {
         goodID: 'A1111',
         details: [
           {color: '米白', size: {34: 12, 35: 13, 36: 14}, cancel: true},
           {color: '黑色', size: {34: 18, 35: 17, 36: 14}, cancel: false}
         ],
         title: [34, 35, 36]
       }*/
    ],
    /*按货品统计*/
    goodsOrder: [],
    /*按区域统计*/
    areaOrder: [],
    factoryList: [],
    firstCategoryList: [],
    secondCategoryList: [],
    thirdCategoryList: [],
    categoryList: [],
    /*编辑操作记录订单号*/
    orderSeq: null,

    /*查询条件*/
    yearList: [],
    periodList: [],
    customList: [],

    /*选择的年份*/
    selectYear: -1,
    /*选择的订货会*/
    selectPeriodSeq: -1,
    /*选择的定货方*/
    selectCustomSeq: -1,
    firstCategorySeq:-1,
    secondCategorySeq:-1,
    thirdCategorySeq:-1
  },
  methods: {
    changeTableType: function (val) {
      /* if (val == this.tableType) {
         return;
       }
 */

      if (val == 1) {
        //1按定货方查询
        reloadGrid()
      } else {
        if (this.selectPeriodSeq == -1) {
          // layer.alert("请选选择年份和订货会", {icon: 0})
          // return;
          //未选择订货会情况下会自动选择第一个订货会并查询
          if (this.yearList && this.yearList.length > 0) {
              this.selectYear = this.yearList[0];

            // console.log("根据年份查询订货会：", this.selectYear)
            //加载年份关联的订货会选择项
            $.get(baseURL + 'online/order/selectPeriod/' + this.selectYear, function (data) {
              if (data.code == 0) {
                vm.periodList = data.result;
                // console.log("订货会列表： ", vm.periodList)
                if (vm.periodList && vm.periodList.length > 0) {
                  vm.selectPeriodSeq = vm.periodList[0].seq;
                }
                if (val == 2) {
                  //2按货号统计
                  vm.reloadGoodsGrid();
                } else if (val == 3) {
                  //3按区域统计
                  vm.reloadAreaGrid();
                } else if (val == 4) {
                    //4按生产厂家统计
                    vm.reloadFactoryGrid();
                } else if (val == 5) {
                    //5按类别统计
                    vm.reloadCategoryGrid();
                }
              } else {
                layer.alert("订货会查询失败：" + data.msg)
              }
            })
          } else {
            layer.alert("无数据");
          }
        } else {
          if (val == 2) {
            //2按货号统计
            this.reloadGoodsGrid();

          } else if (val == 3) {
            //3按区域统计
            this.reloadAreaGrid();
          } else if (val == 4) {
              //4按生产厂家统计
              this.reloadFactoryGrid();
          } else if (val == 5) {
              //5按类别统计
              this.reloadCategoryGrid();
          }
        }

      }


      this.tableType = val;

    },
    /*按区域统计*/
    reloadAreaGrid: function () {
    	console.log( this.selectPeriodSeq)
      $.get(baseURL + 'system/meetingorder/list/byArea/' + this.selectPeriodSeq, function (data) {
        if (data.code == 0) {
          vm.areaOrder = data.result;
          // console.log("按区域统计结果：",vm.areaOrder)
        } else {
          layer.msg(data.msg)
        }
      })
    },
    /*按生产厂家统计*/
      reloadFactoryGrid: function () {
          console.log( this.selectPeriodSeq)
          $.get(baseURL + 'online/order/selectOrderByFactory/' + this.selectPeriodSeq, function (data) {
              if (data.code == 0) {
                  vm.factoryList = data.result;
                  // console.log("按生产厂家统计结果：",vm.factoryList)
              } else {
                  layer.msg(data.msg)
              }
          })
      },
    /*按类别统计*/
      reloadCategoryGrid: function () {
          console.log( this.selectPeriodSeq)
          $.get(baseURL + 'online/order/selectOrderByCategory/'+ this.selectPeriodSeq + '/' + this.thirdCategorySeq, function (data) {
              if (data.code == 0) {
                  vm.categoryList = data.result;
                  // console.log("按类别统计结果：",vm.categoryList)
              } else {
                  layer.msg(data.msg)
              }
          })
      },
    /*按货号统计*/
    reloadGoodsGrid: function () {
    	console.log( this.selectPeriodSeq)
      $.get(baseURL + 'online/order/selectOrderByGood/' + this.selectPeriodSeq, function (data) {
        if (data.code == 0) {
          vm.goodsOrder = data.result;
        } else {
          layer.msg(data.msg)
        }
      })

    },
    hideEditPanel: function () {
      this.showList = true;
    },
    /*取消订单操作*/
    cancelAllProducts: function () {

      $.get(baseURL + 'system/meetingorderproduct/cancelAll/' + this.orderSeq, function (data) {
        if (data.code == 0) {
          layer.msg("操作成功")
          vm.goBack();
        } else {
          layer.alert("操作失败：" + data.msg)
        }
      })
    },
    
    //修改某个尺码数量的功能
    saveAllProducts:function(){
    	var products=vm.products
    	var orderSeq=vm.orderSeq 
        $.post(baseURL + 'system/meetingorderproduct/save', {
        	products:JSON.stringify(products),
        	orderSeq:orderSeq
          }, function (data) {
            if (data.code == 0) {
              layer.msg("操作成功")
             // vm.goBack();
            } else {
              layer.alert("操作失败：" + data.msg)
            }
          })
    	
    	
    	
    },
    /*返回键*/
    goBack: function () {
      reloadGrid();
      this.showList = true;
    },
    /*恢复货品*/
    modifyProductCancel: function (goodSeq, colorSeq, isCancel) {

      // console.log("订单号：", this.orderSeq, " 货号：", goodSeq, " 颜色：", colorSeq, "是否取消：", isCancel)

      $.post(baseURL + 'system/meetingorderproduct/cancel', {
        orderSeq: vm.orderSeq,
        goodSeq: goodSeq,
        colorSeq: colorSeq,
        isCancel: isCancel
      }, function (data) {
        if (data.code == 0) {
          layer.msg("操作成功")
          lineEdit(vm.orderSeq);
        } else {
          layer.alert("操作失败：" + data.msg)
        }
      })

    },
    /*按货号统计 - 取消货号颜色*/
    modifyGoodColorCancel: function (goodSeq, colorSeq, isCancel) {

      if (!goodSeq) {
        layer.msg("操作失败：无法获取到货号序号")
        return;
      } else if (!colorSeq) {
        layer.msg("操作失败：无法获取到颜色序号")
        return;
      }

      $.post(baseURL + 'system/meetingorderproduct/cancel/color', {
        goodSeq: goodSeq,
        colorSeq: colorSeq,
        isCancel: isCancel
      }, function (data) {
        if (data.code == 0) {
          layer.msg("操作成功")
          vm.reloadGoodsGrid();
        } else {
          layer.alert("操作失败：" + data.msg)
        }
      })
    },
    /*按货号统计 - 取消货号*/
    modifyGoodCancel: function (goodSeq, isCancel) {
      // console.log("取消货号：goodSeq:", goodSeq)
      if (!goodSeq) {
        layer.msg("操作失败：无法获取到货号序号")
        return;
      }
      $.post(baseURL + 'system/meetingorderproduct/cancel/goodID', {
        goodSeq: goodSeq,
        isCancel: isCancel
      }, function (data) {
        if (data.code == 0) {
          layer.msg("操作成功")
          vm.reloadGoodsGrid();
        } else {
          layer.alert("操作失败：" + data.msg)
        }
      })
    },
      changeFirstCategory: function () {
          this.loadSecondCategoryList();
      },
      changeSecondCategory: function () {
          this.loadThirdCategoryList();
      },
    loadYearList: function () {
      $.get(baseURL + 'online/order/selectYear', function (data) {
        if (data.code == 0) {
          vm.yearList = data.result;
        } else {
          layer.alert("年份查询失败：" + data.msg)
        }
      })
    },
    loadFirstCategoryList: function () {
        $.get(baseURL + 'online/order/selectCategory/'+0, function (data) {
            if (data.code == 0) {
                vm.firstCategoryList = data.result;
            } else {
                layer.alert("大类查询失败：" + data.msg)
            }
        })
    },
    loadSecondCategoryList: function () {
        $.get(baseURL + 'online/order/selectCategory/'+this.firstCategorySeq, function (data) {
            if (data.code == 0) {
                vm.secondCategoryList = data.result;
            } else {
                layer.alert("中类查询失败：" + data.msg)
            }
        })
    },
    loadThirdCategoryList: function () {
        $.get(baseURL + 'online/order/selectCategory/'+this.secondCategorySeq, function (data) {
            if (data.code == 0) {
                vm.thirdCategoryList = data.result;
            } else {
                layer.alert("小类查询失败：" + data.msg)
            }
        })
    },
    /*选择年份*/
    changeYear: function () {
      // console.log("选择年份：", this.selectYear)

      if (this.selectYear == -1) {
        this.selectPeriodSeq = -1;
        this.selectCustomSeq = -1;
        this.periodList = [];
        this.customList = []
        return;
      }

      //加载年份关联的订货会选择项
      $.get(baseURL + 'online/order/selectPeriod/' + this.selectYear, function (data) {
        if (data.code == 0) {
          vm.periodList = data.result;
        } else {
          layer.alert("订货会查询失败：" + data.msg)
        }
      })
    },
    /*选择订货会*/
    changeMeeting: function () {
      // console.log("选择订货会：", this.selectPeriodSeq)

      if (this.selectPeriodSeq == -1) {
        this.selectCustomSeq = -1;
        this.customList = []
        return;
      }

      //加载年份关联的订货会选择项
      $.get(baseURL + 'online/order/selectCustom/' + this.selectPeriodSeq, function (data) {
        if (data.code == 0) {
          vm.customList = data.result;
        } else {
          layer.alert("订货方查询失败：" + data.msg)
        }
      })
    },
    search: function () {
      // console.log("点击了查询按钮 年份：", this.selectYear, " 订货会：", this.selectPeriodSeq, " 订货方：", this.selectCustomSeq)
       var val=this.tableType
    	if (val == 1) {
            //1按定货方查询
            reloadGrid()
          } else if (val == 2) {
                //2按货号统计
                this.reloadGoodsGrid();

           } else if (val == 3) {
                //3按区域统计
              this.reloadAreaGrid();
           } else if (val == 4) {
              //4按生产厂家统计
              this.reloadFactoryGrid();
          } else if (val == 5) {
              //5按类别统计
              this.reloadCategoryGrid();
        }
    },
    exportExcel: function () {

      if (this.selectPeriodSeq == -1) {
        layer.alert("请先选择年份和波次", {icon: 0})
        return;
      }

      var excelForm = document.getElementById('exportOrderExcel');
      excelForm.token.value = token;
      excelForm.periodSeq.value = this.selectPeriodSeq;
      var _this = this;
      if (this.tableType == 1) {
        // console.log("下载定货方订货单")
        excelForm.action = baseURL + 'online/order/exportCustomExcel';
        excelForm.customSeq.value = this.selectCustomSeq;
      } else if (this.tableType == 2) {
        // console.log("下载货品订货单")
        excelForm.action = baseURL + 'online/order/exportGoodsExcel';

      } else if (this.tableType == 3) {
        // console.log("下载区域订货单")
        excelForm.action = baseURL + 'online/order/area';
      } else if (this.tableType == 4) {
          // console.log("下载生产厂家订货单")
          excelForm.action = baseURL + 'online/order/exportFactoryExcel';
      } else if (this.tableType == 5) {
          // console.log("下载类别订货单")
          if (this.thirdCategorySeq == -1) {
              layer.alert("请先选择大类、中类和小类", {icon: 0})
              return;
          }
          excelForm.firstSeq.value = _this.firstCategorySeq;
          excelForm.secondSeq.value = _this.secondCategorySeq;
          excelForm.thirdSeq.value = _this.thirdCategorySeq;
          excelForm.action = baseURL + 'online/order/exportCategoryExcel';
      }
      excelForm.submit();
    },
    exportDistributeBoxExcel: function () {
    	if (this.selectPeriodSeq == -1) {
            layer.alert("请选选择年份和订货会", {icon: 0})
            return;
          }
    	  var excelForm = document.getElementById('exportOrderExcel');
          excelForm.token.value = token;
          excelForm.periodSeq.value = this.selectPeriodSeq;
          excelForm.action = baseURL + 'online/order/exportDistributeBoxExcel';
          excelForm.submit();
    },
  },
  created: function () {
    this.loadYearList();
    this.loadFirstCategoryList();
  }
})
