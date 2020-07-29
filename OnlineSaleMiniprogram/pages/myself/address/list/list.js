// pages/myself/address/list/list.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.select) {
      this.setData({
        selectModel: true
      });
    }
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
    // 修改地址返回时需要刷新
    loadList(this);
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
  setDefaultClick: function (event) {
    if (this.data.selectModel) {
      return;
    }
    let current = this;
    let seq = event.currentTarget.dataset.seq;
    let old = event.currentTarget.dataset.old;
    console.log(event.currentTarget.dataset);
    if (old) {
      wx.showModal({
        title: '提示',
        content: '确认取消默认地址',
        success: function (res) {
          if (res.confirm) {
            app.gwRequest({
              url: 'address/setdefault', type: 'POST',
              data: { seq: seq, isdefault: false }
            }).then(res => {
              console.log(res);
              loadList(current);
            });
          }
        }
      });
    } else {
      wx.showModal({
        title: '提示',
        content: '确认设置为默认地址',
        success: function (res) {
          if (res.confirm) {
            app.gwRequest({
              url: 'address/setdefault', type: 'POST',
              data: { seq: seq, isdefault: true }
            }).then(res => {
              console.log(res);
              loadList(current);
            });
          }
        }
      });
    }
  },
  addAddress: function () {
    wx.navigateTo({
      url: '/pages/myself/address/edit/edit',
    });
  },
  editAddress: function (event) {
    console.log(event);
    let seq = event.currentTarget.dataset.addressId;
    wx.navigateTo({
      url: '/pages/myself/address/edit/edit?seq=' + seq,
    });
  },
  delAddress: function (event) {
  },
  selectModelClick: function (event) {
    if (this.data.selectModel) {
      let index = event.currentTarget.dataset.index;
      let selectAddress = this.data.addressData[index];
      app.globalData.selectAddress = selectAddress;
      console.debug(selectAddress);
      wx.navigateBack({});
    }
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
    if (this.data.selectModel) {
      return;
    }
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
        [`addressData[${index}].txtStyle`]: txtStyle
      });
    }
  },
  touchE: function (e) {
    if (this.data.selectModel) {
      return;
    }
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
        [`addressData[${index}].txtStyle`]: txtStyle
      });
    }
  }
});

function loadList(current) {
  app.gwRequest({ url: 'address/list' }).then(res => {
    current.setData({
      addressData: res.addresses
    });
  });
}