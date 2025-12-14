import CustomSwiper from '@/components/CustomSwiper.vue'
import CustomScenicSpotRecommend from '@/components/CustomScenicSpotRecommend.vue'
import CustomFoodRecommend from '@/components/CustomFoodRecommend.vue'
import CustomHotelRecommend from '@/components/CustomHotelRecommend.vue'



declare module 'vue' {
  export interface GlobalComponents {
    CustomSwiper: typeof CustomSwiper
    CustomScenicSpotRecommend: typeof CustomScenicSpotRecommend
    CustomFoodRecommend: typeof CustomFoodRecommend
    CustomHotelRecommend: typeof CustomHotelRecommend
  }
}

// 组件实例类型
export type XtxGuessInstance = InstanceType<typeof XtxGuess>
export type CustomSwiperInstance = InstanceType<typeof CustomSwiper>
export type CustomScenicSpotRecommendInstance = InstanceType<typeof CustomScenicSpotRecommend>
export type CustomFoodRecommendInstance = InstanceType<typeof CustomFoodRecommend>
export type CustomHotelRecommendInstance = InstanceType<typeof CustomHotelRecommend>


