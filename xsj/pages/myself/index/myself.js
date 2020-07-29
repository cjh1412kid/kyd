// pages/myself/index/myself.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    needAuthor: true,
    headerUrl: 'https://www.fyweather.com/SmartSale/picture/online_sales_app/myself/1/header.png',
    nickName: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let current = this;
    wx.getUserInfo({
      success: res => {
        current.setData({
          needAuthor: false,
          nickName: res.userInfo.nickName,
          headerUrl: res.userInfo.avatarUrl
        });
      },
      fail: err => {

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
  bindGetUserInfo: function(res) {
    let message = res.detail;
    let current = this;
    current.setData({
      needAuthor: false,
      nickName: message.userInfo.nickName,
      headerUrl: message.userInfo.avatarUrl
    });
  },
  openAddress: function() {
    wx.navigateTo({
      url: '/pages/myself/address/list/list',
    });
  },
  orderBtnClick: function(event) {
    let orderStatus = event.currentTarget.dataset.orderStatus;
    wx.navigateTo({
      url: '/pages/order/list/list?orderStatus=' + orderStatus
    });
  }
})