import type { OrderListResult } from '@/types/order'
import type {
  OrderReq,
  OrderCreateParams,
  OrderListParams,
  OrderLogisticResult,
  OrderPreResult,
  OrderResult,
} from '@/types/order'
import { http } from '@/utils/http'


// 淦
export const addCollectAPI = (data: OrderReq) => {
  return http<OrderPreResult>({
    method: 'POST',
    url: '/collect',
    data,
  })
}

// 淦
export const deleteMemberCollectAPI = (ids: string) => {
  return http({
    method: 'DELETE',
    url: `/collect/${ids}`,
  })
}

// 淦
export const getMemberCollectPageAPI = (data: OrderListParams) => {
  return http<OrderPreResult>({
    method: 'GET',
    url: '/collect',
    data,
  })
}