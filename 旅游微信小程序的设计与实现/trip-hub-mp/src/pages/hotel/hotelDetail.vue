<script setup lang="ts">
import { getHotelByIdAPI } from '@/services/hotel'
import type { HotelItem } from '@/types/hotel'
import { addCollectAPI } from '@/services/collect'
import { addOrderAPI } from '@/services/order'
import { getMemberProfileAPI, patchUserBalanceAPI } from '@/services/profile'
import { onLoad, onShow } from '@dcloudio/uni-app'
import { ref } from 'vue'
import { useMemberStore } from '@/stores'

// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()
const memberStore = useMemberStore()
// 接收页面参数
const query = defineProps<{
  id: string
}>()

const hotels = ref<HotelItem[]>([])
const hotel = ref<HotelItem>()
const userInfo = ref<any>(null)

const getFoodByIdData = async () => {
  const res = await getHotelByIdAPI(query.id)
  hotel.value = res.data
  if (res.data && res.data.content) {
    hotel.value.content = hotel.value.content.replace(/\<img/gi, '<img style="max-width:100%;height:auto" ')
  }
  hotels.value.push(hotel.value)
}

// 获取用户信息
const getUserInfo = async () => {
  if (memberStore.profile) {
    try {
      const res = await getMemberProfileAPI()
      userInfo.value = res.data
    } catch (error) {
      console.error('获取用户信息失败:', error)
    }
  }
}

// 页面加载
onLoad(() => {
  getFoodByIdData()
  getUserInfo()
})

// 页面显示时刷新用户信息
onShow(() => {
  getUserInfo()
})

// 轮播图变化时
const currentIndex = ref(0)
const onchange: UniHelper.SwiperOnChange = (ev) => {
  currentIndex.value = ev.detail!.current
}

// 点击图片时
const onTapImage = (url: string) => {
  // 大图预览
  uni.previewImage({
    current: url,
    urls: [url],
  })
}

const buy = async (id: number, price: number) => {
  // 先检查是否登录
  if (!memberStore.profile) {
    uni.showToast({ icon: 'error', title: '请先登录' })
    return
  }

  // 检查用户信息是否已加载
  if (!userInfo.value) {
    uni.showToast({ icon: 'error', title: '获取用户信息失败，请重试' })
    return
  }

  const currentBalance = userInfo.value.balance || 0

  // 检查余额是否足够
  if (currentBalance < price) {
    uni.showModal({
      title: '余额不足',
      content: `当前余额 ¥${currentBalance.toFixed(2)}，酒店价格 ¥${price}，需要充值 ¥${(price - currentBalance).toFixed(2)}`,
      confirmText: '去充值',
      cancelText: '取消',
      success: (res) => {
        if (res.confirm) {
          // 跳转到钱包充值页面
          uni.navigateTo({ url: '/pagesMember/wallet/wallet' })
        }
      },
    })
    return
  }

  // 余额足够，显示支付确认
  uni.showModal({
    content: `需要支付 ¥${price}，是否预订酒店？`,
    success: async (res) => {
      if (res.confirm) {
        try {
          // 显示加载
          uni.showLoading({ title: '预订中...', mask: true })

          // 调用购买接口
          await addOrderAPI({ relationId: id, type: 3 })

          // 购买成功，计算扣款后的余额
          const newBalance = currentBalance - price

          // 更新用户余额
          await patchUserBalanceAPI(userInfo.value.id, newBalance)

          // 更新本地用户信息
          userInfo.value.balance = newBalance

          uni.hideLoading()
          uni.showToast({
            icon: 'success',
            title: '预订成功',
            duration: 1500
          })

          // 延迟1.5秒后返回上一页
          setTimeout(() => {
            uni.navigateBack()
          }, 1500)
        } catch (error: any) {
          uni.hideLoading()

          // 处理异常情况，API 返回的错误信息
          let errorMessage = '预订失败，请重试'

          if (error?.msg) {
            errorMessage = error.msg
          }

          // 显示错误提示给用户
          uni.showToast({
            icon: 'error',
            title: errorMessage,
            duration: 2000
          })

          console.error('预订失败:', {
            code: error?.code,
            message: errorMessage,
            fullError: error
          })
        }
      }
    },
  })
}
const coolect = (id: number, name: string) => {
  // 模态弹窗
  uni.showModal({
    content: '是否要收藏酒店 ' + name + ' ?',
    success: (res) => {
      if (res.confirm) {
        console.log("确定了", id)
        const res = addCollectAPI({ relationId: id, type: 3 })
        console.log("res", res)
        if (memberStore.profile) {
          uni.showToast({ icon: "success", title: "收藏成功" })
        }
      }
    },
  })
}

