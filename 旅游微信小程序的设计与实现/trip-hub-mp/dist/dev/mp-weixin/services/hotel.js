"use strict";
const utils_http = require("../utils/http.js");
const getHotelAPI = (data) => {
  return utils_http.http({
    method: "GET",
    url: "/hotel",
    data
  });
};
const getHotelByIdAPI = (id) => {
  return utils_http.http({
    method: "GET",
    url: "/hotel/" + id
  });
};
exports.getHotelAPI = getHotelAPI;
exports.getHotelByIdAPI = getHotelByIdAPI;
//# sourceMappingURL=hotel.js.map
