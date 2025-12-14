<script setup lang="ts">
import { postNormalLoginAPI, postQuickLoginAPI } from '@/services/login'
import { useMemberStore } from '@/stores'
import type { LoginResult } from '@/types/member'
import { ref, reactive } from 'vue'

// 获取用户手机号码
const onGetphonenumber: UniHelper.ButtonOnGetphonenumber = async (ev) => {
  await checkedAgreePrivacy()
  const res = await postQuickLoginAPI('15502227374')
  const loginResultReactive = reactive({
    token: res.data,
  })
  loginSuccess(loginResultReactive)
}

const form = ref({
  username: '13594677777',
  password: '123456',
})

const onSubmit = async () => {
  await checkedAgreePrivacy()
  const res = await postNormalLoginAPI(form.value)
  const loginResultReactive = reactive({
    token: res.data,
  })
  loginSuccess(loginResultReactive)
}

const isAgreePrivacy = ref(true)
const isAgreePrivacyShakeY = ref(false)
const checkedAgreePrivacy = async () => {
  if (!isAgreePrivacy.value) {
    uni.showToast({
      icon: 'none',
      title: '请先阅读并勾选协议',
    })
    // 震动提示
    isAgreePrivacyShakeY.value = true
    setTimeout(() => {
      isAgreePrivacyShakeY.value = false
    }, 500)
    // 返回错误
    return Promise.reject(new Error('请先阅读并勾选协议'))
  }
}

const loginSuccess = (profile: LoginResult) => {
  // 保存信息
  const memberStore = useMemberStore()
  memberStore.setProfile(profile)
  // 成功提示
  uni.showToast({ icon: 'success', title: '登录成功' })
  setTimeout(() => {
    // 页面跳转
    // uni.navigateBack()
    uni.switchTab({ url: '/pages/my/my' })
  }, 1000)
}
</script>

<template>
  <view class="viewport">
    <view class="logo">
      <view class="title">重庆旅游</view>
      <view class="subtitle">欢迎来到魅力重庆</view>
    </view>
    <view class="login">
      <!-- 小程序账号密码登录 -->
      <view class="button-privacy-wrap">
        <input
          v-model="form.username"
          class="input"
          type="text"
          placeholder="请输入用户名/手机号码"
        />
        <input
          v-model="form.password"
          class="input"
          type="text"
          password
          placeholder="请输入密码"
        />
        <button @tap="onSubmit" class="button phone">登录</button>
        <navigator :url="`/pages/register/register`" hover-class="none" class="navigator">
          <button class="register">注册</button>
        </navigator>
      </view>
      <!-- <view class="extra">
        <view class="caption">
          <text>其他登录方式</text>
        </view>
        <view class="options">
          
          <button @getphonenumber="onGetphonenumber" open-type="getPhoneNumber">
            <text class="icon icon-phone">模拟快捷登录</text>
          </button>
        </view>
      </view> -->
      <view class="tips" :class="{ animate__shakeY: isAgreePrivacyShakeY }">
        <label class="label" @tap="isAgreePrivacy = !isAgreePrivacy">
          <radio class="radio" color="#28bb9c" :checked="isAgreePrivacy" />
          <text>登录/注册即视为你同意重庆旅游平台</text>
        </label>
        <navigator class="link" hover-class="none" url="./protocal">《服务条款》</navigator>
        和
        <text class="link" @tap="onOpenPrivacyContract">《隐私协议》</text>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
page {
  height: 100%;
}

.viewport {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 20rpx 40rpx;
}

.logo {
  flex: 1;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  .title {
    font-size: 56rpx;
    font-weight: bold;
    color: #28bb9c;
    margin-bottom: 20rpx;
  }

  .subtitle {
    font-size: 32rpx;
    color: #333;
    margin-bottom: 30rpx;
    font-weight: 500;
  }

  .description {
    display: flex;
    flex-direction: column;
    gap: 12rpx;
    margin-bottom: 40rpx;

    text {
      font-size: 26rpx;
      color: #666;
      line-height: 1.6;
    }
  }

  .features {
    display: flex;
    justify-content: center;
    gap: 40rpx;
    margin-top: 20rpx;

    .feature-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 8rpx;

      .feature-icon {
        font-size: 48rpx;
      }

      .feature-text {
        font-size: 24rpx;
        color: #666;
      }
    }
  }
}

.register {
  margin-top: 12px;
  align-items: center;
  justify-content: center;
  width: 100%;
  // height: 80rpx;
  line-height: 80rpx;
  font-size: 28rpx;
  border-radius: 72rpx;
  color: #000000;
}

.login {
  display: flex;
  flex-direction: column;
  height: 60vh;
  padding: 40rpx 20rpx 20rpx;

  .input {
    width: 100%;
    height: 80rpx;
    font-size: 28rpx;
    border-radius: 72rpx;
    border: 1px solid #ddd;
    padding-left: 30rpx;
    margin-bottom: 20rpx;
  }

  .button {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 80rpx;
    font-size: 28rpx;
    border-radius: 72rpx;
    color: #fff;

    .icon {
      font-size: 40rpx;
      margin-right: 6rpx;
    }
  }

  .phone {
    background-color: #28bb9c;
  }

  .wechat {
    background-color: #06c05f;
  }

  .extra {
    flex: 1;
    padding: 70rpx 70rpx 0;

    .caption {
      width: 440rpx;
      line-height: 1;
      border-top: 1rpx solid #ddd;
      font-size: 26rpx;
      color: #999;
      position: relative;

      text {
        transform: translate(-40%);
        background-color: #fff;
        position: absolute;
        top: -12rpx;
        left: 50%;
      }
    }

    .options {
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: 70rpx;

      button {
        padding: 0;
        background-color: transparent;

        &::after {
          border: none;
        }
      }
    }

    .icon {
      font-size: 24rpx;
      color: #444;
      display: flex;
      flex-direction: column;
      align-items: center;

      &::before {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 80rpx;
        height: 80rpx;
        margin-bottom: 6rpx;
        font-size: 40rpx;
        border: 1rpx solid #444;
        border-radius: 50%;
      }
    }

    .icon-weixin::before {
      border-color: #06c05f;
      color: #06c05f;
    }
  }
}

@keyframes animate__shakeY {
  0% {
    transform: translate(0, 0);
  }

  50% {
    transform: translate(0, -5rpx);
  }

  100% {
    transform: translate(0, 0);
  }
}

.animate__shakeY {
  animation: animate__shakeY 0.2s ease-in-out 3;
}

.button-privacy-wrap {
  position: relative;

  .button-opacity {
    opacity: 0;
    position: absolute;
    z-index: 1;
  }
}

.tips {
  position: absolute;
  bottom: 80rpx;
  left: 20rpx;
  right: 20rpx;
  font-size: 22rpx;
  color: #999;
  text-align: center;

  .radio {
    transform: scale(0.6);
    margin-right: -4rpx;
    margin-top: -4rpx;
    vertical-align: middle;
  }

  .link {
    display: inline;
    color: #28bb9c;
  }
}
</style>