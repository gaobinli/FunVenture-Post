"use strict";
const common_vendor = require("../../common/vendor.js");
const services_login = require("../../services/login.js");
require("../../stores/index.js");
const stores_modules_member = require("../../stores/modules/member.js");
require("../../utils/http.js");
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "login",
  setup(__props) {
    const form = common_vendor.ref({
      username: "13594677777",
      password: "123456"
    });
    const onSubmit = async () => {
      await checkedAgreePrivacy();
      const res = await services_login.postNormalLoginAPI(form.value);
      const loginResultReactive = common_vendor.reactive({
        token: res.data
      });
      loginSuccess(loginResultReactive);
    };
    const isAgreePrivacy = common_vendor.ref(true);
    const isAgreePrivacyShakeY = common_vendor.ref(false);
    const checkedAgreePrivacy = async () => {
      if (!isAgreePrivacy.value) {
        common_vendor.index.showToast({
          icon: "none",
          title: "请先阅读并勾选协议"
        });
        isAgreePrivacyShakeY.value = true;
        setTimeout(() => {
          isAgreePrivacyShakeY.value = false;
        }, 500);
        return Promise.reject(new Error("请先阅读并勾选协议"));
      }
    };
    const loginSuccess = (profile) => {
      const memberStore = stores_modules_member.useMemberStore();
      memberStore.setProfile(profile);
      common_vendor.index.showToast({ icon: "success", title: "登录成功" });
      setTimeout(() => {
        common_vendor.index.switchTab({ url: "/pages/my/my" });
      }, 1e3);
    };
    return (_ctx, _cache) => {
      return {
        a: form.value.username,
        b: common_vendor.o(($event) => form.value.username = $event.detail.value),
        c: form.value.password,
        d: common_vendor.o(($event) => form.value.password = $event.detail.value),
        e: common_vendor.o(onSubmit),
        f: `/pages/register/register`,
        g: isAgreePrivacy.value,
        h: common_vendor.o(($event) => isAgreePrivacy.value = !isAgreePrivacy.value),
        i: common_vendor.o(
          //@ts-ignore
          (...args) => _ctx.onOpenPrivacyContract && _ctx.onOpenPrivacyContract(...args)
        ),
        j: isAgreePrivacyShakeY.value ? 1 : ""
      };
    };
  }
});
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pages/login/login.vue"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=login.js.map
