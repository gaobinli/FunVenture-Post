"use strict";
const utils_http = require("../utils/http.js");
const addCollectAPI = (data) => {
  return utils_http.http({
    method: "POST",
    url: "/collect",
    data
  });
};
const deleteMemberCollectAPI = (ids) => {
  return utils_http.http({
    method: "DELETE",
    url: `/collect/${ids}`
  });
};
const getMemberCollectPageAPI = (data) => {
  return utils_http.http({
    method: "GET",
    url: "/collect",
    data
  });
};
exports.addCollectAPI = addCollectAPI;
exports.deleteMemberCollectAPI = deleteMemberCollectAPI;
exports.getMemberCollectPageAPI = getMemberCollectPageAPI;
//# sourceMappingURL=collect.js.map
