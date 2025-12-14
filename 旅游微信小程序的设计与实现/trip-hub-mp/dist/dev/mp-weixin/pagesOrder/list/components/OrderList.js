"use strict";
const common_vendor = require("../../../common/vendor.js");
const services_order = require("../../../services/order.js");
require("../../../stores/index.js");
const services_profile = require("../../../services/profile.js");
const stores_modules_member = require("../../../stores/modules/member.js");
require("../../../utils/http.js");
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "OrderList",
  props: {
    orderType: null
  },
  setup(__props) {
    const props = __props;
    const { safeAreaInsets } = common_vendor.index.getSystemInfoSync();
    stores_modules_member.useMemberStore();
    const userInfo = common_vendor.ref();
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
      const res = await services_order.getMemberOrderPageAPI(queryParams);
      isLoading.value = false;
      orderList.value.push(...res.data.list);
      if (queryParams.currentPage < res.data.totalPage) {
        queryParams.currentPage++;
      } else {
        isFinish.value = true;
      }
    };
    common_vendor.onMounted(async () => {
      if (stores_modules_member.useMemberStore() && stores_modules_member.useMemberStore().profile) {
        console.log("=======start.................==");
        const res = await services_profile.getMemberProfileAPI();
        console.log("=======res==", res);
        userInfo.value = res.data;
        console.log("=======userInfo.value==", userInfo.value);
        getMemberOrderData();
      }
    });
    const statusMap = {
      1: "待发货",
      2: "待收货",
      3: "已收货",
      4: "退货中",
      5: "已退货"
    };
    const getStatusText = (status) => {
      return statusMap[status] || "未知状态";
    };
    const getStatusColor = (status) => {
      const colorMap = {
        1: "#ff9240",
        // 待发货 - 橙色
        2: "#ff6666",
        // 待收货 - 红色
        3: "#28bb9c",
        // 已收货 - 绿色
        4: "#ffa868",
        // 退货中 - 浅橙色
        5: "#999999"
        // 已退货 - 灰色
      };
      return colorMap[status] || "#999";
    };
    const onConfirmReceipt = (orderId) => {
      common_vendor.index.showModal({
        content: "确认已收到货物吗？",
        confirmText: "确认收货",
        cancelText: "取消",
        success: async (res) => {
          if (res.confirm) {
            try {
              common_vendor.index.showLoading({ title: "处理中...", mask: true });
              await services_order.patchOrderStatusAPI(orderId, 3);
              common_vendor.index.hideLoading();
              common_vendor.index.showToast({ icon: "success", title: "确认收货成功" });
              const order = orderList.value.find((v) => v.id === orderId);
              if (order) {
                order.status = 3;
              }
            } catch (error) {
              common_vendor.index.hideLoading();
              common_vendor.index.showToast({ icon: "error", title: "确认收货失败，请重试" });
              console.error("确认收货失败:", error);
            }
          }
        }
      });
    };
    const onApplyReturn = (orderId) => {
      common_vendor.index.showModal({
        content: "确认要申请退货吗？",
        confirmText: "确认退货",
        cancelText: "取消",
        success: async (res) => {
          if (res.confirm) {
            try {
              common_vendor.index.showLoading({ title: "处理中...", mask: true });
              await services_order.patchOrderStatusAPI(orderId, 4);
              common_vendor.index.hideLoading();
              common_vendor.index.showToast({ icon: "success", title: "退货申请成功" });
              const order = orderList.value.find((v) => v.id === orderId);
              if (order) {
                order.status = 4;
              }
            } catch (error) {
              common_vendor.index.hideLoading();
              common_vendor.index.showToast({ icon: "error", title: "申请退货失败，请重试" });
              console.error("申请退货失败:", error);
            }
          }
        }
      });
    };
    const onOrderDelete = (id) => {
      common_vendor.index.showModal({
        content: "你确定要删除该订单？",
        success: async (res) => {
          if (res.confirm) {
            console.log("删除,", id);
            await services_order.deleteMemberOrderAPI(id);
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
      queryParams.page = 1;
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
          return common_vendor.e({
            a: common_vendor.t(order.createTime.replace("T", " ")),
            b: common_vendor.t(getStatusText(order.status)),
            c: getStatusColor(order.status),
            d: common_vendor.o(($event) => onOrderDelete(order.id), order.id),
            e: order.coverUrl,
            f: common_vendor.t(order.shortRecommend),
            g: `/pages/scenicSpotRecommend/scenicSpotRecommend?id=${order.relationId}`,
            h: common_vendor.t(order.price),
            i: order.status === 2 || order.status === 3
          }, order.status === 2 || order.status === 3 ? common_vendor.e({
            j: order.status === 2
          }, order.status === 2 ? {
            k: common_vendor.o(($event) => onConfirmReceipt(order.id), order.id)
          } : {}, {
            l: order.status === 3
          }, order.status === 3 ? {
            m: common_vendor.o(($event) => onApplyReturn(order.id), order.id)
          } : {}) : {}, {
            n: order.id
          });
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
          return common_vendor.e({
            a: common_vendor.t(order.createTime.replace("T", " ")),
            b: common_vendor.t(getStatusText(order.status)),
            c: getStatusColor(order.status),
            d: common_vendor.o(($event) => onOrderDelete(order.id), order.id),
            e: order.coverUrl,
            f: common_vendor.t(order.shortRecommend),
            g: `/pages/food/foodDetail?id=${order.relationId}`,
            h: common_vendor.t(order.price),
            i: order.status === 2 || order.status === 3
          }, order.status === 2 || order.status === 3 ? common_vendor.e({
            j: order.status === 2
          }, order.status === 2 ? {
            k: common_vendor.o(($event) => onConfirmReceipt(order.id), order.id)
          } : {}, {
            l: order.status === 3
          }, order.status === 3 ? {
            m: common_vendor.o(($event) => onApplyReturn(order.id), order.id)
          } : {}) : {}, {
            n: order.id
          });
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
          return common_vendor.e({
            a: common_vendor.t(order.createTime.replace("T", " ")),
            b: common_vendor.t(getStatusText(order.status)),
            c: getStatusColor(order.status),
            d: common_vendor.o(($event) => onOrderDelete(order.id), order.id),
            e: order.coverUrl,
            f: common_vendor.t(order.shortRecommend),
            g: `/pages/hotel/hotelDetail?id=${order.relationId}`,
            h: common_vendor.t(order.price),
            i: order.status === 2 || order.status === 3
          }, order.status === 2 || order.status === 3 ? common_vendor.e({
            j: order.status === 2
          }, order.status === 2 ? {
            k: common_vendor.o(($event) => onConfirmReceipt(order.id), order.id)
          } : {}, {
            l: order.status === 3
          }, order.status === 3 ? {
            m: common_vendor.o(($event) => onApplyReturn(order.id), order.id)
          } : {}) : {}, {
            n: order.id
          });
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
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pagesOrder/list/components/OrderList.vue"]]);
wx.createComponent(Component);
//# sourceMappingURL=OrderList.js.map
