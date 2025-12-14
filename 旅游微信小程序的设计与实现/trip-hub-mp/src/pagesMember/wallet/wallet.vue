<script setup lang="ts">
import { useMemberStore } from '@/stores'
import { getMemberProfileAPI } from '@/services/profile'
import { patchUserBalanceAPI } from '@/services/profile'
import { ref, reactive } from 'vue'
import { onShow } from '@dcloudio/uni-app'

// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()

const memberStore = useMemberStore()
const userInfo = ref<any>(null)

// 钱包数据
const walletData = reactive({
  currentBalance: 0,
  rechargeAmount: '',
  paymentMethod: 'wechat', // 'wechat', 'alipay', 'bank'
  showRechargeForm: false,
})

// 支付方式列表
const paymentMethods = [
  { value: 'wechat', label: '微信支付', icon: '💬' },
  { value: 'alipay', label: '支付宝', icon: '🔵' },
  { value: 'bank', label: '银行卡', icon: '🏦' },
]

// 获取用户信息
onShow(async () => {
  if (memberStore.profile) {
    const res = await getMemberProfileAPI()
    userInfo.value = res.data
    walletData.currentBalance = userInfo.value.balance || 0
  }
})

// 显示/隐藏充值表单
const toggleRechargeForm = () => {
  walletData.showRechargeForm = !walletData.showRechargeForm
  if (!walletData.showRechargeForm) {
    walletData.rechargeAmount = ''
  }
}

// 验证充值金额
const validateRechargeAmount = () => {
  if (!walletData.rechargeAmount) {
    uni.showToast({
      icon: 'error',
      title: '请输入充值金额',
    })
    return false
  }

  const amount = parseFloat(walletData.rechargeAmount)
  if (isNaN(amount) || amount <= 0) {
    uni.showToast({
      icon: 'error',
      title: '充值金额必须大于0',
    })
    return false
  }

  if (amount > 100000) {
    uni.showToast({
      icon: 'error',
      title: '充值金额不能超过100000',
    })
    return false
  }

  return true
}

// 处理充值
const handleRecharge = async () => {
  if (!validateRechargeAmount()) {
    return
  }

  const rechargeAmount = parseFloat(walletData.rechargeAmount)
  const methodLabel = paymentMethods.find(m => m.value === walletData.paymentMethod)?.label || '微信支付'

  // 显示处理中
  uni.showLoading({
    title: '处理中...',
    mask: true,
  })

  try {
    // 模拟支付过程（实际应该调用支付接口）
    setTimeout(async () => {
      try {
        // 计算新余额
        const newBalance = walletData.currentBalance + rechargeAmount

        // 调用API更新余额
        const updateRes = await patchUserBalanceAPI(userInfo.value.id, newBalance)

        // 更新本地状态
        walletData.currentBalance = newBalance
        walletData.rechargeAmount = ''
        walletData.showRechargeForm = false

        uni.hideLoading()
        uni.showToast({
          icon: 'success',
          title: `充值成功 +¥${rechargeAmount.toFixed(2)}`,
        })

        // 更新用户信息缓存
        userInfo.value.balance = newBalance
      } catch (error) {
        uni.hideLoading()
        uni.showToast({
          icon: 'error',
          title: '充值失败，请重试',
        })
        console.error('充值失败:', error)
      }
    }, 1500)
  } catch (error) {
    uni.hideLoading()
    uni.showToast({
      icon: 'error',
      title: '处理失败',
    })
    console.error('错误:', error)
  }
}

// 选择支付方式
const selectPaymentMethod = (method: string) => {
  walletData.paymentMethod = method
}

// 取消充值
const cancelRecharge = () => {
  walletData.rechargeAmount = ''
  walletData.showRechargeForm = false
}
</script>

