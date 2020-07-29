const myRequest = require('./utils/request.js');
const user_token_key = 'wechat_xcx_user_token';
let userTokenValue =false, isLoadingToken = false;

//app.js
App({
  onLaunch: function () {
    wx.checkSession({
      success: (res) => {
        getStorageToken();
      },
      fail: (err) => {
        console.log(err);
        getLogin();
      }
    });
  },
  gwRequest: function (options) {
    return new Promise((resolve, reject) => {
      let interval = setInterval(function () {
        if (userTokenValue) {
          clearInterval(interval);
          resolve(userTokenValue);
        } else {
          getLogin(true);
        }
      }, 500);
    }).then(token => {
      var url = options.url
      if (url != "goods/selectDetail" && url != "goods/detail") {
        var user = wx.getStorageSync('user');
        if (!user.telephone || user.telephone == "" || user.telephone == null) {
          wx.reLaunch({
            url: '/pages/authorize/authorize',
          })

          return false;
        }
      }
      let headerData = options.header || {};
      headerData.token = token;
      options.header = headerData;
      return myRequest.commonRequest(options);
    });
  },
  gwUploadRequest: function (options) {
    return new Promise((resolve, reject) => {
      let interval = setInterval(function () {
        if (userTokenValue) {
          clearInterval(interval);
          resolve(userTokenValue);
        } else {
          getLogin(true);
        }
      }, 500);
    }).then(token => {
      let headerData = options.header || {};
      headerData.token = token;
      options.header = headerData;
      return myRequest.upoloadFileRequest(options);
    });
  },
  gwToast: function (msg) {
    showLocalToast(msg);
  },
  gwModal: function (options) {
    return showLocalModal(options)
  },
  getLogin:function(){
    getLogin();
  },
  globalData: {
    userToken: null,
     //serviceURL: "https://www.fyweather.com/SmartSale/interface/online/miniapp/"  //小程序绑定的域名地址,项目代码移动请在请求地址后添加 "/small"
   //serviceURL: "http://127.0.0.1:8080/interface/online/miniapp/"  //小程序绑定的域名地址,项目代码移动请在请求地址后添加 "/small"
  }
});

// 获取本地存储中的token
function getStorageToken() {
  isLoadingToken = true;
  wx.getStorage({
    key: user_token_key,
    success: (res) => {
      let wxxcx_token = res.data;
      if (wxxcx_token) {
        checkToken(wxxcx_token);
      } else {
        getLogin();
      }
    },
    fail: () => {
      getLogin();
    }
  });
}

// 调用登录接口
function getLogin(fromInterval) {

  if (fromInterval && isLoadingToken) {
    return;
  }
  isLoadingToken = true;
  wx.login({
    success: res => {
      myRequest.commonRequest({ type: 'POST', url: 'login', data: { code: res.code, miniapp: 4} }).then(loginResult => {
        var user = loginResult.customerUserInfo
        console.log(user)
        if (user.isUse==1){
          wx.reLaunch({
            url: '/pages/noUse/noUse',
          })
      
          return;
        }
        wx.setStorage({
          key: user_token_key,
          data: loginResult.token,
        });
        isLoadingToken = false;
       
        wx.setStorage({
          key: "user",
          data: loginResult.customerUserInfo,
        });
        wx.setStorage({
          key: "isAdmin",
          data: loginResult.isAdmin,
        });
        wx.setStorage({
          key: "isOrder",
          data: loginResult.isOrder,
        });
        userTokenValue = loginResult.token;
        isLoadingToken = false;
      }).catch(err => {
        console.error(err);
        isLoadingToken = false;
      });
    }
  });
}

function checkToken(wxxcx_token) {
  myRequest.commonRequest({ type: 'POST', url: 'check', data: { token: wxxcx_token } }).then(checkResult => {
    switch (checkResult.errorCode) {
      case -1:
        showLocalToast("参数错误");
        break;
      case -2:
        getLogin();
        break;
      case -3:
        wx.setStorage({
          key: user_token_key,
          data: checkResult.msg,
        });
        userTokenValue = checkResult.msg;
        break;
      case 0:
        userTokenValue = wxxcx_token;
        break;
    }
  }).catch(err => {
    console.error(err);
    showLocalToast("网络连接出错");
    isLoadingToken = false;
  });
}

function showLocalToast(msg) {
  wx.showToast({
    icon: 'none',
    title: msg,
    mask: true,
  });
}

function showLocalModal(options) {
  return new Promise((resolve, reject) => {
    wx.showModal({
      title: options.title,
      content: options.content,
      success: function (res) {
        resolve(res);
      },
      fail: function (err) {
        reject(err);
      }
    });
  });

}
