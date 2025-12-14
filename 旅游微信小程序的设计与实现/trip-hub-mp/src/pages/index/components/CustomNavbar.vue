<script setup lang="ts">
import { useCustomScenicSpotRecommendList } from '@/composables'

// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()

// 组合式函数调用
const { scenicSpotRef, onScrolltolower } = useCustomScenicSpotRecommendList()

const search = async (inputSearchValue: string) => {
  console.log(inputSearchValue.detail.value)
  scenicSpotRef.value.searchValue = inputSearchValue.detail.value
  scenicSpotRef.value?.resetData()
  await Promise.all([scenicSpotRef.value?.getMore()])
}
</script>

<template>
  <view class="navbar" :style="{ paddingTop: safeAreaInsets!.top + 10 + 'px' }">
    <view class="logo">
      <text class="logo-title">重庆旅游</text>
      <text class="logo-text">美丽 · 重庆 · 自然</text>
    </view>
    <!-- 搜索条 -->
    <view class="search">
      <input class="uni-input" focus placeholder="输入景点名称" @input="search($event)" />
    </view>
  </view>
</template>

<style lang="scss">
/* 自定义导航条 */
.navbar {
  background-image: url(@/static/images/navigator_bg.png);
  background-size: cover;
  position: relative;
  display: flex;
  flex-direction: column;
  padding-top: 20px;
  padding-bottom: 20px;

  .logo {
    display: flex;
    align-items: center;
    height: 64rpx;
    padding-left: 30rpx;

    .logo-title {
      line-height: 45rpx;
      color: #fff;
      margin: 2rpx 0 0 20rpx;
      font-size: 45rpx;
    }

    .logo-text {
      flex: 1;
      line-height: 28rpx;
      color: #fff;
      margin: 2rpx 0 0 20rpx;
      padding-left: 20rpx;
      border-left: 1rpx solid #fff;
      font-size: 26rpx;
    }
  }

  .search {
    // display: flex;
    // align-items: center;
    // justify-content: space-between;
    padding: 0 10rpx 0 26rpx;
    height: 64rpx;
    // margin: 16rpx 20rpx;
    margin-top: 16rpx;
    margin-left: 20rpx;
    margin-right: 20rpx;
    color: #fff;
    font-size: 28rpx;
    border-radius: 32rpx;
    background-color: rgba(255, 255, 255, 0.5);

    .uni-input {
      line-height: 64rpx;
      height: 64rpx;
    }
  }

  .icon-search {
    &::before {
      margin-right: 10rpx;
    }

  }

  // .icon-scan {
  //   font-size: 30rpx;
  //   padding: 15rpx;
  // }
}
</style>
