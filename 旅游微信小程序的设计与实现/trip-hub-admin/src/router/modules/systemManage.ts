import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
  {
    path: '/systemManage',
    component: Layout,
    redirect: '/systemManage/user',
    meta: { title: 'message.menu.systemManage.name', icon: 'sfont system-page' },
    alwayShow: true,
    children: [
      {
        path: 'user',
        component: createNameComponent(() => import('@/views/back/system/user/index.vue')),
        meta: { title: 'message.menu.systemManage.user', cache: false }
      },
      {
        path: 'role',
        component: createNameComponent(() => import('@/views/back/system/role/index.vue')),
        meta: { title: 'message.menu.systemManage.role', cache: false }
      },
      {
        path: 'menu',
        component: createNameComponent(() => import('@/views/back/system/menu/index.vue')),
        meta: { title: 'message.menu.systemManage.menu', cache: false }
      },
      
    ]
  }
]

export default route