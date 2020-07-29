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
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    //普通链接
    const pageLinks = options.links || '';
    let goodSeq = options.seq || '';

    // 扫码详情
    if (options.scene) {
      let params = decodeURIComponent(options.scene).split('&');
      params.forEach((value) => {
        if (value.indexOf('seq=') == 0) {
          goodSeq = value.split('=')[1];
        }
      });
    }

    this.setData({
      pageLinks: pageLinks,
      goodSeq: goodSeq
    });
    let current = this;
    app.gwRequest({
      url: 'goods/detail',
      type: 'post',
      data: {
        seq: goodSeq
      }
    }).then(res => {
      current.setData({
        goodsDetail: res.detail
      });
    }).catch(err => {
      console.error(err);
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

      current.setData({
        goodsSetStock: setStock,
        goodsSizeArray: sizeArray,
        goodsColorArray: colorArray,
        goodsSizeAndColor: sizeAndColors
      });
    }).catch(err => {
      console.error(err);
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
  toCartPage: function() {
    wx.switchTab({
      url: '/pages/cart/index/cart'
    });
  },
  chooseGoods: function(event) {
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
  }
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