"use strict";
const utils_http = require("../utils/http.js");
const getFoodAPI = (data) => {
  return utils_http.http({
    method: "GET",
    url: "/food",
    data
  });
};
const getFoodByIdAPI = (id) => {
  return utils_http.http({
    method: "GET",
    url: "/food/" + id
  });
};
exports.getFoodAPI = getFoodAPI;
exports.getFoodByIdAPI = getFoodByIdAPI;
//# sourceMappingURL=food.js.map
