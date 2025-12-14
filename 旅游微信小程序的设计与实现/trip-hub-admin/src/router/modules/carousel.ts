import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
  {
    path: '/carousel',
    component: Layout,
    redirect: '/carousel',
    meta: { title: 'message.menu.carousel.name', icon: 'sfont system-shuliang_mianxing' },
    alwayShow: false,
    children: [
      {
        path: 'carousel',
        component: createNameComponent(() => import('@/views/back/carousel/index.vue')),
        meta: { title: 'message.menu.carousel.name', icon: 'sfont system-shuliang_mianxing', hideClose: true }
      }
    ]
  }
]

export default route