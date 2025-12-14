import type { CustomScenicSpotRecommendInstance } from '@/types/components'
import type { CustomFoodRecommendInstance } from '@/types/components'
import type { CustomHotelRecommendInstance } from '@/types/components'

import { ref } from 'vue'

/**
 * 酒店推荐组合式函数
 */
export const useCustomHotelRecommendList = () => {
  const hotelRef = ref<CustomHotelRecommendInstance>()
  // 滚动触底事件
  const onScrolltolower = () => {
    hotelRef.value?.getMore()
  }
  // 返回 ref 和事件处理函数
  return {
    hotelRef,
    onScrolltolower,
  }
}

/**
 * 美食推荐组合式函数
 */
export const useCustomFoodRecommendList = () => {
  const foodRef = ref<CustomFoodRecommendInstance>()
  // 滚动触底事件
  const onScrolltolower = () => {
    foodRef.value?.getMore()
  }
  // 返回 ref 和事件处理函数
  return {
    foodRef,
    onScrolltolower,
  }
}


/**
 * 景点推荐组合式函数
 */
export const useCustomScenicSpotRecommendList = () => {
  const scenicSpotRef = ref<CustomScenicSpotRecommendInstance>()
  // 滚动触底事件
  const onScrolltolower = () => {
    scenicSpotRef.value?.getMore()
  }
  // 返回 ref 和事件处理函数
  return {
    scenicSpotRef,
    onScrolltolower,
  }
}

