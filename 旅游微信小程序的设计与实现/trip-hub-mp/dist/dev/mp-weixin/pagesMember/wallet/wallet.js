"use strict";
const common_vendor = require("../../common/vendor.js");
require("../../stores/index.js");
const services_profile = require("../../services/profile.js");
const stores_modules_member = require("../../stores/modules/member.js");
require("../../utils/http.js");
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "wallet",
  setup(__props) {
    const { safeAreaInsets } = common_vendor.index.getSystemInfoSync();
    const memberStore = stores_modules_member.useMemberStore();
    const userInfo = common_vendor.ref(null);
    const walletData = common_vendor.reactive({
      currentBalance: 0,
      rechargeAmount: "",
      paymentMethod: "wechat",
      // 'wechat', 'alipay', 'bank'
      showRechargeForm: false
    });
    const paymentMethods = [
      { value: "wechat", label: "微信支付", icon: "💬" },
      { value: "alipay", label: "支付宝", icon: "🔵" },
      { value: "bank", label: "银行卡", icon: "🏦" }
    ];
    common_vendor.onShow(async () => {
      if (memberStore.profile) {
        const res = await services_profile.getMemberProfileAPI();
        userInfo.value = res.data;
        walletData.currentBalance = userInfo.value.balance || 0;
      }
    });
    const toggleRechargeForm = () => {
      walletData.showRechargeForm = !walletData.showRechargeForm;
      if (!walletData.showRechargeForm) {
        walletData.rechargeAmount = "";
      }
    };
    const validateRechargeAmount = () => {
      if (!walletData.rechargeAmount) {
        common_vendor.index.showToast({
          icon: "error",
          title: "请输入充值金额"
        });
        return false;
      }
      const amount = parseFloat(walletData.rechargeAmount);
      if (isNaN(amount) || amount <= 0) {
        common_vendor.index.showToast({
          icon: "error",
          title: "充值金额必须大于0"
        });
        return false;
      }
      if (amount > 1e5) {
        common_vendor.index.showToast({
          icon: "error",
          title: "充值金额不能超过100000"
        });
        return false;
      }
      return true;
    };
    const handleRecharge = async () => {
      var _a;
      if (!validateRechargeAmount()) {
        return;
      }
      const rechargeAmount = parseFloat(walletData.rechargeAmount);
      ((_a = paymentMethods.find((m) => m.value === walletData.paymentMethod)) == null ? void 0 : _a.label) || "微信支付";
      common_vendor.index.showLoading({
        title: "处理中...",
        mask: true
      });
      try {
        setTimeout(async () => {
          try {
            const newBalance = walletData.currentBalance + rechargeAmount;
            const updateRes = await services_profile.patchUserBalanceAPI(userInfo.value.id, newBalance);
            walletData.currentBalance = newBalance;
            walletData.rechargeAmount = "";
            walletData.showRechargeForm = false;
            common_vendor.index.hideLoading();
            common_vendor.index.showToast({
              icon: "success",
              title: `充值成功 +¥${rechargeAmount.toFixed(2)}`
            });
            userInfo.value.balance = newBalance;
          } catch (error) {
            common_vendor.index.hideLoading();
            common_vendor.index.showToast({
              icon: "error",
              title: "充值失败，请重试"
            });
            console.error("充值失败:", error);
          }
        }, 1500);
      } catch (error) {
        common_vendor.index.hideLoading();
        common_vendor.index.showToast({
          icon: "error",
          title: "处理失败"
        });
        console.error("错误:", error);
      }
    };
    const selectPaymentMethod = (method) => {
      walletData.paymentMethod = method;
    };
    const cancelRecharge = () => {
      walletData.rechargeAmount = "";
      walletData.showRechargeForm = false;
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.t(walletData.currentBalance.toFixed(2)),
        b: !walletData.showRechargeForm
      }, !walletData.showRechargeForm ? {
        c: common_vendor.o(toggleRechargeForm)
      } : {}, {
        d: walletData.showRechargeForm
      }, walletData.showRechargeForm ? {
        e: walletData.rechargeAmount,
        f: common_vendor.o(($event) => walletData.rechargeAmount = $event.detail.value),
        g: common_vendor.f([50, 100, 500], (amount, k0, i0) => {
          return {
            a: common_vendor.t(amount),
            b: amount,
            c: common_vendor.o(($event) => walletData.rechargeAmount = amount.toString(), amount)
          };
        }),
        h: common_vendor.f(paymentMethods, (method, k0, i0) => {
          return common_vendor.e({
            a: common_vendor.t(method.icon),
            b: common_vendor.t(method.label),
            c: walletData.paymentMethod === method.value
          }, walletData.paymentMethod === method.value ? {} : {}, {
            d: method.value,
            e: common_vendor.o(($event) => selectPaymentMethod(method.value), method.value),
            f: walletData.paymentMethod === method.value ? 1 : ""
          });
        }),
        i: common_vendor.o(cancelRecharge),
        j: common_vendor.o(handleRecharge)
      } : {}, {
        k: !walletData.showRechargeForm
      }, !walletData.showRechargeForm ? {} : {}, {
        l: common_vendor.unref(safeAreaInsets).top + "px"
      });
    };
  }
});
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-f8ba33b5"], ["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pagesMember/wallet/wallet.vue"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=wallet.js.map
