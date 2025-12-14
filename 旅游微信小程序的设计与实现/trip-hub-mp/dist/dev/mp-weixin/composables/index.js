"use strict";
const common_vendor = require("../common/vendor.js");
const useCustomHotelRecommendList = () => {
  const hotelRef = common_vendor.ref();
  const onScrolltolower = () => {
    var _a;
    (_a = hotelRef.value) == null ? void 0 : _a.getMore();
  };
  return {
    hotelRef,
    onScrolltolower
  };
};
const useCustomFoodRecommendList = () => {
  const foodRef = common_vendor.ref();
  const onScrolltolower = () => {
    var _a;
    (_a = foodRef.value) == null ? void 0 : _a.getMore();
  };
  return {
    foodRef,
    onScrolltolower
  };
};
const useCustomScenicSpotRecommendList = () => {
  const scenicSpotRef = common_vendor.ref();
  const onScrolltolower = () => {
    var _a;
    (_a = scenicSpotRef.value) == null ? void 0 : _a.getMore();
  };
  return {
    scenicSpotRef,
    onScrolltolower
  };
};
exports.useCustomFoodRecommendList = useCustomFoodRecommendList;
exports.useCustomHotelRecommendList = useCustomHotelRecommendList;
exports.useCustomScenicSpotRecommendList = useCustomScenicSpotRecommendList;
//# sourceMappingURL=index.js.map
