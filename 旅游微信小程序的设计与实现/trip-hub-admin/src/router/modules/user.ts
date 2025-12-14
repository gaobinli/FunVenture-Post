import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
  {
    path: '/user',
    component: Layout,
    redirect: '/user',
    meta: { title: 'message.menu.user.name', icon: 'sfont system-xingmingyonghumingnicheng' },
    alwayShow: false,
    children: [
      {
        path: 'user',
        component: createNameComponent(() => import('@/views/back/user/index.vue')),
        meta: { title: 'message.menu.user.name', icon: 'sfont system-xingmingyonghumingnicheng', hideClose: true }
      }
    ]
  }
]

export default route