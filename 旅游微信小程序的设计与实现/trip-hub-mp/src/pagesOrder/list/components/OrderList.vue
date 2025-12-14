<script setup lang="ts">
import { deleteMemberOrderAPI, getMemberOrderPageAPI, patchOrderStatusAPI } from '@/services/order'
import { onMounted, ref } from 'vue'
import { useMemberStore } from '@/stores'
// import { onShow } from '@dcloudio/uni-app'
import { getMemberProfileAPI } from '@/services/profile'

// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()

// 获取信息
const memberStore = useMemberStore()
const userInfo = ref()

// onShow(async () => {
//   if (useMemberStore() && useMemberStore().profile) {
//     const res = await getMemberProfileAPI()
//     console.log('=======res==', res)
//     userInfo.value = res.data
//   }
// })

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
  const res = await getMemberOrderPageAPI(queryParams)
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

onMounted(async () => {
  if (useMemberStore() && useMemberStore().profile) {
    console.log('=======start.................==')
    const res = await getMemberProfileAPI()
    console.log('=======res==', res)
    userInfo.value = res.data
    console.log('=======userInfo.value==', userInfo.value)
    getMemberOrderData()
  }
})

// 订单状态映射
const statusMap = {
  1: '待发货',
  2: '待收货',
  3: '已收货',
  4: '退货中',
  5: '已退货'
}

// 获取订单状态文本
const getStatusText = (status: number) => {
  return statusMap[status] || '未知状态'
}

// 获取订单状态颜色
const getStatusColor = (status: number) => {
  const colorMap = {
    1: '#ff9240', // 待发货 - 橙色
    2: '#ff6666', // 待收货 - 红色
    3: '#28bb9c', // 已收货 - 绿色
    4: '#ffa868', // 退货中 - 浅橙色
    5: '#999999'  // 已退货 - 灰色
  }
  return colorMap[status] || '#999'
}

// 确认收货
const onConfirmReceipt = (orderId: string) => {
  uni.showModal({
    content: '确认已收到货物吗？',
    confirmText: '确认收货',
    cancelText: '取消',
    success: async (res) => {
      if (res.confirm) {
        try {
          uni.showLoading({ title: '处理中...', mask: true })
          await patchOrderStatusAPI(orderId, 3)
          uni.hideLoading()
          uni.showToast({ icon: 'success', title: '确认收货成功' })
          // 更新订单状态
          const order = orderList.value.find((v) => v.id === orderId)
          if (order) {
            order.status = 3
          }
        } catch (error) {
          uni.hideLoading()
          uni.showToast({ icon: 'error', title: '确认收货失败，请重试' })
          console.error('确认收货失败:', error)
        }
      }
    },
  })
}

// 申请退货
const onApplyReturn = (orderId: string) => {
  uni.showModal({
    content: '确认要申请退货吗？',
    confirmText: '确认退货',
    cancelText: '取消',
    success: async (res) => {
      if (res.confirm) {
        try {
          uni.showLoading({ title: '处理中...', mask: true })
          await patchOrderStatusAPI(orderId, 4)
          uni.hideLoading()
          uni.showToast({ icon: 'success', title: '退货申请成功' })
          // 更新订单状态
          const order = orderList.value.find((v) => v.id === orderId)
          if (order) {
            order.status = 4
          }
        } catch (error) {
          uni.hideLoading()
          uni.showToast({ icon: 'error', title: '申请退货失败，请重试' })
          console.error('申请退货失败:', error)
        }
      }
    },
  })
}

const onOrderDelete = (id: string) => {
  uni.showModal({
    content: '你确定要删除该订单？',
    success: async (res) => {
      if (res.confirm) {
        console.log('删除,', id)
        await deleteMemberOrderAPI(id)
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
  queryParams.page = 1
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
        <view class="left">
          <text class="date">{{ order.createTime.replace('T', ' ') }}</text>
          <text class="status-text" :style="{ color: getStatusColor(order.status) }">
            {{ getStatusText(order.status) }}
          </text>
        </view>
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
      <view class="payment">
        <text>实付</text>
        <text class="amount"> <text class="symbol">¥</text>{{ order.price }}</text>
      </view>
      <!-- 订单操作 -->
      <view class="action" v-if="order.status === 2 || order.status === 3">
        <button
          v-if="order.status === 2"
          class="button secondary"
          @tap="onConfirmReceipt(order.id)"
        >
          确认收货
        </button>
        <button
          v-if="order.status === 3"
          class="button secondary"
          @tap="onApplyReturn(order.id)"
        >
          申请退货
        </button>
      </view>
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
        <view class="left">
          <text class="date">{{ order.createTime.replace('T', ' ') }}</text>
          <text class="status-text" :style="{ color: getStatusColor(order.status) }">
            {{ getStatusText(order.status) }}
          </text>
        </view>
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
      <view class="payment">
        <text>实付</text>
        <text class="amount"> <text class="symbol">¥</text>{{ order.price }}</text>
      </view>
      <!-- 订单操作 -->
      <view class="action" v-if="order.status === 2 || order.status === 3">
        <button
          v-if="order.status === 2"
          class="button secondary"
          @tap="onConfirmReceipt(order.id)"
        >
          确认收货
        </button>
        <button
          v-if="order.status === 3"
          class="button secondary"
          @tap="onApplyReturn(order.id)"
        >
          申请退货
        </button>
      </view>
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
        <view class="left">
          <text class="date">{{ order.createTime.replace('T', ' ') }}</text>
          <text class="status-text" :style="{ color: getStatusColor(order.status) }">
            {{ getStatusText(order.status) }}
          </text>
        </view>
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
      <view class="payment">
        <text>实付</text>
        <text class="amount"> <text class="symbol">¥</text>{{ order.price }}</text>
      </view>
      <!-- 订单操作 -->
      <view class="action" v-if="order.status === 2 || order.status === 3">
        <button
          v-if="order.status === 2"
          class="button secondary"
          @tap="onConfirmReceipt(order.id)"
        >
          确认收货
        </button>
        <button
          v-if="order.status === 3"
          class="button secondary"
          @tap="onApplyReturn(order.id)"
        >
          申请退货
        </button>
      </view>
    </view>
    <view class="loading-text" :style="{ paddingBottom: safeAreaInsets?.bottom + 'px' }">
      {{ isFinish ? '没有更多数据~' : '正在加载...' }}
    </view>
  </scroll-view>
</template>

<style lang="scss">
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
    font-size: 26rpx;
    color: #999;
    margin-bottom: 15rpx;

    .left {
      display: flex;
      align-items: center;
      flex: 1;
      gap: 16rpx;

      .date {
        color: #666;
        font-size: 24rpx;
      }

      .status-text {
        font-size: 26rpx;
        font-weight: 500;
      }
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
