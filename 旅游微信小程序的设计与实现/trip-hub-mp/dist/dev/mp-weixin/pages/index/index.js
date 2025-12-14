"use strict";
const common_vendor = require("../../common/vendor.js");
const services_home = require("../../services/home.js");
const composables_index = require("../../composables/index.js");
require("../../utils/http.js");
require("../../stores/index.js");
require("../../stores/modules/member.js");
if (!Array) {
  const _easycom_CustomSwiper2 = common_vendor.resolveComponent("CustomSwiper");
  const _easycom_CustomScenicSpotRecommend2 = common_vendor.resolveComponent("CustomScenicSpotRecommend");
  (_easycom_CustomSwiper2 + _easycom_CustomScenicSpotRecommend2)();
}
const _easycom_CustomSwiper = () => "../../components/CustomSwiper.js";
const _easycom_CustomScenicSpotRecommend = () => "../../components/CustomScenicSpotRecommend.js";
if (!Math) {
  (PageSkeleton + _easycom_CustomSwiper + CategoryPanel + _easycom_CustomScenicSpotRecommend)();
}
const CategoryPanel = () => "./components/CategoryPanel.js";
const PageSkeleton = () => "./components/PageSkeleton.js";
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "index",
  setup(__props) {
    const { safeAreaInsets } = common_vendor.index.getSystemInfoSync();
    const bannerList = common_vendor.ref([]);
    const getHomeBannerData = async () => {
      const res = await services_home.getHomeBannerAPI();
      bannerList.value = res.data;
    };
    const categoryList = common_vendor.ref([]);
    const getHomeCategoryData = async () => {
      const res = await services_home.getHomeCategoryAPI();
      categoryList.value = res.data;
    };
    const isLoading = common_vendor.ref(false);
    common_vendor.onLoad(async () => {
      var _a;
      isLoading.value = true;
      getHomeBannerData();
      getHomeCategoryData();
      await Promise.all([getHomeBannerData(), getHomeCategoryData(), (_a = scenicSpotRef.value) == null ? void 0 : _a.getMore()]);
      isLoading.value = false;
    });
    const { scenicSpotRef, onScrolltolower } = composables_index.useCustomScenicSpotRecommendList();
    const isTriggered = common_vendor.ref(false);
    const onRefresherrefresh = async () => {
      var _a, _b;
      console.log("自定义下拉刷新触发");
      isTriggered.value = true;
      await getHomeBannerData();
      await getHomeCategoryData();
      (_a = scenicSpotRef.value) == null ? void 0 : _a.resetData();
      await Promise.all([getHomeBannerData(), getHomeCategoryData(), (_b = scenicSpotRef.value) == null ? void 0 : _b.getMore()]);
      isTriggered.value = false;
    };
    const search = async (inputSearchValue) => {
      var _a, _b;
      console.log(inputSearchValue.detail.value);
      scenicSpotRef.value.searchValue = inputSearchValue.detail.value;
      (_a = scenicSpotRef.value) == null ? void 0 : _a.resetData();
      await Promise.all([(_b = scenicSpotRef.value) == null ? void 0 : _b.getMore()]);
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.o(($event) => search($event)),
        b: common_vendor.unref(safeAreaInsets).top + 10 + "px",
        c: isLoading.value
      }, isLoading.value ? {} : {
        d: common_vendor.p({
          list: bannerList.value
        }),
        e: common_vendor.p({
          list: categoryList.value
        }),
        f: common_vendor.sr(scenicSpotRef, "08ef7c2e-3", {
          "k": "scenicSpotRef"
        })
      }, {
        g: common_vendor.o(onRefresherrefresh),
        h: isTriggered.value,
        i: common_vendor.o(
          //@ts-ignore
          (...args) => common_vendor.unref(onScrolltolower) && common_vendor.unref(onScrolltolower)(...args)
        )
      });
    };
  }
});
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pages/index/index.vue"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=index.js.map
