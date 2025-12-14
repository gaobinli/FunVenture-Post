"use strict";
const common_vendor = require("../common/vendor.js");
const services_home = require("../services/home.js");
require("../utils/http.js");
require("../stores/index.js");
require("../stores/modules/member.js");
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "CustomScenicSpotRecommend",
  setup(__props, { expose }) {
    const pageParams = {
      currentPage: 1,
      pageSize: 5
    };
    const scenicSpotList = common_vendor.ref([]);
    const finish = common_vendor.ref(false);
    const getHomeScenicSpotRecommendData = async () => {
      if (finish.value === true) {
        return common_vendor.index.showToast({ icon: "none", title: "没有更多数据~" });
      }
      let params = {
        "qp-name-like": searchValue.value,
        ...pageParams
      };
      const res = await services_home.getScenicSpotAPI(params);
      scenicSpotList.value.push(...res.data.list);
      if (pageParams.currentPage < res.data.totalPage) {
        pageParams.currentPage++;
      } else {
        finish.value = true;
      }
    };
    const searchValue = common_vendor.ref("");
    const resetData = () => {
      pageParams.currentPage = 1;
      scenicSpotList.value = [];
      finish.value = false;
    };
    common_vendor.onMounted(() => {
      getHomeScenicSpotRecommendData();
    });
    expose({
      searchValue,
      resetData,
      getMore: getHomeScenicSpotRecommendData
    });
    return (_ctx, _cache) => {
      return {
        a: common_vendor.f(scenicSpotList.value, (item, k0, i0) => {
          return {
            a: item.coverUrl,
            b: common_vendor.t(item.name),
            c: common_vendor.t(item.shortRecommend),
            d: item.id,
            e: `/pages/scenicSpotRecommend/scenicSpotRecommend?id=${item.id}`
          };
        }),
        b: common_vendor.t(finish.value ? "没有更多数据~" : "正在加载...")
      };
    };
  }
});
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/components/CustomScenicSpotRecommend.vue"]]);
wx.createComponent(Component);
//# sourceMappingURL=CustomScenicSpotRecommend.js.map
