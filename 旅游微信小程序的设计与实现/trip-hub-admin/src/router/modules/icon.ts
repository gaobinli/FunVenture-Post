import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
  {
    path: '/icon',
    component: Layout,
    redirect: '/icon',
    meta: { title: 'message.menu.icon.name', icon: 'sfont system-component' },
    alwayShow: false,
    children: [
      {
        path: 'icon',
        component: createNameComponent(() => import('@/views/back/icon/index.vue')),
        meta: { title: 'message.menu.icon.name', icon: 'sfont system-component', hideClose: true }
      }
    ]
  }
]

export default route