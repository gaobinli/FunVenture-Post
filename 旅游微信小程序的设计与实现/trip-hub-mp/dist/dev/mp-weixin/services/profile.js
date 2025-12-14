"use strict";
const utils_http = require("../utils/http.js");
const getMemberProfileAPI = () => {
  return utils_http.http({
    method: "GET",
    url: "/user/getUserInfo"
  });
};
const patchMemberProfileAPI = (data) => {
  return utils_http.http({
    method: "PATCH",
    url: "/user/" + data.id,
    data
  });
};
const postMemberProfileAPI = (data) => {
  return utils_http.http({
    method: "POST",
    url: "/user/register",
    data
  });
};
const patchUserBalanceAPI = (userId, balance) => {
  return utils_http.http({
    method: "PATCH",
    url: "/user/" + userId,
    data: {
      balance
    }
  });
};
exports.getMemberProfileAPI = getMemberProfileAPI;
exports.patchMemberProfileAPI = patchMemberProfileAPI;
exports.patchUserBalanceAPI = patchUserBalanceAPI;
exports.postMemberProfileAPI = postMemberProfileAPI;
//# sourceMappingURL=profile.js.map
