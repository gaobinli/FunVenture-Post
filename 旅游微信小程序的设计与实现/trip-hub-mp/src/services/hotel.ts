import type { PageParams, PageResult } from '@/types/global'
import type { HotelItem } from '@/types/hotel'
import { http } from '@/utils/http'


export const getHotelAPI = (data?: PageParams) => {
    return http<PageResult<HotelItem>[]>({
        method: 'GET',
        url: '/hotel',
        data
    })
}


export const getHotelByIdAPI = (id: string) => {
    return http<HotelItem>({
        method: 'GET',
        url: '/hotel/' + id,
    })
}
