import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
  {
    path: '/hotel',
    component: Layout,
    redirect: '/hotel/hotel',
    meta: { title: 'message.menu.hotel.name', icon: 'sfont system-zuixiaohua' },
    alwayShow: false,
    children: [
      {
        path: 'hotel',
        component: createNameComponent(() => import('@/views/back/hotel/index.vue')),
        meta: { title: 'message.menu.hotel.name', cache: false }
      },
      {
        path: 'add',
        component: createNameComponent(() => import('@/views/back/hotel/add.vue')),
        hideMenu: true,
        meta: { title: 'message.menu.hotel.add', cache: false }
      },
      {
        path: 'update',
        component: createNameComponent(() => import('@/views/back/hotel/update.vue')),
        hideMenu: true,
        meta: { title: 'message.menu.hotel.update', cache: false }
      }
    ]
  }
]

export default route