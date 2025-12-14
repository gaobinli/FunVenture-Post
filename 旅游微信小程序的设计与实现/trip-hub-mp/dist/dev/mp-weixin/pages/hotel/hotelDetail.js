"use strict";
const common_vendor = require("../../common/vendor.js");
const services_hotel = require("../../services/hotel.js");
const services_collect = require("../../services/collect.js");
const services_order = require("../../services/order.js");
const services_profile = require("../../services/profile.js");
require("../../stores/index.js");
const stores_modules_member = require("../../stores/modules/member.js");
require("../../utils/http.js");
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "hotelDetail",
  props: {
    id: null
  },
  setup(__props) {
    const query = __props;
    const { safeAreaInsets } = common_vendor.index.getSystemInfoSync();
    const memberStore = stores_modules_member.useMemberStore();
    const hotels = common_vendor.ref([]);
    const hotel = common_vendor.ref();
    const userInfo = common_vendor.ref(null);
    const getFoodByIdData = async () => {
      const res = await services_hotel.getHotelByIdAPI(query.id);
      hotel.value = res.data;
      if (res.data && res.data.content) {
        hotel.value.content = hotel.value.content.replace(/\<img/gi, '<img style="max-width:100%;height:auto" ');
      }
      hotels.value.push(hotel.value);
    };
    const getUserInfo = async () => {
      if (memberStore.profile) {
        try {
          const res = await services_profile.getMemberProfileAPI();
          userInfo.value = res.data;
        } catch (error) {
          console.error("获取用户信息失败:", error);
        }
      }
    };
    common_vendor.onLoad(() => {
      getFoodByIdData();
      getUserInfo();
    });
    common_vendor.onShow(() => {
      getUserInfo();
    });
    const currentIndex = common_vendor.ref(0);
    const onchange = (ev) => {
      currentIndex.value = ev.detail.current;
    };
    const onTapImage = (url) => {
      common_vendor.index.previewImage({
        current: url,
        urls: [url]
      });
    };
    const buy = async (id, price) => {
      if (!memberStore.profile) {
        common_vendor.index.showToast({ icon: "error", title: "请先登录" });
        return;
      }
      if (!userInfo.value) {
        common_vendor.index.showToast({ icon: "error", title: "获取用户信息失败，请重试" });
        return;
      }
      const currentBalance = userInfo.value.balance || 0;
      if (currentBalance < price) {
        common_vendor.index.showModal({
          title: "余额不足",
          content: `当前余额 ¥${currentBalance.toFixed(2)}，酒店价格 ¥${price}，需要充值 ¥${(price - currentBalance).toFixed(2)}`,
          confirmText: "去充值",
          cancelText: "取消",
          success: (res) => {
            if (res.confirm) {
              common_vendor.index.navigateTo({ url: "/pagesMember/wallet/wallet" });
            }
          }
        });
        return;
      }
      common_vendor.index.showModal({
        content: `需要支付 ¥${price}，是否预订酒店？`,
        success: async (res) => {
          if (res.confirm) {
            try {
              common_vendor.index.showLoading({ title: "预订中...", mask: true });
              await services_order.addOrderAPI({ relationId: id, type: 3 });
              const newBalance = currentBalance - price;
              await services_profile.patchUserBalanceAPI(userInfo.value.id, newBalance);
              userInfo.value.balance = newBalance;
              common_vendor.index.hideLoading();
              common_vendor.index.showToast({
                icon: "success",
                title: "预订成功",
                duration: 1500
              });
              setTimeout(() => {
                common_vendor.index.navigateBack();
              }, 1500);
            } catch (error) {
              common_vendor.index.hideLoading();
              let errorMessage = "预订失败，请重试";
              if (error == null ? void 0 : error.msg) {
                errorMessage = error.msg;
              }
              common_vendor.index.showToast({
                icon: "error",
                title: errorMessage,
                duration: 2e3
              });
              console.error("预订失败:", {
                code: error == null ? void 0 : error.code,
                message: errorMessage,
                fullError: error
              });
            }
          }
        }
      });
    };
    const coolect = (id, name) => {
      common_vendor.index.showModal({
        content: "是否要收藏酒店 " + name + " ?",
        success: (res) => {
          if (res.confirm) {
            console.log("确定了", id);
            const res2 = services_collect.addCollectAPI({ relationId: id, type: 3 });
            console.log("res", res2);
            if (memberStore.profile) {
              common_vendor.index.showToast({ icon: "success", title: "收藏成功" });
            }
          }
        }
      });
    };
    return (_ctx, _cache) => {
      var _a, _b, _c, _d, _e, _f;
      return {
        a: common_vendor.f(hotels.value, (item, k0, i0) => {
          return {
            a: common_vendor.o(($event) => onTapImage(item.url), item),
            b: item.url,
            c: item
          };
        }),
        b: common_vendor.o(onchange),
        c: common_vendor.t(currentIndex.value + 1),
        d: common_vendor.t((_a = hotel.value) == null ? void 0 : _a.name),
        e: common_vendor.t((_b = hotel.value) == null ? void 0 : _b.shortRecommend),
        f: common_vendor.t((_c = hotel.value) == null ? void 0 : _c.price),
        g: common_vendor.t((_d = hotel.value) == null ? void 0 : _d.address),
        h: (_e = hotel.value) == null ? void 0 : _e.content,
        i: common_vendor.o(($event) => buy(hotel.value.id, hotel.value.price)),
        j: common_vendor.o(($event) => coolect(hotel.value.id, hotel.value.name)),
        k: ((_f = common_vendor.unref(safeAreaInsets)) == null ? void 0 : _f.bottom) + "px"
      };
    };
  }
});
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pages/hotel/hotelDetail.vue"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=hotelDetail.js.map
