<script setup lang="ts">
import { onLoad } from '@dcloudio/uni-app'
import { ref } from 'vue'
import PageSkeleton from "./components/PageSkeleton.vue";
import { useCustomHotelRecommendList } from '@/composables'

// 是否加载中的标记
const isLoading = ref(false)
// 页面数据加载
onLoad(async () => {
  isLoading.value = true
  hotelRef.value?.getMore()
  isLoading.value = false
})

// 组合式函数调用
const { hotelRef, onScrolltolower } = useCustomHotelRecommendList()

// 自定义下拉刷新被触发
const isTriggered = ref(false)
const onRefresherrefresh = async () => {
  console.log("自定义下拉刷新触发");
  // 开始动画
  isTriggered.value = true
  // 加载数据（await 等待一个接口请求完才请求下一个，性能优化，直接三个接口一起，然后等待结束）
  hotelRef.value?.resetData()
  await Promise.all([hotelRef.value?.getMore()])
  // 关闭动画
  isTriggered.value = false
}

const search = async (inputSearchValue: string) => {
  hotelRef.value.searchValue = inputSearchValue.detail.value
  hotelRef.value?.resetData()
  await Promise.all([hotelRef.value?.getMore()])
}
</script>

<template>
  <view class="navbar">
    <!-- 搜索条 -->
    <view class="search">
      <input class="uni-input" focus placeholder="输入酒店名称" @input="search($event)" />
    </view>
  </view>
  <!-- 自定义导航栏 -->
  <scroll-view refresher-enabled @refresherrefresh="onRefresherrefresh" :refresher-triggered="isTriggered"
    @scrolltolower="onScrolltolower" scroll-y>
    <PageSkeleton v-if="isLoading" />
    <template v-else>
      <CustomHotelRecommend ref="hotelRef" />
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

  .search {
    // display: flex;
    // align-items: center;
    // justify-content: space-between;
    padding: 0 10rpx 0 26rpx;
    height: 64rpx;
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
}
</style>