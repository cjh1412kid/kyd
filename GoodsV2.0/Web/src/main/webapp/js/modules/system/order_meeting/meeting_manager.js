$(function () {

  //初始化表格

  $("#jqGrid").jqGrid({
    url: baseURL + 'system/meeting/list',
    datatype: "json",
    mtype: "POST",
    postData: {},
    colModel: [
      {label: '序号', name: 'seq', width: 60, hidden: true, align: "center"},
      {label: '名称', name: 'name', width: 150, align: "center"},
      {label: '年份', name: 'year', width: 80, align: "center"},
      {label: '开始时间', name: 'startTime', width: 150, align: "center"},
      {label: '结束时间', name: 'endTime', width: 150, align: "center"},
      {label: '创建时间', name: 'inputTime', width: 150, align: "center"},
      {
        label: '操作', width: 100, align: "center",
        formatter: function (cellvalue, options, rowObject) {
          return '<button class="operation-btn-security" onclick="lineEdit(' + rowObject.seq + ')">修改</button>'
            + '<button class="operation-btn-dangery" onclick="lineDel(' + rowObject.seq + ')">删除</button>';
        }
      },
      {
    	  label: '订货会用户管理', width: 100, align: "center", 
    	  formatter: function (cellvalue, options, rowObject) {
    		  return '<button class="operation-btn-warn" onclick="userManager(' + rowObject.seq + ')">订货会用户管理</button>';
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

  /*初始化时间*/
  $('#startTimePicker').daterangepicker({
    singleDatePicker: true,
    showDropdowns: true,
    autoUpdateInput: false,
    timePicker24Hour: true,
    timePicker: true,
    locale: {
      format: 'YYYY/MM/DD HH:mm:ss',
      applyLabel: "应用",
      cancelLabel: "取消",
      customRangeLabel: '选择时间',
      daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
      monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
      firstDay: 0
    }
  }).on('apply.daterangepicker', function (ev, picker) {
    var dateStr = picker.startDate.format(picker.locale.format);
    // console.log("开始时间：", dateStr)
    vm.$set(vm.meeting, 'startTime', dateStr);
  });

  $('#endTimePicker').daterangepicker({
    singleDatePicker: true,
    showDropdowns: true,
    autoUpdateInput: false,
    timePicker24Hour: true,
    timePicker: true,
    locale: {
      format: 'YYYY/MM/DD HH:mm:ss',
      applyLabel: "应用",
      cancelLabel: "取消",
      customRangeLabel: '选择时间',
      daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
      monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
      firstDay: 0
    }
  }).on('apply.daterangepicker', function (ev, picker) {
    var dateStr = picker.startDate.format(picker.locale.format);
    // console.log("结束时间：", dateStr)
    vm.$set(vm.meeting, 'endTime', dateStr);
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

    $.get(baseURL + 'system/meeting/delete/' + seq, function (data) {
      if (data.code == 0) {
        layer.msg("删除成功")
        reloadGrid()
      } else {
        layer.alert(data.msg)
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

  $.get(baseURL + 'system/meeting/info/' + seq, function (data) {
    if (data.code == 0) {
      vm.meeting = {};
      vm.showList = false;
      vm.title = '修改订货会';

      vm.meeting = data.meeting;
    } else {
      layer.alert("信息获取失败：" + data.msg)
    }
  })
}
/**
 * 跳转至订货用户管理
 */
function userManager(seq) {
	 if (!seq) {
		    layer.alert("功能异常：未获取到序号，请刷新页面后重试")
		    return;
	}
location.href="meeting_user.html?seq="+seq;
}


var vm = new Vue({
  el: '#rrapp',
  data: {
    showList: true,
    title: '新建订货会',
    meeting: {},
    yearOpts: []
  },
  methods: {
    /*添加一条新记录*/
    add: function () {
      this.title = '新建订货会';
      this.meeting = {};
      this.showList = false;
    },
    hideEditPanel: function () {
      this.showList = true;
    },
    saveOrUpdate: function () {

      if (!this.validatePostParam()) {
        return;
      }

      $.post(baseURL + 'system/meeting/save', this.meeting, function (data) {
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
      if (isBlank(this.meeting.name)) {
        layer.alert("名称不能为空")
        return false;
      }
      if (isBlank(this.meeting.startTime)) {
        layer.alert("开始时间不能为空")
        return false;
      }
      if (isBlank(this.meeting.endTime)) {
        layer.alert("结束时间不能为空")
        return false;
      }

      if (isBlank(this.meeting.year)) {
        layer.alert("请选择年份")
        return false;
      }

      return true;
    },
    exportQRCode: function () {
      var form = document.getElementById('exportQRCode');
      form.action = baseURL + "system/meeting/exportQRCode";
      form.token.value = token;
      form.submit();
    },
    listYearOptions: function () {
      var currYear = new Date().getFullYear();
      this.yearOpts = [currYear, currYear + 1];
      this.$emit(this.meeting, 'year', currYear);
    },
    
    initMap: function(){
        if (isBlank(vm.meeting.address)&&isBlank(vm.meeting.lng)&&isBlank(vm.meeting.lat)) {
            alert("地址不能为空");
            return;
        }
        
    	//打开地图窗口
        layer.open({
            type: 1,
            offset: '50px',
            skin: 'layui-layer-molv',
            title: "地图选点",
            area: ['818px', '460px'],
            shade: 0,
            shadeClose: false,
            content: jQuery("#BMapLayer"),
            btn: ['确定', '取消'],
            btn1: function (index) {
            	//百度经纬度坐标
                var lnglat = [marker.getPosition().lng, marker.getPosition().lat];
                
                //转换为高德经纬度
                AMap.convertFrom(lnglat, 'baidu', function (status, result) {
                  if (result.info === 'ok') {
                	  vm.$set(vm.meeting, "lng", result.locations[0].lng);
                	  vm.$set(vm.meeting, "lat", result.locations[0].lat);
                  } else {
                	  alert("转换为高德经纬度失败！");
                  }
                });
            	layer.close(index);
            }
        });
        
        // 新建百度地图
        var map = new BMap.Map("allmap");
        var point = new BMap.Point(120.706362,28.001972); //默认地址"温州市"
        map.centerAndZoom(point,13);
        map.enableScrollWheelZoom(true);
        
        //添加覆盖物
        var marker = new BMap.Marker(point);
        marker.enableDragging();
        //覆盖物标签
        var label = new BMap.Label(marker.getPosition().lng + "," + marker.getPosition().lat, {offset:new BMap.Size(-8,-18)});
        label.setStyle({
        	maxWidth: "none"
        });
        marker.setLabel(label);
        
        map.addOverlay(marker);
        
    	//单击设置覆盖物位置
    	map.addEventListener("click",function(e){
    		marker.setPosition(e.point);
        	label.setContent(e.point.lng + "," + e.point.lat);
    	});
    	
    	//拖拽覆盖物过程事件
    	marker.addEventListener("dragging", function(e){
    		label.setContent(e.point.lng + "," + e.point.lat)      
    	})
    	
    	//定位到输入的门店地址
    	var myGeo = new BMap.Geocoder();
    	if(!isBlank(vm.meeting.lng)&&!isBlank(vm.meeting.lat)){
    		var latlng=bd_encrypt(vm.meeting.lng, vm.meeting.lat)
    		point = new BMap.Point(latlng.bd_lng, latlng.bd_lat); 
    		map.centerAndZoom(point, 16);
    		marker.setPosition(point);
        	label.setContent(point.lng + "," + point.lat);
    	}else{
    		myGeo.getPoint(vm.meeting.address, function(point){
        		if (point) {
        			map.centerAndZoom(point, 16);
            		marker.setPosition(point);
                	label.setContent(point.lng + "," + point.lat);
        		} else {
        			alert("没有解析到结果，请手动选择！");
        		}
        	}, "");
    	}
    	
    	
    }
    
    
  },
  created: function () {
    this.listYearOptions();
  }
})

function bd_encrypt(gg_lng, gg_lat) {
    var X_PI = Math.PI * 3000.0 / 180.0;
    var x = gg_lng, y = gg_lat;
    var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
    var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
    var bd_lng = z * Math.cos(theta) + 0.0065;
    var bd_lat = z * Math.sin(theta) + 0.006;
    return {
        bd_lat: bd_lat,
        bd_lng: bd_lng
    };
}
