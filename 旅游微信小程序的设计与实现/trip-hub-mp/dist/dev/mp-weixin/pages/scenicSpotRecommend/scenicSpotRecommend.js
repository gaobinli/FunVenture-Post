"use strict";
const common_vendor = require("../../common/vendor.js");
const services_scenicSpot = require("../../services/scenicSpot.js");
const services_order = require("../../services/order.js");
const services_collect = require("../../services/collect.js");
const services_profile = require("../../services/profile.js");
require("../../stores/index.js");
const stores_modules_member = require("../../stores/modules/member.js");
require("../../utils/http.js");
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "scenicSpotRecommend",
  props: {
    id: null
  },
  setup(__props) {
    const query = __props;
    const { safeAreaInsets } = common_vendor.index.getSystemInfoSync();
    const memberStore = stores_modules_member.useMemberStore();
    const scenicSpots = common_vendor.ref([]);
    const scenicSpot = common_vendor.ref();
    const imgs = common_vendor.ref([]);
    const userInfo = common_vendor.ref(null);
    const getScenicSpotByIdData = async () => {
      const res = await services_scenicSpot.getScenicSpotByIdAPI(query.id);
      scenicSpot.value = res.data;
      if (res.data && res.data.introduce) {
        scenicSpot.value.introduce = scenicSpot.value.introduce.replace(/\<img/gi, '<img style="max-width:100%;height:auto" ');
      }
      imgs.value.push(res.data.coverUrl);
      imgs.value.push(res.data.url);
      scenicSpots.value.push(scenicSpot.value);
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
      getScenicSpotByIdData();
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
        urls: imgs.value
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
          content: `当前余额 ¥${currentBalance.toFixed(2)}，门票价格 ¥${price}，需要充值 ¥${(price - currentBalance).toFixed(2)}`,
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
        content: `需要支付 ¥${price}，是否购买门票？`,
        success: async (res) => {
          if (res.confirm) {
            try {
              common_vendor.index.showLoading({ title: "购买中...", mask: true });
              await services_order.addOrderAPI({ relationId: id, type: 1 });
              const newBalance = currentBalance - price;
              await services_profile.patchUserBalanceAPI(userInfo.value.id, newBalance);
              userInfo.value.balance = newBalance;
              common_vendor.index.hideLoading();
              common_vendor.index.showToast({
                icon: "success",
                title: "购买成功",
                duration: 1500
              });
              setTimeout(() => {
                common_vendor.index.navigateBack();
              }, 1500);
            } catch (error) {
              common_vendor.index.hideLoading();
              let errorMessage = "购买失败，请重试";
              if (error == null ? void 0 : error.msg) {
                errorMessage = error.msg;
              }
              common_vendor.index.showToast({
                icon: "error",
                title: errorMessage,
                duration: 2e3
              });
              console.error("购买失败:", {
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
        content: "是否要收藏景点 " + name + " ?",
        success: (res) => {
          if (res.confirm) {
            console.log("确定了", id);
            const res2 = services_collect.addCollectAPI({ relationId: id, type: 1 });
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
        a: common_vendor.f(imgs.value, (item, k0, i0) => {
          return {
            a: common_vendor.o(($event) => onTapImage(item), item),
            b: item,
            c: item
          };
        }),
        b: common_vendor.o(onchange),
        c: common_vendor.t(currentIndex.value + 1),
        d: common_vendor.t(imgs.value.length),
        e: common_vendor.t((_a = scenicSpot.value) == null ? void 0 : _a.name),
        f: common_vendor.t((_b = scenicSpot.value) == null ? void 0 : _b.shortRecommend),
        g: common_vendor.t((_c = scenicSpot.value) == null ? void 0 : _c.price),
        h: common_vendor.t((_d = scenicSpot.value) == null ? void 0 : _d.address),
        i: (_e = scenicSpot.value) == null ? void 0 : _e.introduce,
        j: common_vendor.o(($event) => buy(scenicSpot.value.id, scenicSpot.value.price)),
        k: common_vendor.o(($event) => coolect(scenicSpot.value.id, scenicSpot.value.name)),
        l: ((_f = common_vendor.unref(safeAreaInsets)) == null ? void 0 : _f.bottom) + "px"
      };
    };
  }
});
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pages/scenicSpotRecommend/scenicSpotRecommend.vue"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=scenicSpotRecommend.js.map
