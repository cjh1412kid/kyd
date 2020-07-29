const app = getApp();

let pageNum = 1;
const pageSize = 10;
//正在加载的标记
let pageIsLoading = false;

// pages/home/home.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    brandData: {},
    likeList: [],
    loading: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    Promise.all([loadTopic(this), loadLikeList(this, true)]).then(res => {
      pageIsLoading = false;
    }).catch(err => {
      console.error(err);
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
    pageNum = 1;
    pageIsLoading = true;
    Promise.all([loadTopic(this), loadLikeList(this, true)]).then(res => {
      pageIsLoading = false;
      wx.stopPullDownRefresh();
    }).catch(err => {
      console.error(err);
    });
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (this.data.loading && !pageIsLoading) {
      pageIsLoading = true;
      loadLikeList(this).then(res => {
        pageIsLoading = false;
      });
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  scanCode: function () {
    wx.scanCode({
      success: (res) => {
        console.log(res);
      },
      fail: (err) => {
        console.error(err);
      }
    });
  },
  searchClick: function () {
    wx.navigateTo({
      url: '/pages/goods/search/search'
    });
  },
  swiperClick: function (event) {
    let type = event.currentTarget.dataset.type,
      linkSeq = event.currentTarget.dataset.linkSeq;
    if(type == 1){
      wx.navigateTo({
        url: '/pages/goods/detail/detail?seq=' + linkSeq
      });
    } else {
      wx.navigateTo({
        url: '/pages/goods/list/list?links=' + linkSeq
      });
    }
  },
  categoryList: function () {
    wx.navigateTo({
      url: '/pages/goods/category/category'
    });
  },
  categoryClick: function (event) {
    let type = event.currentTarget.dataset.type;
    wx.navigateTo({
      url: '/pages/goods/list/list?type=' + type
    });
  },
  goodsClick: function (event) {
    let seq = event.currentTarget.dataset.seq;
    wx.navigateTo({
      url: '/pages/goods/detail/detail?seq=' + seq
    });
  }
});
function loadTopic(current) {
  return app.gwRequest({ url: 'home/topic' }).then(res => {
    current.setData({
      brandData: res.result
    });
    return Promise.resolve(true);
  });
}

function loadLikeList(current, refresh = false) {
  return app.gwRequest({ url: 'home/like', data: { page: pageNum, limit: pageSize } }).then(res => {
    console.log(res);
    let loadingFlag = true;
    if (res.page.list.length < pageSize) {
      loadingFlag = false;
    } else {
      pageNum++;
    }
    let list = current.data.likeList;
    if (refresh) {
      list = res.page.list
    } else {
      list = list.concat(res.page.list);
    }
    current.setData({
      likeList: list,
      loading: loadingFlag
    });
    return Promise.resolve(true);
  });
}