<template>
  <Layer :layer="layer" @confirm="submit" ref="layerDom">
    <el-form :model="form" :rules="rules" ref="ruleForm" label-width="120px" style="margin-right:30px;">
      <el-form-item label="昵称：" prop="nickName">
        <el-input v-model="form.nickName" placeholder="请输入昵称"></el-input>
      </el-form-item>
      <el-form-item label="手机号：" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="subtitle">
        <el-input v-model="form.password" placeholder="请输入密码，默认与账号一样"></el-input>
      </el-form-item>
      <el-form-item label="性别：">
        <el-radio-group v-model="form.sex">
            <el-radio :label=1>男</el-radio>
            <el-radio :label=2>女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="头像：" prop="url">
        <el-upload
          class="upload-demo"
          drag
          :action="uploadPath"
          multiple="false"
          :on-success="handleUploadSuccess"
          :show-file-list="false"
        >
          <img v-if="form.avatar" :src="form.avatar" class="avatar" style="widht:148px; height:148px" />
          <span v-else>
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              拖动图片或 <em>点击上传</em>
            </div>
          </span>
        </el-upload>
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
import { add, update } from '@/api/back/user'
import { getRoleMenuList, getAllMenuList } from '@/api/back/system/menu'
import Layer from '@/components/layer/index.vue'
import { UploadFilled } from '@element-plus/icons-vue'

const baseURL = import.meta.env.VITE_BASE_URL

export default defineComponent({
  components: {
    Layer,
    UploadFilled
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
      nickName: '',
      phone: '',
      password: '',
      sex: '',
      avatar: ''
    })
    let menuOptions = ref([])
    const rules = {
      nickName: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
      phone: [
        { required: true, message: '请输入手机号', trigger: 'blur' },
        { pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: '手机号格式不正确', trigger: 'blur'}
      ]
    }
    init()
    function init() { // 用于判断新增还是编辑功能
      if (props.layer.row) {
        form.value = JSON.parse(JSON.stringify(props.layer.row)) // 数量量少的直接使用这个转
      } else {

      }
    }
    let uploadPath = baseURL + '/upload'
    const handleUploadSuccess = (res, file) => {
      form.value.avatar = res.data
    }
    return {
      form,
      rules,
      layerDom,
      ruleForm,
      menuOptions,
      handleUploadSuccess,
      uploadPath
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