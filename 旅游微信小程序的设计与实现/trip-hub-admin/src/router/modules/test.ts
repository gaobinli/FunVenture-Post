import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
  {
    path: '/test',
    component: Layout,
    redirect: '/test/editor',
    meta: { title: 'message.menu.test.name', icon: 'sfont system-page' },
    alwayShow: true,
    children: [
      {
        path: 'editor',
        component: createNameComponent(() => import('@/views/back/test/wordEditor.vue')),
        meta: { title: 'message.menu.test.editor', cache: false }
      },
    //   {
    //     path: 'role',
    //     component: createNameComponent(() => import('@/views/back/system/role/index.vue')),
    //     meta: { title: 'message.menu.systemManage.role', cache: false }
    //   },
    //   {
    //     path: 'menu',
    //     component: createNameComponent(() => import('@/views/back/system/menu/index.vue')),
    //     meta: { title: 'message.menu.systemManage.menu', cache: false }
    //   },
      
    ]
  }
]

export default route