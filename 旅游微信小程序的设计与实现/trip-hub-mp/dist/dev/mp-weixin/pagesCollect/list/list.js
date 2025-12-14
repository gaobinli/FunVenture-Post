"use strict";
const common_vendor = require("../../common/vendor.js");
if (!Math) {
  CollectList();
}
const CollectList = () => "./components/CollectList.js";
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "list",
  props: {
    type: null
  },
  setup(__props) {
    const query = __props;
    const orderTabs = common_vendor.ref([
      { orderType: 1, title: "景点" },
      { orderType: 2, title: "美食" },
      { orderType: 3, title: "酒店" }
    ]);
    const activeIndex = common_vendor.ref(orderTabs.value.findIndex((v) => v.orderType === Number(query.type)));
    return (_ctx, _cache) => {
      return {
        a: common_vendor.f(orderTabs.value, (item, index, i0) => {
          return {
            a: common_vendor.t(item.title),
            b: item.title,
            c: common_vendor.o(($event) => activeIndex.value = index, item.title)
          };
        }),
        b: activeIndex.value * 33 + "%",
        c: common_vendor.f(orderTabs.value, (item, k0, i0) => {
          return {
            a: "5f9b7d72-0-" + i0,
            b: common_vendor.p({
              ["order-type"]: item.orderType
            }),
            c: item.title
          };
        }),
        d: activeIndex.value,
        e: common_vendor.o(($event) => activeIndex.value = $event.detail.current)
      };
    };
  }
});
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pagesCollect/list/list.vue"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=list.js.map
