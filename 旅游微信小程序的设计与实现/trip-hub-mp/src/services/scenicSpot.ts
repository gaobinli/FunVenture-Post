import type { ScenicSpotItem } from '@/types/scenicSpot'
import { http } from '@/utils/http'

export const getScenicSpotByIdAPI = (id: string) => {
  return http<ScenicSpotItem>({
    method: 'GET',
    url: '/scenicSpot/' + id,
  })
}