<template>
  <view class="viewport" :style="{ paddingTop: safeAreaInsets!.top + 'px' }">
    <!-- 导航栏 -->
    <view class="navbar">
      <navigator open-type="navigateBack" class="back" hover-class="none">
        <view class="back-icon">‹</view>
      </navigator>
      <view class="title">我的钱包</view>
      <view class="placeholder"></view>
    </view>

    <!-- 余额展示 -->
    <view class="balance-card">
      <view class="balance-title">当前余额</view>
      <view class="balance-amount">¥{{ walletData.currentBalance.toFixed(2) }}</view>
      <view class="balance-description">可用于订单支付</view>
    </view>

    <!-- 充值按钮 -->
    <view class="recharge-button-wrapper">
      <button
        v-if="!walletData.showRechargeForm"
        @tap="toggleRechargeForm"
        class="recharge-button"
      >
        + 立即充值
      </button>
    </view>

    <!-- 充值表单 -->
    <view v-if="walletData.showRechargeForm" class="recharge-form">
      <view class="form-title">充值金额</view>

      <!-- 金额输入 -->
      <view class="amount-input-wrapper">
        <view class="currency-symbol">¥</view>
        <input
          v-model="walletData.rechargeAmount"
          class="amount-input"
          type="number"
          placeholder="请输入充值金额"
          placeholder-class="placeholder"
        />
      </view>

      <!-- 快速充值金额 -->
      <view class="quick-amount">
        <view class="quick-title">快速充值</view>
        <view class="quick-buttons">
          <button
            v-for="amount in [50, 100, 500]"
            :key="amount"
            @tap="walletData.rechargeAmount = amount.toString()"
            class="quick-btn"
          >
            ¥{{ amount }}
          </button>
        </view>
      </view>

      <!-- 支付方式选择 -->
      <view class="payment-method">
        <view class="method-title">选择支付方式</view>
        <view class="method-list">
          <view
            v-for="method in paymentMethods"
            :key="method.value"
            @tap="selectPaymentMethod(method.value)"
            class="method-item"
            :class="{ active: walletData.paymentMethod === method.value }"
          >
            <view class="method-icon">{{ method.icon }}</view>
            <view class="method-label">{{ method.label }}</view>
            <view v-if="walletData.paymentMethod === method.value" class="method-check">✓</view>
          </view>
        </view>
      </view>

      <!-- 操作按钮 -->
      <view class="form-buttons">
        <button @tap="cancelRecharge" class="button cancel">取消</button>
        <button @tap="handleRecharge" class="button confirm">确认充值</button>
      </view>
    </view>

    <!-- 交易记录提示 -->
    <view v-if="!walletData.showRechargeForm" class="transaction-notice">
      <view class="notice-title">充值说明</view>
      <view class="notice-content">
        <view class="notice-item">✓ 充值金额实时到账</view>
        <view class="notice-item">✓ 支持微信、支付宝、银行卡支付</view>
        <view class="notice-item">✓ 充值后可用于所有订单支付</view>
        <view class="notice-item">✓ 余额不支持提现</view>
      </view>
    </view>
  </view>
</template>

<style lang="scss" scoped>
page {
  background-color: #f7f7f8;
  height: 100%;
}

.viewport {
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: #f7f7f8;
}

/* 导航栏 */
.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 44px;
  padding: 0 16px;
  background-color: #fff;
  border-bottom: 1rpx solid #eee;

  .back {
    width: 44px;
    height: 44px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #28bb9c;

    .back-icon {
      font-size: 32px;
      font-weight: bold;
    }
  }

  .title {
    flex: 1;
    text-align: center;
    font-size: 16px;
    font-weight: 500;
    color: #333;
  }

  .placeholder {
    width: 44px;
  }
}

