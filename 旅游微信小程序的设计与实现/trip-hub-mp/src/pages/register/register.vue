<script setup lang="ts">
import { onLoad } from '@dcloudio/uni-app'
import { postMemberProfileAPI } from '@/services/profile'
import type { ProfileDetail } from '@/types/member'
import { reactive, ref } from 'vue'
import { useMemberStore } from '@/stores'

// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()

const memberStore = useMemberStore()

const userInfo = reactive({
  avatar: '',
  phone: '',
  nickName: '',
  password: '',
  sex: 1,
  enable: 1,
})
// 获取个人信息
// const profile = ref<ProfileDetail>({} as ProfileDetail)
// const getMemberProfileData = async () => {
// const res = await getMemberProfileAPI()
// userInfo.value = res.data
// }

onLoad(() => {
  // getMemberProfileData()
})

// 修改头像
const onAvatarChange = () => {
  // 调用拍照/选择图片
  uni.chooseMedia({
    // 文件个数
    count: 1,
    // 文件类型
    mediaType: ['image'],
    success: (res) => {
      // 本地路径
      const { tempFilePath } = res.tempFiles[0]
      // 文件上传
      uni.uploadFile({
        url: '/upload',
        name: 'file',
        filePath: tempFilePath,
        success: (res) => {
          if (res.statusCode === 200) {
            const avatarUploadUrl = JSON.parse(res.data).data
            userInfo.avatar = avatarUploadUrl
            uni.showToast({ icon: 'success', title: '上传成功' })
          } else {
            uni.showToast({ icon: 'error', title: '出现错误' })
          }
        },
      })
    },
  })
}

// 点击保存提交表单
const onSubmit = async () => {
  if (!userInfo.phone) {
    uni.showToast({ icon: 'error', title: '请填写手机号' })
    return
  }
  if (userInfo.phone.length != 11 || !/^[1][3,4,5,7,8][0-9]{9}$/.test(userInfo.phone)) {
    uni.showToast({
      title: '手机号格式错误',
      icon: 'error',
    })
    return
  }
  if (!userInfo.nickName) {
    uni.showToast({ icon: 'error', title: '请填写姓名' })
    return
  }
  if (!userInfo.password) {
    uni.showToast({ icon: 'error', title: '请填写密码' })
    return
  }
  if (!userInfo.sex) {
    uni.showToast({ icon: 'error', title: '请选择性别' })
    return
  }
  const res = await postMemberProfileAPI(userInfo)
  uni.showToast({ icon: 'success', title: '注册成功' })
  // 清理用户信息
  setTimeout(() => {
    uni.navigateTo({ url: '/pages/login/login' })
  }, 600)
}

// 修改性别
const onSexChange: UniHelper.RadioGroupOnChange = (ev) => {
  userInfo.sex = Number(ev.detail.value)
}
</script>

<template>
  <view class="viewport">
    <!-- 导航栏 -->
    <view class="navbar" :style="{ paddingTop: safeAreaInsets?.top + 'px' }">
      <navigator open-type="navigateBack" class="back icon-left" hover-class="none"></navigator>
      <view class="title">注册信息</view>
    </view>
    <!-- 头像 -->

    <!-- 表单 -->
    <view class="form">
      <!-- 表单内容 -->
      <view class="avatar">
        <view @tap="onAvatarChange" class="avatar-content">
          <image v-if="userInfo.avatar" class="image" :src="userInfo.avatar" mode="aspectFill" />
          <image v-else class="image" src="@/static/images/default_avatar.png" mode="aspectFill" />
          <up-text type="info" text="点击上传头像"></up-text>
        </view>
      </view>
      <view class="form-content">
        <view class="form-item">
          <span style="color: red">*</span><text class="label">手机号</text>
          <input class="input" type="text" placeholder="请填写手机号" v-model="userInfo.phone" />
        </view>
        <view class="form-item">
          <span style="color: red">*</span><text class="label">姓名</text>
          <input class="input" type="text" placeholder="请填写姓名" v-model="userInfo.nickName" />
        </view>
        <view class="form-item">
          <span style="color: red">*</span><text class="label">性别</text>
          <radio-group @change="onSexChange">
            <label class="radio">
              <radio value="1" color="#27ba9b" :checked="userInfo.sex === 1" />
              男
            </label>
            <label class="radio">
              <radio value="2" color="#27ba9b" :checked="userInfo.sex === 2" />
              女
            </label>
          </radio-group>
        </view>
        <view class="form-item">
          <span style="color: red">*</span><text class="label">密码</text>
          <input
            class="input"
            type="password"
            placeholder="请填写密码"
            v-model="userInfo.password"
          />
        </view>
      </view>
      <!-- 提交按钮 -->
      <button @tap="onSubmit" class="form-button">注 册</button>
    </view>
  </view>
</template>

<style lang="scss">
page {
  background-color: #f4f4f4;
}

.viewport {
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: #ffbf6b;
  background-size: auto 420rpx;
  background-repeat: no-repeat;
}

// 导航栏
.navbar {
  position: relative;
  background-color: #f4f4f4;
  height: 44px;

  .title {
    height: 44px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 16px;
    font-weight: 500;
    color: #333;
  }

  .back {
    position: absolute;
    height: 44px;
    width: 44px;
    left: 10px;
    font-size: 24px;
    color: #28bb9c;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 10;

    &::before {
      content: '';
      position: absolute;
      width: 8px;
      height: 14px;
      border-left: 2px solid #28bb9c;
      border-top: 2px solid #28bb9c;
      transform: rotate(-45deg);
      left: 10px;
    }
  }
}

// 头像
.avatar {
  text-align: center;
  width: 100%;
  height: 260rpx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  .image {
    width: 160rpx;
    height: 160rpx;
    border-radius: 50%;
    background-color: #eee;
  }

  .text {
    display: block;
    padding-top: 20rpx;
    line-height: 1;
    font-size: 26rpx;
    color: #fff;
  }
}

// 表单
.form {
  background-color: #f4f4f4;

  &-content {
    margin: 20rpx 20rpx 0;
    padding: 0 20rpx;
    border-radius: 10rpx;
    background-color: #fff;
  }

  &-item {
    display: flex;
    height: 96rpx;
    line-height: 46rpx;
    padding: 25rpx 10rpx;
    background-color: #fff;
    font-size: 28rpx;
    border-bottom: 1rpx solid #ddd;

    &:last-child {
      border: none;
    }

    .label {
      width: 180rpx;
      color: #333;
    }

    .account {
      color: #666;
    }

    .input {
      flex: 1;
      display: block;
      height: 46rpx;
    }

    .radio {
      margin-right: 20rpx;
    }

    .picker {
      flex: 1;
    }

    .placeholder {
      color: #808080;
    }
  }

  &-button {
    height: 80rpx;
    text-align: center;
    line-height: 80rpx;
    margin: 30rpx 20rpx;
    color: #fff;
    border-radius: 80rpx;
    font-size: 30rpx;
    background-color: #27ba9b;
  }
}
</style>