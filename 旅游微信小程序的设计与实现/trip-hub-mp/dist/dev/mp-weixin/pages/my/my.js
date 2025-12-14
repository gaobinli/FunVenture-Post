"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
require("../../stores/index.js");
const services_profile = require("../../services/profile.js");
const composables_index = require("../../composables/index.js");
const stores_modules_member = require("../../stores/modules/member.js");
require("../../utils/http.js");
if (!Array) {
  const _easycom_CustomScenicSpotRecommend2 = common_vendor.resolveComponent("CustomScenicSpotRecommend");
  _easycom_CustomScenicSpotRecommend2();
}
const _easycom_CustomScenicSpotRecommend = () => "../../components/CustomScenicSpotRecommend.js";
if (!Math) {
  _easycom_CustomScenicSpotRecommend();
}
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "my",
  setup(__props) {
    const { safeAreaInsets } = common_vendor.index.getSystemInfoSync();
    const memberStore = stores_modules_member.useMemberStore();
    const userInfo = common_vendor.ref();
    const { scenicSpotRef, onScrolltolower } = composables_index.useCustomScenicSpotRecommendList();
    common_vendor.onShow(async () => {
      if (stores_modules_member.useMemberStore() && stores_modules_member.useMemberStore().profile) {
        const res = await services_profile.getMemberProfileAPI();
        userInfo.value = res.data;
      }
    });
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.unref(memberStore).profile
      }, common_vendor.unref(memberStore).profile ? common_vendor.e({
        b: userInfo.value.avatar
      }, userInfo.value.avatar ? {
        c: userInfo.value.avatar
      } : {
        d: common_assets._imports_0
      }, {
        e: common_vendor.t(userInfo.value.nickName || userInfo.value.phone)
      }) : {
        f: common_assets._imports_0
      }, {
        g: common_vendor.unref(safeAreaInsets).top + "px",
        h: common_vendor.unref(memberStore).profile
      }, common_vendor.unref(memberStore).profile ? {
        i: common_vendor.t(userInfo.value.balance || "0.00")
      } : {}, {
        j: common_vendor.sr(scenicSpotRef, "59d6d8f0-0", {
          "k": "scenicSpotRef"
        }),
        k: common_vendor.o(
          //@ts-ignore
          (...args) => common_vendor.unref(onScrolltolower) && common_vendor.unref(onScrolltolower)(...args)
        )
      });
    };
  }
});
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pages/my/my.vue"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=my.js.map
