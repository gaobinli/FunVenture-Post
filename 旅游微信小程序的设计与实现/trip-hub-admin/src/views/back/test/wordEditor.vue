<template>
  <vue3-tinymce v-model="a" :setting="state.setting" />
</template>

<script>
import { defineComponent, ref, reactive } from 'vue'
import Vue3Tinymce from '@jsdawn/vue3-tinymce';
const baseURL = import.meta.env.VITE_BASE_URL

export default defineComponent({
  name: 'crudTable',
  components: {
    Vue3Tinymce,
  },
  setup() {
    const a = ref('')
    const state = reactive({
        // editor 配置项
        setting: {
            language: 'zh-Hans',
            language_url: '/src/components/tinymce/langs/zh-Hans.js',
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
    return {
      a,
      state
    }
  }
})
</script>

<style lang="scss" scoped>
  
</style>