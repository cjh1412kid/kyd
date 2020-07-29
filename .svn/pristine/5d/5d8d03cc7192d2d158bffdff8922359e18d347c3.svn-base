$(function () {
	var url = document.location.toString();
　　　　var arrUrl = url.split("?");
　　　　var para = arrUrl[1];
　　　	var seq=para.split("=")[1]
　　　	vm.seq=seq
  $("#jqGrid").jqGrid(
    {
      url: baseURL + 'system/meetingArea/meetingList',
      datatype: "json",
      mtype: "POST",
      postData: {
        'companyType': vm.companyType,
        'seq':seq
      },
      colModel: [
        {
          label: '所属类型',
          name: 'attachTypeName',
          width: 120,
          align: "center"
        },
        {
          label: '所属公司/代理名称',
          name: 'attachComapnyName',
          width: 280,
          align: "center"
        },
        {
          label: '用户名',
          name: 'AccountName',
          width: 85,
          align: "center"
        },
        {
          label: '昵称',
          name: 'UserName',
          width: 85,
          align: "center"
        },
        {
          label: '账号创建时间',
          name: 'InputTime',
          width: 105,
          align: "center"
        },
        {
          label: '账号截止时间',
          name: 'effectiveDate',
          width: 105,
          align: "center"
        },{
        	 label: '用户管理id',
             name: 'omSeq',
             width: 105,
             align: "center",
             hidden:true
        },{
       	 label: '用户id',
         name: 'Seq',
         width: 105,
         align: "center",
         hidden:true
        }   
        ],
      height: 'auto',
      rowNum: 10,
      rowList: [10, 30, 50],
      rownumbers: true,
      rownumWidth: 25,
      autowidth: true,
      multiselect: true,
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
        var rowIds = $("#jqGrid").jqGrid('getDataIDs');
        for(var k=0; k<rowIds.length; k++) {
            var curRowData = $("#jqGrid").jqGrid('getRowData', rowIds[k]);
            console.log(curRowData)
        	var omSeq=curRowData.omSeq
        	if(omSeq!=''){
        		jQuery("#jqGrid").jqGrid('setSelection',rowIds[k]);
        	}
        }
      },
    });
});


var vm = new Vue({
  el: '#rrapp',
  data: {
    branchOfficeName: "",
    agentName: "",
    confirmPassword: "",
    orderPartyDetail: {},
    showList: true,
    showorderParty: false,
    title: '',
    companyType: 2,
    restOfQuota: '',
    seq:'',
    attachTypeArray: [{
      attachType: 1,
      attachTypeName: "直属工厂"
    }, {
      attachType: 2,
      attachTypeName: "分公司"
    }, {
      attachType: 3,
      attachTypeName: "代理商"
    }],
    saleTypeArray: [{
      saleType: 2,
      saleTypeName: "总代理"
    }, {
      saleType: 3,
      saleTypeName: "批发商"
    }, {
      saleType: 4,
      saleTypeName: "直营店"
    }],
    shopList: ''
  },
  methods: {
	    getCompanyType: function (type) {
	        vm.companyType = type;
	        vm.reloadOrderParty();
	      },
	      reloadOrderParty: function () {
	        // 初始化参数
	        vm.branchOfficeName = "", vm.agentName = "", vm.showList = true;
	        vm.showorderParty = false;
	        vm.orderPartyDetail = {};
	        setTimeout(function () {
	          $("#jqGrid").jqGrid('setGridParam', {
	            postData: {
	              'companyType': vm.companyType
	            }
	          }).trigger('reloadGrid');
	        }, 500);
	      },
	      accredit:function(){
	    	var allids= jQuery("#jqGrid").jqGrid('getDataIDs');
	    	var ids=$("#jqGrid").jqGrid('getGridParam','selarrrow');
	    	var seqs=[]
	    	var allSeqs=[]
	    	$(allids).each(function (index, id){
	    		var row = $("#jqGrid").jqGrid('getRowData', id);
	    		var Seq=row.Seq
	    		allSeqs.push(Seq)
	    	})
	    	$(ids).each(function (index, id){
	    		var row = $("#jqGrid").jqGrid('getRowData', id);
	    		var Seq=row.Seq
	    		seqs.push(Seq)
	    	})
	    	 $.post(baseURL + 'system/meetingArea/saveArea?', {
	    		 seqs: seqs,
	    		 allSeqs:allSeqs,
	    		 areaSeq: vm.seq,
	    	 }, function (r) {
	    		 location.href="meeting_area.html" 
	      })
    }
}
})
