<template>
  <div class="container">
    <div class="box">
      <div class="login-content-left">
        <img :src="loginLeftPng"/>
        <div class="login-content-left-mask">
          <div>{{ $t(systemTitle) }}</div>
          <div>{{ $t(systemSubTitle) }}</div>
        </div>
      </div>

      <div class="box-inner">
        <h1>{{ $t('message.system.welcome') }}</h1>
        <el-form class="form">
          <el-input
              size="large"
              v-model="form.name"
              :placeholder="$t('message.system.userName')"
              type="text"
              maxlength="50"
          >
            <template #prepend>
              <i class="sfont system-xingmingyonghumingnicheng"></i>
            </template>
          </el-input>

          <el-input
              size="large"
              ref="password"
              v-model="form.password"
              :type="passwordType"
              :placeholder="$t('message.system.password')"
              name="password"
              maxlength="50"
          >
            <template #prepend>
              <i class="sfont system-mima"></i>
            </template>
            <template #append>
              <i class="sfont password-icon" :class="passwordType ? 'system-yanjing-guan': 'system-yanjing'"
                 @click="passwordTypeChange"></i>
            </template>
          </el-input>

  
          <div class="verification-code">
            <el-input
                size="large"
                ref="code"
                v-model="form.code"
                :type="code"
                :placeholder="$t('message.system.verificationCode')"
                name="code"
                maxlength="30"
            >
              <template #prepend>
                <!-- <i class="sfont system-mima"></i> -->
                <el-icon><CircleCheck /></el-icon>
              </template>
            </el-input>
            <img :src="codeUrl" @click="changeVerificationCode" class="login-code-img"/>            
          </div>
          

          <el-button type="primary" :loading="form.loading" @click="submit" style="width: 100%;" size="medium">
            {{ $t('message.system.login') }}
          </el-button>
        </el-form>
        <div class="fixed-top-right">
          <select-lang/>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { systemTitle, systemSubTitle } from '@/config'
import { defineComponent, ref, reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import type { RouteLocationRaw  } from 'vue-router'
import { getAuthRoutes } from '@/router/permission'
import { ElMessage } from 'element-plus'
import selectLang from '@/layout/Header/functionList/word.vue'
import loginLeftPng from '@/assets/login/left.jpg';
import { log } from 'console'
import { verificationCodeApi } from '@/api/back/system/user'
import { CircleCheck } from '@element-plus/icons-vue'


export default defineComponent({
  components: {
    selectLang,
    CircleCheck
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const form = reactive({
      name: 'admin',
      password: '123456',
      code: '1234',
      uuid: '',
      loading: false
    })
    
    onMounted(() => {
        changeVerificationCode()
    })


    const passwordType = ref('password')
    let codeUrl = ref('')
    const passwordTypeChange = () => {
      passwordType.value === '' ? passwordType.value = 'password' : passwordType.value = ''
    }
    const checkForm = () => {
      return new Promise((resolve, reject) => {
        if (form.name === '') {
          ElMessage.warning({
            message: '用户名不能为空',
            type: 'warning'
          });
          return;
        }
        if (form.password === '') {
          ElMessage.warning({
            message: '密码不能为空',
            type: 'warning'
          })
          return;
        }
        if (form.code === '') {
          ElMessage.warning({
            message: '验证码不能为空',
            type: 'warning'
          })
          return;
        }
        resolve(true)
      })
    }
    const submit = () => {
      checkForm()
      .then(() => {
        form.loading = true
        let params = {
          username: form.name,
          password: form.password,
          code: form.code,
          uuid: form.uuid
        }
        store.dispatch('user/login', params)
        .then(async (res) => {
          ElMessage.success({
            message: '登录成功',
            type: 'success',
            showClose: true,
            duration: 1000
          })
          setTimeout(() => {
            location.reload()
            getAuthRoutes()
            router.push(route.query.redirect as RouteLocationRaw || '/')
          }, 500)
          
        }).finally(() => {
          form.loading = false
        })
      })
    }
    const changeVerificationCode = () => {
      verificationCodeApi()
      .then(res => {
        codeUrl.value = "data:image/gif;base64," + res.data.img
        form.uuid = res.data.uuid
      })
    }
    return {
      loginLeftPng,
      systemTitle,
      systemSubTitle,
      form,
      passwordType,
      codeUrl,
      passwordTypeChange,
      submit,
      changeVerificationCode
    }
  }
})
</script>

<style lang="scss" scoped>
.verification-code {
  float: absolute;
  .el-input {
    float: left;
    width: 68%; 
  }
  .login-code-img {
    float: left;
    width: 30%; 
    margin-left: 2%;
  }
}


.container {
  position: relative;
  width: 100vw;
  height: 100vh;
  background: #fff url('@/assets/login/login-background.jpg') no-repeat center;
  overflow: hidden;
  background-size: cover;
  cursor: pointer;
  user-select: none;

  .box {
    width: 1160px;
    display: flex;
    position: absolute;
    left: 50%;
    top: 50%;
    background: white;
    border-radius: 8px;
    transform: translate(-50%, -50%);
    height: 440px;
    overflow: hidden;
    box-shadow: 0 6px 20px 5px rgba(152, 152, 152, 0.1),
    0 16px 24px 2px rgba(117, 117, 117, 0.14);

    .login-content-left {
      position: relative;

      img {
        height: 440px;
      }

      .login-content-left-mask {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-image: linear-gradient(rgba(0,204,222,0.8), rgba(51,132,224,0.8));
        text-align: center;
        color: #fff;
        font-size: 1.8rem;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        letter-spacing: 2px;

        div:nth-child(1) {
          font-size: 3.5rem;
          margin-bottom: 1em;
        }
      }
    }

    .box-inner {
      width: 500px;

      h1 {
        margin-top: 80px;
        text-align: center;
      }

      .form {
        width: 80%;
        margin: 50px auto 15px;

        .el-input {
          margin-bottom: 20px; 
        }

        .password-icon {
          cursor: pointer;
          color: #409eff;
        }
      }

      .fixed-top-right {
        position: absolute;
        top: 10px;
        right: 10px;
      }
    }
  }
}

@media screen and (max-width: 1260px) {
  .login-content-left {
    display: none;
  }
  .box {
    width: 500px !important;
  }
}

@media screen and (max-width: 750px) {
  .container .box, .container .box-inner {
    width: 100vw !important;
    height: 100vh;
    box-shadow: none;
    left: 0;
    top: 0;
    transform: none;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    h1 {
      margin-top: 0;
    }

    .form {
    }
  }
}
</style>