/* 余额卡片 */
.balance-card {
  margin: 30rpx 20rpx;
  padding: 40rpx 30rpx;
  background: linear-gradient(135deg, #28bb9c 0%, #1e9b7c 100%);
  border-radius: 10rpx;
  color: #fff;
  text-align: center;
  box-shadow: 0 4rpx 15rpx rgba(40, 187, 156, 0.3);

  .balance-title {
    font-size: 24rpx;
    opacity: 0.9;
    margin-bottom: 16rpx;
  }

  .balance-amount {
    font-size: 64rpx;
    font-weight: bold;
    margin-bottom: 12rpx;
    letter-spacing: 2rpx;
  }

  .balance-description {
    font-size: 20rpx;
    opacity: 0.8;
  }
}

/* 充值按钮 */
.recharge-button-wrapper {
  padding: 0 20rpx 30rpx;

  .recharge-button {
    width: 100%;
    height: 80rpx;
    line-height: 80rpx;
    text-align: center;
    background-color: #28bb9c;
    color: #fff;
    font-size: 30rpx;
    border-radius: 8rpx;
    border: none;
  }
}

/* 充值表单 */
.recharge-form {
  background-color: #fff;
  margin: 20rpx;
  padding: 30rpx;
  border-radius: 10rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.1);

  .form-title {
    font-size: 28rpx;
    font-weight: 500;
    color: #333;
    margin-bottom: 20rpx;
  }

  /* 金额输入 */
  .amount-input-wrapper {
    display: flex;
    align-items: center;
    height: 80rpx;
    background-color: #f5f5f5;
    border-radius: 8rpx;
    padding: 0 20rpx;
    margin-bottom: 30rpx;
    border: 2rpx solid #e5e5e5;

    .currency-symbol {
      font-size: 32rpx;
      color: #28bb9c;
      font-weight: bold;
      margin-right: 10rpx;
    }

    .amount-input {
      flex: 1;
      height: 80rpx;
      font-size: 32rpx;
      background-color: transparent;
      border: none;

      .placeholder {
        color: #999;
      }
    }
  }

  /* 快速充值 */
  .quick-amount {
    margin-bottom: 30rpx;

    .quick-title {
      font-size: 24rpx;
      color: #666;
      margin-bottom: 12rpx;
    }

    .quick-buttons {
      display: flex;
      gap: 12rpx;

      .quick-btn {
        flex: 1;
        height: 60rpx;
        line-height: 60rpx;
        text-align: center;
        background-color: #f5f5f5;
        color: #28bb9c;
        font-size: 24rpx;
        border: 2rpx solid #ddd;
        border-radius: 6rpx;

        &:active {
          background-color: #e8f5f1;
          border-color: #28bb9c;
        }
      }
    }
  }

  /* 支付方式 */
  .payment-method {
    margin-bottom: 30rpx;

    .method-title {
      font-size: 24rpx;
      color: #666;
      margin-bottom: 16rpx;
    }

    .method-list {
      display: flex;
      gap: 16rpx;

      .method-item {
        flex: 1;
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 20rpx;
        background-color: #f5f5f5;
        border: 2rpx solid #ddd;
        border-radius: 8rpx;
        position: relative;

        &.active {
          background-color: #e8f5f1;
          border-color: #28bb9c;

          .method-label {
            color: #28bb9c;
          }
        }

        .method-icon {
          font-size: 40rpx;
          margin-bottom: 8rpx;
        }

        .method-label {
          font-size: 20rpx;
          color: #666;
        }

        .method-check {
          position: absolute;
          top: 8rpx;
          right: 8rpx;
          width: 28rpx;
          height: 28rpx;
          background-color: #28bb9c;
          color: #fff;
          border-radius: 50%;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 16rpx;
          font-weight: bold;
        }
      }
    }
  }

  /* 操作按钮 */
  .form-buttons {
    display: flex;
    gap: 16rpx;
    margin-top: 30rpx;

    .button {
      flex: 1;
      height: 80rpx;
      line-height: 80rpx;
      text-align: center;
      font-size: 28rpx;
      border-radius: 8rpx;
      border: none;

      &.cancel {
        background-color: #f5f5f5;
        color: #666;
      }

      &.confirm {
        background-color: #28bb9c;
        color: #fff;
      }
    }
  }
}

/* 充值说明 */
.transaction-notice {
  margin: 20rpx;
  padding: 20rpx;
  background-color: #fff;
  border-radius: 10rpx;

  .notice-title {
    font-size: 28rpx;
    font-weight: 500;
    color: #333;
    margin-bottom: 16rpx;
  }

  .notice-content {
    .notice-item {
      font-size: 24rpx;
      color: #666;
      line-height: 40rpx;
      padding-left: 10rpx;
    }
  }
}
</style>
