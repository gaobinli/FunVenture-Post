<template>
  <div class="layout-container">
    <div class="layout-container-form flex space-between">
      <div class="layout-container-form-search">
        <el-form :inline="true" :model="params" class="demo-form-inline">
          <el-form-item label="账号：">
              <el-input v-model="query.qp_code_like" placeholder="请输入用户账号" clearable></el-input>
          </el-form-item>
          <el-form-item label="用户名称：">
              <el-input v-model="query.qp_name_like" placeholder="请输入用户名称" clearable></el-input>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" @click="getTableData()">查询</el-button>
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
        <el-table-column prop="code" label="账号" align="center" />
        <el-table-column prop="name" label="名字" align="center" />
        <el-table-column prop="phone" label="手机号" align="center" />
        <el-table-column prop="email" label="邮箱" align="center" />
        <el-table-column prop="" label="启用状态">
            <template #default="scope">
                <el-switch v-model="scope.row.enable" @change="updateEnableStatus(scope.row)" />
            </template>
        </el-table-column>
        <el-table-column prop="createUser" label="创建人" align="center" />
        <el-table-column :label="$t('message.common.handle')" align="center" fixed="right" width="200">
          <template #default="scope">
            <el-button @click="handleEdit(scope.row)">{{ $t('message.common.update') }}</el-button>
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
import { dataList, del, patch } from '@/api/back/system/user'
import Layer from './layer.vue'
import { ElMessage } from 'element-plus'
import type { LayerInterface } from '@/components/layer/index.vue'
import { Plus, Search, Delete } from '@element-plus/icons'
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
      row.password = null;
      layer.title = '编辑数据'
      layer.row = row
      layer.show = true
    }
    const updateEnableStatus = (row: any) => {
      patch(row.id, row)
      .then(res => {
        getTableData
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
      handleSelectionChange,
      handleAdd,
      handleEdit,
      handleDel,
      getTableData,
      updateEnableStatus
    }
  }
})
</script>

<style lang="scss" scoped>
  
</style>