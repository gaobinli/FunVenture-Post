"use strict";
const utils_http = require("../utils/http.js");
const getHomeBannerAPI = () => {
  return utils_http.http({
    method: "GET",
    url: "/carousel/list"
  });
};
const getHomeCategoryAPI = () => {
  return utils_http.http({
    method: "GET",
    url: "/icon/list"
  });
};
const getScenicSpotAPI = (data) => {
  return utils_http.http({
    method: "GET",
    url: "/scenicSpot",
    data
  });
};
exports.getHomeBannerAPI = getHomeBannerAPI;
exports.getHomeCategoryAPI = getHomeCategoryAPI;
exports.getScenicSpotAPI = getScenicSpotAPI;
//# sourceMappingURL=home.js.map
