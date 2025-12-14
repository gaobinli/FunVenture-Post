import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
  {
    path: '/scenicSpot',
    component: Layout,
    redirect: '/scenicSpot/scenicSpot',
    meta: { title: 'message.menu.scenicSpot.name', icon: 'sfont system-yanjing' },
    alwayShow: false,
    children: [
      {
        path: 'scenicSpot',
        component: createNameComponent(() => import('@/views/back/scenicSpot/index.vue')),
        meta: { title: 'message.menu.scenicSpot.name', cache: false }
      },
      {
        path: 'add',
        component: createNameComponent(() => import('@/views/back/scenicSpot/add.vue')),
        hideMenu: true,
        meta: { title: 'message.menu.scenicSpot.add', cache: false }
      },
      {
        path: 'update',
        component: createNameComponent(() => import('@/views/back/scenicSpot/update.vue')),
        hideMenu: true,
        meta: { title: 'message.menu.scenicSpot.update', cache: false }
      }
    ]
  }
]

export default route