
<template>
  <div class="layout-container">
    <el-form :model="form" :rules="rules" ref="ruleForm" label-width="120px" style="margin-right:30px;">
            <el-form-item label="景点名称：" prop="name">
        <el-input v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="门票价格：" prop="price">
        <el-input-number v-model="form.price" :min="1" :max="99999" @change="handleChange" />
      </el-form-item>
      <el-form-item label="库存：" prop="inventory">
        <el-input-number v-model="form.inventory" :min="0" :max="999999" />
      </el-form-item>
      <el-form-item label="分类：" prop="library">
        <el-input v-model="form.library" placeholder="请输入分类"></el-input>
      </el-form-item>
      <el-form-item label="景点地址：" prop="address">
        <el-input v-model="form.address" placeholder="请输入景点地址"></el-input>
      </el-form-item>
      <el-form-item label="简单推荐：" prop="shortRecommend">
        <el-input v-model="form.shortRecommend" placeholder="请输入简单推荐"></el-input>
      </el-form-item>
      <el-form-item label="景点封面图：" prop="coverUrl">
        <el-upload
          class="upload-demo"
          drag
          :action="uploadPath"
          multiple="false"
          :on-success="handleUploadSuccessCover"
          :show-file-list="false"
        >
          <img v-if="form.coverUrl" :src="form.coverUrl" class="avatar" style="widht:148px; height:148px" />
          <span v-else>
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              拖动图片或 <em>点击上传</em>
            </div>
          </span>
        </el-upload>
      </el-form-item>
      <el-form-item label="景点导航图：" prop="url">
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
      
      <el-form-item label="景点介绍：" prop="introduce">
        <vue3-tinymce v-model="form.introduce" :setting="state.setting" script-src="/tinymce/tinymce.min.js" />
      </el-form-item>
      <div>
        <el-button type="primary" @click="addForm">确认</el-button>
        <el-button @click="close">返回</el-button>
      </div>
    </el-form>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus, Search, Delete } from '@element-plus/icons'
import { handleTime } from '@/utils/system/util'
import { useRouter, useRoute } from 'vue-router'
import { add } from '@/api/back/scenicSpot'
import Vue3Tinymce from '@jsdawn/vue3-tinymce'
import { useStore } from 'vuex'
import { UploadFilled } from '@element-plus/icons-vue'

const baseURL = import.meta.env.VITE_BASE_URL

export default defineComponent({
  name: 'crudTable',
  components: {
    Vue3Tinymce,
    UploadFilled
  },
  setup() {
    const router = useRouter()
    const store = useStore()
    const route = useRoute()
    const ruleForm = ref(null)
    let form = ref({
      id: '',
      name: '',
      url: '',
      address: '',
      introduce: '',
      price: '',
      coverUrl: '',
      shortRecommend: '',
      inventory: '',
      library: '',
    })
    const rules = {
      id: [{ required: true, message: '请输入主键', trigger: 'blur' }],
      name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
      url: [{ required: true, message: '请输入景点导航图', trigger: 'blur' }],
      coverUrl: [{ required: true, message: '请上传景点封面图', trigger: 'blur' }],
      address: [{ required: true, message: '请输入景点地址', trigger: 'blur' }],
      introduce: [{ required: true, message: '请输入景点介绍', trigger: 'blur' }],
      price: [{ required: true, message: '请输入门票价格', trigger: 'blur' }],
      shortRecommend: [{ required: true, message: '请输入简单推荐', trigger: 'blur' }],
      inventory: [{ required: true, message: '请输入库存', trigger: 'blur' }],
      library: [{ required: true, message: '请输入分类', trigger: 'blur' }],
    }
    const provinceOptions = ref([])
    const classificationOptions = ref([])
    const sujectOptions = ref([])
    const yearOptions = ref([])
    let uploadPath = baseURL + '/upload'
    init()
    function init() { // 用于判断新增还是编辑功能
      
    }
    const close = () => {
      router.push({path:'/scenicSpot'});
    }
    const addForm = () => {
      if (ruleForm) {
        ruleForm.value.validate((valid) => {
          if (valid) {
            add(form.value)
            .then(res => {
              ElMessage({
                type: 'success',
                message: '添加成功'
              })
              router.push({path:'/scenicSpot'});
            })
          } else {
            return false;
          }
        });
      }
    }
    const state = reactive({
        // editor 配置项
        setting: {
            language: 'zh-Hans',
            height: 400, // editor 高度

            toolbar: 'undo redo | fullscreen | blocks alignleft aligncenter alignright alignjustify | link unlink | numlist bullist | image media table | fontsize forecolor backcolor | bold italic underline strikethrough |',
            toolbar_mode: 'sliding',
            quickbars_selection_toolbar:
                'removeformat | bold italic underline strikethrough | fontsize forecolor backcolor',
            plugins: 'link image media table lists fullscreen quickbars',
            font_size_formats: '12px 14px 16px 18px',
            link_default_target: '_blank',
            link_title: false,
            nonbreaking_force_tab: true,

            // 开启组件拓展的 上传图片功能，工具栏 图片按钮 弹框中出现 `upload` 选项
            custom_images_upload: true,
            // 复用 图片上传 api 地址
            images_upload_url:  baseURL + '/upload',
            // 上传成功回调函数，return 图片地址。默认 response.location
            custom_images_upload_callback: response => response.data,
            // 上传 api 请求头
            // custom_images_upload_header: { 'X-Token': 'xxxx' },
            // 上传 api 额外的参数。图片默认 file
            custom_images_upload_param: { }

        },
    });
    const handleUploadSuccess = (res, file) => {
      form.value.url = res.data
    }
    const handleUploadSuccessCover = (res, file) => {
      form.value.coverUrl = res.data
    }
    return {
      Plus,
      Search,
      Delete,
      close,
      handleTime,
      form,
      rules,
      state,
      provinceOptions,
      classificationOptions,
      sujectOptions,
      yearOptions,
      addForm,
      ruleForm,
      uploadPath,
      handleUploadSuccess,
      handleUploadSuccessCover
    }
  }
})
</script>

<style lang="scss" scoped>
.layout-container {
  padding-top: 40px;
  padding-left: 20px;
}
</style>