import type { PageParams, PageResult } from '@/types/global'
import type { FoodItem } from '@/types/food'
import { http } from '@/utils/http'


export const getFoodAPI = (data?: PageParams) => {
    return http<PageResult<FoodItem>[]>({
        method: 'GET',
        url: '/food',
        data
    })
}


export const getFoodByIdAPI = (id: string) => {
    return http<FoodItem>({
        method: 'GET',
        url: '/food/' + id,
    })
}
