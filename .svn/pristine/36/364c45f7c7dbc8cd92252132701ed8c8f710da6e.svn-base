// pages/order/detail/detail.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    pickQRCodeShow: false,
    pickQRCodeUrl: '',
    orderDetail: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let seq = options.seq;
    if (!seq) {
      app.gwToast('加载订单出错！');
      return;
    }
    let current = this;
    loadOrderDetail(current, seq);
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
  noteShopShip: function() {
    app.gwToast('提醒已发送');
  },
  gotShipBtnClick: function() {
    let seq = this.data.orderDetail.seq;
    let current = this;
    app.gwModal({
      title: '提醒',
      content: '确认已收货？'
    }).then(res => {
      if (res.confirm) {
        app.gwRequest({
          url: 'order/gotShipOrder',
          type: 'POST',
          data: {
            orderSeq: seq
          }
        }).then(res => {
          app.gwToast(res.msg);
          loadOrderDetail(current, seq);
        });
      }
    });
  },
  pickGoods: function() {
    let seq = this.data.orderDetail.seq;
    let current = this;
    app.gwRequest({
      url: 'order/cancelQRCode?seq=' + seq
    }).then(res => {
      current.setData({
        pickQRCodeShow: true,
        pickQRCodeUrl: res.url
      });
    }).catch(err => {
      app.gwToast(err.msg);
    });
  },
  hideQRCode: function() {
    this.setData({
      pickQRCodeShow: false,
      pickQRCodeUrl: ''
    });
  },
  cancelBtnClick: function() {
    let seq = this.data.orderDetail.seq;
    app.gwModal({
      title: '提醒',
      content: '确定要取消？'
    }).then(res => {
      if (res.confirm) {
        app.gwRequest({
          url: 'order/cancelOrder',
          type: 'POST',
          data: {
            orderSeq: seq
          }
        }).then(res => {
          app.gwToast(res.msg);
          wx.navigateBack({});
        });
      }
    });
  },
  deleteBtnClick: function() {
    let seq = this.data.orderDetail.seq;
    app.gwModal({
      title: '提醒',
      content: '确定要删除？'
    }).then(res => {
      if (res.confirm) {
        app.gwRequest({
          url: 'order/deleteOrder',
          type: 'POST',
          data: {
            orderSeq: seq
          }
        }).then(res => {
          app.gwToast(res.msg);
          wx.navigateBack({});
        });
      }
    });
  },
  payBtnClick: function() {
    let current = this;
    let seq = this.data.orderDetail.seq;
    app.gwRequest({
      url: 'order/payOrder',
      type: 'POST',
      data: {
        seq: seq
      }
    }).then(res => {
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
          }
        });
      } else {
        app.gwToast(res.msg);
      }
    });
  }
})

function loadOrderDetail(current, seq) {
  app.gwRequest({
    url: 'order/orderDetail',
    data: {
      seq: seq
    }
  }).then(res => {
    console.log(res);
    current.setData({
      orderDetail: res.result[0]
    });
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
        loadOrderDetail(current, seq);
      } else {
        if (waitTime > 10) {
          clearInterval(waitInterval);
          app.gwModal({
            title: '提醒',
            content: '获取支付结果超时，请联系客户或前往收银台处理'
          }).then(res => {
            loadOrderDetail(current, seq);
          }).catch(res => {
            loadOrderDetail(current, seq);
          });
        }
      }
    });
  }, 5000);
}