</script>

<template>
  <scroll-view scroll-y class="viewport">
    <view class="goods">
      <view class="preview">
        <swiper @change="onchange" circular>
          <swiper-item v-for="item in hotels" :key="item">
            <image @tap="onTapImage(item.url)" mode="aspectFill" :src="item.url" />
          </swiper-item>
        </swiper>
        <view class="indicator">
          <text class="current">{{ currentIndex + 1 }}</text>
          <text class="split">/</text>
          <text class="total">1</text>
        </view>
      </view>
      <view class="meta">
        <view class="price">
          <text class="number">{{ hotel?.name }}</text>
        </view>
        <view class="name ellipsis">{{ hotel?.shortRecommend }}</view>
      </view>
      <view class="operation">
        <view class="item">
          <text class="label">门票</text>
          <text class="text ellipsis"> {{ hotel?.price }} </text>
        </view>
        <view class="item">
          <text class="label">地址</text>
          <text class="text ellipsis"> {{ hotel?.address }} </text>
        </view>
        <view class="item">
          <text class="label">来源</text>
          <text class="text ellipsis"> 重庆旅游平台 </text>
        </view>
      </view>
    </view>

    <view class="detail panel">
      <view class="title">
        <text>酒店详情</text>
      </view>
      <view class="content">
        <rich-text :nodes="hotel?.content"></rich-text>
      </view>
    </view>
  </scroll-view>
  <!-- 用户操作 -->
  <view class="toolbar" :style="{ paddingBottom: safeAreaInsets?.bottom + 'px' }">
    <view class="icons">
    </view>
    <view class="buttons">
      <view class="addcart" @tap="buy(hotel!.id, hotel!.price)"> 酒店预订 </view>
      <view class="payment" @tap="coolect(hotel!.id, hotel!.name)"> 加入收藏 </view>
    </view>
  </view>
</template>

