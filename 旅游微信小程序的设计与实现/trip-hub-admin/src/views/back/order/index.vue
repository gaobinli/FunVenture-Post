
<template>
  <div class="layout-container">
    <div class="layout-container-form flex space-between">
      <div class="layout-container-form-search">
        <el-form :inline="true" :model="params" class="demo-form-inline">
          <el-form-item label="订单类型：">
            <el-select v-model="query.qp_type_like" placeholder="请选择订单类型" clearable>
              <el-option label="景点" :value="1" />
              <el-option label="美食" :value="2" />
              <el-option label="酒店" :value="3" />
            </el-select>
          </el-form-item>
          <el-form-item label="用户：">
            <el-select v-model="query.qp_userId_like" placeholder="请选择用户" clearable>
              <el-option
                v-for="user in userList"
                :key="user.id"
                :label="user.nickName"
                :value="user.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态：">
            <el-select v-model="query.qp_status_like" placeholder="请选择状态" clearable>
              <el-option label="待发货" :value="1" />
              <el-option label="待收货" :value="2" />
              <el-option label="已收货" :value="3" />
              <el-option label="退货中" :value="4" />
              <el-option label="已退货" :value="5" />
            </el-select>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" @click="getTableData(true)">查询</el-button>
          </el-form-item>
        </el-form>
       </div>
    </div>
    <div class="layout-container-form flex space-between">
      <div class="layout-container-form-handle">
        <el-button type="primary" :icon="Plus" @click="handleAdd">{{ $t('message.common.add') }}</el-button>
        <el-popconfirm :title="$t('message.common.delTip')" @confirm="handleDel(chooseData)">
          <template #reference>
            <el-button type="danger" :icon="Delete" :disabled="chooseData.length === 0">{{ $t('message.common.delBat') }}</el-button>
          </template>
        </el-popconfirm>
      </div>
    </div>
    <div class="layout-container-table">
      <Table
        ref="table"
        v-model:page="page"
        v-loading="loading"
        :showIndex="true"
        :showSelection="true"
        :data="tableData"
        @getTableData="getTableData"
        @selection-change="handleSelectionChange"
      >
        <el-table-column prop="type" label="订单类型" align="center">
          <template #default="scope">
            {{ getTypeText(scope.row.type) }}
          </template>
        </el-table-column>
        <el-table-column prop="userId" label="用户" align="center">
          <template #default="scope">
            {{ getUserNickName(scope.row.userId) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" align="center">
          <template #default="scope">
            {{ getStatusText(scope.row.status) }}
          </template>
        </el-table-column>
        <el-table-column prop="name" label="名称" align="center" />
        <el-table-column prop="createTime" label="创建时间" align="center" :formatter='handleTime' />
        <el-table-column :label="$t('message.common.handle')" align="center" fixed="right" width="300">
          <template #default="scope">
            <el-button @click="handleEdit(scope.row)">{{ $t('message.common.update') }}</el-button>
            <el-button
              v-if="scope.row.status === 1"
              type="success"
              @click="handleShipOrder(scope.row)">
              发货
            </el-button>
            <el-button
              v-if="scope.row.status === 4"
              type="warning"
              @click="handleReturnOrder(scope.row)">
              退货
            </el-button>
            <el-popconfirm :title="$t('message.common.delTip')" @confirm="handleDel([scope.row])">
              <template #reference>
                <el-button type="danger">{{ $t('message.common.del') }}</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </Table>
      <Layer :layer="layer" @getTableData="getTableData" v-if="layer.show" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, reactive } from 'vue'
import Table from '@/components/table/index.vue'
import { Page } from '@/components/table/type'
import { dataList, del, patch } from '@/api/back/order'
import { userList as fetchUserList } from '@/api/back/user'
import Layer from './layer.vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { LayerInterface } from '@/components/layer/index.vue'
import { Plus, Search, Delete } from '@element-plus/icons'
import { handleTime } from '@/utils/system/util'

// 订单类型枚举
const TYPE_MAP: Record<number, string> = {
  1: '景点',
  2: '美食',
  3: '酒店'
}

// 订单状态枚举
const STATUS_MAP: Record<number, string> = {
  1: '待发货',
  2: '待收货',
  3: '已收货',
  4: '退货中',
  5: '已退货'
}

export default defineComponent({
  name: 'crudTable',
  components: {
    Table,
    Layer
  },
  setup() {
    // 存储搜索用的数据
    const query = reactive({

    })
    // 用户列表
    const userList = ref<any[]>([])
    // 弹窗控制器
    const layer: LayerInterface = reactive({
      show: false,
      title: '新增',
      showButton: true
    })
    // 分页参数, 供table使用
    const page: Page = reactive({
      currentPage: 1,
      pageSize: 10,
      totalCount: 0
    })
    const loading = ref(true)
    const tableData = ref([])
    const chooseData = ref([])
    // 用户ID和nickName的映射Map
    const userNickNameMap = ref(new Map())
    const handleSelectionChange = (val: []) => {
      chooseData.value = val
    }
    // 获取表格数据
    // params <init> Boolean ，默认为false，用于判断是否需要初始化分页
    const getTableData = (init: boolean) => {
      loading.value = true
      if (init) {
        page.currentPage = 1
      }
      let params = {
        currentPage: page.currentPage,
        pageSize: page.pageSize,
        ...query
      }
      dataList(params)
      .then(res => {
        tableData.value = res.data.list
        page.totalCount = Number(res.data.totalCount)
      })
      .catch(error => {
        tableData.value = []
        page.currentPage = 1
        page.totalCount = 0
      })
      .finally(() => {
        loading.value = false
      })
    }
    // 删除功能
    const handleDel = (data: object[]) => {
      let params = {
        ids: data.map((e:any)=> {
          return e.id
        }).join(',')
      }
      del(params.ids)
      .then(res => {
        ElMessage({
          type: 'success',
          message: '删除成功'
        })
        getTableData(tableData.value.length === 1 ? true : false)
      })
    }
    // 新增弹窗功能
    const handleAdd = () => {
      layer.title = '新增数据'
      layer.show = true
      delete layer.row
    }
    // 编辑弹窗功能
    const handleEdit = (row: object) => {
      layer.title = '编辑数据'
      layer.row = row
      layer.show = true
    }
    // 获取订单类型文本
    const getTypeText = (type: number) => {
      return TYPE_MAP[type] || '未知'
    }
    // 获取订单状态文本
    const getStatusText = (status: number) => {
      return STATUS_MAP[status] || '未知'
    }
    // 获取用户昵称
    const getUserNickName = (userId: number) => {
      return userNickNameMap.value.get(userId) || '未知用户'
    }
    // 获取所有用户列表
    const loadUserList = () => {
      fetchUserList()
      .then((res: any) => {
        if (res.data && res.data.length > 0) {
          userList.value = res.data
          // 建立ID和nickName的映射
          res.data.forEach((user: any) => {
            userNickNameMap.value.set(user.id, user.nickName)
          })
        }
      })
      .catch(error => {
        console.error('获取用户列表失败', error)
      })
    }
    // 发货处理
    const handleShipOrder = (row: any) => {
      ElMessageBox.confirm(
        '确认要发货吗？',
        '发货确认',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }
      )
      .then(() => {
        patch(row.id, { status: 2 })
        .then(res => {
          ElMessage({
            type: 'success',
            message: '发货成功'
          })
          getTableData(false)
        })
        .catch(error => {
          ElMessage({
            type: 'error',
            message: '发货失败'
          })
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '已取消发货'
        })
      })
    }
    // 退货处理
    const handleReturnOrder = (row: any) => {
      ElMessageBox.confirm(
        '确认要完成退货吗？',
        '退货确认',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }
      )
      .then(() => {
        patch(row.id, { status: 5 })
        .then(res => {
          ElMessage({
            type: 'success',
            message: '退货成功'
          })
          getTableData(false)
        })
        .catch(error => {
          ElMessage({
            type: 'error',
            message: '退货失败'
          })
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '已取消退货'
        })
      })
    }
    // 初始化时加载用户列表和订单数据
    loadUserList()
    getTableData(true)
    return {
      Plus,
      Search,
      Delete,
      query,
      tableData,
      chooseData,
      loading,
      page,
      layer,
      userList,
      handleSelectionChange,
      handleAdd,
      handleEdit,
      handleDel,
      getTableData,
      handleTime,
      getTypeText,
      getStatusText,
      getUserNickName,
      handleShipOrder,
      handleReturnOrder
    }
  }
})
</script>

<style lang="scss" scoped>

</style>