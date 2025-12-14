"use strict";
const common_vendor = require("../common/vendor.js");
require("../stores/index.js");
const stores_modules_member = require("../stores/modules/member.js");
const baseURL = "http://127.0.0.1:9000";
const httpInterceptor = {
  // 拦截前触发
  invoke(options) {
    var _a;
    if (!options.url.startsWith("http")) {
      options.url = baseURL + options.url;
    }
    options.timeout = 1e4;
    options.header = {
      ...options.header,
      "source-client": "miniapp"
    };
    const memberStore = stores_modules_member.useMemberStore();
    const token = (_a = memberStore.profile) == null ? void 0 : _a.token;
    if (token) {
      options.header.token = "Bearer " + token;
    }
  }
};
common_vendor.index.addInterceptor("request", httpInterceptor);
common_vendor.index.addInterceptor("uploadFile", httpInterceptor);
const http = (options) => {
  return new Promise((resolve, reject) => {
    common_vendor.index.request({
      ...options,
      // 2、请求成功
      success(res) {
        const response = res.data;
        if (response.code === "000000") {
          resolve(response);
        } else if (response.code === "401") {
          const memberStore = stores_modules_member.useMemberStore();
          memberStore.clearProfile();
          common_vendor.index.navigateTo({ url: "/pages/login/login" });
          reject(response);
        } else {
          reject(response);
        }
      },
      // 响应失败
      fail(err) {
        common_vendor.index.showToast({
          icon: "none",
          title: "网络错误，换个网络试试"
        });
        reject(err);
      }
    });
  });
};
exports.http = http;
//# sourceMappingURL=http.js.map
