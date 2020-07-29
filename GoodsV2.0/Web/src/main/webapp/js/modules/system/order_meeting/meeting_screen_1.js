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
	 getArea(seq);
	 getNum(seq);
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
  	  getNum(item.seq);
  	  avm.t1=window.setInterval(refreshCount, 30000);
  	  $('#myCarousel').carousel({interval:10000});
}



 var myChart;
 var myPie;
 var myPie1;
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
	  width: 222,
	  height: 196,
	  colorDark : '#3D3D3D',
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
	    	var counts=[];
	    	if(r.result[0].totalNum>0){
	    		avm.noData=false
	    	for(var i=0;i<rankList.length;i++){
	    		var goodId=rankList[i].goodId;
	    		var num=rankList[i].num;
	    		var count=rankList[i].goodsCount
	    		goodIds.push(goodId)
	    		nums.push(num)
	    		counts.push(count)
	    	}
	    		
	    	var option = {
	                title:{
	                    text:'单品订货量排行',
	                    x:'center',
	                    y:20,
	                    textAlign:'center',
	                    textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                    	"fontSize": 16,
	                    	"color": "#ffffff"
	                    }
	                },
	                tooltip:{
	                	show:true
	                },
	                xAxis:{
	                    data:goodIds,
	                    axisLabel:{
	                    	interval:0,
	                    	rotate:40,
	                    	textStyle: {
                                color: '#ffffff',
                                fontWeight:600,
                                fontSize:14,
                            }
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
	                    endValue: 30 //初始化滚动条
	                  }],
	                yAxis:[{
	                	show:false
	                	},
	                	{
		                show:false
		                },
	                ],
	                series:[{
	                    name:'订货量',
	                    type:'bar',
	                    data:nums,
	                    itemStyle:{
	                    	  normal:{  
	                    		  color: 'rgba(255,73,73)',
	                    	  },
	                    },
	                },
	                {
	                    name:'订货次数',
	                    type:'line',
	                    data:counts,
	                    yAxisIndex: 1,   
	                    symbolSize:10,
	                    itemStyle:{
	                    normal:{
	                    color:"#DDA0DD"
	                    }
	                }},
	                
	                ]
	            };
	 	   //初始化echarts实例
	         myChart = echarts.init(document.getElementById('chartmain1'));

	        //使用制定的配置项和数据显示图表
	        myChart.setOption(option);
	        
//	        
//	    	var option1 = {
//	    			 title:{
//	    		            text:'按选款次数统计',
//	    		            top:'bottom',
//	    		            left:'center',
//	    		            textStyle:{
//	    		                fontSize: 14,
//	    		                fontWeight: '',
//	    		                color: '#fff'
//	    		            },
//	    		        },//标题
//	    		        tooltip: {
//	    		            trigger: 'item',
//	    		            formatter: "{a} <br/>{b}: {c} ({d}%)",
//	    		            /*formatter:function(val){   //让series 中的文字进行换行
//	    		                 console.log(val);//查看val属性，可根据里边属性自定义内容
//	    		                 var content = var['name'];
//	    		                 return content;//返回可以含有html中标签
//	    		             },*/ //自定义鼠标悬浮交互信息提示，鼠标放在饼状图上时触发事件
//	    		        },//提示框，鼠标悬浮交互时的信息提示
//	    		        series: [
//	    		        	{
//	    		                name:'货品货号',//tooltip提示框中显示内容
//	    		                type: 'pie',//图形类型，如饼状图，柱状图等
//	    		                radius: '55%',
//	    		                //roseType:'area',是否显示成南丁格尔图，默认false
//	    		                itemStyle: {
//	    		                    emphasis: {
//	    		                    	 shadowBlur: 200,
//	    		                         shadowColor: 'rgba(255, 255, 255)'
//	    		                    }//鼠标放在各个区域的样式
//	    		                },
//	    		                label: {//饼形图上的文本标签
//	    		                    normal: {
//	    		                        textStyle: {
//	    		                            color: 'rgba(255, 255, 255)'
//	    		                        }
//	    		                    }
//	    		                },
//	    		                labelLine: {//标签的视觉引导线
//	    		                    normal: {
//	    		                        lineStyle: {
//	    		                            color: 'rgba(255, 255, 255)'
//	    		                        }
//	    		                    }
//	    		                },
//	    		                data: pieDatas,//数据，数据中其他属性，查阅文档
//	    		            }//数组中一个{}元素，一个图，以此可以做出环形图
//	    		        ]
//	    	}
//	    	  //初始化echarts实例
//	         myPie = echarts.init(document.getElementById('pie1'));
//
//	        //使用制定的配置项和数据显示图表
//	    	myPie.setOption(option1);
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
			if(i<=5){
				var num=rankList[i-1].num;
				if(num>0){
				newRankList.push(rankList[i-1])	
				html+="<div class='rank'>"
				html+="<img class='img' src='"+rankList[i-1].img+"'><div id='"+rankList[i-1].meetingGoodsSeq+"' class='code'></div><div class='goodId'>"+rankList[i-1].goodId+"</div>"
				html+="</div>"
				}	
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
			  width: 100,
			  height: 100,
			  colorDark : '#3D3D3D',
			  colorLight : '#ffffff',
			  correctLevel : QRCode.CorrectLevel.H
			});
	}
}




