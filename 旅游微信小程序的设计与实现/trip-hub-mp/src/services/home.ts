import type { PageParams, PageResult } from '@/types/global'
import type { BannerItem, CategoryItem, GuessItem } from '@/types/home'
import { http } from '@/utils/http'

export const getHomeBannerAPI = () => {
    return http<BannerItem[]>({
        method: 'GET',
        url: '/carousel/list'
    })
}

export const getHomeCategoryAPI = () => {
    return http<CategoryItem[]>({
        method: 'GET',
        url: '/icon/list'
    })
}

export const getScenicSpotAPI = (data?: PageParams) => {
    return http<PageResult<GuessItem>[]>({
        method: 'GET',
        url: '/scenicSpot',
        data
    })
}