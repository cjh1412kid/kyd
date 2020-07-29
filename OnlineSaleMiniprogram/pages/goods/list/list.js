// pages/goods/list/list.js
const app = getApp();

let currentPage = 1;
let limit = 10;

//正在加载的标记
let pageIsLoading = false;

Page({

  /**
   * 页面的初始数据
   */
  //listType -1：带分类选择，0：热销爆款，1：新品特推，2：商家促销，3：明星同款，4：精选专题，5：品牌活动，6：猜你喜欢
  data: {
    dataList: [],
    loading: false,
    orderBy: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let current = this;
    console.log(options);
    let intType = parseInt(options.type);
    let listType = intType == 0 ? intType : (intType || -1);
    let categorySeq = options.categorySeq || -1;
    let categoryName = options.categoryName || '分类商品列表';
    this.setData({
      listType: listType,
      categorySeq: categorySeq
    });
    let titleText;
    switch (listType) {
      case 0:
        titleText = '热销爆款';
        break;
      case 1:
        titleText = '新品特推';
        break;
      case 2:
        titleText = '商家促销';
        break;
      case 3:
        titleText = '明星同款';
        break;
      case 4:
        titleText = '精选专题';
        break;
      case 5:
        titleText = '品牌活动';
        break;
    }
    if (categorySeq != -1) {
      titleText = categoryName
    }
    if (titleText) {
      wx.setNavigationBarTitle({
        title: titleText
      });
    }

    loadDataList(this, true).then(res => { });
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
    currentPage = 1;
    pageIsLoading = true;

    loadDataList(this, true).then(res => {
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
      loadDataList(this).then(res => {
        pageIsLoading = false;
      });
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  orderByClick: function (event) {
    if (pageIsLoading) {
      return;
    }
    let sortId = event.currentTarget.dataset.sortId;
    this.setData({
      orderBy: sortId
    });

    currentPage = 1;
    pageIsLoading = true;
    loadDataList(this, true).then(res => {
      pageIsLoading = false;
    });
  },
  goodsClick: function (event) {
    let seq = event.currentTarget.dataset.seq;
    wx.navigateTo({
      url: '/pages/goods/detail/detail?seq=' + seq
    });
  }
});

function loadDataList(current, refresh = false) {
  return app.gwRequest({
    url: 'goods/list', data: {
      page: currentPage,
      limit: 10,
      orderBy: current.data.orderBy,
      orderDir: 0,
      categorySeq: current.data.categorySeq,
      topicType: current.data.listType
    }
  }).then(res => {
    let loadingFlag = true;
    if (res.page.list.length < limit) {
      loadingFlag = false;
    } else {
      currentPage++;
    }

    let list = current.data.dataList;
    if (refresh) {
      list = res.page.list
    } else {
      list = list.concat(res.page.list);
    }

    current.setData({
      dataList: list,
      loading: loadingFlag
    });
    return Promise.resolve(true);
  });
}