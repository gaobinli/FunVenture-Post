<template>
  <Layer :layer="layer" @confirm="submit" ref="layerDom">
    <el-form :model="form" :rules="rules" ref="ruleForm" label-width="120px" style="margin-right:30px;">
      <el-form-item label="菜单编码：" prop="code">
        <el-input v-model="form.code" :disabled="form.id === '' ? false : true" placeholder="请输入菜单编码"></el-input>
      </el-form-item>
      <el-form-item label="菜单名称：" prop="name">
        <el-input v-model="form.name" placeholder="请输入菜单名称"></el-input>
      </el-form-item>
      <el-form-item label="菜单路径：">
        <el-input v-model="form.path" placeholder="请输入菜单路径"></el-input>
      </el-form-item>
      <el-form-item label="排序：" prop="sort">
        <el-input-number v-model="form.sort" :min="1" :max="99999" @change="handleChange" />
      </el-form-item>
    </el-form>
  </Layer>
</template>

<script lang="ts">
import type { LayerType } from '@/components/layer/index.vue'
import type { Ref } from 'vue'
import type { ElFormItemContext } from 'element-plus/lib/el-form/src/token'
import { defineComponent, ref } from 'vue'
import { add, update, getAllMenuList } from '@/api/back/system/menu'
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
    let selectedParentCode = ref('')
    let menuList = ref([])
    let form = ref({
      id: '',
      code: '',
      parentCode: '',
      name: '',
      path: '',
      icon: '',
      sort: ''
    })
    
    const rules = {
      code: [{ required: true, message: '请输入编码', trigger: 'blur' }],
      name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
      email: [{ required: true, message: '请选择', trigger: 'blur' }],
      enable: [{ required: true, message: '请选择', trigger: 'blur' }],
      phone: [{ required: true, message: '请选择', trigger: 'blur' }]
    }
    init()
    function init() { // 用于判断新增还是编辑功能
      if (props.layer.row) {
        form.value = JSON.parse(JSON.stringify(props.layer.row)) // 数量量少的直接使用这个转
      } else {

      }
      getAllMenuList().then(res => {
        menuList.value = res.data
      })
    }
    return {
      form,
      rules,
      layerDom,
      ruleForm,
      selectedParentCode,
      menuList,
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
    // 编辑提交事件， password 不要赋值
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