// pages/goods/category/category.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    categoryData: [],
    activeIndex: undefined,
    currentSelectArray: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let current = this;
    app.gwRequest({ url: 'goods/category' }).then(res => {
      console.log(res.result.length);
      if (res.result.length > 0) {
        current.setData({
          activeIndex: 0,
          categoryData: res.result,
          currentSelectArray: res.result[0].child
        });
      }
    })
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
  categoryClick: function (event) {
    let categorySeq = event.currentTarget.dataset.seq;
    let categoryName = event.currentTarget.dataset.name;
    wx.navigateTo({
      url: '/pages/goods/list/list?categorySeq=' + categorySeq + '&categoryName=' + categoryName
    });
  },
  firstCategoryChange: function (event) {
    let categoryIndex = event.currentTarget.dataset.index;
    categoryIndex = parseInt(categoryIndex);
    let categoryList = this.data.categoryData;
    this.setData({
      activeIndex: categoryIndex,
      currentSelectArray: categoryList[categoryIndex].child
    });
  }
})