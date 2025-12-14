"use strict";
const common_vendor = require("../../../common/vendor.js");
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "CategoryPanel",
  props: {
    list: null
  },
  setup(__props) {
    return (_ctx, _cache) => {
      return {
        a: common_vendor.f(__props.list, (item, k0, i0) => {
          return {
            a: item.url,
            b: common_vendor.t(item.name),
            c: `/pages/scenicSpotRecommend/scenicSpotRecommend?id=${item.relationId}`,
            d: item.id
          };
        })
      };
    };
  }
});
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pages/index/components/CategoryPanel.vue"]]);
wx.createComponent(Component);
//# sourceMappingURL=CategoryPanel.js.map
