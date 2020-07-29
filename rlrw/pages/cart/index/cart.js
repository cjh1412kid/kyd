// pages/cart/cart.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    cartData: [],
    allIsChecked: false,
    totalPrice: 0
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
    loadCartList(this);
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    loadCartList(this);
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  strollClick: function () {
    wx.switchTab({
      url: '/pages/home/home'
    });
  },
  checkBtnClick: function (event) {
    let current = this;
    let index = event.currentTarget.dataset.index;
    let clickData = this.data.cartData[index];
    let checkStatus;
    if (clickData.isChecked == 1) {
      checkStatus = 0;
    } else {
      checkStatus = 1;
    }

    //更新界面
    let totalPrice = current.data.totalPrice;
    if (checkStatus == 1) {
      totalPrice += (clickData.buyCount * clickData.salePrice);
    } else {
      totalPrice -= (clickData.buyCount * clickData.salePrice);
    }
    current.setData({
      totalPrice: totalPrice,
      [`cartData[${index}].isChecked`]: checkStatus
    });

    //发送请求
    app.gwRequest({
      url: 'cart/edit', type: 'POST',
      data: {
        shoppingCartSeq: clickData.seq,
        isChecked: checkStatus
      }
    }).then(res => {

    });
  },
  numberChangeClick: function (event) {
    let current = this;
    let index = event.currentTarget.dataset.index;
    let clickData = this.data.cartData[index];
    let changeNum = parseInt(event.currentTarget.dataset.num);
    let newBuyCount = clickData.buyCount + changeNum;
    if (newBuyCount <= 0) newBuyCount = 1;

    let totalPrice = current.data.totalPrice;
    totalPrice += ((newBuyCount - clickData.buyCount) * clickData.salePrice);
    current.setData({
      totalPrice: totalPrice,
      [`cartData[${index}].buyCount`]: newBuyCount
    });

    //发送请求
    app.gwRequest({
      url: 'cart/edit', type: 'POST',
      data: {
        shoppingCartSeq: clickData.seq,
        buyCount: newBuyCount,
        totalPrice: newBuyCount * clickData.salePrice
      }
    }).then(res => {

    });
  },
  delCartData: function (event) {
    let current = this;
    let index = event.currentTarget.dataset.index;
    let seq = this.data.cartData[index].seq;

    app.gwModal({
      title: '提醒',
      content: '确定要删除？'
    }).then(res => {
      if (res.confirm) {
        app.gwRequest({
          url: 'cart/delete', type: 'POST',
          data: { shoppingCartSeq: seq }
        }).then(res => {
          console.log(res);
          app.gwToast(res.msg);
          loadCartList(current);
        });
      }
    }).catch(err => {
      console.error(err);
    });

  },
  generateOrder: function () {
    let cartData = this.data.cartData;
    let goodsArray = [];
    //app.globalData.orderGoodsList传参数
    for (let index in cartData) {
      let oneData = cartData[index];
      if (oneData.isChecked == 1) {
        oneData.cartSeq = oneData.seq;
        goodsArray.push(oneData);
      }
    }

    app.globalData.orderGoodsList = { fromType: 1, array: goodsArray };

    wx.navigateTo({
      url: '/pages/order/upload/upload',
    });
  },
  //手指刚放到屏幕触发
  touchS: function (e) {
    console.debug("touchS:");
    console.debug(e);
    //判断是否只有一个触摸点
    if (e.touches.length == 1) {
      this.setData({
        //记录触摸起始位置的X坐标
        startX: e.touches[0].clientX
      });
    }
  },
  //触摸时触发，手指在屏幕上每移动一次，触发一次
  touchM: function (e) {
    console.debug("touchM:");
    console.debug(e);
    var that = this
    if (e.touches.length == 1) {
      //记录触摸点位置的X坐标
      var moveX = e.touches[0].clientX;
      //计算手指起始点的X坐标与当前触摸点的X坐标的差值
      var disX = that.data.startX - moveX;
      //delBtnWidth 为右侧按钮区域的宽度
      var delBtnWidth = 180;
      var txtStyle = "";
      if (disX == 0 || disX < 0) {//如果移动距离小于等于0，文本层位置不变
        txtStyle = "left:0";
      } else if (disX > 0) {//移动距离大于0，文本层left值等于手指移动距离
        txtStyle = "left:-" + disX + "rpx";
        if (disX >= delBtnWidth) {
          //控制手指移动距离最大值为删除按钮的宽度
          txtStyle = "left:-" + delBtnWidth + "rpx";
        }
      }
      //获取手指触摸的是哪一个item
      var index = e.currentTarget.dataset.index;
      //更新列表的状态
      this.setData({
        [`cartData[${index}].txtStyle`]: txtStyle
      });
    }
  },
  touchE: function (e) {
    console.debug("touchE:");
    console.debug(e);
    var that = this
    if (e.changedTouches.length == 1) {
      //手指移动结束后触摸点位置的X坐标
      var endX = e.changedTouches[0].clientX;
      //触摸开始与结束，手指移动的距离
      var disX = that.data.startX - endX;
      var delBtnWidth = 180;
      //如果距离小于删除按钮的1/2，不显示删除按钮
      var txtStyle = disX > delBtnWidth / 2 ? "left:-" + delBtnWidth + "rpx" : "left:0";
      //获取手指触摸的是哪一项
      var index = e.currentTarget.dataset.index;
      //更新列表的状态
      that.setData({
        [`cartData[${index}].txtStyle`]: txtStyle
      });
    }
  }
});

function loadCartList(current) {
  app.gwRequest({ url: 'cart/list' }).then(res => {

    let allIsChecked = true;
    let totalPrice = 0;
    for (let index in res.result) {
      let oneData = res.result[index];
      if (oneData.isChecked != 1) {
        allIsChecked = false;
      } else {
        totalPrice += (oneData.buyCount * oneData.salePrice);
      }
    }

    current.setData({
      totalPrice: totalPrice,
      allIsChecked: allIsChecked,
      cartData: res.result
    });
    wx.stopPullDownRefresh();
  }).catch(err => {
    console.error(err);
    wx.stopPullDownRefresh();
  });
}