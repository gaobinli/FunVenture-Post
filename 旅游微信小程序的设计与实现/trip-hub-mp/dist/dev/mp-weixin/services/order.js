"use strict";
const utils_http = require("../utils/http.js");
const addOrderAPI = (data) => {
  return utils_http.http({
    method: "POST",
    url: "/order",
    data
  });
};
const deleteMemberOrderAPI = (ids) => {
  return utils_http.http({
    method: "DELETE",
    url: `/order/${ids}`
  });
};
const getMemberOrderPageAPI = (data) => {
  return utils_http.http({
    method: "GET",
    url: "/order",
    data
  });
};
const patchOrderStatusAPI = (orderId, status) => {
  return utils_http.http({
    method: "PATCH",
    url: `/order/${orderId}`,
    data: {
      status
    }
  });
};
exports.addOrderAPI = addOrderAPI;
exports.deleteMemberOrderAPI = deleteMemberOrderAPI;
exports.getMemberOrderPageAPI = getMemberOrderPageAPI;
exports.patchOrderStatusAPI = patchOrderStatusAPI;
//# sourceMappingURL=order.js.map