<style lang="scss">
page {
  height: 100%;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.viewport {
  background-color: #f4f4f4;
}

.panel {
  margin-top: 20rpx;
  background-color: #fff;

  .title {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 90rpx;
    line-height: 1;
    padding: 30rpx 60rpx 30rpx 6rpx;
    position: relative;

    text {
      padding-left: 10rpx;
      font-size: 28rpx;
      color: #333;
      font-weight: 600;
      border-left: 4rpx solid #27ba9b;
    }

    navigator {
      font-size: 24rpx;
      color: #666;
    }
  }
}

.arrow {
  &::after {
    position: absolute;
    top: 50%;
    right: 30rpx;
    content: '\e6c2';
    color: #ccc;
    font-family: 'erabbit' !important;
    font-size: 32rpx;
    transform: translateY(-50%);
  }
}

.goods {
  background-color: #fff;

  .preview {
    height: 750rpx;
    position: relative;

    .indicator {
      height: 40rpx;
      padding: 0 24rpx;
      line-height: 40rpx;
      border-radius: 30rpx;
      color: #fff;
      font-family: Arial, Helvetica, sans-serif;
      background-color: rgba(0, 0, 0, 0.3);
      position: absolute;
      bottom: 30rpx;
      right: 30rpx;

      .current {
        font-size: 26rpx;
      }

      .split {
        font-size: 24rpx;
        margin: 0 1rpx 0 2rpx;
      }

      .total {
        font-size: 24rpx;
      }
    }
  }

  .meta {
    position: relative;
    border-bottom: 1rpx solid #eaeaea;

    .price {
      height: 130rpx;
      padding: 25rpx 30rpx 0;
      color: #fff;
      font-size: 34rpx;
      box-sizing: border-box;
      background-color: #35c8a9;
    }

    .number {
      font-size: 56rpx;
    }

    .brand {
      width: 160rpx;
      height: 80rpx;
      overflow: hidden;
      position: absolute;
      top: 26rpx;
      right: 30rpx;
    }

    .name {
      max-height: 88rpx;
      line-height: 1.4;
      margin: 20rpx;
      font-size: 32rpx;
      color: #333;
    }

    .desc {
      line-height: 1;
      padding: 0 20rpx 30rpx;
      font-size: 24rpx;
      color: #cf4444;
    }
  }

  .operation {
    padding-left: 20rpx;

    .item {
      height: 90rpx;
      padding-right: 60rpx;
      border-bottom: 1rpx solid #eaeaea;
      font-size: 26rpx;
      color: #333;
      position: relative;
      display: flex;
      align-items: center;

      &:last-child {
        border-bottom: 0 none;
      }
    }

    .label {
      width: 60rpx;
      color: #898b94;
      margin: 0 16rpx 0 10rpx;
    }

    .text {
      flex: 1;
      -webkit-line-clamp: 1;
    }
  }
}

.detail {
  padding-left: 20rpx;

  .content {
    margin-left: -20rpx;
  }

  .properties {
    padding: 0 20rpx;
    margin-bottom: 30rpx;

    .item {
      display: flex;
      line-height: 2;
      padding: 10rpx;
      font-size: 26rpx;
      color: #333;
      border-bottom: 1rpx dashed #ccc;
    }

    .label {
      width: 200rpx;
    }

    .value {
      flex: 1;
    }
  }
}

.similar {
  padding-left: 20rpx;

  .content {
    padding: 0 20rpx 20rpx;
    margin-left: -20rpx;
    background-color: #f4f4f4;
    overflow: hidden;

    navigator {
      width: 345rpx;
      padding: 24rpx 20rpx 20rpx;
      margin: 20rpx 20rpx 0 0;
      border-radius: 10rpx;
      background-color: #fff;
      float: left;
    }

    .image {
      height: 260rpx;
    }

    .name {
      height: 80rpx;
      margin: 10rpx 0;
      font-size: 26rpx;
      color: #262626;
    }

    .price {
      line-height: 1;
      font-size: 20rpx;
      color: #cf4444;
    }

    .number {
      font-size: 26rpx;
      margin-left: 2rpx;
    }
  }

  navigator {
    &:nth-child(even) {
      margin-right: 0;
    }
  }
}

.toolbar {
  background-color: #fff;
  height: 100rpx;
  padding: 0 20rpx;
  border-top: 1rpx solid #eaeaea;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-sizing: content-box;

  .buttons {
    display: flex;

    &>view {
      width: 220rpx;
      text-align: center;
      line-height: 72rpx;
      font-size: 26rpx;
      color: #fff;
      border-radius: 72rpx;
    }

    .addcart {
      background-color: #ffa868;
    }

    .payment {
      background-color: #27ba9b;
      margin-left: 20rpx;
    }
  }

  .icons {
    padding-right: 10rpx;
    display: flex;
    align-items: center;
    flex: 1;

    .icons-button {
      flex: 1;
      text-align: center;
      line-height: 1.4;
      padding: 0;
      margin: 0;
      border-radius: 0;
      font-size: 20rpx;
      color: #333;
      background-color: #fff;
    }

    text {
      display: block;
      font-size: 34rpx;
    }
  }
}
</style>
