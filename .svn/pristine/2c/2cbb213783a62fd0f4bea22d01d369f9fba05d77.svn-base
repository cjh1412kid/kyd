var avm = new Vue({
  el: '#show',
  data: {
	  buyCount:0  ,
	  meetings:"",
	  selectPeriodName:'',
	  meetingSeq:'',
	  startTime:'暂未选择',
	  endTime:'暂未选择',
	  noData:true,
	  rankList:[],
	  t1:"",
	  showFive:true,
  },
  methods: {
	    periodSelect: function (item) {
	    	requestFullScreen();
	        if (item == -1) {
	          avm.meetingSeq = -1;
	          avm.selectPeriodName = "所有订货会";
	          avm.noData=true;
	          window.clearInterval(avm.t1);
	          $(".list").html("");
	          $('#qrcode').html("");
	          avm.startTime="暂未选择";
	          avm.endTime="暂未选择";
	          avm.buyCount=0;
	        } else {
	        	queryMeeting(item)
  	        }
	      }
	    }
});

var requestFullScreen=function() {
	  var width =  window.screen.width;
	  var height =   window.screen.height;
	  var element = document.getElementById("show");
    //某个元素有请求    
  var requestMethod =element.requestFullscreen
  ||element.webkitRequestFullscreen //谷歌
  ||element.mozRequestFullscreen  //火狐
  ||element.msRequestFullscreen; //IE11
 if (requestMethod) {     
  requestMethod.call(element);   //执行这个请求的方法
} else if (typeof window.ActiveXObject !== "undefined") {  //window.ActiveXObject判断是否支持ActiveX控件    
   //这里其实就是模拟了按下键盘的F11，使浏览器全屏
    var wscript = new ActiveXObject("WScript.Shell"); //创建ActiveX  
  if (wscript !== null) {    //创建成功
      wscript.SendKeys("{F11}");//触发f11   
  }   
}   
 }
function refreshCount() {
	var seq=avm.meetingSeq
	if(avm.showFive){
		avm.showFive=false
	}else{
		avm.showFive=true;
	}
	 getProduct(seq);
	 getArea(seq)
	 getTopFive(seq);
}


$(function () {
	getAllMeeting();
	requestFullScreen();
	//根据时间获取当前订货会并查询订货单排行
	$.get(baseURL + "/system/meetingGoods/meeting", function (r) {
		var meeting=r.meeting;
		queryMeeting(meeting)
	 })
})
function queryMeeting(item){
	  avm.meetingSeq = item.seq;
      avm.selectPeriodName = item.name.substring( item.name.length-5);
      var startTime=item.startTime.split(" ")[0]
      var starts=startTime.split("/")
      avm.startTime=starts[1]+"."+starts[2] 
      var endTime=item.endTime.split(" ")[0]
      var ends=endTime.split("/")
      avm.endTime=ends[1]+"."+ends[2]
      getQrCode(item.seq);
  	  getProduct(item.seq);
  	  getArea(item.seq)
  	  getTopFive(item.seq);
  	  avm.t1=window.setInterval(refreshCount, 20000);
  	  $('#myCarousel').carousel({interval:10000});
}



 var myChart;
var endPercent =30
function getAllMeeting(seq){
	 $.get(baseURL + "/system/meetingGoods/meetings", function (r) {
		 avm.meetings=r.meetings
	 })
}



function getQrCode(seq){
	$('#qrcode').html("");
	// 设置参数方式
	var qrcode = new QRCode('qrcode', {
	  text: seq.toString(),
	  width: 127,
	  height: 107,
	  colorDark : '#7d7b7b',
	  colorLight : '#ffffff',
	  correctLevel : QRCode.CorrectLevel.H
	});

	
}

