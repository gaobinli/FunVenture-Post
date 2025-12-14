"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
const services_profile = require("../../services/profile.js");
require("../../utils/http.js");
require("../../stores/index.js");
require("../../stores/modules/member.js");
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "profile",
  setup(__props) {
    const { safeAreaInsets } = common_vendor.index.getSystemInfoSync();
    const userInfo = common_vendor.ref();
    common_vendor.ref({});
    const getMemberProfileData = async () => {
      const res = await services_profile.getMemberProfileAPI();
      userInfo.value = res.data;
    };
    common_vendor.onLoad(() => {
      getMemberProfileData();
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
                userInfo.value.avatar = avatarUploadUrl;
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
      await services_profile.patchMemberProfileAPI(userInfo.value);
      common_vendor.index.showToast({ icon: "success", title: "保存成功" });
      setTimeout(() => {
        common_vendor.index.navigateBack();
      }, 600);
    };
    const onSexChange = (ev) => {
      userInfo.value.sex = Number(ev.detail.value);
    };
    return (_ctx, _cache) => {
      var _a;
      return common_vendor.e({
        a: ((_a = common_vendor.unref(safeAreaInsets)) == null ? void 0 : _a.top) + "px",
        b: userInfo.value.avatar
      }, userInfo.value.avatar ? {
        c: userInfo.value.avatar
      } : {
        d: common_assets._imports_0
      }, {
        e: common_vendor.o(onAvatarChange),
        f: common_vendor.t(userInfo.value.phone),
        g: userInfo.value.nickName,
        h: common_vendor.o(($event) => userInfo.value.nickName = $event.detail.value),
        i: userInfo.value.sex === 1,
        j: userInfo.value.sex === 2,
        k: common_vendor.o(onSexChange),
        l: userInfo.value.password,
        m: common_vendor.o(($event) => userInfo.value.password = $event.detail.value),
        n: common_vendor.o(onSubmit)
      });
    };
  }
});
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/gp/2026016-重庆旅游微信小程序的设计与实现/trip-hub-mp/src/pagesMember/profile/profile.vue"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=profile.js.map
