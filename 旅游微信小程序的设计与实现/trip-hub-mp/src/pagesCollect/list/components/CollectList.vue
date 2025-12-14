<script setup lang="ts">
import { getMemberCollectPageAPI, deleteMemberCollectAPI } from '@/services/collect'
import { onMounted, ref } from 'vue'

// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()

// 定义 porps
const props = defineProps<{
  orderType: number
}>()

// 请求参数
const queryParams: Required<Object> = {
  currentPage: 1,
  pageSize: 5,
  'qp-type-eq': props.orderType,
  dataLimit: 1,
}

const orderList = ref<[]>([])
// 是否加载中标记，用于防止滚动触底触发多次请求
const isLoading = ref(false)
const getMemberOrderData = async () => {
  // 如果数据出于加载中，退出函数
  if (isLoading.value) return
  // 退出分页判断
  if (isFinish.value === true) {
    return uni.showToast({ icon: 'none', title: '没有更多数据~' })
  }
  // 发送请求前，标记为加载中
  isLoading.value = true
  // 发送请求
  const res = await getMemberCollectPageAPI(queryParams)
  // 发送请求后，重置标记
  isLoading.value = false
  // 数组追加
  orderList.value.push(...res.data.list)
  // 分页条件
  if (queryParams.currentPage < res.data.totalPage) {
    // 页码累加
    queryParams.currentPage++
  } else {
    // 分页已结束
    isFinish.value = true
  }
}

onMounted(() => {
  getMemberOrderData()
})

const onOrderDelete = (id: string) => {
  uni.showModal({
    content: '你确定要删除该收藏？',
    success: async (res) => {
      if (res.confirm) {
        console.log('删除,', id)
        await deleteMemberCollectAPI(id)
        const index = orderList.value.findIndex((v) => v.id === id)
        orderList.value.splice(index, 1)
      }
    },
  })
}

// 是否分页结束
const isFinish = ref(false)
// 是否触发下拉刷新
const isTriggered = ref(false)
// 自定义下拉刷新被触发
const onRefresherrefresh = async () => {
  // 开始动画
  isTriggered.value = true
  // 重置数据
  queryParams.currentPage = 1
  orderList.value = []
  isFinish.value = false
  // 加载数据
  await getMemberOrderData()
  // 关闭动画
  isTriggered.value = false
}
</script>

<template>
  <scroll-view
    v-if="props.orderType == 1"
    enable-back-to-top
    scroll-y
    class="orders"
    refresher-enabled
    :refresher-triggered="isTriggered"
    @refresherrefresh="onRefresherrefresh"
    @scrolltolower="getMemberOrderData"
  >
    <view class="card" v-for="order in orderList" :key="order.id">
      <view class="status">
        <text class="date">{{ order.createTime.replace('T', ' ') }}</text>
        <text>{{ order.name }}</text>
        <text class="icon-delete" @tap="onOrderDelete(order.id)"></text>
      </view>
      <navigator
        class="goods"
        :url="`/pages/scenicSpotRecommend/scenicSpotRecommend?id=${order.relationId}`"
        hover-class="none"
      >
        <view class="cover">
          <image class="image" mode="aspectFit" :src="order.coverUrl"></image>
        </view>
        <view class="meta">
          <view class="type">{{ order.shortRecommend }}</view>
        </view>
      </navigator>
    </view>
    <view class="loading-text" :style="{ paddingBottom: safeAreaInsets?.bottom + 'px' }">
      {{ isFinish ? '没有更多数据~' : '正在加载...' }}
    </view>
  </scroll-view>
  <scroll-view
    v-if="props.orderType == 2"
    enable-back-to-top
    scroll-y
    class="orders"
    refresher-enabled
    :refresher-triggered="isTriggered"
    @refresherrefresh="onRefresherrefresh"
    @scrolltolower="getMemberOrderData"
  >
    <view class="card" v-for="order in orderList" :key="order.id">
      <view class="status">
        <text class="date">{{ order.createTime.replace('T', ' ') }}</text>
        <text>{{ order.name }}</text>
        <text class="icon-delete" @tap="onOrderDelete(order.id)"></text>
      </view>
      <navigator
        class="goods"
        :url="`/pages/food/foodDetail?id=${order.relationId}`"
        hover-class="none"
      >
        <view class="cover">
          <image class="image" mode="aspectFit" :src="order.coverUrl"></image>
        </view>
        <view class="meta">
          <view class="type">{{ order.shortRecommend }}</view>
        </view>
      </navigator>
    </view>
    <view class="loading-text" :style="{ paddingBottom: safeAreaInsets?.bottom + 'px' }">
      {{ isFinish ? '没有更多数据~' : '正在加载...' }}
    </view>
  </scroll-view>
  <scroll-view
    v-if="props.orderType == 3"
    enable-back-to-top
    scroll-y
    class="orders"
    refresher-enabled
    :refresher-triggered="isTriggered"
    @refresherrefresh="onRefresherrefresh"
    @scrolltolower="getMemberOrderData"
  >
    <view class="card" v-for="order in orderList" :key="order.id">
      <view class="status">
        <text class="date">{{ order.createTime.replace('T', ' ') }}</text>
        <text>{{ order.name }}</text>
        <text class="icon-delete" @tap="onOrderDelete(order.id)"></text>
      </view>
      <navigator
        class="goods"
        :url="`/pages/hotel/hotelDetail?id=${order.relationId}`"
        hover-class="none"
      >
        <view class="cover">
          <image class="image" mode="aspectFit" :src="order.coverUrl"></image>
        </view>
        <view class="meta">
          <view class="type">{{ order.shortRecommend }}</view>
        </view>
      </navigator>
    </view>
    <view class="loading-text" :style="{ paddingBottom: safeAreaInsets?.bottom + 'px' }">
      {{ isFinish ? '没有更多数据~' : '正在加载...' }}
    </view>
  </scroll-view>
