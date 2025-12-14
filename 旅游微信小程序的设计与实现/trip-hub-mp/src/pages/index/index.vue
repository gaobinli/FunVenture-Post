<script setup lang="ts">
import { getHomeBannerAPI, getHomeCategoryAPI } from '@/services/home'
import CustomNavbar from './components/CustomNavbar.vue'
import CategoryPanel from './components/CategoryPanel.vue'
import { onLoad } from '@dcloudio/uni-app'
import type { BannerItem, CategoryItem } from '@/types/home'
import { ref } from 'vue'
import PageSkeleton from "./components/PageSkeleton.vue";
import { useCustomScenicSpotRecommendList } from '@/composables'

// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()

// 获取轮播图数据
const bannerList = ref<BannerItem[]>([])
const getHomeBannerData = async () => {
  const res = await getHomeBannerAPI()
  bannerList.value = res.data
}

// 获取前台分类数据
const categoryList = ref<CategoryItem[]>([])
const getHomeCategoryData = async () => {
  const res = await getHomeCategoryAPI()
  categoryList.value = res.data
}

// 是否加载中的标记
const isLoading = ref(false)
// 页面数据加载
onLoad(async () => {
  isLoading.value = true
  getHomeBannerData()
  getHomeCategoryData()
  await Promise.all([getHomeBannerData(), getHomeCategoryData(), scenicSpotRef.value?.getMore()])
  isLoading.value = false
})

// 组合式函数调用
const { scenicSpotRef, onScrolltolower } = useCustomScenicSpotRecommendList()

// 自定义下拉刷新被触发
const isTriggered = ref(false)
const onRefresherrefresh = async () => {
  console.log("自定义下拉刷新触发");
  // 开始动画
  isTriggered.value = true
  // 加载数据（await 等待一个接口请求完才请求下一个，性能优化，直接三个接口一起，然后等待结束）
  await getHomeBannerData()
  await getHomeCategoryData()
  // 重置组件数据
  scenicSpotRef.value?.resetData()
  await Promise.all([getHomeBannerData(), getHomeCategoryData(), scenicSpotRef.value?.getMore()])
  // 关闭动画
  isTriggered.value = false
}

const search = async (inputSearchValue: string) => {
  console.log(inputSearchValue.detail.value)
  scenicSpotRef.value.searchValue = inputSearchValue.detail.value
  scenicSpotRef.value?.resetData()
  await Promise.all([scenicSpotRef.value?.getMore()])
}
</script>

<template>
  <!-- 自定义导航栏 -->
  <!-- <CustomNavbar /> -->
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
  <scroll-view refresher-enabled @refresherrefresh="onRefresherrefresh" :refresher-triggered="isTriggered"
    @scrolltolower="onScrolltolower" scroll-y>
    <PageSkeleton v-if="isLoading" />
    <template v-else>
      <CustomSwiper :list="bannerList" />
      <CategoryPanel :list="categoryList" />
      <CustomScenicSpotRecommend ref="scenicSpotRef" />
    </template>
  </scroll-view>
</template>

<style lang="scss">
page {
  background-color: #f7f7f7;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.scroll-view {
  flex: 1;
}

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