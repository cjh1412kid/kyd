// pages/authorize/authorize.js
const app = getApp()
const myRequest = require('../../utils/request.js');
Page({
  data: {
    disable: false,
    color: "#FF6E71",
    character: "同意授权登录",
    buttHidden: false, //授权按钮是否显示
    navHidden: true, //注册按钮是否显示
    bintap: "getUserInfo",
    exit:'取消授权',
    img:'/assets/images/main1.png'
  },

  onShow: function () {
    var that = this;
    that.setData({
      disabled: false,
      color: "#FF6E71",
      character: "同意授权登录"
    })
  },

  //点击授权登录
  onGotUserInfo: function (e) {
    var that = this;
    that.setData({
      disabled: true,
      color: "#C1C1C1",
      character: "正在授权中"
    })
    var user = wx.getStorageSync('user') || {};
    wx.login({
      success: function (r) {
        var code = r.code;//登录凭证
        if (code) {

          //2、调用获取用户信息接口
          wx.getUserInfo({
            success: res => {
              var encryptedData = "";
              var iv = "";
              encryptedData = res.encryptedData;
              iv = res.iv;
              //3.请求自己的服务器，解密用户信息 获取unionId等加密信息
              // wx.request({
              //   url: app.globalData.serviceURL + '/app/decodeUserInfo',//服务接口地址
              //   method: 'post',
              //   header: {
              //     'content-type': 'application/x-www-form-urlencoded'
              //   },
              //   data: { encryptedData: encryptedData, iv: iv, code: code, user: JSON.stringify(user) },
              //   success: function (data) {
              //     console.log(data)
              //     //4.解密成功后 获取自己服务器返回的结果
              //     if (data.data.code == 0) {
              //       wx.setStorageSync("user", data.data.result[0]);
              //       var userJson = data.data.result[0]
              //       var loginId = userJson.seq;
              //       var phone = userJson.loginPhone;
              //       if (!phone) {
              //         that.setData({
              //           character: "获取手机号码",
              //           bintap: "getPhoneNumber",
              //           disabled: false,
              //           color: "#088CD8",
              //         })
              //       } else {
              //         if (data.data.userStatus == 0) {
              //           wx.showModal({
              //             title: '提示',
              //             showCancel: false,
              //             content: '该账号已停用，请联系客服!',
              //             success: function (res) {
              //               wx.reLaunch({
              //                 url: '/pages/authorize/authorize',
              //               })
              //             }
              //           })
              //         } else {
              //           //账号正常跳转到首页
              //           wx.reLaunch({
              //             url: '/pages/index/index',
              //           })
              //         }
              //       }
              //     } else {
              //       wx.showModal({
              //         title: '提示',
              //         showCancel: false,
              //         content: '获取用户信息失败，请稍后重试!',
              //         success: function (res) {
              //           wx.reLaunch({
              //             url: '/pages/authorize/authorize',
              //           })
              //         }
              //       })
              //     }
              //     // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              //     // 所以此处加入 callback 以防止这种情况            
              //     if (that.readyCallback) {
              //       that.globalData.canUseInfo = true;
              //       that.readyCallback(res);
              //     }
              //   },
              //   fail: function () {
              //     console.log('系统错误')
              //   }
              // })
          
              myRequest.commonRequest({
                type: 'POST', url: 'loginXcx', data: { encryptedData: encryptedData, iv: iv,code: code, miniapp:4}
              }).then(res => {
                console.log(res)
                if (res.code == 0) {
                  wx.setStorageSync("user", res.customerUserInfo);
                  if (res.customerUserInfo.isUse == 1) {
                    wx.reLaunch({
                      url: '/pages/noUse/noUse',
                    })
                    return;
                  }
                  var phone = res.customerUserInfo.telephone;
                  if (!phone) {
                    that.setData({
                      character: "获取手机号码",
                      bintap: "getPhoneNumber",
                      disabled: false,
                      color: "#FF6E71",
                      exit:'取消获取',
                      img: '/assets/images/main2.png'
                    })
                  }else{
                    wx.reLaunch({
                      url: '/pages/home/home',
                    })
                  } 
                }
              });


            },
            fail: function () {
              if (that.readyCallback) {
                that.readyCallback(r)
              }
              console.log('已拒绝授权！')
            }
          })
        } else {
          console.log('获取用户登录态失败！' + r.errMsg)
        }
      },
      fail: function () {
        //console.log('登陆失败')
      }
    })
  },
  onGotPhoneNum: function (e) {
    var that = this;
    var encryptedData = e.detail.encryptedData;
    var iv = e.detail.iv;
    var user = wx.getStorageSync('user') || {};
    console.log(user)
    var sessionkey = user.sessionKey;
    console.log(sessionkey)
    wx.checkSession({
      success: function () {
        // wx.request({
        //   url: app.globalData.serviceURL + '/app/getPhone',//服务接口地址
        //   method: 'post',
        //   header: {
        //     'content-type': 'application/x-www-form-urlencoded'
        //   },
        //   data: { encryptedData: encryptedData, iv: iv, user: JSON.stringify(user), session_key: sessionkey },
        //   success: function (data) {
        //     console.log(data)
        //     var result = data.data.result[0]
        //     var isAdmin = result.isAdmin;
        //     if (isAdmin == 1) {
        //       wx.showModal({
        //         title: '提示',
        //         showCancel: false,
        //         content: '该手机号非管理员账号，请使用管理员手机进行查看',
        //         success: function (res) {
        //           wx.reLaunch({
        //             url: '/pages/authorize/authorize',
        //           })
        //         }
        //       })
        //     } else {
        //       wx.setStorageSync("user", data.data.result[0]);
        //       wx.navigateTo({
        //         url: '/pages/index/index',
        //       })
        //     }
        //   }
        // }
        // )
      
        myRequest.commonRequest({
          type: 'POST', url: 'getPhone', data: { encryptedData: encryptedData, iv: iv, userSeq: user.seq, session_key: sessionkey, miniapp:4}
        }).then(res => {
          if (res.code == 0) {
            wx.setStorageSync("user", res.customerUserInfo);
            var phone = res.customerUserInfo.telephone;
            app.getLogin();
            wx.reLaunch({
              url: '/pages/home/home',
            })
            
          }
        });




      },
      fail: function () {

        wx.login({
          success: function (r) {
            var code = r.code;//登录凭证
            if (code) {
              wx.request({
                url: app.globalData.serviceURL + '/weixin/api/getPhone',//服务接口地址
                method: 'post',
                header: {
                  'content-type': 'application/x-www-form-urlencoded'
                },
                data: { encryptedData: encryptedData, iv: iv, code: code, user: JSON.stringify(user) },
                success: function (data) {
                  console.log(data)
                  if (data.data.status == 0) {
                    wx.showModal({
                      title: '提示',
                      showCancel: false,
                      content: '获取手机号码失败，请重试授权!',
                      success: function (res) {
                        wx.reLaunch({
                          url: '/pages/authorize/authorize',
                        })
                      }
                    })
                  } else {
                    wx.setStorageSync("user", data.data);
                    wx.navigateTo({
                      url: '/pages/index/index',
                    })
                  }
                }
              }
              )
            }
          }
        })
      },

    })

  },

})