function getArea(seq){
//	if(myChart != null && myChart != "" && myChart != undefined) {
//	    myChart.dispose();
//	}
	if(myPie1 != null && myPie1 != "" && myPie1 != undefined) {
		myPie1.dispose();
	}
	$.get(baseURL + "/system/meetingRank/areaRank?meetingSeq="+seq, function (r) {
		var rankList=r.result[0].rankList
		avm.buyCount=r.result[0].totalNum;
    	var areaNames=[];
    	var nums=[];
    	var pieDatas=[]
      	if(r.result[0].totalNum>0){
    		avm.noData=false
    	for(var i=0;i<rankList.length;i++){
    		var areaName=rankList[i].areaName;
    		var num=rankList[i].num;
    		areaNames.push(areaName)
    		nums.push(num)
    		var count=rankList[i].goodsCount
    		pieDatas.push({"name":areaName,"value":num});
    	}
//    	  	var option = {
//	                title:{
//	                    text:'区域订货量排行',
//	                    x:'center',
//	                    y:20,
//	                    textAlign:'center',
//	                    textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
//	                    	"fontSize": 24,
//	                    	"color": "#ffffff"
//	                    }
//	                },
//	                tooltip:{
//	                	show:true
//	                },
//	            
//	                xAxis:{
//	                    data:areaNames,
//	                    axisLabel:{
//	                    	interval:0,
//	                    	rotate:40,
//	                    	textStyle: {
//                                color: '#ffffff',
//                                fontWeight:600,
//                                fontSize:14,
//                            }
//	                    }
//	                },
//	                yAxis:{
//	                	show:false
//	                },
//	                dataZoom: [{
//	                    type: 'slider',
//	                    filterMode: 'filter',
//	                    show: false,
//	                    xAxisIndex: [0],
//	                    left: '10%',
//	                    bottom: 0,
//	                    startValue: 0,
//	                    endValue: 50 //初始化滚动条
//	                  }],
//	                series:[{
//	                    name:'订货量',
//	                    type:'bar',
//	                    data:nums,
//	                    itemStyle:{
//	                    	  normal:{  
//	                    		  color: 'rgba(255,73,73)',
//	                    	  },
//	                    },
//	                }]
//	            };
//	 	   //初始化echarts实例
//	         myChart = echarts.init(document.getElementById('chartmain2'));
//	        //使用制定的配置项和数据显示图表
//	        myChart.setOption(option);
	        
	        var option1 = {
	    			 title:{
	    		            text:'区域订货量排行',
	    		           // top:'top',
	    		            y:20,
	    		            left:'center',
	    		            textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
		                    	"fontSize": 20,
		                    	"color": "#ffffff"
		                    },
	    		        },//标题
	    		        tooltip: {
	    		            trigger: 'item',
	    		            formatter: "{a} <br/>{b}: {c} ({d}%)",
	    		            /*formatter:function(val){   //让series 中的文字进行换行
	    		                 console.log(val);//查看val属性，可根据里边属性自定义内容
	    		                 var content = var['name'];
	    		                 return content;//返回可以含有html中标签
	    		             },*/ //自定义鼠标悬浮交互信息提示，鼠标放在饼状图上时触发事件
	    		        },//提示框，鼠标悬浮交互时的信息提示
	    		        series: [
	    		        	{
	    		                name:'区域名称',//tooltip提示框中显示内容
	    		                type: 'pie',//图形类型，如饼状图，柱状图等
	    		                radius: '55%',
	    		                //roseType:'area',是否显示成南丁格尔图，默认false
	    		                itemStyle: {
	    		                    emphasis: {
	    		                    	 shadowBlur: 200,
	    		                         shadowColor: 'rgba(255, 255, 255)'
	    		                    }//鼠标放在各个区域的样式
	    		                },
	    		                label: {//饼形图上的文本标签
	    		                    normal: {
	    		                        textStyle: {
	    		                            color: 'rgba(255, 255, 255)'
	    		                        }
	    		                    }
	    		                },
	    		                labelLine: {//标签的视觉引导线
	    		                    normal: {
	    		                        lineStyle: {
	    		                            color: 'rgba(255, 255, 255)'
	    		                        }
	    		                    }
	    		                },
	    		                data: pieDatas,//数据，数据中其他属性，查阅文档
	    		            }//数组中一个{}元素，一个图，以此可以做出环形图
	    		        ]
	    	}
	    	  //初始化echarts实例
	        myPie1 = echarts.init(document.getElementById('pie1'));

	        //使用制定的配置项和数据显示图表
	        myPie1.setOption(option1);
	    	}else{
	    		avm.noData=true
	    	}
    	
	})
}

