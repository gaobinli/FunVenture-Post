<template>
  <Layer :layer="layer" @confirm="submit" ref="layerDom">
    <el-form :model="form" :rules="rules" ref="ruleForm" label-width="120px" style="margin-right:30px;">
      <el-form-item label="账号：" prop="code">
        <el-input v-model="form.code" :disabled="form.id === '' ? false : true" placeholder="请输入账号" ></el-input>
      </el-form-item>
      <el-form-item label="原密码：" prop="old">
        <el-input v-model="form.old" placeholder="请输入原密码" show-password></el-input>
      </el-form-item>
			<el-form-item label="新密码：" prop="new">
			  <el-input v-model="form.new" placeholder="请输入新密码" show-password></el-input>
			</el-form-item>
    </el-form>
  </Layer>
</template>

<script lang="ts">
import type { LayerType } from '@/components/layer/index.vue'
import type { Ref } from 'vue'
import type { ElFormItemContext } from 'element-plus/lib/el-form/src/token'
import { defineComponent, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useStore } from 'vuex'
import { patchPassword } from '@/api/back/system/user'
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
    const store = useStore()
    
    let form = ref({
      id: '',
      code: '',
      old: '',
      new: '',
    })
    const rules = {
      code: [{ required: true, message: '请输入账号', trigger: 'blur' }],
      old: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
      new: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
    }

    init()
    function init() { 
      form.value.id = store.state.user.info.id
      form.value.code = store.state.user.info.code
    }

    function submit() {
      if (ruleForm.value) {
        ruleForm.value.validate((valid) => {
          if (valid) {
            let params = {
              id: form.value.id,
              password: form.value.old,
              newPassword: form.value.new,
            }
            patchPassword(params.id, params)
            .then(res => {
              ElMessage({
                type: 'success',
                message: '密码修改成功，即将跳转到登录页面'
              })
              layerDom.value && layerDom.value.close()
              setTimeout(() => {
                store.dispatch('user/loginOut')
              }, 2000)
            })
          } else {
            return false;
          }
        });
      }
    }
    return {
      form,
      rules,
      layerDom,
      ruleForm,
      submit
    }
  }
})
</script>

<style lang="scss" scoped>
  
</style>