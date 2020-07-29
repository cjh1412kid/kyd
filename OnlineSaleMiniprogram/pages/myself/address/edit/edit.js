// pages/myself/address/edit/edit.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    region: ['', '', ''],
    address: { isdefault: false }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options);
    let current = this;
    let addressSeq = options.seq;
    let titleText = '新增收货地址';
    if (addressSeq) {
      titleText = '编辑收货地址';
      app.gwRequest({ url: 'address/select', data: { seq: addressSeq } }).then(res => {
        console.log(res);
        let resultAddress = res.address;
        let region = [];
        let addressArray = resultAddress.address.split(' ');
        let addressStr = [];
        for (let i = 0; i < addressArray.length; i++) {
          if (i < 3) {
            region.push(addressArray[i]);
          } else {
            addressStr.push(addressArray[i]);
          }
        }
        resultAddress.detailAddress = addressStr.join(' ');
        current.setData({
          region: region,
          address: resultAddress
        });
      });
    }
    wx.setNavigationBarTitle({
      title: titleText
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
  bindRegionChange: function (event) {
    this.setData({
      region: event.detail.value
    })
  },
  defaultChange: function () {
    let selectIcon = !this.data.address.isdefault;
    this.setData({
      [`address.isdefault`]: selectIcon
    });
  },
  formReset: function () {
    console.log("cancle");
    wx.navigateBack({});
  },
  formSubmit: function (event) {
    let name = event.detail.value.name;
    let phone = event.detail.value.phone;
    let region = event.detail.value.region;
    let address = event.detail.value.address;
    let isdefault = this.data.address.isdefault;
    let toastTitle;
    if (!name || name.length <= 0) {
      toastTitle = '收货人不能为空';
    } else if (!phone || phone.length <= 0) {
      toastTitle = '手机号不能为空';
    } else if (!address || address.length <= 0) {
      toastTitle = '地址不能为空';
    } else if (region[0].length <= 0 || region[1].length <= 0 || region[2].length <= 0) {
      toastTitle = '地区选择不能为空';
    }
    if (toastTitle) {
      wx.showToast({
        icon: 'none',
        title: toastTitle,
      });
      return;
    }
    let seq = this.data.address.seq || '';
    let customSeq = this.data.address.customSeq || '';
    let detailAddress = region.join(' ') + ' ' + address;
    let uploadData = {
      seq: seq,
      customSeq: customSeq,
      recipientsName: name,
      address: detailAddress,
      telephone: phone,
      isDefault: isdefault
    };

    app.gwRequest({ url: 'address/edit', type: 'POST', data: uploadData }).then(res => {
      console.log(res);
    });
  }
})