// pages/goods/detail/detail.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    selectFrom: 2, //0:选择规格,1:加入购物车,2:购买
    dialogShow: false,
    goodsSetStock: 0,
    chooseDetail: {
      num: 1
    },
    items: [{ name: '按件配', value: '1' , checked: 'true' },
      { name: '散配', value: '0'}],
    isChart:1,
    width:750,
    colorArray:[],
    userGoodId:'',
    perBoxNum:'',
    salesNum:0,
    isOrder:0,
    disabled: false,
    isAdmin:0
    },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let current = this;
    var isOrder = wx.getStorageSync('isOrder');
    var isAdmin = wx.getStorageSync('isAdmin');
    current.setData({
      isOrder: isOrder,
      isAdmin: isAdmin
    })
    //普通链接
    const pageLinks = options.links || '';
    let goodSeq = options.seq || '';
    isOrder=options.isOrder||'';
    if (isOrder!=''){
      current.setData({
        isOrder: isOrder
      })
    }
    
    // 扫码详情
    if (options.scene) {
    
      let params = decodeURIComponent(options.scene).split('&');
      console.log(params)
      params.forEach((value) => {
        if (value.indexOf('seq=') == 0) {
          goodSeq = value.split('=')[1];
          current.setData({
            isOrder: 1
          })
        }
      });
    }

    current.setData({
      pageLinks: pageLinks,
      goodSeq: goodSeq
    });
    app.gwRequest({
      url: 'goods/selectDetail',
      type: 'post',
      data: {
        seq: goodSeq
      }
    }).then(res => {
      let sizeAndColors = res.result;
      let setStock = 0;
      let sizeSet = new Set([]),
        colorMap = new Map([]);
      sizeAndColors.forEach((v1) => {
        sizeSet.add(v1.size);
        colorMap.set(v1.colorSeq, v1.colorName);
        if (v1.setStock && parseInt(v1.setStock)) {
          setStock += parseInt(v1.setStock);
        }
      });
      console.log(sizeSet);
      let sizeArray = [],
        colorArray = [];
      sizeSet.forEach((v) => {
        sizeArray.push(v);
      });
      colorMap.forEach((v, k) => {
        colorArray.push({
          seq: k,
          name: v
        });
      });
      var length = sizeArray.length;
      length = length + 4;
      var array = [];
      for (var i = 0; i < colorArray.length; i++) {
        var colorName = colorArray[i].name;
        array.push(colorName)
      }

      current.setData({
        goodsSetStock: setStock,
        goodsSizeArray: sizeArray,
        goodsColorArray: colorArray,
        goodsSizeAndColor: sizeAndColors,
        width: length * 120,
        array: array
      });
      app.gwRequest({
        url: 'goods/detail',
        type: 'post',
        data: {
          seq: goodSeq
        }
      }).then(res => {
        console.log(res)
        var orderCount = res.orderCount;
        var salesMap = res.salesMap;
        var salesNum = res.salesNum
        if (salesMap != null && salesMap != '') {
          var colorArray = current.data.colorArray;
          var salesShoppingCartEntity = salesMap.salesShoppingCartEntity
          var salesCartDistributeBoxMapList = salesMap.salesCartDistributeBoxMapList;
          for (var i = 0; i < salesCartDistributeBoxMapList.length; i++) {
            var salesCartDistributeBoxMap = salesCartDistributeBoxMapList[i]
            var salesCartDetailMapList = salesCartDistributeBoxMap.salesCartDetailMapList
            var salesCartDistributeBoxEntity = salesCartDistributeBoxMap.salesCartDistributeBoxEntity
            var sizeMapArray = [];
            var goodsSizeArray = current.data.goodsSizeArray;
            for (var k = 0; k < goodsSizeArray.length; k++) {
              var name = goodsSizeArray[k];
              var selectNum = '';
              for (var j = 0; j < salesCartDetailMapList.length; j++) {
                var size = salesCartDetailMapList[j].size;
                if (name == size) {
                  selectNum = salesCartDetailMapList[j].salesShoppingCartDetailEntity.selectNum;
                  continue;
                }
              }
              sizeMapArray.push({ "num": selectNum, "name": name })
            }
            var colorDetail = { "seq": salesCartDistributeBoxEntity.colorSeq, "name": salesCartDistributeBoxMap.colorName, "colorTotalNum": salesCartDistributeBoxEntity.colorTotalNum, "boxCount": salesCartDistributeBoxEntity.boxCount, "goodsSizeArray": sizeMapArray }
            colorArray.push(colorDetail)
          }
          if (salesShoppingCartEntity.isAllocated == 1) {
            current.setData({
              items: [{ name: '按件配', value: '1', checked: 'true' },
              { name: '散配', value: '0' }],
            })
          } else {
            current.setData({
              items: [{ name: '按件配', value: '1' },
              { name: '散配', value: '0', checked: 'true' }],
            })
          }

          current.setData({
            colorArray: colorArray,
            salesShoppingCartEntity: salesShoppingCartEntity,
            userGoodId: salesShoppingCartEntity.userGoodID,
            isChart: salesShoppingCartEntity.isAllocated,
            perBoxNum: salesShoppingCartEntity.perBoxNum,

          })
        }



        current.setData({
          goodsDetail: res.detail,
          countRank: res.countRank,
          numRank: res.numRank,
          orderCount: orderCount,
          salesNum: salesNum
        });
      }).catch(err => {
        console.error(err);
      });
    }).catch(err => {
      console.error(err);
    });

  

  },
  home:function(){
    wx.reLaunch({
      url: '/pages/home/home',
    })
  },
  scanCode: function () {
    wx.scanCode({
      success: (res) => {
        let pathUrl = '/' + res.path;
        if (pathUrl == "/undefined") {
          pathUrl = "/" + res.result
        } else {
          pathUrl = decodeURIComponent(pathUrl)
          pathUrl = pathUrl.replace("scene=", "")
        }
        if (pathUrl) {
          wx.redirectTo({
            url: pathUrl +"&isOrder=1"
          });
        } else {
          app.gwToast(res.result);
        }
        console.log(pathUrl);
      },
      fail: (err) => {
        app.gwToast("扫码失败");
      }
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  radioChange:function(e){
    var that=this;
    var value=e.detail.value;
    that.setData({
      isChart: value,
    })
  },
  bindPickerChange:function(e){
    var that = this;
    var isChart=that.data.isChart;
    if (isChart==1){
      var perBoxNum = that.data.perBoxNum;
      if (!perBoxNum){
        wx.showToast({
          title: '未填写配件数量',
          icon: 'none'
        });
        return;
      }
    }


    var index = e.detail.value;
    var goodsColorArray=that.data.goodsColorArray
    var colorArray = that.data.colorArray;
    var colorDetail = goodsColorArray[index];
    var goodsSizeArray = that.data.goodsSizeArray;
    var sizeMapArray=[];
    for (var i = 0; i < goodsSizeArray.length;i++){
      var name = goodsSizeArray[i];
      sizeMapArray.push({ "num": '', "name": name}
      )
    }
    colorDetail = { "seq": colorDetail.seq, "name": colorDetail.name, "colorTotalNum": '', "boxCount": '', "goodsSizeArray": sizeMapArray}
    colorArray.push(colorDetail)
    that.setData({
      colorArray: colorArray
    })
  },
  selectNum:function(e){
    var that=this;
    var selectNum=e.detail.value
    var colorSeq = e.currentTarget.dataset.color;
    var size = e.currentTarget.dataset.size;
    var colorArray = that.data.colorArray;
    var index = e.currentTarget.dataset.index;
    var colorDetail = colorArray[index];
    var goodsSizeArray = colorDetail.goodsSizeArray
    for (var i = 0; i < goodsSizeArray.length;i++){
      var name = goodsSizeArray[i].name;
      if (name == size){
        goodsSizeArray[i].num = selectNum
      }
    }
    colorArray[index].goodsSizeArray = goodsSizeArray
    that.setData({
      colorArray: colorArray
    })
  },
  userGoodId:function(e){
    var that = this;
    var userGoodId = e.detail.value
    that.setData({
      userGoodId: userGoodId
    })
  },
  perBoxNum:function(e){
    var that=this;
    var perBoxNum = e.detail.value
    var colorArray = that.data.colorArray;
    for(var i=0;i<colorArray.length;i++){
      var colorTotalNum = colorArray[i].colorTotalNum
      var boxCount = colorTotalNum / perBoxNum;
      if (!isInteger(boxCount)){
        boxCount=''
      }
      colorArray[i].boxCount=boxCount
    }
    console.log(colorArray)
    that.setData({
      perBoxNum:perBoxNum,
      colorArray: colorArray
    })
  },
  colorTotalNum:function(e){
    var that = this;
    var index = e.currentTarget.dataset.index;
    var colorArray = that.data.colorArray;
    var colorTotalNum = e.detail.value
    colorArray[index].colorTotalNum = colorTotalNum
    var perBoxNum=that.data.perBoxNum;
    var isChart = that.data.isChart
    if(isChart==1){
    var boxCount = colorTotalNum / perBoxNum;
    if (!isInteger(boxCount)) {
      wx.showToast({
        title: '数量不准确',
        icon: 'none'
      });
      return;
    }
    }
    colorArray[index].boxCount = boxCount
    that.setData({
      colorArray: colorArray
    })
  },
  del:function(e){
    var that = this;
    var index = e.currentTarget.dataset.index;
    var colorArray = that.data.colorArray;
    colorArray.splice(index,1)
    that.setData({
      colorArray: colorArray
    })
  },
  submit: function (e) {
    var that = this;
    that.setData({
      disabled: true
    })
    var userGoodId = that.data.userGoodId;
    
    var seq = '';
    if (that.data.salesShoppingCartEntity!=null){
      seq = that.data.salesShoppingCartEntity.seq
    }
  
    var isChart = that.data.isChart;
    var perBoxNum = that.data.perBoxNum;
    var colorArray = that.data.colorArray;
    var goodSeq = that.data.goodSeq;
    if (colorArray.length==0){
      wx.showToast({
        title:"未添加任何配码",
        icon: 'none'
      });
      that.setData({
        disabled: false
      })
      return; 
    }
    for (var i = 0; i < colorArray.length;i++){
      var goodsSizeArray = colorArray[i].goodsSizeArray;
      var colorTotalNum = colorArray[i].colorTotalNum;
      if (colorTotalNum==0){
        wx.showToast({
          title: colorArray[i].name+'数量未填',
          icon: 'none'
        });
        that.setData({
          disabled: false
        })
        return;
      }

      var count = 0;
      for (var j = 0; j < goodsSizeArray.length;j++){
        var num = goodsSizeArray[j].num;
        if (num==''){
          goodsSizeArray[j].num=0;
        }else{
          count = count + parseInt(num);
        }
      }
      if (isChart == 1) {
        var boxCount = colorArray[i].boxCount
        if ((count != perBoxNum && count != colorTotalNum) || !isInteger(boxCount) ){
        wx.showToast({
          title: colorArray[i].name+'配件错误',
          icon: 'none'
        });
          that.setData({
            disabled: false
          })
        return;
      }
      }else{
        if (count != colorTotalNum) {
          wx.showToast({
            title: colorArray[i].name+ '配件错误',
            icon: 'none'
          });
          that.setData({
            disabled: false
          })
          return;
        }
      }
    }
    if (isChart==0){
      perBoxNum=0
   }
    app.gwRequest({
      url: 'cart/addCart',
      type: 'POST',
      data: {
        userGoodId: userGoodId,
        isChart: isChart,
        perBoxNum: perBoxNum,
        colorArray: JSON.stringify(colorArray),
        goodSeq: goodSeq,
        seq: seq
      }
    }).then(res => {
      console.log(res)
      if(res.code==0){
        wx.switchTab({
          url: '/pages/cart/index/cart'
        });
      }
      
    });



  },
  toCartPage: function() {
    wx.switchTab({
      url: '/pages/cart/index/cart'
    });
  },
  chooseGoods: function(event) {
    var user = wx.getStorageSync('user');
    if (!user.telephone || user.telephone == "" || user.telephone == null) {
      wx.reLaunch({
        url: '/pages/authorize/authorize',
      })

      return false;
    }

    let fromType = event.currentTarget.dataset.from;
    this.setData({
      dialogShow: true,
      selectFrom: parseInt(fromType)
    });
  },
  dialogTap: function() {
    this.setData({
      dialogShow: false
    });
  },
  catchDialogEvent: function() {
    //用于捕获点击事件，防止冒泡到dialog隐藏
  },
  colorClick: function(event) {
    let colorSeq = event.currentTarget.dataset.colorSeq;
    if (this.data.chooseDetail.size) {
      let size = this.data.chooseDetail.size;
      getSelectStock(size, colorSeq, this);
    }
    this.setData({
      [`chooseDetail.colorSeq`]: colorSeq
    });
  },
  sizeClick: function(event) {
    let size = event.currentTarget.dataset.size;
    if (this.data.chooseDetail.colorSeq) {
      let colorSeq = this.data.chooseDetail.colorSeq;
      getSelectStock(size, colorSeq, this);
    }
    this.setData({
      [`chooseDetail.size`]: size
    });
  },
  chooseDetailNumChange: function(event) {
    let num = event.currentTarget.dataset.num;
    let curNum = parseInt(num);
    curNum += this.data.chooseDetail.num;
    if (curNum <= 0) curNum = 1;
    this.setData({
      [`chooseDetail.num`]: curNum
    });
  },
  putIntoCart: function() {
    let current = this;
    let pageLinks = this.data.pageLinks;
    let goodSeq = this.data.goodSeq;
    let chooseMsg = this.data.chooseDetail;
    if (chooseMsg.num && chooseMsg.sizeSeq) {
      //添加购物车接口调用
      app.gwRequest({
        url: 'cart/add',
        type: 'POST',
        data: {
          shoesDataSeq: chooseMsg.sizeSeq,
          buyCount: chooseMsg.num,
          totalPrice: chooseMsg.num * this.data.goodsDetail.salePrice,
          openIDLinks: pageLinks
        }
      }).then(res => {
        console.log(res);
        app.gwToast(res.msg);
        if (res.code == 0) {
          current.setData({
            dialogShow: false,
            chooseDetail: {
              num: 1
            }
          });
        }
      });
    } else {
      wx.showToast({
        title: '请选择商品信息',
        icon: 'none'
      });
    }
  },
  generateOrder: function() {
    let pageLinks = this.data.pageLinks;
    let goodSeq = this.data.goodSeq;
    let chooseMsg = this.data.chooseDetail;
    if (chooseMsg.num && chooseMsg.sizeSeq) {
      let goodsArray = [];
      let oneGoods = {
        goodSeq: goodSeq,
        openIDLinks: pageLinks,
        introduce: this.data.goodsDetail.introduce,
        salePrice: this.data.goodsDetail.salePrice,
        color: chooseMsg.colorName,
        size: chooseMsg.size,
        shoesDataSeq: chooseMsg.sizeSeq,
        buyCount: chooseMsg.num,
        img: this.data.goodsDetail.images[0]
      };
      goodsArray.push(oneGoods);
      app.globalData.orderGoodsList = {
        fromType: 2,
        array: goodsArray
      };
      wx.navigateTo({
        url: '/pages/order/upload/upload',
      });
    } else {
      wx.showToast({
        title: '请选择商品信息',
        icon: 'none'
      });
    }
  },

})

function getSelectStock(size, colorSeq, current) {
  current.data.goodsSizeAndColor.forEach((v) => {
    if (v.size == size && v.colorSeq == colorSeq) {
      current.setData({
        [`chooseDetail.colorName`]: v.colorName,
        [`chooseDetail.sizeSeq`]: v.seq,
        [`chooseDetail.stock`]: v.stock
      });
    }
  });
}
function isInteger(obj) {
  return Math.floor(obj) === obj
}