</template>

<style lang="scss">
// 订单列表
.orders {
  .card {
    min-height: 100rpx;
    padding: 20rpx;
    margin: 20rpx 20rpx 0;
    border-radius: 10rpx;
    background-color: #fff;

    &:last-child {
      padding-bottom: 40rpx;
    }
  }

  .status {
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 28rpx;
    color: #999;
    margin-bottom: 15rpx;

    .date {
      color: #666;
      flex: 1;
    }

    .primary {
      color: #ff9240;
    }

    .icon-delete {
      line-height: 1;
      margin-left: 10rpx;
      padding-left: 10rpx;
      border-left: 1rpx solid #e3e3e3;
    }
  }

  .goods {
    display: flex;
    margin-bottom: 20rpx;

    .cover {
      width: 170rpx;
      height: 170rpx;
      margin-right: 20rpx;
      border-radius: 10rpx;
      overflow: hidden;
      position: relative;

      .image {
        width: 170rpx;
        height: 170rpx;
      }
    }

    .quantity {
      position: absolute;
      bottom: 0;
      right: 0;
      line-height: 1;
      padding: 6rpx 4rpx 6rpx 8rpx;
      font-size: 24rpx;
      color: #fff;
      border-radius: 10rpx 0 0 0;
      background-color: rgba(0, 0, 0, 0.6);
    }

    .meta {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }

    .name {
      height: 80rpx;
      font-size: 26rpx;
      color: #444;
    }

    .type {
      line-height: 1.8;
      padding: 0 15rpx;
      margin-top: 10rpx;
      font-size: 24rpx;
      align-self: flex-start;
      border-radius: 4rpx;
      color: #888;
      background-color: #f7f7f8;
    }

    .more {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 22rpx;
      color: #333;
    }
  }

  .payment {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    line-height: 1;
    padding: 20rpx 0;
    text-align: right;
    color: #999;
    font-size: 28rpx;
    border-bottom: 1rpx solid #eee;

    .quantity {
      font-size: 24rpx;
      margin-right: 16rpx;
    }

    .amount {
      color: #444;
      margin-left: 6rpx;
    }

    .symbol {
      font-size: 20rpx;
    }
  }

  .action {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    padding-top: 20rpx;

    .button {
      width: 180rpx;
      height: 60rpx;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-left: 20rpx;
      border-radius: 60rpx;
      border: 1rpx solid #ccc;
      font-size: 26rpx;
      color: #444;
    }

    .secondary {
      color: #27ba9b;
      border-color: #27ba9b;
    }

    .primary {
      color: #fff;
      background-color: #27ba9b;
    }
  }

  .loading-text {
    text-align: center;
    font-size: 28rpx;
    color: #666;
    padding: 20rpx 0;
  }
}
</style>
