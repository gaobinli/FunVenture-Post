"use strict";
const utils_http = require("../utils/http.js");
const getScenicSpotByIdAPI = (id) => {
  return utils_http.http({
    method: "GET",
    url: "/scenicSpot/" + id
  });
};
exports.getScenicSpotByIdAPI = getScenicSpotByIdAPI;
//# sourceMappingURL=scenicSpot.js.map
