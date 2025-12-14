import type {
  OrderReq,
  OrderListParams,
  OrderPreResult,
} from '@/types/order'
import { http } from '@/utils/http'


export const addOrderAPI = (data: OrderReq) => {
  return http<OrderPreResult>({
    method: 'POST',
    url: '/order',
    data,
  })
}

export const deleteMemberOrderAPI = (ids: string) => {
  return http({
    method: 'DELETE',
    url: `/order/${ids}`,
  })
}

export const getMemberOrderPageAPI = (data: OrderListParams) => {
  return http<OrderPreResult>({
    method: 'GET',
    url: '/order',
    data,
  })
}

/**
 * 更新订单状态
 * @param orderId 订单ID
 * @param status 新状态 (2: 待收货, 3: 已收货, 4: 退货中)
 */
export const patchOrderStatusAPI = (orderId: string, status: number) => {
  return http({
    method: 'PATCH',
    url: `/order/${orderId}`,
    data: {
      status
    }
  })
}