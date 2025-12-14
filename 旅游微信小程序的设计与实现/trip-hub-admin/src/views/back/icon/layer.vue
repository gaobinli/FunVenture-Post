<template>
  <Layer :layer="layer" @confirm="submit" ref="layerDom">
    <el-form :model="form" :rules="rules" ref="ruleForm" label-width="120px" style="margin-right:30px;">
            <el-form-item label="名称：" prop="name">
        <el-input v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="icon图标：" prop="url">
        <el-upload
          class="upload-demo"
          drag
          :action="uploadPath"
          multiple="false"
          :on-success="handleUploadSuccess"
          :show-file-list="false"
        >
          <img v-if="form.url" :src="form.url" class="avatar" style="widht:148px; height:148px" />
          <span v-else>
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              拖动图片或 <em>点击上传</em>
            </div>
          </span>
        </el-upload>
      </el-form-item>
      <el-form-item label="排序：" prop="sort">
        <el-input-number v-model="form.sort" :min="1" :max="99999" @change="handleChange" />
      </el-form-item>
      <el-form-item label="关联跳转id：" prop="relationId">
        <el-select v-model="form.relationId" filterable clearable placeholder="请选择景点">
          <el-option
            v-for="item in scenicSpotOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
            >
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
  </Layer>
</template>

<script lang="ts">
import type { LayerType } from '@/components/layer/index.vue'
import type { ElFormItemContext } from 'element-plus/lib/el-form/src/token'
import { defineComponent, ref, Ref, reactive } from 'vue'
import { add, update } from '@/api/back/icon'
import Layer from '@/components/layer/index.vue'
import { scenicSpotList } from '@/api/back/scenicSpot'
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
      name: '',
      url: '',
      sort: '',
      relationId: '',
    })
    const rules = {
      id: [{ required: true, message: '请输入主键', trigger: 'blur' }],
      name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
      url: [{ required: true, message: '请输入icon图标', trigger: 'blur' }],
      sort: [{ required: true, message: '请输入排序', trigger: 'blur' }],
      relationId: [{ required: true, message: '请输入关联跳转id', trigger: 'blur' }],
    }
    const scenicSpotOptions = ref([])
    let uploadPath = baseURL + '/upload'
    init()
    function init() { // 用于判断新增还是编辑功能
      if (props.layer.row) {
        form.value = JSON.parse(JSON.stringify(props.layer.row)) // 数量量少的直接使用这个转
      }
      scenicSpotList()
      .then(res => {
        scenicSpotOptions.value = res.data
      })
    }
    const handleUploadSuccess = (res, file) => {
      form.value.url = res.data
    }
    return {
      form,
      rules,
      layerDom,
      ruleForm,
      uploadPath,
      UploadFilled,
      handleUploadSuccess,
      scenicSpotOptions,
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