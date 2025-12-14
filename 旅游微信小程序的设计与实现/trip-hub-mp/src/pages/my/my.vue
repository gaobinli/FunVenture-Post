<script setup lang="ts">
import { useMemberStore } from '@/stores'
import { onShow } from '@dcloudio/uni-app'
import { ref } from 'vue'
import { getMemberProfileAPI } from '@/services/profile'
import { useCustomScenicSpotRecommendList } from '@/composables'

// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()
// 获取信息
const memberStore = useMemberStore()
const userInfo = ref()

const { scenicSpotRef, onScrolltolower } = useCustomScenicSpotRecommendList()

onShow(async () => {
  if (useMemberStore() && useMemberStore().profile) {
    const res = await getMemberProfileAPI()
    userInfo.value = res.data
  }

})
</script>

<template>
  <scroll-view @scrolltolower="onScrolltolower" class="viewport" scroll-y>
    <!-- 个人资料 -->
    <view class="profile" :style="{ paddingTop: safeAreaInsets!.top + 'px' }">
      <!-- 情况1：已登录 -->
      <view class="overview" v-if="memberStore.profile">
        <navigator url="/pagesMember/profile/profile" hover-class="none">
          <image v-if="userInfo.avatar" class="avatar" :src="userInfo.avatar"></image>
          <image v-else class="avatar" src="@/static/images/default_avatar.png"></image>
        </navigator>
        <view class="meta">
          <navigator url="/pagesMember/profile/profile" hover-class="none">
            <view class="nickname">
              {{ userInfo.nickName || userInfo.phone }}
            </view>
          </navigator>
          <navigator class="extra" url="/pagesMember/profile/profile" hover-class="none">
            <text class="update">更新头像昵称</text>
          </navigator>
        </view>
      </view>
      <!-- 情况2：未登录 -->
      <view class="overview" v-else>
        <navigator url="/pages/login/login" hover-class="none">
          <image class="avatar gray" src="@/static/images/default_avatar.png">
          </image>
        </navigator>
        <view class="meta">
          <navigator url="/pages/login/login" hover-class="none" class="nickname">
            未登录
          </navigator>
          <view class="extra">
            <text class="tips">点击登录账号</text>
          </view>
        </view>
      </view>
      <navigator class="settings" url="/pagesMember/settings/settings" hover-class="none">
        设置
      </navigator>
    </view>
    <!-- 钱包 -->
    <view class="wallet-card" v-if="memberStore.profile">
      <navigator url="/pagesMember/wallet/wallet" hover-class="none">
        <view class="wallet-content">
          <view class="wallet-icon">💳</view>
          <view class="wallet-info">
            <view class="wallet-title">我的钱包</view>
            <view class="wallet-balance">余额: ¥{{ userInfo.balance || '0.00' }}</view>
          </view>
          <view class="wallet-arrow">›</view>
        </view>
      </navigator>
    </view>
    <!-- 我的订单 -->
    <view class="orders">
      <view class="title">
        我的订单
        <navigator class="navigator" url="/pagesOrder/list/list?type=1" hover-class="none">
          查看全部订单<text class="icon-right"></text>
        </navigator>
      </view>
      <view class="section">
        <navigator class="navigator" url="/pagesCollect/list/list?type=1" hover-class="none">
          <button class="contact icon-gift">景点收藏</button>
        </navigator>
        <navigator class="navigator" url="/pagesCollect/list/list?type=2" hover-class="none">
          <button class="contact icon-check">美食收藏</button>
        </navigator>
        <navigator class="navigator" url="/pagesCollect/list/list?type=3" hover-class="none">
          <button class="contact icon-comment">酒店收藏</button>
        </navigator>
        <navigator class="navigator" url="/pagesOrder/list/list?type=1" hover-class="none">
          <button class="contact icon-currency">我的订单</button>
        </navigator>
      </view>
    </view>
    <view class="guess">
      <CustomScenicSpotRecommend ref="scenicSpotRef" />
    </view>
  </scroll-view>
</template>

<style lang="scss">
page {
  height: 100%;
  overflow: hidden;
  background-color: #f7f7f8;
}

.viewport {
  height: 100%;
  background-repeat: no-repeat;
  background-image: url(@/static/images/order_bg.png);
  background-size: 100% auto;
}

/* 用户信息 */
.profile {
  margin-top: 40rpx;
  padding-bottom: 20rpx;
  position: relative;

  .overview {
    display: flex;
    height: 120rpx;
    padding: 0 36rpx;
    color: #fff;
  }

  .avatar {
    width: 120rpx;
    height: 120rpx;
    border-radius: 50%;
    background-color: #eee;
  }

  .gray {
    filter: grayscale(100%);
  }

  .meta {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    line-height: 30rpx;
    padding: 16rpx 0;
    margin-left: 20rpx;
  }

  .nickname {
    max-width: 180rpx;
    margin-bottom: 16rpx;
    font-size: 30rpx;

    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .extra {
    display: flex;
    font-size: 20rpx;
  }

  .tips {
    font-size: 22rpx;
  }

  .update {
    padding: 3rpx 10rpx 1rpx;
    color: rgba(255, 255, 255, 0.8);
    border: 1rpx solid rgba(255, 255, 255, 0.8);
    margin-right: 10rpx;
    border-radius: 30rpx;
  }

  .settings {
    position: absolute;
    bottom: 0;
    right: 40rpx;
    font-size: 30rpx;
    color: #fff;
  }
}

.orders {
  position: relative;
  z-index: 99;
  padding: 30rpx;
  margin: 50rpx 20rpx 0;
  background-color: #fff;
  border-radius: 10rpx;
  box-shadow: 0 4rpx 6rpx rgba(240, 240, 240, 0.6);

  .title {
    height: 40rpx;
    line-height: 40rpx;
    font-size: 28rpx;
    color: #1e1e1e;

    .navigator {
      font-size: 24rpx;
      color: #939393;
      float: right;
    }
  }

  .section {
    width: 100%;
    display: flex;
    justify-content: space-between;
    padding: 40rpx 20rpx 10rpx;

    .navigator,
    .contact {
      text-align: center;
      font-size: 24rpx;
      color: #333;

      &::before {
        display: block;
        font-size: 60rpx;
        color: #ff9545;
      }
    }

    .contact {
      padding: 0;
      margin: 0;
      border: 0;
      background-color: transparent;
      line-height: inherit;
    }
  }
}

/* 钱包卡片 */
.wallet-card {
  position: relative;
  z-index: 99;
  margin: 30rpx 20rpx;
  border-radius: 10rpx;
  box-shadow: 0 4rpx 6rpx rgba(240, 240, 240, 0.6);
  background: linear-gradient(135deg, #28bb9c 0%, #1e9b7c 100%);
  overflow: hidden;

  .wallet-content {
    display: flex;
    align-items: center;
    padding: 30rpx;
    color: #fff;

    .wallet-icon {
      font-size: 50rpx;
      margin-right: 20rpx;
    }

    .wallet-info {
      flex: 1;

      .wallet-title {
        font-size: 28rpx;
        font-weight: 500;
        margin-bottom: 8rpx;
      }

      .wallet-balance {
        font-size: 24rpx;
        opacity: 0.9;
      }
    }

    .wallet-arrow {
      font-size: 40rpx;
      opacity: 0.7;
    }
  }
}

.guess {
  background-color: #f7f7f8;
  margin-top: 20rpx;
}
</style>