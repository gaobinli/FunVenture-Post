import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
  {
    path: '/order',
    component: Layout,
    redirect: '/order/order',
    meta: { title: 'message.menu.order.name', icon: 'sfont system-zuixiaohua' },
    alwayShow: false,
    children: [
      {
        path: 'order',
        component: createNameComponent(() => import('@/views/back/order/index.vue')),
        meta: { title: 'message.menu.order.name', cache: false }
      }
    ]
  }
]

export default route