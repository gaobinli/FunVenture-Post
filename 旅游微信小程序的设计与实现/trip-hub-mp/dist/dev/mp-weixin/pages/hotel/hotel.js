"use strict";
const common_vendor = require("../../common/vendor.js");
const composables_index = require("../../composables/index.js");
if (!Array) {
  const _easycom_CustomHotelRecommend2 = common_vendor.resolveComponent("CustomHotelRecommend");
  _easycom_CustomHotelRecommend2();
}
const _easycom_CustomHotelRecommend = () => "../../components/CustomHotelRecommend.js";
if (!Math) {
  (PageSkeleton + _easycom_CustomHotelRecommend)();
}
const PageSkeleton = () => "./components/PageSkeleton.js";
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "hotel",
  setup(__props) {
    const isLoading = common_vendor.ref(false);
    common_vendor.onLoad(async () => {
      var _a;
      isLoading.value = true;
      (_a = hotelRef.value) == null ? void 0 : _a.getMore();
      isLoading.value = false;
    });
    const { hotelRef, onScrolltolower } = composables_index.useCustomHotelRecommendList();
    const isTriggered = common_vendor.ref(false);
    const onRefresherrefresh = async () => {
      var _a, _b;
      console.log("自定义下拉刷新触发");
      isTriggered.value = true;
      (_a = hotelRef.value) == null ? void 0 : _a.resetData();
      await Promise.all([(_b = hotelRef.value) == null ? void 0 : _b.getMore()]);
      isTriggered.value = false;
    };
    const search = async (inputSearchValue) => {
      var _a, _b;
      hotelRef.value.searchValue = inputSearchValue.detail.value;
      (_a = hotelRef.value) == null ? void 0 : _a.resetData();
      await Promise.all([(_b = hotelRef.value) == null ? void 0 : _b.getMore()]);
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.o(($event) => search($event)),
        b: isLoading.value
      }, isLoading.value ? {} : {
        c: common_vendor.sr(hotelRef, "c07d8b1c-1", {
          "k": "hotelRef"
        })
      }, {
        d: common_vendor.o(onRefresherrefresh),
        e: isTriggered.value,
        f: common_vendor.o(
          //@ts-ignore
          (...args) => common_vendor.unref(onScrolltolower) && common_vendor.unref(onScrolltolower)(...args)
        )
      });
    };
  }
});
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pages/hotel/hotel.vue"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=hotel.js.map
