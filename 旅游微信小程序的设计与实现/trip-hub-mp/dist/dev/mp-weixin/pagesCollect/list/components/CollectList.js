"use strict";
const common_vendor = require("../../../common/vendor.js");
const services_collect = require("../../../services/collect.js");
require("../../../utils/http.js");
require("../../../stores/index.js");
require("../../../stores/modules/member.js");
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "CollectList",
  props: {
    orderType: null
  },
  setup(__props) {
    const props = __props;
    const { safeAreaInsets } = common_vendor.index.getSystemInfoSync();
    const queryParams = {
      currentPage: 1,
      pageSize: 5,
      "qp-type-eq": props.orderType,
      dataLimit: 1
    };
    const orderList = common_vendor.ref([]);
    const isLoading = common_vendor.ref(false);
    const getMemberOrderData = async () => {
      if (isLoading.value)
        return;
      if (isFinish.value === true) {
        return common_vendor.index.showToast({ icon: "none", title: "没有更多数据~" });
      }
      isLoading.value = true;
      const res = await services_collect.getMemberCollectPageAPI(queryParams);
      isLoading.value = false;
      orderList.value.push(...res.data.list);
      if (queryParams.currentPage < res.data.totalPage) {
        queryParams.currentPage++;
      } else {
        isFinish.value = true;
      }
    };
    common_vendor.onMounted(() => {
      getMemberOrderData();
    });
    const onOrderDelete = (id) => {
      common_vendor.index.showModal({
        content: "你确定要删除该收藏？",
        success: async (res) => {
          if (res.confirm) {
            console.log("删除,", id);
            await services_collect.deleteMemberCollectAPI(id);
            const index = orderList.value.findIndex((v) => v.id === id);
            orderList.value.splice(index, 1);
          }
        }
      });
    };
    const isFinish = common_vendor.ref(false);
    const isTriggered = common_vendor.ref(false);
    const onRefresherrefresh = async () => {
      isTriggered.value = true;
      queryParams.currentPage = 1;
      orderList.value = [];
      isFinish.value = false;
      await getMemberOrderData();
      isTriggered.value = false;
    };
    return (_ctx, _cache) => {
      var _a, _b, _c;
      return common_vendor.e({
        a: props.orderType == 1
      }, props.orderType == 1 ? {
        b: common_vendor.f(orderList.value, (order, k0, i0) => {
          return {
            a: common_vendor.t(order.createTime.replace("T", " ")),
            b: common_vendor.t(order.name),
            c: common_vendor.o(($event) => onOrderDelete(order.id), order.id),
            d: order.coverUrl,
            e: common_vendor.t(order.shortRecommend),
            f: `/pages/scenicSpotRecommend/scenicSpotRecommend?id=${order.relationId}`,
            g: order.id
          };
        }),
        c: common_vendor.t(isFinish.value ? "没有更多数据~" : "正在加载..."),
        d: ((_a = common_vendor.unref(safeAreaInsets)) == null ? void 0 : _a.bottom) + "px",
        e: isTriggered.value,
        f: common_vendor.o(onRefresherrefresh),
        g: common_vendor.o(getMemberOrderData)
      } : {}, {
        h: props.orderType == 2
      }, props.orderType == 2 ? {
        i: common_vendor.f(orderList.value, (order, k0, i0) => {
          return {
            a: common_vendor.t(order.createTime.replace("T", " ")),
            b: common_vendor.t(order.name),
            c: common_vendor.o(($event) => onOrderDelete(order.id), order.id),
            d: order.coverUrl,
            e: common_vendor.t(order.shortRecommend),
            f: `/pages/food/foodDetail?id=${order.relationId}`,
            g: order.id
          };
        }),
        j: common_vendor.t(isFinish.value ? "没有更多数据~" : "正在加载..."),
        k: ((_b = common_vendor.unref(safeAreaInsets)) == null ? void 0 : _b.bottom) + "px",
        l: isTriggered.value,
        m: common_vendor.o(onRefresherrefresh),
        n: common_vendor.o(getMemberOrderData)
      } : {}, {
        o: props.orderType == 3
      }, props.orderType == 3 ? {
        p: common_vendor.f(orderList.value, (order, k0, i0) => {
          return {
            a: common_vendor.t(order.createTime.replace("T", " ")),
            b: common_vendor.t(order.name),
            c: common_vendor.o(($event) => onOrderDelete(order.id), order.id),
            d: order.coverUrl,
            e: common_vendor.t(order.shortRecommend),
            f: `/pages/hotel/hotelDetail?id=${order.relationId}`,
            g: order.id
          };
        }),
        q: common_vendor.t(isFinish.value ? "没有更多数据~" : "正在加载..."),
        r: ((_c = common_vendor.unref(safeAreaInsets)) == null ? void 0 : _c.bottom) + "px",
        s: isTriggered.value,
        t: common_vendor.o(onRefresherrefresh),
        v: common_vendor.o(getMemberOrderData)
      } : {});
    };
  }
});
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pagesCollect/list/components/CollectList.vue"]]);
wx.createComponent(Component);
//# sourceMappingURL=CollectList.js.map
