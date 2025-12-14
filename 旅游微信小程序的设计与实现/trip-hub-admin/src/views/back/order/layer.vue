<template>
  <Layer :layer="layer" @confirm="submit" ref="layerDom">
    <el-form :model="form" :rules="rules" ref="ruleForm" label-width="120px" style="margin-right:30px;">
            <el-form-item label="订单类型，1 景点、2 美食、3 酒店：" prop="type">
        <el-input v-model="form.type" placeholder="请输入订单类型，1 景点、2 美食、3 酒店"></el-input>
      </el-form-item>
      <el-form-item label="用户：" prop="userId">
        <el-input v-model="form.userId" placeholder="请输入用户"></el-input>
      </el-form-item>
      <el-form-item label="状态：1 待发货、2 待收货、3 已收货、4 退货中、5 已退货：" prop="status">
        <el-input v-model="form.status" placeholder="请输入状态：1 待发货、2 待收货、3 已收货、4 退货中、5 已退货"></el-input>
      </el-form-item>
      <el-form-item label="关联id：" prop="relationId">
        <el-input v-model="form.relationId" placeholder="请输入关联id"></el-input>
      </el-form-item>
    </el-form>
  </Layer>
</template>

<script lang="ts">
import type { LayerType } from '@/components/layer/index.vue'
import type { ElFormItemContext } from 'element-plus/lib/el-form/src/token'
import { defineComponent, ref, Ref, reactive } from 'vue'
import { add, update } from '@/api/back/order'
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
      type: '',
      userId: '',
      status: '',
      relationId: '',
    })
    const rules = {
      id: [{ required: true, message: '请输入主键', trigger: 'blur' }],
      type: [{ required: true, message: '请输入订单类型，1 景点、2 美食、3 酒店', trigger: 'blur' }],
      userId: [{ required: true, message: '请输入用户', trigger: 'blur' }],
      status: [{ required: true, message: '请输入状态：1 待发货、2 待收货、3 已收货、4 退货中、5 已退货', trigger: 'blur' }],
      relationId: [{ required: true, message: '请输入关联id', trigger: 'blur' }],
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