// pages/order/list/list.js
const app = getApp();

let startNum = 1;
let limit = 10;

//正在加载的标记
let pageIsLoading = false;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    tabs: ['全部', '待支付', '待发货', '已发货', '已完成'],
    activeIndex: 0,
    showList: [{ status: -1 }, { status: 0 }, { status: 1 }, { status: 2 }, { status: 3 }],
    orderList: [],
    loading: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let orderStatus = parseInt(options.orderStatus) || 0;
    this.setData({
      activeIndex: orderStatus
    });

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
    let status = this.data.activeIndex - 1;
    loadOrderList(this, status, true);
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
    startNum = 1;
    pageIsLoading = true;
    let status = this.data.activeIndex - 1;
    loadOrderList(this, status, true).then(res => {
      pageIsLoading = false;
      wx.stopPullDownRefresh();
    });
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (this.data.loading && !pageIsLoading) {
      pageIsLoading = true;
      let status = this.data.activeIndex - 1;
      loadOrderList(this, status).then(res => {
        pageIsLoading = false;
      });
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  tabClick: function (event) {
    let tabIndex = event.currentTarget.dataset.index;
    startNum = 1;
    this.setData({
      activeIndex: tabIndex,
      orderList: []
    });
    loadOrderList(this, tabIndex - 1, true);
  },
  orderDetailClick: function (event) {
    let orderSeq = event.currentTarget.dataset.seq;
    wx.navigateTo({
      url: '/pages/order/detail/detail?seq=' + orderSeq
    });
  }
});

function loadOrderList(current, status = -1, refresh = false) {
  //-1:'全部', 0:'待支付', 1:'待发货', 2:'已发货', 3:'已完成'
  return app.gwRequest({
    url: 'order/onlineOrderList',
    data: { start: startNum, num: limit, orderStatus: status }
  }).then(res => {

    let loadingFlag = true;
    if (res.result.length < limit) {
      loadingFlag = false;
    } else {
      startNum += limit;
    }

    let list = current.data.orderList;
    if (refresh) {
      list = res.result;
    } else {
      list = list.concat(res.result);
    }

    current.setData({
      orderList: list,
      loading: loadingFlag
    });
  });
}