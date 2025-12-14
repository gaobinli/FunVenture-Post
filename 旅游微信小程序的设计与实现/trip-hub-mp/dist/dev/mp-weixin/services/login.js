"use strict";
const utils_http = require("../utils/http.js");
const postNormalLoginAPI = (data) => {
  return utils_http.http({
    method: "POST",
    url: "/user/login",
    data
  });
};
exports.postNormalLoginAPI = postNormalLoginAPI;
//# sourceMappingURL=login.js.map
