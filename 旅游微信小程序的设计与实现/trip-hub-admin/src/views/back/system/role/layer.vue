<template>
  <Layer :layer="layer" @confirm="submit" ref="layerDom">
    <el-form :model="form" :rules="rules" ref="ruleForm" label-width="120px" style="margin-right:30px;">
      <el-form-item label="角色编码：" prop="code">
        <el-input v-model="form.code" :disabled="form.id === '' ? false : true" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="角色名称：" prop="name">
        <el-input v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="菜单：">
        <el-select v-model="form.menuCodes" multiple placeholder="选择菜单，非必选">
          <el-option
            v-for="item in menuOptions"
            :key="item.code"
            :label="item.name"
            :value="item.code"
            >
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
  </Layer>
</template>

<script lang="ts">
import type { LayerType } from '@/components/layer/index.vue'
import type { Ref } from 'vue'
import type { ElFormItemContext } from 'element-plus/lib/el-form/src/token'
import { defineComponent, ref } from 'vue'
import { add, update } from '@/api/back/system/role'
import { getRoleMenuList, getAllMenuList } from '@/api/back/system/menu'
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
      menuCodes: []
    })
    let menuOptions = ref([])
    const rules = {
      code: [{ required: true, message: '请输入角色编码', trigger: 'blur' }],
      name: [{ required: true, message: '请输入角色姓名', trigger: 'blur' }],
    }
    init()
    function init() { // 用于判断新增还是编辑功能
      if (props.layer.row) {
        form.value = JSON.parse(JSON.stringify(props.layer.row)) // 数量量少的直接使用这个转
        getRoleMenuList(form.value.code)
        .then(res => {
          res.data.forEach((v) => {
              form.value.menuCodes.push(v.code)
          })
        })
      } else {

      }
      getAllMenuList()
      .then(res => {
        menuOptions.value = res.data
      })
    }
    return {
      form,
      rules,
      layerDom,
      ruleForm,
      menuOptions,
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