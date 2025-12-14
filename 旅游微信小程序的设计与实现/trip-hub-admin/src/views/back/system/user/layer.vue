<template>
  <Layer :layer="layer" @confirm="submit" ref="layerDom">
    <el-form :model="form" :rules="rules" ref="ruleForm" label-width="120px" style="margin-right:30px;">
      <el-form-item label="账号：" prop="code">
        <el-input v-model="form.code" :disabled="form.id === '' ? false : true" placeholder="请输入账号"></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="password">
        <el-input v-model="form.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item label="姓名：" prop="name">
        <el-input v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="手机号：" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="邮箱：">
        <el-input v-model="form.email" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="角色：">
        <el-select v-model="form.roleCodes" multiple placeholder="选择角色，非必选">
          <el-option
            v-for="item in roleOptions"
            :key="item.code"
            :label="item.name"
            :value="item.code"
            >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否启用：">
          <el-switch v-model="form.enable" />
      </el-form-item>
    </el-form>
  </Layer>
</template>

<script lang="ts">
import type { LayerType } from '@/components/layer/index.vue'
import type { Ref } from 'vue'
import type { ElFormItemContext } from 'element-plus/lib/el-form/src/token'
import { defineComponent, ref } from 'vue'
import { add, update } from '@/api/back/system/user'
import { roleList, getUserRoleList } from '@/api/back/system/role'
import Layer from '@/components/layer/index.vue'
export default defineComponent({
  components: {
    Layer
  },
  props: {
    layer: {
      type: Object,
      default: () => {
        return {
          show: false,
          title: '',
          showButton: true
        }
      }
    }
  },
  setup(props, ctx) {
    const ruleForm: Ref<ElFormItemContext|null> = ref(null)
    const layerDom: Ref<LayerType|null> = ref(null)
    let form = ref({
      id: '',
      code: '',
      name: '',
      email: '',
      password: '',
      enable: false,
      phone: '',
      roleCodes: []
    })
    let roleOptions = ref([])
    const rules = {
      code: [{ required: true, message: '请输入账号', trigger: 'blur' }],
      name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
      // email: [
      //   { required: true, message: '请输入邮箱', trigger: 'blur' },
      //   { pattern: /^[a-zA-Z0-9][a-zA-Z0-9_]+\@[a-zA-Z0-9]+\.(com|cn|net|com.cn)$/i, message: '邮箱格式不正确', trigger: 'blur' }
      // ],
      enable: [{ required: true, message: '请选择是否启用', trigger: 'blur' }],
      phone: [
        { required: true, message: '请输入手机号', trigger: 'blur' },
        { pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: '手机号格式不正确', trigger: 'blur'}
      ]
    }
    init()
    function init() { // 用于判断新增还是编辑功能
      if (props.layer.row) {
        form.value = JSON.parse(JSON.stringify(props.layer.row)) // 数量量少的直接使用这个转
        getUserRoleList(form.value.id)
        .then(res => {
          res.data.forEach((v) => {
              form.value.roleCodes.push(v.code)
          })
        })
      } else {
        
      }
      roleList()
      .then(res => {
        roleOptions.value = res.data
      })
    }
    return {
      form,
      rules,
      layerDom,
      ruleForm,
      roleOptions
    }
  },
  methods: {
    submit() {
      if (this.ruleForm) {
        this.ruleForm.validate((valid) => {
          if (valid) {
            let params = this.form
            if (this.layer.row) {
              this.updateForm(params)
            } else {
              this.addForm(params)
            }
          } else {
            return false;
          }
        });
      }
    },
    // 新增提交事件
    addForm(params: object) {
      params.password = params.code
      add(params)
      .then(res => {
        this.$message({
          type: 'success',
          message: '新增成功'
        })
        this.$emit('getTableData', true)
        this.layerDom && this.layerDom.close()
      })
    },
    // 编辑提交事件
    updateForm(params: any) {
      update(params.id, params)
      .then(res => {
        this.$message({
          type: 'success',
          message: '编辑成功'
        })
        this.$emit('getTableData', false)
        this.layerDom && this.layerDom.close()
      })
    }
  }
})
</script>

<style lang="scss" scoped>
  
</style>