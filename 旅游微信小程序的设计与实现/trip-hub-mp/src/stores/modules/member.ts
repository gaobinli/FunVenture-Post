import type { LoginResult } from '@/types/member'
import { defineStore } from 'pinia'
import { reactive, ref } from 'vue'

// 定义 Store
export const useMemberStore = defineStore(
  'member',
  () => {
    // 信息
    const profile = ref<LoginResult>()

    // 保存信息，登录时使用
    const setProfile = (val: LoginResult) => {
      profile.value = val
    }

    // 清理信息，退出时使用
    const clearProfile = () => {
      profile.value = undefined
    }

    // 记得 return
    return {
      profile,
      setProfile,
      clearProfile,
    }
  },
  // TODO: 持久化
  {
    // 网页端可以这样配置
    // persist: true,
    persist: {
      storage: {
        getItem(key) {
          return uni.getStorageSync(key)
        },
        setItem(key, value) {
          uni.setStorageSync(key, value)
        }
      }
    }
  },
)
