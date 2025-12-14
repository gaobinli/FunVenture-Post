<script setup lang="ts">
import { getFoodAPI } from '@/services/food'
import type { PageParams } from '@/types/global'
import type { FoodItem } from '@/types/food'
import { onMounted, ref } from 'vue'

// 分页参数
const pageParams: Required<PageParams> = {
  currentPage: 1,
  pageSize: 5,
}
const foodList = ref<FoodItem[]>([])
// 已结束标记
const finish = ref(false)
const getHomeScenicSpotRecommendData = async () => {
  // 退出分页判断
  if (finish.value === true) {
    return uni.showToast({ icon: 'none', title: '没有更多数据~' })
  }
  let params = {
    'qp-name-like': searchValue.value,
    ...pageParams
  }

  const res = await getFoodAPI(params)
  // 数组追加
  foodList.value.push(...res.data.list)
  // 分页条件
  if (pageParams.currentPage < res.data.totalPage) {
    // 页码累加
    pageParams.currentPage++
  } else {
    finish.value = true
  }
}
// 重置数据
const resetData = () => {
  pageParams.currentPage = 1
  foodList.value = []
  finish.value = false
}
const searchValue = ref('')
// 组件挂载完毕
onMounted(() => {
  getHomeScenicSpotRecommendData()
})
// 暴露方法
defineExpose({
  searchValue,
  resetData,
  getMore: getHomeScenicSpotRecommendData,
})
</script>

<template>
  <view class="guess">
    <navigator class="guess-item" v-for="item in foodList" :key="item.id" :url="`/pages/food/foodDetail?id=${item.id}`">
      <image class="image" mode="aspectFill" :src="item.url"></image>
      <view class="name"> {{ item.name }} </view>
      <view class="shortRecommend">
        <text>{{ item.shortRecommend }}</text>
      </view>
    </navigator>
  </view>
  <view class="loading-text">
    {{ finish ? '没有更多数据~' : '正在加载...' }}
  </view>
</template>

<style lang="scss">
:host {
  display: block;
}

/* 分类标题 */
.caption {
  display: flex;
  justify-content: center;
  line-height: 1;
  padding: 36rpx 0 40rpx;
  font-size: 32rpx;
  color: #262626;

  .text {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0 28rpx 0 30rpx;

    &::before,
    &::after {
      content: '';
      width: 20rpx;
      height: 20rpx;
      background-image: url(@/static/images/bubble.png);
      background-size: contain;
      margin: 0 10rpx;
    }
  }
}

.guess {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  padding: 0 20rpx;

  .guess-item {
    width: 345rpx;
    padding: 24rpx 20rpx 20rpx;
    margin-bottom: 20rpx;
    border-radius: 10rpx;
    overflow: hidden;
    background-color: #fff;
  }

  .image {
    width: 304rpx;
    height: 304rpx;
  }

  .name {
    height: 35rpx;
    margin: 10rpx 0;
    font-size: 26rpx;
    color: #262626;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }

  .price {
    line-height: 1;
    padding-top: 4rpx;
    color: #cf4444;
    font-size: 26rpx;
  }

  .shortRecommend {
    line-height: 1;
    padding-top: 4rpx;
    color: #978b8b;
    font-size: 26rpx;
  }

  .small {
    font-size: 80%;
  }
}

// 加载提示文字
.loading-text {
  text-align: center;
  font-size: 28rpx;
  color: #666;
  padding: 20rpx 0;
}
</style>