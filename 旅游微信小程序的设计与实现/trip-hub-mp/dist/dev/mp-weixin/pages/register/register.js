"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
const services_profile = require("../../services/profile.js");
require("../../stores/index.js");
const stores_modules_member = require("../../stores/modules/member.js");
require("../../utils/http.js");
if (!Array) {
  const _component_up_text = common_vendor.resolveComponent("up-text");
  _component_up_text();
}
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "register",
  setup(__props) {
    const { safeAreaInsets } = common_vendor.index.getSystemInfoSync();
    stores_modules_member.useMemberStore();
    const userInfo = common_vendor.reactive({
      avatar: "",
      phone: "",
      nickName: "",
      password: "",
      sex: 1,
      enable: 1
    });
    common_vendor.onLoad(() => {
    });
    const onAvatarChange = () => {
      common_vendor.index.chooseMedia({
        // 文件个数
        count: 1,
        // 文件类型
        mediaType: ["image"],
        success: (res) => {
          const { tempFilePath } = res.tempFiles[0];
          common_vendor.index.uploadFile({
            url: "/upload",
            name: "file",
            filePath: tempFilePath,
            success: (res2) => {
              if (res2.statusCode === 200) {
                const avatarUploadUrl = JSON.parse(res2.data).data;
                userInfo.avatar = avatarUploadUrl;
                common_vendor.index.showToast({ icon: "success", title: "上传成功" });
              } else {
                common_vendor.index.showToast({ icon: "error", title: "出现错误" });
              }
            }
          });
        }
      });
    };
    const onSubmit = async () => {
      if (!userInfo.phone) {
        common_vendor.index.showToast({ icon: "error", title: "请填写手机号" });
        return;
      }
      if (userInfo.phone.length != 11 || !/^[1][3,4,5,7,8][0-9]{9}$/.test(userInfo.phone)) {
        common_vendor.index.showToast({
          title: "手机号格式错误",
          icon: "error"
        });
        return;
      }
      if (!userInfo.nickName) {
        common_vendor.index.showToast({ icon: "error", title: "请填写姓名" });
        return;
      }
      if (!userInfo.password) {
        common_vendor.index.showToast({ icon: "error", title: "请填写密码" });
        return;
      }
      if (!userInfo.sex) {
        common_vendor.index.showToast({ icon: "error", title: "请选择性别" });
        return;
      }
      await services_profile.postMemberProfileAPI(userInfo);
      common_vendor.index.showToast({ icon: "success", title: "注册成功" });
      setTimeout(() => {
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
      }, 600);
    };
    const onSexChange = (ev) => {
      userInfo.sex = Number(ev.detail.value);
    };
    return (_ctx, _cache) => {
      var _a;
      return common_vendor.e({
        a: ((_a = common_vendor.unref(safeAreaInsets)) == null ? void 0 : _a.top) + "px",
        b: userInfo.avatar
      }, userInfo.avatar ? {
        c: userInfo.avatar
      } : {
        d: common_assets._imports_0
      }, {
        e: common_vendor.p({
          type: "info",
          text: "点击上传头像"
        }),
        f: common_vendor.o(onAvatarChange),
        g: userInfo.phone,
        h: common_vendor.o(($event) => userInfo.phone = $event.detail.value),
        i: userInfo.nickName,
        j: common_vendor.o(($event) => userInfo.nickName = $event.detail.value),
        k: userInfo.sex === 1,
        l: userInfo.sex === 2,
        m: common_vendor.o(onSexChange),
        n: userInfo.password,
        o: common_vendor.o(($event) => userInfo.password = $event.detail.value),
        p: common_vendor.o(onSubmit)
      });
    };
  }
});
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pages/register/register.vue"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=register.js.map