function getNum(seq){
	if(myChart != null && myChart != "" && myChart != undefined) {
	    myChart.dispose();
	}
	    $.get(baseURL + "/system/meetingRank/numRank?meetingSeq="+seq, function (r) {
	    	var rankList=r.result[0].rankList
	    	var goodIds=[];
	    	var nums=[];
	    	if(r.result[0].totalNum>0){
	    		avm.noData=false
	    	for(var i=0;i<rankList.length;i++){
	    		var goodId=rankList[i].goodId;
	    		var num=rankList[i].goodsCount;
	    		goodIds.push(goodId)
	    		nums.push(num)
	    	}
	    		
	    	var option = {
	                title:{
	                    text:'单品订货次数排行',
	                    x:'center',
	                    y:20,
	                    textAlign:'center',
	                    textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                    	"fontSize": 24,
	                    	"color": "#ffffff"
	                    }
	                },
	                tooltip:{
	                	show:true
	                },
	                xAxis:{
	                    data:goodIds,
	                    axisLabel:{
	                    	interval:0,
	                    	rotate:40,
	                    	textStyle: {
                                color: '#ffffff',
                                fontWeight:600,
                                fontSize:14,
                            }
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
	                    endValue: 30 //初始化滚动条
	                  }],
	                yAxis:{
	                	show:false
	                },
	                series:[{
	                    name:'订货次数',
	                    type:'bar',
	                    data:nums,
	                    itemStyle:{
	                    	  normal:{  
	                    		  color: 'rgba(255,73,73)',
	                    	  },
	                    },
	                }]
	            };
	 	   //初始化echarts实例
	         myChart = echarts.init(document.getElementById('chartmain3'));

	        //使用制定的配置项和数据显示图表
	        myChart.setOption(option);
	    	}else{
	    		avm.noData=true
	    	}
	    	if(myPie != null && myPie != "" && myPie != undefined) {
	    		myPie.dispose();
	    	}
	    	$.get(baseURL + "/system/meetingRank/areaRank?meetingSeq="+seq, function (r) {
	    		var rankList=r.result[0].rankList
	        	var pieDatas=[];
	        	for(var i=0;i<rankList.length;i++){
	        		var areaName=rankList[i].areaName;
	        		var count=rankList[i].goodsCount
	        		pieDatas.push({"name":areaName,"value":count});
	        	}
	        	 var option1 = {
		    			 title:{
		    		            text:'区域订货次数排行',
		    		           // top:'top',
		    		            y:20,
		    		            left:'center',
		    		            textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
			                    	"fontSize": 16,
			                    	"color": "#ffffff"
			                    },
		    		        },//标题
		    		        tooltip: {
		    		            trigger: 'item',
		    		            formatter: "{a} <br/>{b}: {c} ({d}%)",
		    		            /*formatter:function(val){   //让series 中的文字进行换行
		    		                 console.log(val);//查看val属性，可根据里边属性自定义内容
		    		                 var content = var['name'];
		    		                 return content;//返回可以含有html中标签
		    		             },*/ //自定义鼠标悬浮交互信息提示，鼠标放在饼状图上时触发事件
		    		        },//提示框，鼠标悬浮交互时的信息提示
		    		        series: [
		    		        	{
		    		                name:'区域名称',//tooltip提示框中显示内容
		    		                type: 'pie',//图形类型，如饼状图，柱状图等
		    		                radius: '55%',
		    		                //roseType:'area',是否显示成南丁格尔图，默认false
		    		                itemStyle: {
		    		                    emphasis: {
		    		                    	 shadowBlur: 200,
		    		                         shadowColor: 'rgba(255, 255, 255)'
		    		                    }//鼠标放在各个区域的样式
		    		                },
		    		                label: {//饼形图上的文本标签
		    		                    normal: {
		    		                        textStyle: {
		    		                            color: 'rgba(255, 255, 255)'
		    		                        }
		    		                    }
		    		                },
		    		                labelLine: {//标签的视觉引导线
		    		                    normal: {
		    		                        lineStyle: {
		    		                            color: 'rgba(255, 255, 255)'
		    		                        }
		    		                    }
		    		                },
		    		                data: pieDatas,//数据，数据中其他属性，查阅文档
		    		            }//数组中一个{}元素，一个图，以此可以做出环形图
		    		        ]
		    	}
		    	  //初始化echarts实例
		         myPie = echarts.init(document.getElementById('pie3'));

		        //使用制定的配置项和数据显示图表
		    	myPie.setOption(option1);
	    	})
	    });     


}


var vm = new Vue({

})


     