function getProduct(seq){
	if(myChart != null && myChart != "" && myChart != undefined) {
	    myChart.dispose();
	}
	    $.get(baseURL + "/system/meetingRank/goodsIdRank?meetingSeq="+seq, function (r) {
	    	var rankList=r.result[0].rankList
	    	avm.buyCount=r.result[0].totalNum;
	    	var goodIds=[];
	    	var nums=[];
	    
	    	if(r.result[0].totalNum>0){
	    		avm.noData=false
	    	for(var i=0;i<rankList.length;i++){
	    		var goodId=rankList[i].goodId;
	    		var num=rankList[i].num;
	    		goodIds.push(goodId)
	    		nums.push(num)
	    	}
	    		
	    	var option = {
	                title:{
	                    text:'单品订货量排行',
	                    x:'center',
	                    y:20,
	                    textAlign:'center',
	                    textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                    	fontSize: 24,
	                    }
	                },
	                tooltip:{
	                	show:true
	                },
	                xAxis:{
	                    data:goodIds,
	                    axisLabel:{
	                    	interval:0,
	                    	rotate:40
	                    }
	                },
	                dataZoom: [{
	                    type: 'slider',
	                    filterMode: 'filter',
	                    show: false,
	                    xAxisIndex: [0],
	                    left: '10%',
	                    bottom: 0,
	                    startValue: 0,
	                    endValue: 50 //初始化滚动条
	                  }],
	                yAxis:{
	                	show:false
	                },
	                series:[{
	                    name:'订货量',
	                    type:'bar',
	                    data:nums,
	                    itemStyle:{
	                    	  normal:{  
	                    		  color: '#F57323',
	                    	  },
	                    },
	                }]
	            };
	 	   //初始化echarts实例
	         myChart = echarts.init(document.getElementById('chartmain1'));

	        //使用制定的配置项和数据显示图表
	        myChart.setOption(option);
	    	}else{
	    		avm.noData=true
	    	}
	    });     


}
function getTopFive(seq){
	 $.get(baseURL + "/system/meetingRank/goodsIdRank?meetingSeq="+seq, function (r) {
		 var rankList=r.result[0].rankList
		 var newRankList=[];
		 var html="";
		for(var i=1;i<=rankList.length;i++){
			if(i<=3){
				newRankList.push(rankList[i-1])	
				html+="<div class='rank'>"
				html+="<img class='img' src='"+rankList[i-1].img+"'><div id='"+rankList[i-1].meetingGoodsSeq+"' class='code'></div>"
				html+="</div>"
			}
		}
		$(".list").html(html)
		avm.rankList=newRankList
		getCode(newRankList)
	 })
}

function getCode(newRankList){
	for(var i=0;i<newRankList.length;i++){
			var seq=newRankList[i].meetingGoodsSeq
			$('#'+seq).html("");
			// 设置参数方式
			var qrcode = new QRCode(document.getElementById(seq), {
			  text: seq.toString(),
			  width: 102,
			  height: 87,
			  colorDark : '#7d7b7b',
			  colorLight : '#ffffff',
			  correctLevel : QRCode.CorrectLevel.H
			});
	}
}




function getArea(seq){
	if(myChart != null && myChart != "" && myChart != undefined) {
	    myChart.dispose();
	}
	$.get(baseURL + "/system/meetingRank/areaRank?meetingSeq="+seq, function (r) {
		var rankList=r.result[0].rankList
		
		avm.buyCount=r.result[0].totalNum;
    	var areaNames=[];
    	var nums=[];
      	if(r.result[0].totalNum>0){
    		avm.noData=false
    	for(var i=0;i<rankList.length;i++){
    		var areaName=rankList[i].areaName;
    		var num=rankList[i].num;
    		areaNames.push(areaName)
    		nums.push(num)
    	}
    	  	var option = {
	                title:{
	                    text:'区域订货量排行',
	                    x:'center',
	                    y:20,
	                    textAlign:'center',
	                    textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                    	fontSize: 24,
	                    }
	                },
	                tooltip:{
	                	show:true
	                },
	            
	                xAxis:{
	                    data:areaNames,
	                },
	                yAxis:{
	                	show:false
	                },
	                dataZoom: [{
	                    type: 'slider',
	                    filterMode: 'filter',
	                    show: false,
	                    xAxisIndex: [0],
	                    left: '10%',
	                    bottom: 0,
	                    startValue: 0,
	                    endValue: 50 //初始化滚动条
	                  }],
	                series:[{
	                    name:'订货量',
	                    type:'bar',
	                    data:nums,
	                    itemStyle:{
	                    	  normal:{  
	                    		  color: '#F57323',
	                    	  },
	                    },
	                }]
	            };
	 	   //初始化echarts实例
	         myChart = echarts.init(document.getElementById('chartmain2'));
	        //使用制定的配置项和数据显示图表
	        myChart.setOption(option);
	    	}else{
	    		avm.noData=true
	    	}
    	
	})
}

var vm = new Vue({

})


     