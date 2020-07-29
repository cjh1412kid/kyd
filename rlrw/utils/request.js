//const baseUrl = 'http://127.0.0.1:8080/interface/online/miniapp/';
//const baseUrl = 'http://222.93.105.42:9191/interface/online/miniapp/';
const baseUrl = 'https://www.fyweather.com/SmartSale/interface/online/miniapp/'

function commonRequest(options) {
  return new Promise((resolve, reject) => {
    let headerData = options.header || {};
    let requestType = options.type || 'GET';
    requestType = requestType.toUpperCase();
    if (requestType == 'POST') {
      headerData['content-type'] = 'application/x-www-form-urlencoded';
    }
    wx.request({
      url: baseUrl + options.url,
      method: requestType,
      data: options.data,
      header: headerData,
      success: function(res) {
        if (res.statusCode === 200) {
          if (res.data.code === 0) {
            resolve(res.data);
          } else {
            reject(res.data);
          }
        } else {
          reject(res.data);
        }
      },
      fail: function(err) {
        console.error(err);
        if (err instanceof Error) {
          err = err.message;
        }
        reject(err);
      }
    });
  });
}

function upoloadFileRequest() {

}

module.exports = {
  commonRequest: commonRequest,
  upoloadFileRequest: upoloadFileRequest
}