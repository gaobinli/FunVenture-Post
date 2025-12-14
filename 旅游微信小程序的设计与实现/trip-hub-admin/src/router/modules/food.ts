import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
  {
    path: '/food',
    component: Layout,
    redirect: '/food/food',
    meta: { title: 'message.menu.food.name', icon: 'sfont system-document' },
    alwayShow: false,
    children: [
      {
        path: 'food',
        component: createNameComponent(() => import('@/views/back/food/index.vue')),
        meta: { title: 'message.menu.food.name', cache: false }
      },
      {
        path: 'add',
        component: createNameComponent(() => import('@/views/back/food/add.vue')),
        hideMenu: true,
        meta: { title: 'message.menu.food.add', cache: false }
      },
      {
        path: 'update',
        component: createNameComponent(() => import('@/views/back/food/update.vue')),
        hideMenu: true,
        meta: { title: 'message.menu.food.update', cache: false }
      }
    ]
  }
]

export default route