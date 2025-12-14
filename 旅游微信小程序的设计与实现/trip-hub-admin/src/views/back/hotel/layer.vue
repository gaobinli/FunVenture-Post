<template>
  <Layer :layer="layer" @confirm="submit" ref="layerDom">
    <el-form :model="form" :rules="rules" ref="ruleForm" label-width="120px" style="margin-right:30px;">
            <el-form-item label="封面：" prop="url">
        <el-input v-model="form.url" placeholder="请输入封面"></el-input>
      </el-form-item>
      <el-form-item label="名称：" prop="name">
        <el-input v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="价格：" prop="price">
        <el-input v-model="form.price" placeholder="请输入价格"></el-input>
      </el-form-item>
      <el-form-item label="详情内容：" prop="content">
        <el-input v-model="form.content" placeholder="请输入详情内容"></el-input>
      </el-form-item>
    </el-form>
  </Layer>
</template>

<script lang="ts">
import type { LayerType } from '@/components/layer/index.vue'
import type { ElFormItemContext } from 'element-plus/lib/el-form/src/token'
import { defineComponent, ref, Ref, reactive } from 'vue'
import { add, update } from '@/api/back/system/role'
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
      url: '',
      name: '',
      price: '',
      content: '',
    })
    const rules = {
      id: [{ required: true, message: '请输入主键', trigger: 'blur' }],
      url: [{ required: true, message: '请输入封面', trigger: 'blur' }],
      name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
      price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
      content: [{ required: true, message: '请输入详情内容', trigger: 'blur' }],
    }
    init()
    function init() { // 用于判断新增还是编辑功能
      if (props.layer.row) {
        form.value = JSON.parse(JSON.stringify(props.layer.row)) // 数量量少的直接使用这个转
      } else {

      }
    }
    return {
      form,
      rules,
      layerDom,
      ruleForm,
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