// pages/rankList/rankList.js
const app = getApp();
import * as echarts from '../../ec-canvas/echarts';
var Chart1 = null;
var Chart2 = null;
var Chart3 = null;
var Chart4 = null;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    type:1,
    name1:'各品类占比',
    name2:'女鞋关注',
    name4: '男鞋关注',
    name3:'单品关注度排行',
    chartHeight: 0,
    ec1: {
      lazyLoad: true // 延迟加载
    },
    ec2: {
      lazyLoad: true // 延迟加载
    },
    ec3: {
      lazyLoad: true // 延迟加载
    },
    ec4: {
      lazyLoad: true // 延迟加载
    },
    womanCategoryList:[],
    manCategoryList: [],
    goodsCategoryList:[],
    rankList:[],
    manNum:0,
    womenNum:0,
    manCount:0,
    womanCount:0,
    totalNum:0,
    totalCount:0,
    categoryName:'',
    seqList:[],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
   
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;
    that.echartsComponnet1 = this.selectComponent('#mychart-dom-pie1')
    that.echartsComponnet2 = this.selectComponent('#mychart-dom-pie2')
    that.echartsComponnet4 = this.selectComponent('#mychart-dom-pie4')
    that.echartsComponnet3 = this.selectComponent('#mychart-dom-bar')
    that.getData(); 
  },
  getData:function(){
    var that=this;
    var type=that.data.type;
    app.gwRequest({
      url: 'rank/rankList',
      data: {
        type:type
      }
    }).then(res => {
      console.log(res)
      var rank=res.rank;
      that.setData({
        womanCategoryList: rank.womanCategoryList,
        goodsCategoryList: rank. goodsCategoryList,
        manCategoryList:rank.manCategoryList,
        rankList: rank.rankList,
        manCategoryOther:rank.manCategoryOther,
        womanCategoryOther: rank.womanCategoryOther
      })

      that.setData({
        chartHeight: 100 * (rank.rankList.length) +60 ,
        length: rank.rankList.length
      })
      if (rank.rankList.length>30){
        that.setData({
          chartHeight: 100 *30,
          length: 30
        })
      }
      if (Chart1) {
        Chart1.dispose();
      }
      that.init_echarts1(); //初始化pie1图表
       if (Chart2) {
         Chart2.dispose();
       }
       that.init_echarts2(); //初始化pie2图表
       if (Chart3) {
         Chart3.dispose();
       }
       that.init_echarts3(); //初始化pie3图表
      if (Chart4) {
        Chart4.dispose();
      }
      that.init_echarts4(); //初始化pie3图表
    })
  }, 
  //初始化图表
  init_echarts1: function () {
    this.echartsComponnet1.init((canvas, width, height) => {
      // 初始化图表
      Chart1 = echarts.init(canvas, null, {
        width: width,
        height: height
      });
      // Chart.setOption(this.getOption());+
      this.setOption1(Chart1);
      
      // 注意这里一定要返回 chart 实例，否则会影响事件处理等
      return Chart1;
    });
  },
  //初始化图表
  init_echarts2: function () {
    this.echartsComponnet2.init((canvas, width, height) => {
      // 初始化图表
      Chart2 = echarts.init(canvas, null, {
        width: width*0.8,
        height: height
      });
      // Chart.setOption(this.getOption());+
      this.setOption2(Chart2);

      // 注意这里一定要返回 chart 实例，否则会影响事件处理等
      return Chart2;
    });
  },
  //初始化图表
  init_echarts4: function () {
    this.echartsComponnet4.init((canvas, width, height) => {
      // 初始化图表
      Chart4 = echarts.init(canvas, null, {
        width: width * 0.8,
        height: height
      });
      // Chart.setOption(this.getOption());+
      this.setOption4(Chart4);

      // 注意这里一定要返回 chart 实例，否则会影响事件处理等
      return Chart4;
    });
  },
  init_echarts3: function () {
    this.echartsComponnet3.init((canvas, width, height) => {
      // 初始化图表
      Chart3 = echarts.init(canvas, null, {
        width: width,
        height: height
      });
      // Chart.setOption(this.getOption());+
      this.setOption3(Chart3);

      // 注意这里一定要返回 chart 实例，否则会影响事件处理等
      return Chart3;
    });
  },
  setOption1: function (Chart) {
    Chart1.clear();  // 清除
    Chart1.setOption(this.getOption1());  //获取新数据

  },
  setOption2: function (Chart) {
    Chart2.clear();  // 清除
    Chart2.setOption(this.getOption2());  //获取新数据
  },
  setOption4: function (Chart) {
    Chart4.clear();  // 清除
    Chart4.setOption(this.getOption4());  //获取新数据
  },
  setOption3: function (Chart) {
    Chart3.clear();  // 清除
    Chart3.setOption(this.getOption3());  //获取新数据
  },
 
  getOption1: function () {
    var that = this;
    var type=that.data.type;
    var goodsCategoryList = that.data.goodsCategoryList
    var list=[]
    var allCount=0;
    var allNum=0;
    for (var i = 0; i<goodsCategoryList.length;i++){
      var name = goodsCategoryList[i].categoryName;
      var count = goodsCategoryList[i].count;
      var num = goodsCategoryList[i].num;
      var categorySeq = goodsCategoryList[i].categorySeq;
      if(type==1){
        list.push({ 'name': name, 'value': count})
      } else if (type == 2){
        list.push({ 'name': name, 'value': num})
      }
      allCount = allCount + count;
      allNum = allNum+num;
    }
    that.setData({
      totalCount: allCount,
      totalNum: allNum,
    })
    var option = {
      backgroundColor: "#ffffff",
      color: ["#5786F7", "#FF4C4E","#61A0A8"],
      series: [{
        label: {
          normal: {
            fontSize: 14
          }
        },
        type: 'pie',
        center: ['50%', '50%'],
        data: list
        ,
        label: {
          show: false,
          formatter: '{b}',
          emphasis: {
            show: true
          }
        },
      }],
      itemStyle: {
        emphasis: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 2, 2, 0.3)'
        }
      },
      labelLine: { show: false },
    };
  
    return option;
  },
  getOption2: function () {
    var that = this;
    var categoryList = that.data.womanCategoryList;
    var type=that.data.type;
    var list=[];
  

    for (var i = 0; i < categoryList.length; i++) {
      var name = categoryList[i].categoryName;
      var count = categoryList[i].count;
      var num = categoryList[i].num;
      var categorySeq = categoryList[i].categorySeq;
      if (type == 1) {
        list.push({ 'name': name.replace(new RegExp('鞋', 'g'), ""), 'value': count })
      } else if (type == 2) {
        list.push({ 'name': name.replace(new RegExp('鞋', 'g'), ""), 'value': num })
      }
      }
    console.log(list)
    var option = {
      backgroundColor: "#ffffff",
      series: [{
        label: {
          normal: {
            fontSize: 14
          }
        },
        type: 'pie',
        center: ['50%', '50%'],
        data: list,
        radius: [0, '40%'],
        label: {
          normal: {
            show: true,
            formatter: '{b}:{c}',
            fontWeight :50,
            fontSize :2,
          },
          emphasis: {
            show: true
          }
        },
        labelLine:{
          length:2,
        }
      }],
      itemStyle: {
        emphasis: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 2, 2, 0.3)'
        },
      }
    };
    return option;
  },
  getOption4: function () {
    var that = this;
    var categoryList = that.data.manCategoryList;
    var type = that.data.type;
    var list = [];
    for (var i = 0; i < categoryList.length; i++) {
      var name = categoryList[i].categoryName;
      var count = categoryList[i].count;
      var num = categoryList[i].num;
      var categorySeq = categoryList[i].categorySeq;
      if (type == 1) {
        list.push({ 'name': name.replace(new RegExp('鞋', 'g'), ""), 'value': count })
      } else if (type == 2) {
        list.push({ 'name': name.replace(new RegExp('鞋', 'g'), ""), 'value': num })
      }
    }
 
    var option = {
      backgroundColor: "#ffffff",
      series: [{
        label: {
          normal: {
            fontSize: 14
          }
        },
        type: 'pie',
        center: ['50%', '50%'],
        data: list,
        radius: [0, '40%'],
        label: {
          normal: {
            show: true,
            formatter: '{b}:{c}',
            fontWeight: 50,
            fontSize: 2,
          },
          emphasis: {
            show: true
          }
        },
        labelLine: {
          length: 2,
        }
      }],
      itemStyle: {
        emphasis: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 2, 2, 0.3)'
        },
      }
    };
    return option;
  },

  getOption3: function () {
    var that = this;
    // 指定图表的配置项和数据
    var rankList=that.data.rankList;
    var type=that.data.type;
    var name3 = '单品关注度排行'
    var color ='#ADB8F9'
    if (type==2){
      name3 = "单品订货量排行"
      color ='#7ED3F3'
    }
    that.setData({
      name3: name3
    })
    var nameList=[];
    var list=[];
    var goodsList=[];
    var seqList=[]

    var length = rankList.length;
    for (var i = 0; i < rankList.length;i++){
      if (length-i<30){
      var name =rankList[i].goodId
      var count=rankList[i].count
      var num=rankList[i].num
      nameList.push(name)
      if (type==1){
        list.push(count)
      }else{
        list.push(num)
      }
        seqList.push(rankList[i].shoesSeq)
      }
    }
    that.setData({
      seqList: seqList.reverse()
    })

    var option = {
      color: color,
      grid: {
        left: 5,
        right: 20,
        top: 0,
        containLabel: true,
      },
      xAxis: [
        {
          type: 'value',
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            color: '#666'
          },
          nameGap: -30,
          position: 'top',
          splitLine: false,
  
        },
      ],
      yAxis: [
        {
          type: 'category',
          data: nameList,

          axisLine: {
            lineStyle: {
              color: '#999'
            }
          },
          axisLabel: {
            color: '#666'
          }
        }
      ],
      series: [
        {
          type: 'bar',
          label: {
            normal: {
              show: false,
              position: 'inside'
            }
          },
          barWidth: 20,
          itemStyle: {
            // emphasis: {
            //   color: '#37a2da'
            // }
          },
          data: list,
         
        },
      ]
    }
 
    return option;
  },


  choose:function(e){
    var type=e.currentTarget.dataset.index;
    var that=this;
    that.setData({
      type: type
    })
    if(type==1){
      that.setData({
        name2: '女鞋关注',
        name4: '男鞋关注',
      })
    }else if(type==2){
      that.setData({
        name2: '女鞋订货量',
        name4: '男鞋订货量',
      })
    }
    that.getData(); 
  },
  goto:function(e){
    var seq = e.currentTarget.dataset.seq;
    wx.navigateTo({
      url: '/pages/goods/detail/detail?seq=' + seq
    })

  }

})