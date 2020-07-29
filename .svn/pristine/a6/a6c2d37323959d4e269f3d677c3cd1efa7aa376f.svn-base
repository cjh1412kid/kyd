const myRequest = require('./utils/request.js');
const user_token_key = 'wechat_xcx_user_token';
let userTokenValue, isLoadingToken = false;

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
      let headerData = options.header || {};
      headerData.token = token;
      options.header = headerData;
      return myRequest.commonRequest(options);
    });
  },
  gwToast: function (msg) {
    showLocalToast(msg);
  },
  gwModal: function (options) {
    return showLocalModal(options)
  },
  globalData: {
    userToken: null
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
      myRequest.commonRequest({ type: 'POST', url: 'login', data: { code: res.code, miniapp: 2 } }).then(loginResult => {
        wx.setStorage({
          key: user_token_key,
          data: loginResult.token,
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