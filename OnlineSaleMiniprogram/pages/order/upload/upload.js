// pages/order/upload/upload.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    selfPick: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let current = this;
    let listObj = app.globalData.orderGoodsList || {};
    // fromType 0：错误，1：购物车，2：直接购买
    let list = listObj.array || [],
      fromType = listObj.fromType || 0;
    console.log(list);
    let totalGoodsPrice = 0,
      expressPrice = 0;
    for (let i = 0; i < list.length; i++) {
      let goods = list[i];
      totalGoodsPrice += (goods.buyCount * goods.salePrice);
    }
    this.setData({
      fromType: fromType,
      goodsList: list,
      totalGoodsPrice: totalGoodsPrice,
      expressPrice: expressPrice
    });

    app.gwRequest({
      url: 'address/getdefault'
    }).then(res => {
      current.setData({
        address: res.address
      });
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
    //获取位置信息
    if (app.globalData.selectAddress) {
      this.setData({
        address: app.globalData.selectAddress
      });
    }
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
    delete app.globalData.orderGoodsList;
    delete app.globalData.selectAddress;
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
  chooseAddress: function() {
    wx.navigateTo({
      url: '/pages/myself/address/list/list?select=true',
    });
  },
  selfPickClick: function() {
    let newPick = !this.data.selfPick;
    this.setData({
      selfPick: newPick
    });
  },
  uploadOrder: function() {
    if (!this.data.address && !this.data.address.seq) {
      app.gwToast("请选择收货地址");
      return;
    }

    let submitType = this.data.fromType;
    let orderPrice = this.data.totalGoodsPrice + this.data.expressPrice;
    let userDeliverySeq = this.data.address.seq;
    let selfPick = this.data.selfPick;
    let shoesDataBuyCountList = [];

    for (let index in this.data.goodsList) {
      let oneGood = this.data.goodsList[index];
      let uploadGood = {
        shoesDataSeq: oneGood.shoesDataSeq,
        buyCount: oneGood.buyCount,
        openIDLinks: oneGood.openIDLinks
      }
      // 购物车加上购物车序号
      if (submitType == 1) {
        uploadGood.shoppingCartSeq = oneGood.cartSeq;
      }
      shoesDataBuyCountList.push(uploadGood);
    }
    app.gwRequest({
      url: 'order/submitOrder',
      type: 'POST',
      data: {
        shoesDataBuyCountList: JSON.stringify(shoesDataBuyCountList),
        submitType: submitType,
        orderPrice: orderPrice,
        userDeliverySeq: userDeliverySeq,
        selfPick: selfPick
      }
    }).then(res => {
      console.log(res);
      app.gwToast(res.msg);
      let orderSeq = res.result[0];
      wx.showLoading({});
      // 重定向到订单详情
      payOrder(this, orderSeq);
    }).catch(err => {
      if (err.msg) {
        app.gwToast(err.msg);
      }
    });
  }
})

function payOrder(current, seq) {
  app.gwRequest({
    url: 'order/payOrder',
    type: 'POST',
    data: {
      seq: seq
    }
  }).then(res => {
    wx.hideLoading();
    if (res.code == 0) {
      wx.requestPayment({
        timeStamp: res.payOrderResult.timeStamp,
        nonceStr: res.payOrderResult.nonceStr,
        package: res.payOrderResult.packageValue,
        signType: res.payOrderResult.signType,
        paySign: res.payOrderResult.paySign,
        success: function(res) {
          waitPayResult(current, seq);
        },
        fail: function(err) {
          if (err.errMsg == "requestPayment:fail cancel") {
            app.gwToast("支付已取消");
          } else {
            app.gwToast("支付失败");
          }
          gotoOrderDetails(seq);
        }
      });
    } else {
      app.gwToast(res.msg);
      gotoOrderDetails(seq);
    }
  });
}

function waitPayResult(current, seq) {
  wx.showLoading({
    title: '等待支付结果',
    mask: true,
  });
  let waitTime = 0;
  let waitInterval = setInterval(function() {
    app.gwRequest({
      url: 'order/payStatus',
      data: {
        seq: seq
      }
    }).then(res => {
      waitTime++;
      if (res.code == 0 && res.status == 1) {
        wx.hideLoading();
        clearInterval(waitInterval);
        app.gwToast("支付成功");
        gotoOrderDetails(seq);
      } else {
        if (waitTime > 10) {
          clearInterval(waitInterval);
          app.gwModal({
            title: '提醒',
            content: '获取支付结果超时，请联系客户或前往收银台处理'
          }).then(res => {
            gotoOrderDetails(seq);
          }).catch(res => {
            gotoOrderDetails(seq);
          });
        }
      }
    });
  }, 5000);
}

function gotoOrderDetail(seq) {
  wx.redirectTo({
    url: '/pages/order/detail/detail?seq=' + seq,
  });
}